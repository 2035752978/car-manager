<template>
    <div>
        <template v-for="(item, index) in data">
            <!-- 因为有子集和无子集渲染html标签不一样，所以要分为两种情况
             情况一：有子集的情况：                         -->
            <el-submenu :key="index" :index="item.id" v-if="item.children.length > 0">
                <template slot="title">
                    <i :class="item.icon"></i>
                    <span>{{ item.label }}</span>
                </template>
                <myitem :data="item.children"></myitem>
            </el-submenu>
            <!-- 情况二：没子集的情况 -->
            <el-menu-item :key="item.url" v-else :index="item.path">
                <i :class="item.icon"></i>
                <span slot="title">{{ item.label }}</span>
            </el-menu-item>
        </template>
    </div>
</template>

<script>
export default {
    name: "myitem",
    props: {
        data: {
            type: Array,
            default: () => [],
        },
    },
};
</script>
<style>
/* 设定一些基本的颜色变量，以简化颜色管理和提升一致性 */
:root {
    --aside-bg-color: #f0f2f5; /* 浅灰色背景，让导航栏看起来更轻盈 */
    /*--menu-text-color: #606266; !* 菜单文字颜色 *!*/
    --menu-active-color: #409eff; /* Element UI 默认蓝色 */
    --menu-active-text-color: #ffffff; /* 激活菜单项的文字颜色 */
    --menu-hover-bg-color: #ecf5ff; /* 菜单项悬停背景颜色 */
}

.el-aside {
    background-color: var(--aside-bg-color);
    padding-top: 10px; /* 顶部空白，让标题不紧贴边缘 */
}

.title {
    background-color: transparent; /* 透明背景或其他颜色 */
    border-radius: 10px; /* 圆角效果 */
    color: var(--menu-text-color);
    /*padding: 15px 20px; !* 内边距调整 *!*/
    margin-bottom: 10px; /* 与菜单项之间的间隔 */
    /*box-shadow: 0 4px 6px rgba(0,0,0,0.1); !* 轻微的阴影效果 *!*/
    font-size: 19px;
}

/* 菜单项样式 */
.el-menu {
    background-color: var(--aside-bg-color);
    border-radius: 10px; /* 整个菜单的圆角效果 */
    /*box-shadow: 0 4px 6px rgba(0,0,0,0.1); !* 轻微的阴影效果 *!*/
}

/* 菜单项悬停与激活状态样式 */
.el-menu-item:hover,
.el-submenu__title:hover,
.el-menu-item.is-active,
.el-submenu__title.is-active {
    background-color: var(--menu-hover-bg-color);
    border-radius: 10px; /* 圆角效果 */
    color: var(--menu-active-text-color);
}

/* 激活的菜单项样式 */
.el-menu-item.is-active {
    background-color: var(--menu-active-color);
}

/* 菜单文字颜色 */
.el-menu-item,
.el-submenu__title {
    color: var(--menu-text-color);
}

/* 菜单项图标 */
.el-menu-item i,
.el-submenu__title i {
    color: var(--menu-text-color); /* 图标颜色与文字保持一致 */
    margin-right: 8px; /* 图标与文字之间的距离 */
}

/* 嵌套子菜单样式 */
.el-menu .el-submenu {
    border-radius: 10px; /* 圆角效果 */
}

/* 子菜单展开箭头颜色 */
.el-menu .el-submenu__title .el-submenu__icon-arrow {
    color: var(--menu-text-color);
}

</style>