<script setup lang="ts">
defineProps<{
  title?: string;
  subtitle?: string;
}>();
</script>

<template>
  <div class="wrap">
    <div class="shell single">
      <section class="brand">
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
            登录后就能开始你的 TriAura
            之旅：记录、整理、追踪，把生活变得更清晰。
          </div>

          <!-- ✅ 把登录/注册塞进 brandCard -->
          <div class="brandMock">
            <slot />
          </div>

          <!-- footer slot 也可以留在卡里底部 -->
          <div class="footer">
            <slot name="footer" />
          </div>

          <div class="brandFoot">
            © 2026 TriAura ·
            <a
              class="beianLink"
              href="https://beian.miit.gov.cn/"
              target="_blank"
              rel="noopener"
            >
              鲁ICP备2026006101号
            </a>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
.wrap {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 32px;
  position: relative;
  overflow: hidden;

  /* ✅ 奶油底色 + 柔和彩雾（比你现在更暖、更干净） */
  background:
    radial-gradient(
      900px 520px at 50% 32%,
      rgba(255, 255, 255, 0.95),
      rgba(255, 255, 255, 0) 65%
    ),
    radial-gradient(
      650px 480px at 15% 30%,
      rgba(255, 224, 190, 0.45),
      rgba(255, 224, 190, 0) 60%
    ),
    radial-gradient(
      720px 520px at 85% 40%,
      rgba(210, 228, 255, 0.45),
      rgba(210, 228, 255, 0) 62%
    ),
    radial-gradient(
      720px 520px at 30% 85%,
      rgba(221, 210, 255, 0.38),
      rgba(221, 210, 255, 0) 62%
    ),
    linear-gradient(180deg, #fbfbfe 0%, #f4f2fb 100%);
}

.shell {
  width: min(1100px, 100%);
  display: grid;
  grid-template-columns: 1.1fr 1fr;
  gap: 28px;
  align-items: stretch;
}

.shell.single {
  grid-template-columns: 1fr;
  justify-items: center;
}
.shell.single .panel {
  width: min(520px, 100%);
}

@media (max-width: 980px) {
  .shell {
    grid-template-columns: 1fr;
  }
}

.brandCard {
  height: 100%;
  border-radius: 22px;
  padding: 28px;
  background: rgba(255, 255, 255, 0.18);
  border: 1px solid rgba(255, 255, 255, 0.7);
  box-shadow: 0 24px 70px rgba(15, 23, 42, 0.12);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  color: rgba(0, 0, 0, 0.92);
}

.brandTop {
  display: flex;
  gap: 14px;
  align-items: center;
}
.logo {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.12);
  border: 1px solid rgba(255, 255, 255, 0.16);
  font-weight: 700;
}
.brandName {
  font-weight: 700;
  font-size: 16px;
}
.brandSub {
  font-size: 12px;
  opacity: 0.85;
}
.brandTitle {
  margin-top: 18px;
  font-size: 26px;
  font-weight: 800;
}
.brandDesc {
  margin-top: 10px;
  font-size: 13px;
  line-height: 1.7;
  opacity: 0.9;
}

.brandMock {
  margin-top: 18px;
  min-height: 220px;
  border-radius: 18px;
  border: 1px dashed rgba(255, 255, 255, 0.22);
  background: rgba(255, 255, 255, 0.06);
  padding: 18px;
}

.brandFoot {
  margin-top: 18px;
  font-size: 12px;
  opacity: 0.8;
  text-align: center;
  justify-items: center;
}

.head {
  margin-bottom: 14px;
}
.title {
  font-size: 28px;
  font-weight: 800;
  color: #0b1220;
}
.subtitle {
  margin-top: 6px;
  font-size: 13px;
  color: #64748b;
}

.footer {
  margin-top: 16px;
  font-size: 12px;
  color: #64748b;
}
.brandFoot {
  margin-top: 12px;
  font-size: 12px;
  opacity: 0.75;
  text-align: center;
}

.brandFoot .beianLink {
  color: inherit;
  text-decoration: none;
}

.brandFoot .beianLink:hover {
  text-decoration: underline;
}
</style>
