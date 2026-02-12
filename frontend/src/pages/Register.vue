<template>
  <AuthLayout title="注册" subtitle="创建账号，开始你的 TriAura 系统" single>
    <form class="form" @submit.prevent="onSubmit">
      <label class="field">
        <span class="label">手机号 / 邮箱</span>
        <input v-model.trim="target" class="input" placeholder="建议手机号更快" />
      </label>

      <div class="field">
        <span class="label">验证码</span>
        <div class="codeRow">
          <input v-model.trim="code" class="input" placeholder="6 位验证码" />
          <button type="button" class="ghost" :disabled="cooldown > 0" @click="sendCode">
            {{ cooldown > 0 ? `${cooldown}s` : "获取验证码" }}
          </button>
        </div>
        <div class="hint">本地联调：验证码会出现在后端日志里（local）</div>
      </div>

      <label class="field">
  <span class="label">设置密码</span>
  <input
    v-model="password"
    class="input"
    type="password"
    placeholder="至少 8 位，建议包含大小写/数字/符号"
    @input="pwdTouched = true"
  />

  <!-- ✅ 固定占位：不抖动 -->
  <div class="hintSlot">
    <transition name="fadeUp">
      <div v-if="pwdTouched && password" class="pwdMeter">
        <div class="bars">
          <span class="bar" :class="{ on: pwdScore >= 1 }"></span>
          <span class="bar" :class="{ on: pwdScore >= 2 }"></span>
          <span class="bar" :class="{ on: pwdScore >= 3 }"></span>
          <span class="bar" :class="{ on: pwdScore >= 4 }"></span>
        </div>
        <span class="lvl" :data-sev="pwdLevel.severity">强度：{{ pwdLevel.text }}</span>
      </div>
    </transition>
  </div>
</label>

<label class="field">
  <span class="label">确认密码</span>
  <input
    v-model="password2"
    class="input"
    type="password"
    placeholder="再输入一次"
    @input="pwd2Touched = true"
  />

  <!-- ✅ 固定占位：不抖动 -->
  <div class="hintSlot">
    <transition name="fadeUp">
      <div v-if="pwdSameTip" class="pwdTip" :class="{ bad: !pwdSame }">
        {{ pwdSameTip }}
      </div>
    </transition>
  </div>

</label>

<button class="btn" :disabled="loading || !canSubmit">
  {{ loading ? "注册中..." : "注册并登录" }}
</button>
      <div class="muted">
        已有账号？
        <a class="link" href="javascript:void(0)" @click="goLogin">去登录</a>
      </div>
    </form>
  </AuthLayout>
</template>

<script setup lang="ts">
import { ref, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import AuthLayout from "@/components/AuthLayout.vue";
import { post } from "@/api/http";
import { computed } from "vue";

const router = useRouter();

const target = ref("");
const code = ref("");
const password = ref("");
const password2 = ref("");
const pwdTouched = ref(false);    // 是否开始输入过密码
const pwd2Touched = ref(false);   // 是否开始输入过确认密码

const loading = ref(false);
const cooldown = ref(0);
let timer: any = null;

function startCooldown() {
  cooldown.value = 60;
  timer = setInterval(() => {
    cooldown.value--;
    if (cooldown.value <= 0) {
      clearInterval(timer);
      timer = null;
    }
  }, 1000);
}

onBeforeUnmount(() => timer && clearInterval(timer));

function detectChannel(t: string): "EMAIL" | "PHONE" {
  return t.includes("@") ? "EMAIL" : "PHONE";
}

async function sendCode() {
  const t = target.value.trim();

  if (!t) {
    alert("请输入手机号或邮箱");
    return;
  }

  // 可选：简单校验，避免明显错误
  const isEmail = t.includes("@");
  if (isEmail && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(t)) {
    alert("邮箱格式不正确");
    return;
  }
  if (!isEmail && !/^\+?\d{6,20}$/.test(t)) {
    alert("手机号格式不正确（建议带国家码，如 +86）");
    return;
  }

  // ✅ 防止重复点
  if (cooldown.value > 0) return;

  try {
    // 你后端需要：channel / scene / target
    await post("/api/v1/auth/send-code", {
      channel: detectChannel(t),
      scene: "REGISTER",
      target: t,
    });

    // 成功才倒计时
    startCooldown();
  } catch (err: any) {
    // 你的拦截器会 reject(body)，所以 err.message 一般就有后端 message
    const msg = err?.message || "发送失败，请稍后重试";
    alert(msg);
  }
}
// 强度评分：0-4
function scorePassword(p: string) {
  let score = 0;
  if (!p) return 0;

  if (p.length >= 8) score++;
  if (p.length >= 12) score++;
  if (/[a-z]/.test(p) && /[A-Z]/.test(p)) score++;
  if (/\d/.test(p)) score++;
  if (/[^A-Za-z0-9]/.test(p)) score++;

  // 上面最多 5 分，我们压成 0~4 更直观
  return Math.min(4, score);
}
const pwdScore = computed(() => scorePassword(password.value));

const pwdLevel = computed(() => {
  const s = pwdScore.value;
  if (s <= 1) return { text: "弱", severity: "danger" as const };
  if (s === 2) return { text: "中", severity: "warn" as const };
  return { text: "强", severity: "success" as const };
});

// 实时校验：两次密码一致
const pwdSame = computed(() => {
  if (!password.value || !password2.value) return true; // 没填完先不报错
  return password.value === password2.value;
});

// 提示文案
const pwdSameTip = computed(() => {
  if (!pwd2Touched.value) return "";
  if (!password2.value) return "";
  return pwdSame.value ? "两次密码一致" : "两次密码不一致";
});

// 是否允许提交：
// - 两次一致
// - 强度 >= 中（你想更严格就改成 >=3）
const canSubmit = computed(() => {
  return (
    target.value.trim().length > 0 &&
    code.value.trim().length > 0 &&
    password.value.length > 0 &&
    password2.value.length > 0 &&
    pwdSame.value &&
    pwdScore.value >= 2
  );
});
async function onSubmit() {
  pwdTouched.value = true;
  pwd2Touched.value = true;

  if (!pwdSame.value) {
    alert("两次密码不一致");
    return;
  }
  if (pwdScore.value < 2) {
    alert("密码强度太弱：至少 8 位，并包含数字/大小写/特殊符号中的两类");
    return;
  }

  loading.value = true;
  try {
    // TODO：接入后端 register
    await new Promise((r) => setTimeout(r, 450));
    router.push("/");
  } finally {
    loading.value = false;
  }
}

function goLogin() {
  router.push("/login");
}
</script>

<style scoped>
.form{ display:grid; gap: 14px; }
.field{ display:grid; gap: 6px; }  
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

.codeRow{ display:grid; grid-template-columns: 1fr auto; gap: 10px; align-items:center; }
.ghost{
  padding: 10px 12px;
  border-radius: 12px;
  border: 1px solid #e6e8f0;
  background: #fff;
  color:#0f172a;
  cursor:pointer;
}
.ghost:hover{ background:#f8fafc; }
.ghost:disabled{ opacity:.6; cursor:not-allowed; }

.hint{ font-size: 12px; color:#64748b; margin-top: 2px; }

.btn{
  width: 100%;
  padding: 12px 14px;
  border-radius: 12px;
  border: 1px solid #0f172a;
  background: #0f172a;
  color: #fff;
  font-weight: 650;
  cursor: pointer;
}
.btn:disabled{ opacity: .6; cursor:not-allowed; }

.link{ color:#0f172a; text-decoration:none; font-size:12px; }
.link:hover{ text-decoration: underline; }

.muted{ text-align:center; font-size:12px; color:#64748b; margin-top: 2px; }
.pwdMeter{
  display:flex;
  align-items:center;
  justify-content:space-between;
  gap:10px;
  margin-top: 6px;
}

.bars{ display:flex; gap:6px; }
.bar{
  width: 28px;
  height: 6px;
  border-radius: 999px;
  background: #e2e8f0;
  transition: .15s;
}
.bar.on{ background: #0f172a; }

.lvl{
  font-size:12px;
  color:#64748b;
}
.lvl[data-sev="danger"]{ color:#ef4444; }
.lvl[data-sev="warn"]{ color:#f59e0b; }
.lvl[data-sev="success"]{ color:#16a34a; }

.pwdTip{
  margin-top: 6px;
  font-size: 12px;
  color: #16a34a;
}
.pwdTip.bad{
  color: #ef4444;
}
/* 提示占位变更小，且不要再额外加 margin */
.hintSlot{
  min-height: 16px;     /* ✅更紧凑：16~18 都行 */
  line-height: 16px;
  margin-top:6px;
}

/* ✅ 轻微动画：出现不突兀 */
.fadeUp-enter-active, .fadeUp-leave-active{
  transition: opacity .15s ease, transform .15s ease;
}
.fadeUp-enter-from, .fadeUp-leave-to{
  opacity: 0;
  transform: translateY(-4px);
}
</style>