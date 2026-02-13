<template>
  <form class="form" @submit.prevent="onSubmit">
    <label class="field">
      <span class="label">手机号 / 邮箱</span>
      <input v-model.trim="target" class="input" placeholder="建议手机号更快" />
    </label>

    <div class="field">
      <span class="label">验证码</span>
      <div class="codeWrap">
        <input v-model.trim="code" class="input" placeholder="6 位验证码" />
<Button
  class="otpBtn"
  severity="secondary"
  variant="outlined"
  :disabled="cooldown > 0 || !target"
  type="button"
  @click="sendCode"
>
  {{ cooldown > 0 ? `${cooldown}s` : "获取验证码" }}
</Button>
      </div>
    </div>

    <label class="field">
      <span class="label">设置密码</span>
      <input
        v-model="password"
        class="input"
        type="password"
        placeholder="至少 8 位，建议包含大小写/数字/符号"
        @blur="pwdTouched = true"
      />
      <div class="msgSlot">
        <div v-if="pwdTouched" class="msg" :class="pwdLevel.severity">
          强度：{{ pwdLevel.text }} · {{ pwdScore }}/4
        </div>
      </div>
    </label>

    <label class="field">
      <span class="label">确认密码</span>
      <input
        v-model="password2"
        class="input"
        type="password"
        placeholder="再输入一次"
        @blur="pwd2Touched = true"
      />
      <div class="msgSlot">
        <div
          v-if="pwd2Touched || password2"
          class="msg"
          :class="pwdState.ok ? 'success' : 'danger'"
        >
          {{ pwdState.tip || "请检查两次密码" }}
        </div>
      </div>
    </label>

    <Button class="loginBtn" :disabled="loading || !canSubmit" type="submit">
      {{ loading ? "注册中..." : "注册并登录" }}
    </Button>

    <div class="muted">
      已有账号？
      <a class="link" href="javascript:void(0)" @click="emit('go-login')"
        >去登录</a
      >
    </div>
  </form>
</template>

<script setup lang="ts">
const emit = defineEmits<{
  (e: "go-login"): void;
}>();
import { useRegister } from "./register";

const {
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
} = useRegister();
</script>

<style scoped src="./register.css"></style>
