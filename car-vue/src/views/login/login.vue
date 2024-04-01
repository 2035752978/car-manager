<template>
    <div class="main">
        <div class="loginBox">
            <!-- 标题部分 -->
            <div class="title">校内车辆出入管理系统</div>
            <!-- 登录表单 -->
            <el-form ref="form" :model="form" class="loginInfo">
                <!-- 用户名输入框 -->
                <el-form-item>
                    <el-input v-model="form.userName" placeholder="请输入账号" clearable></el-input>
                </el-form-item>
                <!-- 密码输入框 -->
                <el-form-item>
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" clearable show-password></el-input>
                </el-form-item>
                <!-- 验证码部分 -->
                <el-form-item class="codeBox">
                    <el-input v-model="form.code" placeholder="请输入验证码" clearable></el-input>
                    <div class="identifybox" @click="refreshCode">
                        <sidentify :identifyCode="identifyCode"></sidentify>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item class="buttonItem">
                    <el-button type="primary" round @click="login">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
import sidentify from '../../components/sidentify'
import { getLogin } from '../../request/api'
export default {
  name: 'Login',
  props: {
    msg: String
  },
  components: { sidentify },
  data() {
    return {
      form: {
        userName: '',
        password: '',
        code: ''
      },
      identifyCodes: "1234567890", //验证码的数字库
      identifyCode: "",  // 验证码组件传值
    }
  },
  created() {
    this.refreshCode(); //初始化验证码
  },
  mounted() {
    // 验证码初始化
    this.identifyCode = '';
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
          this.randomNum(0, this.identifyCodes.length)
        ];
      }
    },
    login() {
      if (this.form.userName == "") {
        this.$message.error('请输入正确的用户名');
      } else if (this.form.password == "") {
        this.$message.error('请输入正确的密码');
      }
      else if (this.form.code == '') {
        this.$message.error('请输入验证码');
      } else if (this.form.code != this.identifyCode) {
        this.$message.error('验证码不正确');
      }
      else {
        let params = {
          username: this.form.userName,
          password: this.form.password
        }
        getLogin(params).then((res) => {
          console.log(res);
          if (res.data.code == 200) {
            console.log(res.data.data.menuList.data);
            localStorage.setItem("token", res.data.data.token)
            localStorage.setItem("listData", JSON.stringify(res.data.data.menuList.data));
            this.$router.push("/index");
          } else {
            this.$message.error(res.data.msg);
          }
        })
      }
    },
  }
}
</script>


<style scoped>
/*.main {*/
/*    display: flex;*/
/*    align-items: center; !* 居中对齐 *!*/
/*    justify-content: center; !* 水平居中 *!*/
/*    !*background: url('../../assets/carBanner.jpg') no-repeat;*!*/
/*    background: url('https://qiniu-pic.atri.wiki/img/ljcbj.png') no-repeat center center; !* 背景图片 *!*/
/*    background-size: cover; !* 背景图片覆盖整个容器 *!*/
/*}*/
.main {
    display: flex;
    /*align-items: flex-start; !* 调整对齐到顶部 *!*/
    /*justify-content: flex-end; !* 调整对齐到右边 *!*/
        align-items: center; /* 居中对齐 */
        justify-content: center; /* 水平居中 */
    height: 90vh;
    /*padding-right: 5%; !* 右边距 *!*/
    padding-top: 5%; /* 顶部距离 */
    background: linear-gradient(rgba(255, 255, 255, 0.1), rgba(255, 255, 255, 0.1)),
    url('https://qiniu-pic.atri.wiki/img/ljcbj2.jpg') no-repeat center center; /* 添加渐变效果并保持背景图片 */
    background-size: cover;
}

.title {
    margin-bottom: 1rem; /* 标题下方的间隔 */
    font-size: 1.8rem; /* 标题字体大小 */
    color: #106ba3; /* 标题字体颜色 */
}

.loginBox {
    width: 300px; /* 可以根据需要调整登录框宽度 */
    padding: 2rem; /* 内部间距 */
    background-color: rgba(255, 255, 255, 0.9); /* 背景颜色，轻微透明 */
    border-radius: 1rem; /* 边角圆滑度 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 盒子阴影效果 */
}

.loginInfo {
    display: flex;
    flex-direction: column; /* 项目垂直排列 */
    align-items: center; /* 水平居中对齐 */
}

.el-form-item {
    margin-bottom: 1rem; /* 表单项下方间隔 */
}

/* 输入框和按钮宽度统一 */
.el-input,
.el-button {
    width: 100%;
}

/* 验证码部分样式调整 */
.codeBox {
    display: flex; /* 使用flex布局 */
    justify-content: space-between; /* 两端对齐 */
}

/* 登录按钮位置调整 */
.buttonItem {
    margin-top: 1rem; /* 按钮上方间隔 */
}

.identifybox {
    cursor: pointer; /* 鼠标悬浮时手型指针 */
    /* 可以在这里为验证码框添加特定的样式 */
}
</style>