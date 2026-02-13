import { computed, onBeforeUnmount, ref, watch } from "vue";
import { useRouter, useRoute } from "vue-router";
import { post } from "@/api/http";
import { useToast } from "primevue/usetoast";

export function useRegister() {
  const router = useRouter();
  const route = useRoute();
  const toast = useToast();

  const target = ref("");
  const code = ref("");
  const password = ref("");
  const password2 = ref("");

  const pwdTouched = ref(false);
  const pwd2Touched = ref(false);

  const loading = ref(false);
  const cooldown = ref(0);
  let timer: number | null = null;

  function showSuccess(summary: string, detail: string) {
    toast.add({ severity: "success", summary, detail, life: 2000 });
  }
  function showError(summary: string, detail: string) {
    toast.add({ severity: "error", summary, detail, life: 3000 });
  }

  onBeforeUnmount(() => {
    if (timer) window.clearInterval(timer);
  });

  function startCooldown() {
    cooldown.value = 60;
    if (timer) window.clearInterval(timer);

    timer = window.setInterval(() => {
      cooldown.value--;
      if (cooldown.value <= 0 && timer) {
        window.clearInterval(timer);
        timer = null;
      }
    }, 1000);
  }

  function detectChannel(t: string): "EMAIL" | "PHONE" {
    return t.includes("@") ? "EMAIL" : "PHONE";
  }

  async function sendCode() {
    const t = target.value.trim();
    if (!t) {
      showError("发送失败", "请输入手机号或邮箱");
      return;
    }

    if (cooldown.value > 0) return;

    try {
      await post("/api/v1/auth/send-code", {
        channel: detectChannel(t),
        scene: "REGISTER",
        target: t,
      });
      showSuccess("已发送", "验证码已发送，请查收");
      startCooldown();
    } catch (err: any) {
      const payload = err?.response?.data || err?.data || null;
      const msg = payload?.message || err?.message || "发送失败";
      showError("发送失败", msg);
    }
  }

  // 评分：0-4
  function scorePassword(p: string) {
    let s = 0;
    if (!p) return 0;
    if (p.length >= 8) s++;
    if (p.length >= 12) s++;
    if (/[a-z]/.test(p) && /[A-Z]/.test(p)) s++;
    if (/\d/.test(p)) s++;
    if (/[^A-Za-z0-9]/.test(p)) s++;
    return Math.min(4, s);
  }

  const pwdScore = computed(() => scorePassword(password.value));
  const pwdLevel = computed(() => {
    const s = pwdScore.value;
    if (s <= 1) return { text: "弱", severity: "danger" as const };
    if (s === 2) return { text: "中", severity: "warn" as const };
    return { text: "强", severity: "success" as const };
  });

  // ✅ 实时一致性：但避免“只填确认密码 -> 显示一致”的 bug
  const pwdState = computed(() => {
    if (!pwd2Touched.value && !password2.value) return { ok: false, tip: "" };
    if (!password.value || !password2.value)
      return { ok: false, tip: "请先完整输入两次密码" };
    if (password.value === password2.value)
      return { ok: true, tip: "两次密码一致" };
    return { ok: false, tip: "两次密码不一致" };
  });

  // 你要的“实时检查”
  watch(password2, () => {
    if (password2.value) pwd2Touched.value = true;
  });

  const canSubmit = computed(() => {
    return (
      target.value.trim().length > 0 &&
      code.value.trim().length > 0 &&
      password.value.length > 0 &&
      password2.value.length > 0 &&
      pwdState.value.ok &&
      pwdScore.value >= 2
    );
  });

  async function onSubmit() {
    pwdTouched.value = true;
    pwd2Touched.value = true;

    if (!canSubmit.value) {
      showError("注册失败", "请检查信息：验证码/密码强度/两次密码一致");
      return;
    }

    loading.value = true;
    try {
      const t = target.value.trim();
      await post("/api/v1/auth/register", {
        channel: detectChannel(t),
        target: t,
        code: code.value.trim(),
        password: password.value,
      });

      showSuccess("注册成功", "欢迎加入 TriAura");
      const redirect = (route.query.redirect as string) || "/";
      router.push(redirect);
    } catch (err: any) {
      const payload = err?.response?.data || err?.data || null;
      const msg = payload?.message || err?.message || "注册失败";
      showError("注册失败", msg);
    } finally {
      loading.value = false;
    }
  }

  return {
    target,
    code,
    password,
    password2,
    pwdTouched,
    pwd2Touched,
    loading,
    cooldown,
    pwdScore,
    pwdLevel,
    pwdState,
    canSubmit,
    sendCode,
    onSubmit,
  };
}
