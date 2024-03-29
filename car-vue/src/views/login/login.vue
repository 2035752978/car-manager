<template>
  <div class="main">
    <div class="loginBox">
      <div class="title">校内车辆出入管理系统</div>
      <el-form ref="form" :model="form" class="loginInfo">
        <el-form-item>
          <el-input v-model="form.userName" placeholder="请输入账号" clearable style="width: 24.3vw;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" placeholder="请输入密码" clearable show-password
            style=" width: 24.3vw;"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.code" placeholder="请输入验证码" clearable style="width: 18vw;"></el-input>
          <div class="identifybox" @click="refreshCode">
            <sidentify :identifyCode="identifyCode"></sidentify>
          </div>
        </el-form-item>
        <el-button type="primary" round @click="login">登录</el-button>
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
.main {
  width: 100vw;
  height: 100vh;
  display: flex;
  /* flex-direction: row; */
  align-items: center;
  justify-content: center;
  background: url('../../assets/carBanner.jpg') no-repeat;
  background-size: 100% 100%;
}

.title {
  width: 100%;
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 30px;
  color: #0ba5ff;
}

.loginBox {
  width: 35vw;
  height: 45vh;
  background-color: #fffcd3;
  border-radius: 25px;
  border: 1px solid #848484;
  padding: 20px;
}

.loginInfo {
  width: 70%;
  height: 75%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding-top: 40px;
  margin: 0 auto;

  .el-form-item {
    width: 100%;
    height: 25%;
    margin: 0;
    display: flex;
    /* justify-content: center; */

  }

  /deep/.el-form-item__content {
    display: flex;
    flex-direction: row;
  }


  button {
    width: 240px;
    height: 40px;
  }
}
</style>