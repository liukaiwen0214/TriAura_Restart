<template>
  <form class="form" @submit.prevent="onSubmit">
    <div
      class="modeTabs"
      :class="{ pwd: mode === 'pwd', code: mode === 'code' }"
    >
      <button
        type="button"
        class="tab"
        :class="{ active: mode === 'pwd' }"
        @click="setMode('pwd')"
      >
        密码登录
      </button>

      <button
        type="button"
        class="tab"
        :class="{ active: mode === 'code' }"
        @click="setMode('code')"
      >
        验证码登录
      </button>

      <span class="tabInk" aria-hidden="true"></span>
    </div>
    <label class="field">
      <span class="label">账号</span>
      <input
        v-model.trim="account"
        class="input"
        placeholder="手机号 / 邮箱"
        autocomplete="username"
      />
    </label>
    <!-- 账号输入框下面：modeTabs 不动 -->

    <Transition name="mode-swap" mode="out-in">
      <!-- 密码登录 -->
      <div v-if="mode === 'pwd'" key="pwd">
        <label class="field">
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
      </div>

      <!-- 验证码登录 -->
      <div v-else key="code">
        <label class="field">
          <span class="label">验证码</span>
          <div class="codeWrap">
            <input
              v-model.trim="code"
              class="input"
              placeholder="请输入验证码"
              inputmode="numeric"
              autocomplete="one-time-code"
            />
            <Button
              severity="secondary"
              variant="outlined"
              :disabled="sending || leftSec > 0 || !account"
              type="button"
              @click="sendCode"
            >
              <span v-if="leftSec === 0">{{
                sending ? "发送中..." : "发送验证码"
              }}</span>
              <span v-else>{{ leftSec }}s</span>
            </Button>
          </div>
        </label>
      </div>
    </Transition>

    <div class="row">
      <label class="chk">
        <input type="checkbox" v-model="remember" />
        <span>记住我</span>
      </label>

      <a class="link" href="javascript:void(0)">忘记密码</a>
    </div>

    <Button class="loginBtn" :disabled="loading" type="submit">
      {{ loading ? "登录中..." : "登录" }}
    </Button>

    <div class="muted">
      还没有账号？
      <a class="link" href="javascript:void(0)" @click="emit('go-register')"
        >去注册</a
      >
    </div>
  </form>
</template>

<script setup lang="ts">
import { useLoginPage } from "./login";

const emit = defineEmits<{
  (e: "go-register"): void;
}>();

const {
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
} = useLoginPage();
</script>

<style scoped src="./login.css"></style>
