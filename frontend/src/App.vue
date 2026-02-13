<script setup lang="ts">
import { computed } from "vue";
import { useRoute } from "vue-router";

import NavBar from "@/components/NavBar.vue";
import Toast from "primevue/toast";

const route = useRoute();

// ✅ /auth 这种页面隐藏导航栏：在路由 meta 里写 hideNav: true
const showNav = computed(() => !route.meta?.hideNav);
</script>

<template>
  <NavBar v-if="showNav" />
  <router-view />
  <Toast />
</template>