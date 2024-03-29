<template>
  <div>
    <el-container>
      <el-aside width="210px">
        <div class="title">
          校内车辆出入管理系统
        </div>
        <el-menu :collapse="isCollapse" :default-active="activeIndex" :default-openeds="defaultOpeneds" class="elMenu" background-color="#33ccff"
          text-color="#fff" active-text-color="#fff" :unique-opened="true" router ref="elMenu" @select="menuSelect">
          <!-- 递归动态菜单 -->
          <myitem :data="dataList"></myitem>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
          <div style="float: right; color: #686868">
            {{ name }}
            <div style="padding: 0 16px; float: right; cursor: pointer" @click="quit">
              退出登录
            </div>
          </div>
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { getLogout } from '../request/api'
import myitem from "../components/myitem.vue";
import eventBus from '@/utils/EventBus';
export default {
  components: {
    myitem,
  },
  data() {
    return {
      name: "",
      dataList: [],
      isCollapse: false,
      activeIndex: this.$route.path,
      defaultOpeneds: [], // 初始化defaultOpeneds为空数组
    };
  },
  created() {
    this.dataList = JSON.parse(localStorage.getItem('listData'));
     this.setDefaultOpeneds();
    eventBus.$on('showMessage', ({ type, message }) => {
      this.showMessage(type, message);
    });
  },
  mounted() { },
  methods: {
    quit() {
      let params = {
        token: localStorage.getItem('token')
      }
      getLogout(params).then((res) => {
        if (res.data.code == 200) {
          localStorage.clear();
          this.$router.push({
            path: "/login",
          });
        } else {
          this.$message.error(res.data.msg);
        }
      })
    },
    menuSelect(index) {
      this.activeIndex = index;
    },
     setDefaultOpeneds() {  
      // 假设你有一个方法可以根据用户的权限获取默认展开的菜单项索引  
      // 这里只是一个示例，你需要根据你的实际逻辑来实现  
      const userPermissions = JSON.parse(localStorage.getItem('listData')); // 假设权限数据存储在这里  
      const defaultOpenIndexes = userPermissions.map(permission => permission.id); // 假设每个权限对象都有一个menuIndex属性  
      this.defaultOpeneds = defaultOpenIndexes; // 设置defaultOpeneds为计算得到的索引数组  
      console.log(defaultOpeneds)
    }, 
    showMessage(type, message) {
      Message({
        type: type,
        message: message,
        duration: 2000, // 显示时间为5秒
      });
    }
  },
};
</script>

<style scoped>
.el-header {
  background-color: #fff;
  color: #ffffff;
  line-height: 60px;
}

.el-aside::-webkit-scrollbar {
  display: none;
}

.title {
  width: 209px;
  height: 60px;
  text-align: center;
  line-height: 60px;
  border: 0;
  color: #ffffff;
  font-weight: bold;
  font-size: 25px;
}

.title img {
  vertical-align: middle;
}

.el-aside {
  color: #fff;
  min-height: 100vh !important;
  height: auto;
  background: #409EFF;
}

.el-menu {
  width: 100%;
  background: #409EFF;
  color: #fff;
}

.wz img {
  margin-left: 5px;
}

.wz span {
  font-size: 14px;
  font-family: PingFangSC-Medium, PingFang SC;
  font-weight: 500;
}

.el-menu :hover {
  background: #282a2a;
}

.el-menu>>>.el-menu--inline {
  background: #242832;
  text-align: center;
  color: #feb822;
}

.el-menu>>>.el-menu-item {
  background: #242832;
  color: #959999;
}

.el-menu>>>.el-menu-item:hover {
  background-color: #765d21;
  color: #ffffff;
}

.el-main {
  min-width: 1300px;
  width: auto;
  padding: 0px;
  min-height: calc(100vh - 60px);
  height: auto;
  background-color: #f3f5fb;
  overflow-y: scroll;
}

.el-main::-webkit-scrollbar {
  display: none;
}

.fade-enter {
  opacity: 0;
}

.fade-leave {
  opacity: 1;
}

.fade-enter-active {
  transition: opacity 0.5s;
}

.fade-leave-active {
  opacity: 0;
  transition: opacity 0.5s;
}
</style>
