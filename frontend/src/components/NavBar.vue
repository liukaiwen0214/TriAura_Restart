<template>
  <header class="nav">
    <div class="left">
      <div class="logo">TRIAURA</div>

      <nav class="menus">
        <div class="group">
          <!-- 外层：两行 -->
          <div class="groupMain">
            <span class="title">生活</span>
            <span class="sub">记录美好生活</span>
          </div>
          <!-- 内层：下拉 -->
          <div class="dropdown">
            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('life.manage')"
            >
              <i class="pi pi-book ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">生活管理</div>
                <div class="ddDesc">日记、习惯、碎片记录</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('life.finance')"
            >
              <i class="pi pi-wallet ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">财务管理</div>
                <div class="ddDesc">收支、预算、资产概览</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('life.more')"
            >
              <i class="pi pi-ellipsis-h ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">更多</div>
                <div class="ddDesc">把常用入口都放这</div>
              </div>
            </a>
          </div>
        </div>
        <div class="group">
          <!-- 外层：两行 -->
          <div class="groupMain">
            <span class="title">工作</span>
            <span class="sub">没有好东西</span>
          </div>

          <!-- 内层：下拉 -->
          <div class="dropdown">
            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('work.project')"
            >
              <i class="pi pi-sitemap ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">项目</div>
                <div class="ddDesc">计划、进度、里程碑</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('work.files')"
            >
              <i class="pi pi-folder-open ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">附件</div>
                <div class="ddDesc">文档、资料、归档</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('work.notes')"
            >
              <i class="pi pi-file-edit ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">笔记</div>
                <div class="ddDesc">灵感、会议、备忘</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('work.more')"
            >
              <i class="pi pi-ellipsis-h ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">更多</div>
                <div class="ddDesc">把常用入口都放这</div>
              </div>
            </a>
          </div>
        </div>
        <div class="group">
          <!-- 外层：两行 -->
          <div class="groupMain">
            <span class="title">游戏</span>
            <span class="sub">生活再惨也是需要快乐</span>
          </div>

          <!-- 内层：下拉 -->
          <div class="dropdown">
            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('game.delta')"
            >
              <i class="pi pi-bolt ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">三角洲</div>
                <div class="ddDesc">战绩、配置、队友</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('game.onmyoji')"
            >
              <i class="pi pi-star ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">阴阳师</div>
                <div class="ddDesc">御魂、阵容、每日</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('game.stats')"
            >
              <i class="pi pi-chart-line ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">数据</div>
                <div class="ddDesc">投入时间、快乐指数</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('game.more')"
            >
              <i class="pi pi-ellipsis-h ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">其他</div>
                <div class="ddDesc">偶尔换个世界</div>
              </div>
            </a>
          </div>
        </div>
        <div class="group">
          <!-- 外层：两行 -->
          <div class="groupMain">
            <span class="title">厨房</span>
            <span class="sub">猛猛得吃</span>
          </div>

          <!-- 内层：下拉 -->
          <div class="dropdown">
            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('kitchen.inventory')"
            >
              <i class="pi pi-box ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">库存</div>
                <div class="ddDesc">剩余、到期、提醒</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('kitchen.ingredients')"
            >
              <i class="pi pi-tags ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">食材</div>
                <div class="ddDesc">分类、批次、单位</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('kitchen.recipes')"
            >
              <i class="pi pi-bookmark ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">食谱</div>
                <div class="ddDesc">收藏、做过、想做</div>
              </div>
            </a>

            <a
              class="ddItem"
              href="javascript:void(0)"
              @click.prevent="noop('kitchen.more')"
            >
              <i class="pi pi-ellipsis-h ddIcon"></i>
              <div class="ddText">
                <div class="ddTitle">更多</div>
                <div class="ddDesc">采购、清单、统计</div>
              </div>
            </a>
          </div>
        </div>
      </nav>
    </div>
    <div class="right">
      <!-- ✅ 消息：hover 小气泡 -->
      <div
        class="notifyWrap"
        @mouseenter="openNotify()"
        @mouseleave="closeNotify()"
      >
        <button class="iconBtn" title="消息">
          <i class="pi pi-bell"></i>

          <!-- 可选：未读小点（有未读就显示） -->
          <span v-if="hasUnread" class="dot" aria-hidden="true"></span>
        </button>
        <Transition name="pop" appear>
          <div class="notifyPanel" v-if="notifyOpen">
            <div class="notifyHead">
              <div class="notifyTitle">消息</div>
              <div class="notifyCount">{{ visibleNotifs.length }} 条</div>
            </div>

            <div class="notifyList">
              <a
                v-for="n in visibleNotifs"
                :key="n.id"
                class="notifyItem"
                href="javascript:void(0)"
                @click.prevent="noop('notify:' + n.id)"
              >
                <div class="notifyTop">
                  <div class="notifyItemTitle" :class="{ unread: n.unread }">
                    {{ n.title }}
                  </div>

                  <!-- ✅ tag：右上角，淡一点，不抢视线 -->
                  <span v-if="n.tag" class="notifyTag">{{ n.tag }}</span>
                </div>
                <div class="notifyMeta" v-if="n.content || n.time">
                  <!-- ✅ hover 看全文：用 title 最稳，不影响 click/noop -->
                  <div
                    v-if="n.content"
                    class="notifyContent"
                    :title="n.content"
                  >
                    {{ n.content }}
                  </div>

                  <div v-if="n.time" class="notifyTime">
                    {{ n.time }}
                  </div>
                </div>
              </a>
            </div>

            <div class="notifyFoot">
              <button class="notifyMore" type="button" @click="viewMore">
                查看更多
              </button>
            </div>
          </div>
        </Transition>
      </div>
      <!-- ✅ 任务：hover 小面板 + 回车快加 + 弹窗详细 -->
      <div class="taskWrap" @mouseenter="openTask()" @mouseleave="closeTask()">
        <button class="iconBtn" title="待办">
          <i class="pi pi-check-square"></i>
          <span
            v-if="pendingCount > 0"
            class="dot dotBlue"
            aria-hidden="true"
          ></span>
        </button>

        <Transition name="pop" appear>
          <div class="taskPanel" v-if="taskOpen">
            <div class="taskHead">
              <div class="taskTitle">待办</div>
              <div class="taskCount">{{ visibleTasks.length }} 条</div>
            </div>

            <!-- ✅ 快速新增：回车就加（简易） -->
            <div class="taskQuick">
              <input
                v-model.trim="quickText"
                class="taskQuickInput"
                placeholder="回车添加一个待办…"
                @keydown.enter.prevent="addQuick()"
              />
              <button
                class="taskQuickPlus"
                type="button"
                @click="openTaskDialog()"
              >
                <i class="pi pi-plus"></i>
              </button>
            </div>

            <div class="taskList">
              <div
                v-for="t in visibleTasks"
                :key="t.id"
                class="taskItem"
                @click="openTaskItem(t)"
              >
                <label class="taskLeft">
                  <input
                    type="checkbox"
                    class="taskChk"
                    :checked="t.done"
                    @click.stop
                    @change="toggleDone(t.id)"
                  />
                </label>

                <div class="taskMid">
                  <!-- 标题行：点 + 标题（同一行） -->
                  <div class="taskTopRow">
                    <span
                      class="prioDot"
                      :data-p="t.priority || 'MEDIUM'"
                    ></span>
                    <div class="taskItemTitle" :class="{ done: t.done }">
                      {{ t.title }}
                    </div>
                  </div>
                  <div v-if="t.note" class="taskNote">{{ t.note }}</div>
                </div>
                <!-- ✅ 右侧独立一列：上 tag 下 time,且和中间两行对齐 -->
                <div class="taskRight">
                  <span v-if="t.tag" class="taskTag">{{ t.tag }}</span>
                  <span v-if="t.time" class="taskTime">{{ t.time }}</span>
                </div>
              </div>
            </div>

            <div class="taskFoot">
              <button class="taskMore" type="button" @click="viewTaskMore">
                查看更多
              </button>
            </div>
          </div>
        </Transition>
      </div>

      <!-- ✅ 个人信息：hover 面板 -->
      <div
        class="profileWrap"
        @mouseenter="openProfile()"
        @mouseleave="closeProfile()"
      >
        <div class="avatar" title="个人信息">{{ avatarText }}</div>

        <Transition name="pop" appear>
          <div class="profilePanel" v-if="profileOpen">
            <!-- 顶部信息 -->
            <div class="profileHead">
              <div class="profileAvatar">{{ avatarText }}</div>

              <div class="profileInfo">
                <div class="profileTop">
                  <div class="profileName" :title="displayName">
                    {{ displayName }}
                  </div>
                  <Badge
                    variant="secondary"
                    class="h-5 px-2 text-xs gap-1 bg-blue-500! text-white! hover:bg-blue-500! dark:bg-blue-600! dark:hover:bg-blue-600!"
                  >
                    <BadgeCheckIcon class="h-3.5 w-3.5" />
                    {{ roleText }}
                  </Badge>
                </div>
                <div class="profileSub" :title="accountText">
                  {{ accountText }}
                </div>
              </div>
            </div>

            <!-- 快捷入口 -->
            <div class="profileActions">
              <button class="actionItem" type="button" @click="noop('profile')">
                <span>个人资料</span>
                <span class="chev">›</span>
              </button>

              <button class="actionItem" type="button" @click="noop('about')">
                <span>关于</span>
                <span class="chev">›</span>
              </button>

              <button class="actionItem" type="button" @click="noop('switch')">
                <span>切换账号</span>
                <span class="chev">›</span>
              </button>

              <button class="actionItem danger" type="button" @click="doLogout">
                退出登录
              </button>
            </div>
          </div>
        </Transition>
      </div>
    </div>
    <div class="navLine" aria-hidden="true"></div>
  </header>

  <!-- 占位：避免 fixed 顶栏压住内容 -->
  <div class="navSpacer"></div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { useRouter } from "vue-router";
import { toast } from "vue-sonner";
import { useAuth } from "@/auth/useAuth";
import { Badge } from "@/components/ui/badge";
import { BadgeCheckIcon } from "lucide-vue-next";

const router = useRouter();
const { state, logout } = useAuth();

// 你之前的占位点击函数（保留）
function noop(key: string) {
  console.log("[nav-click]", key);
}

/** ✅ 消息数据结构（后端以后按这个吐给你即可） */
type Notif = {
  id: string;
  title: string;
  content?: string;
  tag?: string; // 日记/厨房/阴阳师/三角洲… 后端传啥显示啥
  time?: string; // 建议后端直接传可展示文本
  unread?: boolean;
};

/** ✅ 暂时 mock 5+ 条，面板只显示 5 条 */
const notifications = ref<Notif[]>([
  {
    id: "n1",
    title: "今天的日记还没写完",
    content: "你已经连续记录 3 天了，再写一句就完美。",
    tag: "日记",
    time: "刚刚",
    unread: true,
  },
  {
    id: "n2",
    title: "厨房库存：牛奶快到期",
    content: "建议 2 天内用掉，做个拿铁也行。",
    tag: "厨房",
    time: "10 分钟前",
    unread: true,
  },
  {
    id: "n3",
    title: "阴阳师：体力溢出了",
    content: "别浪费啦，刷一把觉醒材料。",
    tag: "阴阳师",
    time: "1 小时前",
    unread: false,
  },
  {
    id: "n4",
    title: "三角洲：今晚组队吗",
    content: "老地方集合，带上消音。",
    tag: "三角洲",
    time: "昨天",
    unread: false,
  },
  {
    id: "n5",
    title: "财务：本周支出有点多",
    content: "外卖 + 奶茶占比最高（懂你😌）。",
    tag: "财务",
    time: "2 天前",
    unread: false,
  },
  {
    id: "n6",
    title: "工作：项目有 1 个待办",
    content: "把接口 error code 对齐一下。",
    tag: "工作",
    time: "3 天前",
    unread: false,
  },
]);

const visibleNotifs = computed(() => notifications.value.slice(0, 5));
const hasUnread = computed(() => notifications.value.some((x) => x.unread));

/** ✅ hover 展开/收起：加一点点延迟，手感更柔和 */
const notifyOpen = ref(false);
let closeTimer: number | null = null;

function openNotify() {
  if (closeTimer) {
    window.clearTimeout(closeTimer);
    closeTimer = null;
  }
  notifyOpen.value = true;
}

function closeNotify() {
  // 小延迟避免鼠标边缘抖动就关掉
  if (closeTimer) window.clearTimeout(closeTimer);
  closeTimer = window.setTimeout(() => {
    notifyOpen.value = false;
  }, 120); // 120ms 够柔，不会卡
}

function viewMore() {
  toast.info("提示", { description: "功能暂不支持" });
}

// ✅ 任务数据结构（后端以后按这个吐给你即可）
type Task = {
  id: string;
  title: string;
  note?: string;
  tag?: string; // 日记/厨房/阴阳师/三角洲… 后端传啥显示啥
  time?: string; // 比如：今天 / 明天 / 2天后 / 10:30
  done?: boolean;
  priority?: Priority; // ✅ 新增
};

// ✅ mock：先给几条
const tasks = ref<Task[]>([
  {
    id: "t1",
    title: "把今天的日记补完",
    note: "就一句也行",
    tag: "日记",
    time: "今天",
    done: false,
  },
  {
    id: "t2",
    title: "牛奶处理掉",
    note: "拿铁 / 麦片",
    tag: "厨房",
    time: "2 天内",
    done: false,
  },
  {
    id: "t3",
    title: "阴阳师清体力",
    note: "觉醒材料",
    tag: "阴阳师",
    time: "今晚",
    done: true,
  },
  {
    id: "t4",
    title: "三角洲看看配置",
    note: "开黑前检查",
    tag: "三角洲",
    time: "周末",
    done: false,
  },
  {
    id: "t5",
    title: "复盘本周开销",
    note: "外卖奶茶太多了",
    tag: "财务",
    time: "本周",
    done: false,
  },
  {
    id: "t6",
    title: "给项目接口补 error code",
    tag: "工作",
    time: "下周",
    done: false,
  },
]);

// 面板只显示 5 条（你定的）
const visibleTasks = computed(() => tasks.value.slice(0, 5));
const pendingCount = computed(() => tasks.value.filter((t) => !t.done).length);

// ✅ hover 展开/收起：微延迟更柔和
const taskOpen = ref(false);
let taskCloseTimer: number | null = null;

function openTask() {
  if (taskCloseTimer) {
    window.clearTimeout(taskCloseTimer);
    taskCloseTimer = null;
  }
  taskOpen.value = true;
}

function closeTask() {
  if (taskCloseTimer) window.clearTimeout(taskCloseTimer);
  taskCloseTimer = window.setTimeout(() => {
    taskOpen.value = false;
  }, 120);
}

// ✅ 快速新增：回车就加（简易）
const quickText = ref("");
function addQuick() {
  const text = quickText.value.trim();
  if (!text) return;

  tasks.value.unshift({
    id: "t_" + Date.now(),
    title: text,
    done: false,
    // tag/time/note 为空 → 简易新增
  });

  quickText.value = "";
}

// ✅ 勾选完成：轻薄动画靠 CSS 做
function toggleDone(id: string) {
  const t = tasks.value.find((x) => x.id === id);
  if (!t) return;
  t.done = !t.done;
}

// ✅ “查看更多” 暂不支持 → toast
function viewTaskMore() {
  toast.info("功能暂不支持");
}

// ✅ 详细弹窗（先留接口，你后面要 Dialog 我再给你补完整）
function openTaskDialog() {
  toast.info("详细弹窗下一步加");
}
function openTaskItem(t: any) {
  toast.info("功能暂不支持");
}

/** 昵称优先：displayName / username；没有就用邮箱前缀/手机号后4位兜底 */
const displayName = computed(() => {
  const me: any = state.me || {};
  return (
    me.displayName ||
    me.username ||
    (me.email ? String(me.email).split("@")[0] : "") ||
    (me.phone ? `用户${String(me.phone).slice(-4)}` : "") ||
    "我的"
  );
});

/** 第二行账号：邮箱优先，否则手机号，否则 id */
const accountText = computed(() => {
  const me: any = state.me || {};
  return me.email || me.phone || me.id || "";
});

/** 角色 */
const roleText = computed(() => {
  const me: any = state.me || {};
  return me.role || "USER";
});
const roleVariant = computed(() => {
  const r = roleText.value;
  if (r === "OWNER") return "secondary";
  if (r === "ADMIN") return "default";
  return "outline";
});
/** 头像字母：取昵称首字母（中文就直接取第一个字） */
const avatarText = computed(() => {
  const n = (displayName.value || "K").trim();
  return n.slice(0, 1).toUpperCase();
});

/** hover 展开/收起（加一点延迟，手感更软） */
const profileOpen = ref(false);
let profileCloseTimer: number | null = null;

function openProfile() {
  if (profileCloseTimer) {
    window.clearTimeout(profileCloseTimer);
    profileCloseTimer = null;
  }
  profileOpen.value = true;
}

function closeProfile() {
  if (profileCloseTimer) window.clearTimeout(profileCloseTimer);
  profileCloseTimer = window.setTimeout(() => {
    profileOpen.value = false;
  }, 140);
}

function goProfile() {
  // 先占位：后面你做页面了改成 router.push(...)
  toast("功能暂未开放", { description: "个人资料页面后续上线" });
  profileOpen.value = false;
}

function goAbout() {
  toast("TriAura", { description: "© 2026 TriAura · 鲁ICP备2026006101号" });
  profileOpen.value = false;
}

function switchAccount() {
  toast("功能暂未开放", { description: "切换账号后续支持" });
  profileOpen.value = false;
}

async function doLogout() {
  try {
    await logout();
  } finally {
    profileOpen.value = false;
    router.replace("/auth?tab=login");
  }
}
</script>

<style scoped>
/* 顶栏玻璃 */
.nav {
  position: fixed;
  top: 14px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 1000;

  width: min(1320px, calc(100% - 48px));
  height: 54px;
  border-radius: 9px;
  overflow: visible;

  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  padding: 0 14px;
  -webkit-backdrop-filter: blur(18px) saturate(160%);
  transition:
    opacity 0.18s ease,
    background-color 0.18s ease,
    border-color 0.18s ease,
    box-shadow 0.18s ease,
    backdrop-filter 0.18s ease;
}
.navLine {
  position: absolute;
  left: 1px;
  right: 1px; /* ✅ 线更长：想更长就改成 10px / 8px */
  bottom: 0;
  height: 1px;
  pointer-events: none;

  background: linear-gradient(
    90deg,
    rgba(15, 23, 42, 0),
    rgba(15, 23, 42, 0.1) 18%,
    rgba(15, 23, 42, 0.18) 50%,
    rgba(15, 23, 42, 0.1) 82%,
    rgba(15, 23, 42, 0)
  );
  opacity: 0.9;
  z-index: 3;
}
/* 占位高度：54 + top 14 + 额外间距 */
.navSpacer {
  height: 84px;
}

/* 内容布局 */
.left {
  display: flex;
  align-items: center;
  gap: 14px;
}
.logo {
  font-weight: 900;
  letter-spacing: 0.8px;
  font-size: 14px;
  color: #0b1220;
  user-select: none;
}
.menus {
  display: flex;
  overflow: visible;
  align-items: center;
  gap: 10px;
}
.group {
  position: relative;
}
/* ✅ hover 桥：让鼠标从 groupMain 移到 dropdown 时不掉 hover */
.group::after {
  content: "";
  position: absolute;
  left: 0;
  right: 0;
  top: 100%;
  height: 14px; /* 这个就是通道高度 */
}
.groupMain {
  display: flex;
  flex-direction: column;
  gap: 2px;
  padding: 6px 10px;
  border-radius: 9px;
  cursor: pointer;
  align-items: center; /* ✅ 两行居中 */
  text-align: center; /* ✅ 文本居中 */
  transition: background 0.15s ease;
}
.group:hover .groupMain {
  background: rgba(15, 23, 42, 0.06);
}
.dropdown {
  position: absolute;
  left: 50%;
  top: 100%;
  transform: translateX(-50%) translateY(-6px);
  opacity: 0;
  pointer-events: none;

  min-width: 190px;
  padding: 10px;
  border-radius: 14px;
  z-index: 2000;

  /* ✅ 玻璃：用“低不透明度白”让背景渐变透出来，而不是煞白 */
  background: rgba(255, 255, 255, 0.42);

  /* ✅ 很浅的边界，让它从背景里“分离”出来一点点 */
  border: 1px solid rgba(255, 255, 255, 0.35);

  /* ✅ 轻轻的阴影 + 内高光，增加层次但不硬 */
  box-shadow:
    0 22px 55px rgba(15, 23, 42, 0.1),
    0 1px 0 rgba(255, 255, 255, 0.55) inset;

  /* ✅ 模糊 + 饱和度：玻璃感核心 */
  backdrop-filter: blur(18px) saturate(160%);
  -webkit-backdrop-filter: blur(18px) saturate(160%);

  transition:
    opacity 0.16s ease,
    transform 0.16s ease;
}
.group:hover .dropdown {
  opacity: 1;
  transform: translateX(-50%) translateY(8px); /* ✅ 视觉往下落一点，但不留空隙 */
  pointer-events: auto;
}

/* 内层 item：一行一个 */
.ddItem {
  display: grid;
  grid-template-columns: 34px 1fr;
  gap: 10px;
  align-items: center;

  padding: 10px 12px;
  border-radius: 12px;
  text-decoration: none;

  color: #0b1220;
  background: rgba(255, 255, 255, 0); /* 默认透明，hover 再给高光 */
  transition:
    background 0.15s ease,
    transform 0.15s ease;
}
.ddIcon {
  width: 34px;
  height: 34px;
  border-radius: 10px;

  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 15px;
  color: rgba(15, 23, 42, 0.72);

  background: rgba(15, 23, 42, 0.06);
  border: 1px solid rgba(255, 255, 255, 0.35);
}
.ddItem:hover {
  background: rgba(15, 23, 42, 0.06);
  transform: translateY(-1px);
}
.ddText {
  display: flex;
  flex-direction: column;
  gap: 2px;
  min-width: 0;
}

.ddTitle {
  font-size: 13px;
  font-weight: 800;
  line-height: 1.2;
}

.ddDesc {
  font-size: 12px;
  color: rgba(100, 116, 139, 0.95);
  line-height: 1.2;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.ddItem:hover {
  background: rgba(15, 23, 42, 0.06);
  transform: translateY(-1px);
}

/* hover 时 icon 更亮一点点 */
.ddItem:hover .ddIcon {
  background: rgba(15, 23, 42, 0.08);
  color: rgba(15, 23, 42, 0.85);
}
.title {
  font-size: 12px;
  font-weight: 700;
  color: #0b1220;
}
.sub {
  font-size: 11px;
  color: #64748b;
}

/* 右侧 */
.right {
  display: flex;
  align-items: center;
  gap: 10px;
}
.iconBtn {
  width: 36px;
  height: 36px;
  border-radius: 9px;
  border: 1px solid rgba(15, 23, 42, 0.1);
  background: rgba(255, 255, 255, 0.55);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition:
    background 0.15s ease,
    transform 0.15s ease;
}
.iconBtn:hover {
  background: rgba(255, 255, 255, 0.75);
  transform: translateY(-1px);
}
/* ========== Notify Popover ========== */
.notifyWrap {
  position: relative;
  display: flex;
  align-items: center;
}

.iconBtn {
  position: relative; /* 为未读点定位 */
}

.dot {
  position: absolute;
  right: 8px;
  top: 8px;
  width: 7px;
  height: 7px;
  border-radius: 999px;
  background: rgba(239, 68, 68, 0.95);
  box-shadow: 0 0 0 2px rgba(255, 255, 255, 0.7);
}

/* 面板：玻璃、半透明、吃背景 */
.notifyPanel {
  position: absolute;
  top: calc(100% + 10px);
  left: 50%;
  transform: translateX(-50%);
  /* ✅ 如果你还想加一点“落下来的感觉”，用这一行替换上面 transform：
     transform: translateX(-50%) translateY(2px);
  */

  width: 320px;
  border-radius: 12px;
  padding: 10px;
  z-index: 3000;

  background: rgba(255, 255, 255, 0.62);
  border: 1px solid rgba(255, 255, 255, 0.55);
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.12);
  backdrop-filter: blur(18px) saturate(160%);
  -webkit-backdrop-filter: blur(18px) saturate(160%);
}

/* 头部 */
.notifyHead {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 4px 6px 8px 6px;
}
.notifyTitle {
  font-size: 13px;
  font-weight: 800;
  color: #0b1220;
}
.notifyCount {
  font-size: 12px;
  color: #64748b;
}

/* 列表 */
.notifyList {
  display: grid;
  gap: 6px;
  padding: 2px;
}

.notifyItem {
  display: block;
  text-decoration: none;
  border-radius: 10px;
  padding: 10px 10px;
  color: inherit;
  transition:
    background 0.15s ease,
    transform 0.15s ease;
  background: rgba(255, 255, 255, 0.3);
  border: 1px solid rgba(15, 23, 42, 0.06);
}
.notifyItem:hover {
  background: rgba(255, 255, 255, 0.5);
  transform: translateY(-1px);
}

.notifyTop {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 10px;
}

.notifyItemTitle {
  font-size: 13px;
  font-weight: 800;
  color: #0b1220;
  line-height: 1.2;
}
.notifyItemTitle.unread {
  /* 未读稍微更强调一点点即可 */
  text-shadow: 0 0 0 rgba(0, 0, 0, 0);
}
.notifyMeta {
  margin-top: 6px;
  display: flex;
  align-items: baseline;
  gap: 10px;
}

/* ✅ tag：右上角，小、淡、不抢标题 */
.notifyTag {
  font-size: 11px;
  font-weight: 700;
  color: rgba(15, 23, 42, 0.45);
  background: rgba(255, 255, 255, 0.35);
  border: 1px solid rgba(15, 23, 42, 0.06);
  padding: 2px 8px;
  border-radius: 999px;
  white-space: nowrap;
  flex: 0 0 auto;
}

.notifyContent {
  flex: 1;
  min-width: 0; /* 关键：允许 ellipsis 生效 */
  font-size: 13px;
  line-height: 1.3;
  color: rgba(15, 23, 42, 0.74); /* 比标题淡一点，轻薄 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.notifyTime {
  margin-top: 6px;
  font-size: 11px;
  color: #94a3b8;
}

/* 底部 */
.notifyFoot {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(15, 23, 42, 0.06);
  display: flex;
  justify-content: center;
}
.notifyMore {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 12px;
  font-weight: 800;
  color: #0b1220;
  padding: 8px 10px;
  border-radius: 9px;
  transition: background 0.15s ease;
}
.notifyMore:hover {
  background: rgba(15, 23, 42, 0.06);
}
/* 面板出现/消失：柔和一点 */
.pop-enter-active,
.pop-leave-active {
  transition:
    opacity 0.16s ease,
    transform 0.16s ease,
    filter 0.16s ease;
  will-change: opacity, transform, filter;
}

.pop-enter-from,
.pop-leave-to {
  opacity: 0;
  transform: translateX(-50%) translateY(-6px) scale(0.98);
  filter: blur(6px);
}

.pop-enter-to,
.pop-leave-from {
  opacity: 1;
  transform: translateX(-50%) translateY(0) scale(1);
  filter: blur(0);
}
.avatar {
  width: 36px;
  height: 36px;
  border-radius: 9px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  color: #0b1220;
  border: 1px solid rgba(15, 23, 42, 0.1);
  background: rgba(255, 255, 255, 0.55);
}
/* ====== Tasks Popover ====== */
.taskWrap {
  position: relative;
  display: flex;
  align-items: center;
}

.dotBlue {
  background: rgba(59, 130, 246, 0.95);
}

.taskPanel {
  position: absolute;
  top: calc(100% + 10px);
  left: 50%;
  transform: translateX(-50%);
  width: 340px;
  border-radius: 12px;
  padding: 10px;
  z-index: 3000;

  background: rgba(255, 255, 255, 0.62);
  border: 1px solid rgba(255, 255, 255, 0.55);
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.12);
  backdrop-filter: blur(18px) saturate(160%);
  -webkit-backdrop-filter: blur(18px) saturate(160%);
}

.taskHead {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 4px 6px 8px 6px;
}
.taskTitle {
  font-size: 13px;
  font-weight: 800;
  color: #0b1220;
}
.taskCount {
  font-size: 12px;
  color: #64748b;
}

/* 快加 */
.taskQuick {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 2px 4px 10px 4px;
}
.taskQuickInput {
  flex: 1;
  height: 36px;
  border-radius: 10px;
  border: 1px solid rgba(15, 23, 42, 0.1);
  background: rgba(255, 255, 255, 0.45);
  padding: 0 12px;
  outline: none;
  font-size: 12px;
  color: #0b1220;
}
.taskQuickInput:focus {
  border-color: rgba(15, 23, 42, 0.18);
  background: rgba(255, 255, 255, 0.55);
}
.taskQuickPlus {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  border: 1px solid rgba(15, 23, 42, 0.1);
  background: rgba(255, 255, 255, 0.45);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition:
    background 0.15s ease,
    transform 0.15s ease;
}
.taskQuickPlus:hover {
  background: rgba(255, 255, 255, 0.62);
  transform: translateY(-1px);
}

/* 列表 */
.taskList {
  display: grid;
  gap: 6px;
  padding: 2px;
}

.taskItem {
  position: relative;
  border-radius: 12px;
  padding: 10px 10px;
  background: rgba(255, 255, 255, 0.3);
  border: 1px solid rgba(15, 23, 42, 0.06);

  display: grid;
  grid-template-columns: 22px 1fr 72px; /* ✅ 右侧列固定，排版更稳 */
  gap: 10px;
  align-items: start;
  cursor: pointer;

  transition:
    background 0.15s ease,
    transform 0.15s ease;
}
.taskItem:hover {
  background: rgba(255, 255, 255, 0.45);
  transform: translateY(-1px);
  transition:
    background 0.15s ease,
    transform 0.15s ease;
}

.taskLeft {
  display: flex;
  align-items: center;
  justify-content: center;
  padding-top: 2px;
}
.taskChk {
  width: 16px;
  height: 16px;
}

.taskMid {
  min-width: 0;
}
.taskRight {
  display: grid;
  grid-template-rows: auto auto; /* 两行：上=tag，下=time */
  row-gap: 6px;
  justify-items: end; /* 右对齐 */
  align-content: start; /* 从上开始排 */
}
.taskTopRow {
  display: grid;
  align-items: center;
  justify-content: space-between;
  grid-template-columns: 10px 1fr 15px; /* 左 dot 位 | 标题位 | 右占位 */
  gap: 10px;
}
.taskItemTitle {
  font-size: 13px;
  font-weight: 750;
  color: #0b1220;
  line-height: 1.2;

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  min-width: 0;

  cursor: pointer;
}
.taskItemTitle.done {
  opacity: 0.55;
  text-decoration: line-through;
}

.taskTag {
  font-size: 11px;
  font-weight: 700;
  color: rgba(15, 23, 42, 0.45);
  background: rgba(255, 255, 255, 0.35);
  border: 1px solid rgba(15, 23, 42, 0.06);
  padding: 2px 8px;
  border-radius: 999px;
  white-space: nowrap;
}

.taskNote {
  font-size: 12px;
  color: rgba(15, 23, 42, 0.68);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  line-height: 1.25;
  margin-top: 12px; /* 让备注和标题的距离固定 */
}

.taskTime {
  font-size: 11px;
  color: #94a3b8;
  white-space: nowrap;
  justify-self: end;
  align-self: start; /* 或 baseline（看你浏览器效果） */
  margin-top: 2px; /* 微调：让它更贴近备注行 */
}

/* 底部 */
.taskFoot {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(15, 23, 42, 0.06);
  display: flex;
  justify-content: center;
}
.taskMore {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 12px;
  font-weight: 800;
  color: #0b1220;
  padding: 8px 10px;
  border-radius: 9px;
  transition: background 0.15s ease;
}
.taskMore:hover {
  background: rgba(15, 23, 42, 0.06);
}
/* 标题行：让点和标题稳稳对齐 */
.todoTitleRow {
  display: flex;
  align-items: center;
  gap: 8px;
  min-width: 0;
}

/* 小圆点本体 */
.prioDot {
  display: inline-block; /* ✅ 避免 inline baseline 捣乱 */
  width: 8px;
  height: 8px;
  border-radius: 999px;
  flex: 0 0 auto;
  align-self: center; /* ✅ 双保险 */
  margin: 0; /* ✅ 清掉可能的旧 margin-top */
  opacity: 0.92;

  /* 轻轻的光圈：更软 */
  box-shadow: 0 0 0 4px rgba(15, 23, 42, 0.04);
}

/* 颜色：不刺眼，偏“奶油感” */
.prioDot[data-p="HIGH"] {
  background: rgba(239, 68, 68, 0.85);
  box-shadow: 0 0 0 4px rgba(239, 68, 68, 0.1);
}
.prioDot[data-p="MEDIUM"] {
  background: rgba(245, 158, 11, 0.78);
  box-shadow: 0 0 0 4px rgba(245, 158, 11, 0.1);
}
.prioDot[data-p="LOW"] {
  background: rgba(100, 116, 139, 0.55);
  box-shadow: 0 0 0 4px rgba(100, 116, 139, 0.08);
}

/* 标题不要被挤压 */
.todoTitle {
  font-weight: 800;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
/* ========== Profile Popover ========== */
.profileWrap {
  position: relative;
  display: flex;
  align-items: center;
}

/* 面板：玻璃、半透明、吃背景 */
.profilePanel {
  position: absolute;
  top: calc(100% + 10px);
  /* ✅ 以头像按钮为中心点 */
  left: 50%;
  right: auto;
  transform: translateX(-50%);
  width: 300px;
  border-radius: 12px;
  padding: 10px;
  z-index: 3500;

  background: rgba(255, 255, 255, 0.62);
  border: 1px solid rgba(255, 255, 255, 0.55);
  box-shadow: 0 18px 40px rgba(15, 23, 42, 0.12);
  backdrop-filter: blur(18px) saturate(160%);
  -webkit-backdrop-filter: blur(18px) saturate(160%);

  transform: translateX(-50%) translateY(2px);
}

/* 顶部 */
.profileHead {
  display: grid;
  grid-template-columns: 40px 1fr;
  gap: 10px;
  padding: 6px 6px 10px 6px;
  align-items: center;
}

.profileAvatar {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;

  font-weight: 900;
  color: #0b1220;
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: rgba(255, 255, 255, 0.55);
}

.profileInfo {
  min-width: 0;
}

.profileTop {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
}

.profileName {
  font-size: 13px;
  font-weight: 900;
  color: #0b1220;
  line-height: 1.2;
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.profileRole {
  font-size: 11px;
  font-weight: 800;
  color: rgba(15, 23, 42, 0.55);
  background: rgba(255, 255, 255, 0.35);
  border: 1px solid rgba(15, 23, 42, 0.06);
  padding: 2px 8px;
  border-radius: 999px;
  white-space: nowrap;
  flex: 0 0 auto;
}

.profileSub {
  margin-top: 4px;
  font-size: 12px;
  color: rgba(15, 23, 42, 0.6);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* 列表 */
.profileList {
  display: grid;
  gap: 6px;
  padding: 2px;
}

.profileItem {
  width: 100%;
  text-align: left;
  border: 1px solid rgba(15, 23, 42, 0.06);
  background: rgba(255, 255, 255, 0.3);
  color: #0b1220;

  padding: 10px 10px;
  border-radius: 10px;
  cursor: pointer;

  font-size: 12px;
  font-weight: 800;

  transition:
    background 0.15s ease,
    transform 0.15s ease;
}

.profileItem:hover {
  background: rgba(255, 255, 255, 0.5);
  transform: translateY(-1px);
}

/* 底部 */
.profileFoot {
  margin-top: 8px;
  padding-top: 8px;
  border-top: 1px solid rgba(15, 23, 42, 0.06);
}

.profileLogout {
  width: 100%;
  border: none;
  background: rgba(15, 23, 42, 0.06);
  color: #0b1220;

  padding: 10px 10px;
  border-radius: 10px;
  cursor: pointer;

  font-size: 12px;
  font-weight: 900;

  transition:
    background 0.15s ease,
    transform 0.15s ease;
}
.profileLogout:hover {
  background: rgba(15, 23, 42, 0.1);
  transform: translateY(-1px);
}
.roleBadge {
  height: 22px;
  padding: 0 10px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 800;
  letter-spacing: 0.2px;
}

.profileActions {
  display: grid;
  gap: 10px;
  margin-top: 12px;
}

.actionItem {
  height: 44px;
  border-radius: 12px;
  border: 1px solid rgba(15, 23, 42, 0.06);
  background: rgba(255, 255, 255, 0.28);
  backdrop-filter: blur(10px) saturate(140%);
  -webkit-backdrop-filter: blur(10px) saturate(140%);

  padding: 0 14px;
  display: flex;
  align-items: center;
  justify-content: space-between;

  font-size: 13px;
  font-weight: 800;
  color: #0b1220;

  cursor: pointer;
  transition:
    background 0.15s ease,
    transform 0.15s ease,
    border-color 0.15s ease;
}

.actionItem:hover {
  background: rgba(255, 255, 255, 0.46);
  border-color: rgba(15, 23, 42, 0.1);
  transform: translateY(-1px);
}

.chev {
  font-size: 18px;
  line-height: 1;
  color: rgba(15, 23, 42, 0.35);
}

.actionItem.danger {
  background: rgba(239, 68, 68, 0.08);
  border-color: rgba(239, 68, 68, 0.18);
  color: rgba(185, 28, 28, 1);
  justify-content: center;
}

.actionItem.danger:hover {
  background: rgba(239, 68, 68, 0.12);
}
@media (min-width: 1600px) {
  .nav {
    width: min(1500px, calc(100% - 64px));
  }
}
</style>
