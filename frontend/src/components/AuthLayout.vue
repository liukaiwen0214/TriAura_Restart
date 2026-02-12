<script setup lang="ts">
defineProps<{
  title: string
  subtitle?: string
  single?: boolean   // ✅ 新增：单栏
}>()
</script>

<template>
  <div class="wrap">
    <div class="shell" :class="{ single }">
      <!-- 左侧品牌卡：single=true 时不渲染 -->
      <section v-if="!single" class="brand">
        <div class="brandCard">
          <div class="brandTop">
            <div class="logo">T</div>
            <div>
              <div class="brandName">TriAura</div>
              <div class="brandSub">Make daily life orderly ✨</div>
            </div>
          </div>

          <div class="brandTitle">更轻松的记录与管理</div>
          <div class="brandDesc">
            登录后就能开始你的 TriAura 之旅：记录、整理、追踪，把生活变得更清晰。
          </div>

          <div class="brandMock"></div>

          <div class="brandFoot">© 2026 TriAura</div>
        </div>
      </section>

      <!-- 右侧表单卡：单栏时居中 -->
      <section class="panel">
        <div class="panelCard">
          <div class="head">
            <div class="title">{{ title }}</div>
            <div v-if="subtitle" class="subtitle">{{ subtitle }}</div>
          </div>

          <slot />

          <div class="footer">
            <slot name="footer" />
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
.wrap{
  min-height: 100vh;
  display:flex;
  align-items:center;
  justify-content:center;
  padding: 32px;
  background: radial-gradient(at 63.261459248684474% 40.435661867180194%, hsla(48.00000000000007, 100%, 99.01960784313727%, 1) 0%, hsla(48.00000000000007, 100%, 99.01960784313727%, 0) 100%), radial-gradient(at 18.473599030629593% 79.40548977479239%, hsla(270, 53.84615384615389%, 94.90196078431372%, 1) 0%, hsla(270, 53.84615384615389%, 94.90196078431372%, 0) 100%), radial-gradient(at 47.40296924426739% 16.607117886821054%, hsla(201.95121951219514, 71.92982456140355%, 88.8235294117647%, 1) 0%, hsla(201.95121951219514, 71.92982456140355%, 88.8235294117647%, 0) 100%), radial-gradient(at 1.8497164291168877% 26.801765486112174%, hsla(270, 53.84615384615389%, 94.90196078431372%, 1) 0%, hsla(270, 53.84615384615389%, 94.90196078431372%, 0) 100%), radial-gradient(at 62.88192195325533% 74.38051770016652%, hsla(246.66666666666663, 40.29850746268652%, 86.86274509803921%, 1) 0%, hsla(246.66666666666663, 40.29850746268652%, 86.86274509803921%, 0) 100%), radial-gradient(at 14.260879250351266% 9.302307798394338%, hsla(48.00000000000007, 100%, 99.01960784313727%, 1) 0%, hsla(48.00000000000007, 100%, 99.01960784313727%, 0) 100%), radial-gradient(at 96.49018180932643% 46.06238425943015%, hsla(270, 53.84615384615389%, 94.90196078431372%, 1) 0%, hsla(270, 53.84615384615389%, 94.90196078431372%, 0) 100%), radial-gradient(at 18.84291244561107% 45.7932449716266%, hsla(201.95121951219514, 71.92982456140355%, 88.8235294117647%, 1) 0%, hsla(201.95121951219514, 71.92982456140355%, 88.8235294117647%, 0) 100%), radial-gradient(at 24.214807677915395% 43.8602030360739%, hsla(270, 53.84615384615389%, 94.90196078431372%, 1) 0%, hsla(270, 53.84615384615389%, 94.90196078431372%, 0) 100%);            
}

.shell{
  width: min(1100px, 100%);
  display:grid;
  grid-template-columns: 1.1fr 1fr;
  gap: 28px;
  align-items: stretch;
}

/* ✅ 单栏：只剩右边，并居中 */
.shell.single{
  grid-template-columns: 1fr;
  justify-items: center;
}
.shell.single .panel{
  width: min(520px, 100%);
}

.brandCard{
  height: 100%;
  border-radius: 22px;
  padding: 28px;
  background: rgba(255,255,255,.06);
  border: 1px solid rgba(255,255,255,.10);
  box-shadow: 0 20px 60px rgba(0,0,0,.35);
  backdrop-filter: blur(10px);
  color: rgba(255,255,255,.92);
}
.brandTop{ display:flex; gap: 14px; align-items:center; }
.logo{
  width: 44px; height: 44px;
  display:flex; align-items:center; justify-content:center;
  border-radius: 14px;
  background: rgba(255,255,255,.10);
  border: 1px solid rgba(255,255,255,.16);
  font-weight: 700;
}
.brandName{ font-weight: 700; font-size: 16px; }
.brandSub{ font-size: 12px; opacity: .8; }
.brandTitle{ margin-top: 18px; font-size: 26px; font-weight: 800; }
.brandDesc{ margin-top: 10px; font-size: 13px; line-height: 1.7; opacity: .85; }
.brandMock{
  margin-top: 18px;
  height: 220px;
  border-radius: 18px;
  border: 1px dashed rgba(255,255,255,.18);
  background: rgba(255,255,255,.04);
}
.brandFoot{ margin-top: 18px; font-size: 12px; opacity: .75; }

.panelCard{
  border-radius: 22px;
  padding: 28px;
  background: rgba(255,255,255,.94);
  border: 1px solid rgba(255,255,255,.70);
  box-shadow: 0 20px 60px rgba(0,0,0,.35);
}
.head{ margin-bottom: 18px; }
.title{ font-size: 28px; font-weight: 800; color:#0b1220; }
.subtitle{ margin-top: 6px; font-size: 13px; color:#64748b; }

.footer{ margin-top: 16px; font-size: 12px; color:#64748b; }
</style>