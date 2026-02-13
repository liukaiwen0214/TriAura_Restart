# NavBar 菜单点击（noop）使用文档

本说明只讲一件事：NavBar 里的每个菜单项只传一个 `key`（如 `life.manage`），然后在 `noop(key)` 内部统一做路由跳转。后续你加新页面，只需要：
1）在路由里注册页面  
2）在 `NAV_MAP` 里新增 key → 路由映射  
3）菜单项绑定同一个 key

---

## 一、现状说明

NavBar.vue 里目前有占位函数：

- 点击菜单项时触发：`noop("some.key")`
- 现在只是 `console.log`，后续改成 `router.push(...)`

---

## 二、推荐方案：key → 路由映射（最省事、最好维护）

### 2.1 在 NavBar.vue 的 `<script setup>` 中写映射表

1）引入 `useRouter`，拿到 router  
2）创建 `NAV_MAP`：key 对应要跳转的 path（或 name）

示例（path 版本）：

- key：`life.manage`
- path：`/life`

```ts
import { useRouter } from "vue-router";

const router = useRouter();

const NAV_MAP: Record<string, string> = {
  // 生活
  "life.manage": "/life",
  "life.finance": "/finance",

  // 工作
  "work.projects": "/projects",
  "work.files": "/files",

  // 游戏
  "game.delta": "/game/delta",
  "game.onmyoji": "/game/onmyoji",

  // 厨房
  "kitchen.inventory": "/kitchen/inventory",
  "kitchen.recipe": "/kitchen/recipe",
};
```

### 2.2 修改 noop：拿到映射值并跳转

```ts
function noop(key: string) {
  const to = NAV_MAP[key];

  if (!to) {
    console.warn("[nav-click] 未配置路由：", key);
    return;
  }

  router.push(to);
}
```

### 2.3 模板里菜单项绑定 key（示例）

```html
<a class="ddItem" href="javascript:void(0)" @click="noop('life.manage')">
  生活管理
</a>
```

---

## 三、新增一个页面并接入点击跳转（完整流程示例）

目标：你新建一个 Life 页面，然后点击 “生活 → 生活管理” 跳转过去。

### 3.1 新建页面文件

例如：

- `src/pages/life/LifeManage.vue`

### 3.2 在 `router/index.ts` 注册路由

```ts
import LifeManage from "@/pages/life/LifeManage.vue";

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/life", component: LifeManage, meta: { requiresAuth: true } },
  ],
});
```

### 3.3 在 NavBar 的 NAV_MAP 增加映射

```ts
const NAV_MAP: Record<string, string> = {
  "life.manage": "/life",
};
```

### 3.4 菜单项点击传同样的 key

```html
<a class="ddItem" href="javascript:void(0)" @click="noop('life.manage')">
  生活管理
</a>
```

完成：点击后进入 `/life`。

---

## 四、升级推荐：使用路由 name（以后改 path 不用动菜单）

如果你担心未来 path 会变（比如 `/life` 改成 `/life/manage`），更推荐用 `name`。

### 4.1 路由配置添加 name

```ts
{ path: "/life", name: "lifeManage", component: LifeManage, meta: { requiresAuth: true } }
```

### 4.2 NAV_MAP 存 name

```ts
const NAV_MAP: Record<string, string> = {
  "life.manage": "lifeManage",
};
```

### 4.3 noop 用 name 跳转

```ts
function noop(key: string) {
  const name = NAV_MAP[key];

  if (!name) {
    console.warn("[nav-click] 未配置路由：", key);
    return;
  }

  router.push({ name });
}
```

---

## 五、key 命名规范（建议）

统一用：`模块.功能`

示例：
- `life.manage`
- `life.finance`
- `work.projects`
- `game.delta`
- `kitchen.inventory`

优点：可读性强、扩展时不会乱。

---

## 六、常见问题排查

### 6.1 点击没反应
- 模板有没有写 `@click="noop('xxx')"`
- `NAV_MAP` 有没有这个 key
- Console 是否打印了：`[nav-click] 未配置路由：xxx`

### 6.2 点击导致刷新 / 页面跳走
- 不要用真实 `href="/xxx"` 的 `<a>`
- 你现在用 `href="javascript:void(0)"` 是可以的
- 或者改成 `<button type="button">`（更语义化）

### 6.3 跳转后 404 / 空白
- `router/index.ts` 是否注册了对应路由
- import 路径是否正确
- `meta.requiresAuth` 是否导致被拦截（未登录会被路由守卫送回 `/auth`）
