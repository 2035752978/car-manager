<template>
  <div style="float: left">
    <el-upload
      :class="{ hide: hideUpload }"
      :action="envs"
      :data="QiniuData"
      :http-request="upqiniu"
      list-type="picture-card"
      :before-upload="beforeUpload"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :on-error="uploadError"
      :on-success="handleSuccess"
      accept="image/jpeg, image/jpg, image/png"
      :file-list="fileList"
      :multiple="false"
      :limit="limit"
      :on-exceed="handleExceed"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt />
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Upload",
  props: {
    defaultValue: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      QiniuData: {
        //这里是直接绑定data的值
        key: "", //图片名字处理
        token: "", //七牛云token
        data: {},
        bucket: "",
      },
      envs: "http://upload.qiniup.com",
      dialogImageUrl: [],
      dialogVisible: false,
      fileList: [],
      picList: [],
      img: [],
      imgUrl: "",
      date: "",
      bind: "",
      limit: "",
      hideUpload: false,
    };
  },
  created() {
    const self = this;
    // axios({
    //   url: "https://api2.chunqian.xyz/api/wxlive/shopAdminToken/v1/qn_upload",
    //   method: "GET",
    //   headers: {
    //     project_token: "3jiayun",
    //   },
    // }).then((res) => {
    //   self.QiniuData.token = res.data.data.token;
    //   self.imgUrl = res.data.data.visitPrefix;
    //   self.date = res.data.data.folderPath;
    //   this.QiniuData.key = self.date;
    // });
  },
  methods: {
    handleRemove(file, fileList) {
      // console.log(file, fileList);
      let arr = this.picList;
      arr.forEach((v) => {
        if (v.id == file.uid) {
          this.picList.splice(v, 1);
        }
      });
      this.hideUpload = false;
    },
    handlePictureCardPreview(file) {
      const self = this;
      this.dialogImageUrl = self.url;
      this.dialogVisible = true;
    },
    beforeUpload(file) {
      //图片上传之前的方法
      this.QiniuData.data = file;
      this.QiniuData.key = this.QiniuData.key + `${file.name}`;
    },
    upqiniu(e) {
      const self = this;
      const uid = e.file.uid;
      const file = e.file;
      let config = {
        useCdnDomain: true, //表示是否使用 cdn 加速域名，为布尔值，true 表示使用，默认为 false。
        region: qiniu.region.z1, // 根据具体提示修改上传地区,当为 null 或 undefined 时，自动分析上传域名区域
      };
      let putExtra = {
        fname: this.QiniuData.key, //文件原文件名
        params: {}, //用来放置自定义变量
        mimeType: null, //用来限制上传文件类型，为 null 时表示不对文件类型限制；限制类型放到数组里： ["image/png", "image/jpeg", "image/gif"]
      };
      var observable = qiniu.upload(
        file,
        this.QiniuData.key,
        this.QiniuData.token,
        putExtra,
        config
      );
      observable.subscribe({
        next: (result) => {
          // 主要用来展示进度
          // console.log(result);
        },
        error: (errResult) => {
          // 失败报错信息
          console.log(errResult);
        },
        complete: (result) => {
          // 接收成功后返回的信息
          // console.log(result);
          self.url = self.imgUrl + this.QiniuData.key;
          this.bind = this.defaultValue;
          if (this.bind == 1) {
            this.limit = 1;
            this.picList.push({ id: uid, url: self.url });
            this.aa = true;
          } else if (this.bind == 2) {
            this.limit = 5;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          } else if (this.bind == 3) {
            this.limit = 10;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          } else if (this.bind == 4) {
            this.limit = 1;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          } else if (this.bind == 5) {
            this.limit = 1;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          } else if (this.bind == 6) {
            this.limit = 1;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          } else if (this.bind == 7) {
            this.limit = 5;
            this.aa = true;
            this.picList.push({ id: uid, url: self.url });
          }
          const data = {
            picList: this.picList,
            bind: this.bind,
          };
          this.$emit("getUpload", data);
        },
      });
    },
    uploadError(err, file, fileList) {
      //图片上传失败时调用
      this.$message({
        message: "上传出错，请重试！",
        type: "error",
        center: true,
      });
    },
    handleSuccess(resolvedValue) {
      console.log(resolvedValue);
    },
    handleExceed(files, fileList) {
      console.log(files, this.bind);
      this.$message.warning(
        `当前限制选择 ${this.limit} 个文件，本次选择了 ${files.length} 个文件，共选择了 ${fileList.length} 个文件`
      );
      this.hideUpload = fileList.length >= this.limit;
    },
  },
};
</script>

<!-- <style scoped src="../../assets/css/commodity.css"></style> -->
<style>
.hide .el-upload--picture-card {
  display: none;
}
</style>