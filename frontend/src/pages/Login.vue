<template>
  <AuthLayout title="登录" subtitle="欢迎回来，继续你的 TriAura 之旅" single>
    <form class="form" @submit.prevent="onSubmit">
      <label class="field">
        <span class="label">账号</span>
        <input
          v-model.trim="account"
          class="input"
          placeholder="手机号 / 邮箱"
          autocomplete="username"
        />
      </label>
<div class="modeRow">
  <span class="modeText">
    当前：{{ mode === "pwd" ? "密码登录" : "验证码登录" }}
  </span>
  <a class="link" href="javascript:void(0)" @click="toggleMode">
    切换到{{ mode === "pwd" ? "验证码登录" : "密码登录" }}
  </a>
</div>

<!-- 密码登录 -->
<label v-if="mode === 'pwd'" class="field">
  <span class="label">密码</span>

  <div class="pwdWrap">
    <input
      v-model="password"
      class="input pwdInput"
      :type="showPwd ? 'text' : 'password'"
      placeholder="请输入密码"
      autocomplete="current-password"
    />

    <i
      class="pi eyeInInput"
      :class="showPwd ? 'pi-eye-slash' : 'pi-eye'"
      role="button"
      tabindex="0"
      @click="togglePwd"
      @keydown.enter.prevent="togglePwd"
      @keydown.space.prevent="togglePwd"
      @mousedown.prevent
    ></i>
  </div>
</label>

<!-- 验证码登录 -->
<label v-else class="field">
  <span class="label">验证码</span>

  <div class="codeWrap">
    <input
      v-model="code"
      class="input codeInput"
      placeholder="请输入验证码"
      inputmode="numeric"
      autocomplete="one-time-code"
    />

    <Button
      severity="secondary"
      variant="outlined"
      :disabled="sending || leftSec > 0"
      @click="sendCode"
    >
      <span v-if="leftSec === 0">{{ sending ? "发送中..." : "发送验证码" }}</span>
      <span v-else>{{ leftSec }}s</span>
    </Button>
  </div>
</label>

      <div class="row">
        <label class="chk">
          <input type="checkbox" v-model="remember" />
          <span>记住我</span>
        </label>
        <a class="link" href="javascript:void(0)">忘记密码</a>
      </div>

      <Button class="loginBtn" :disabled="loading" @click="onSubmit">
        {{ loading ? "登录中..." : "登录" }}
      </Button>

      <div class="muted">
        还没有账号？
        <a class="link" href="javascript:void(0)" @click="goRegister">去注册</a>
      </div>
    </form>

    <template #footer>
      <div class="foot">
        登录即表示同意
        <a class="link" href="javascript:void(0)">用户协议</a>
        与
        <a class="link" href="javascript:void(0)">隐私政策</a>
      </div>
    </template>
  </AuthLayout>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import AuthLayout from "@/components/AuthLayout.vue";
import { useAuth } from "@/auth/useAuth";
import { post } from "@/api/http";
import { useToast } from "primevue/usetoast";

const toast = useToast();

const router = useRouter();
const { loginByCode } = useAuth();

const account = ref("");
const password = ref("");
const remember = ref(true);
const showPwd = ref(false);
const loading = ref(false);
const mode = ref<"pwd" | "code">("pwd"); // pwd=密码登录，code=验证码登录

const code = ref("");
const sending = ref(false);
const leftSec = ref(0);


let timer: number | null = null;

function toggleMode() {
  mode.value = mode.value === "pwd" ? "code" : "pwd";
  // 清一下输入，体验更干净
  password.value = "";
  code.value = "";
  showPwd.value = false;
}
const showSuccess = (summary,detail) => {
    toast.add({ severity: 'success', summary: summary, detail: detail, life: 3000 });
};
const showError = (summary,detail) => {
    toast.add({ severity: 'error', summary: summary, detail: detail, life: 3000 });
};

async function sendCode() {
  if (!account.value) {
    alert("请先输入手机号或邮箱");
    return;
  }
  if (sending.value || leftSec.value > 0) return;

  sending.value = true;
  try {
    // TODO：接你的后端发送验证码接口
    // await post("/api/v1/auth/send-code", { channel: "...", target: account.value, scene: "LOGIN" })

    // 这里先模拟成功
    await new Promise((r) => setTimeout(r, 400));

    leftSec.value = 60;
    timer && window.clearInterval(timer);
    timer = window.setInterval(() => {
      leftSec.value--;
      if (leftSec.value <= 0 && timer) {
        window.clearInterval(timer);
        timer = null;
      }
    }, 1000);
  } finally {
    sending.value = false;
  }
}


async function onSubmit() {
  loading.value = true;
  try {
    if (mode.value === "pwd") {
      const result = await post("/api/v1/auth/login/password", {
        account: account.value,
        password: password.value,
      });

      // 这里说明是 2xx 成功
      toast.add({
        severity: "success",
        summary: "登录成功",
        detail: result?.message ?? "欢迎回来",
        life: 2000,
      });

    } else {
      await loginByCode(
        account.value.includes("@") ? "EMAIL" : "PHONE",
        account.value,
        code.value.trim()
      );

      toast.add({
        severity: "success",
        summary: "登录成功",
        detail: "验证码登录成功",
        life: 2000,
      });
    }

    router.push("/");
  } catch (err: any) {

    // ✅ 关键：从 err 里拿到后端返回的 {code,message,data}
    const payload =
      err?.response?.data ||  // axios
      err?.data ||            // 你自己封装 throw 的对象
      null;

    const msg = payload?.message || err?.message || "登录失败";

    toast.add({
      severity: "error",
      summary: "登录失败",
      detail: msg,
      life: 3000,
    });
  } finally {
    loading.value = false;
  }
}

function goRegister() {
  router.push("/register");
}
function togglePwd() {
  showPwd.value = !showPwd.value;
}
</script>

<style scoped>
/* 让 PrimeVue Button 变黑：用 :deep 才能穿透到组件内部 */
:deep(.loginBtn.p-button) {
  background:rgb(0, 0, 0);
  border-color: rgb(0, 0, 0);
  transition:background 0.3s;
}

:deep(.loginBtn.p-button:hover) {
  background:rgb(58, 58, 59);
  border-color: rgb(58, 58, 59);;
}
.form{ display:grid; gap: 14px; }


.field{ display:grid; gap: 8px; }
.label{ font-size: 12px; color:#475569; }

.input{
  width: 100%;
  padding: 12px 12px;
  border-radius: 12px;
  border: 1px solid #e6e8f0;
  background: #fff;
  color: #0f172a;
  outline: none;
  transition: .15s;
}
.input::placeholder{ color:#94a3b8; }
.input:focus{
  border-color: #0f172a;
  box-shadow: 0 0 0 4px rgba(15,23,42,.08);
}

.pwdWrap{
  position: relative;
}

.pwdInput{
  padding-right: 44px; /* 给右侧小眼睛留空间 */
}

.eyeInInput{
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.25rem;
  color: rgb(200, 200, 200);
  cursor: pointer;
  transition: color 0.2s ease;
  user-select: none;
  line-height: 1;
}
.eyeInInput:hover{
  color: rgb(100, 100, 100);
}
.ghost{
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid #e6e8f0;
  background: #fff;
  color:#0f172a;
  cursor:pointer;
}
.ghost:hover{ background:#f8fafc; }

.row{ display:flex; align-items:center; justify-content:space-between; gap:10px; }
.chk{ display:inline-flex; align-items:center; gap:8px; font-size:12px; color:#475569; }
.chk input{ accent-color: #0f172a; }


.link{ color:#0f172a; text-decoration:none; font-size:12px; }
.link:hover{ text-decoration: underline; }

.muted{ text-align:center; font-size:12px; color:#64748b; margin-top: 2px; }
.foot{ display:flex; flex-wrap:wrap; gap:6px; align-items:center; }
.eye{

  color: rgb(200, 200, 200);
  cursor: pointer;
  transition: color 0.2s ease;
  user-select: none;
}
.eye:hover{ color: rgb(100, 100, 100); }
.modeRow{
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:10px;
  margin-top: -2px;
}
.modeText{
  font-size: 12px;
  color:#64748b;
}

.codeWrap{
  display:grid;
  grid-template-columns: 1fr auto;
  gap: 10px;
  align-items:center;
}
.codeInput{
  padding-right: 12px;
}
</style>
