import { ref, onBeforeUnmount } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useAuth } from "@/auth/useAuth";
import { post } from "@/api/http";
import { toast } from 'vue-sonner'


type Mode = "pwd" | "code";
export function useLoginPage() {
  const router = useRouter();
  const route = useRoute();
  const { loginByCode } = useAuth();

  const account = ref("");
  const password = ref("");
  const remember = ref(true);
  const showPwd = ref(false);
  const loading = ref(false);

  const mode = ref<Mode>("pwd");
  const code = ref("");
  const sending = ref(false);
  const leftSec = ref(0);

  let timer: number | null = null;


  function startCountdown(sec = 60) {
    leftSec.value = sec;
    if (timer) window.clearInterval(timer);
    timer = window.setInterval(() => {
      leftSec.value--;
      if (leftSec.value <= 0 && timer) {
        window.clearInterval(timer);
        timer = null;
      }
    }, 1000);
  }

  onBeforeUnmount(() => {
    if (timer) window.clearInterval(timer);
  });


  function togglePwd() {
    showPwd.value = !showPwd.value;
  }

  async function sendCode() {
    if (!account.value) {
      toast.error("请先输入手机号或邮箱");
      return;
    }
    if (sending.value || leftSec.value > 0) return;

    sending.value = true;
    try {
      const channel = account.value.includes("@") ? "EMAIL" : "PHONE";
      await post("/api/v1/auth/send-code", {
        channel,
        scene: "LOGIN",
        target: account.value,
      });

      toast.success("验证码已发送，请查收");
      startCountdown(60);
    } catch (err: any) {
      const payload = err?.response?.data || err?.data || null;
      const msg = payload?.message || err?.message || "发送失败";
      toast.error("发送失败", { description: msg });
    } finally {
      sending.value = false;
    }
  }

  async function onSubmit() {
    loading.value = true;
    try {
      if (!account.value) {
        toast.error("请输入手机号或邮箱");
        return;
      }

      if (mode.value === "pwd") {
        if (!password.value) {
          toast.error("请输入密码");
          return;
        }

        await post("/api/v1/auth/login/password", {
          account: account.value,
          password: password.value,
        });

        toast.success("登录成功");
      } else {
        if (!code.value.trim()) {
          toast.error("登录请输入验证码失败");
          return;
        }

        await loginByCode(
          account.value.includes("@") ? "EMAIL" : "PHONE",
          account.value,
          code.value.trim(),
        );

        toast.success("登录成功");
      }

      const redirect = (route.query.redirect as string) || "/";
      router.push(redirect);
    } catch (err: any) {
      const payload = err?.response?.data || err?.data || null;
      const msg = payload?.message || err?.message || "登录失败";
      toast.error(msg);
    } finally {
      loading.value = false;
    }
  }
  function setMode(m: "pwd" | "code") {
    if (mode.value === m) return;
    mode.value = m;
    // 你之前 toggleMode 里清理的逻辑，也可以保留在这里
    password.value = "";
    code.value = "";
    showPwd.value = false;
  }
  return {
    account,
    password,
    remember,
    showPwd,
    loading,
    mode,
    code,
    sending,
    leftSec,
    togglePwd,
    sendCode,
    onSubmit,
    setMode,
  };
}
