<template>
    <div>
        <transition name="el-fade-in-linear">
            <div v-show="show">
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item>车配置管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="/carDisposition" style="font-weight: 400">车配置列表</a></el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="javascript:" style="color: #409eff">车配置修改</a>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="hr-30" style="width: 100%; height: 30px"></div>
                <div class="list">
                    <div class="l_title"><b>*</b>配置开始时间</div>
                    <el-input placeholder="请输入内容" type="number" v-model="list.commonStr" clearable>
                    </el-input>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>配置结束时间</div>
                    <el-input placeholder="请输入内容" type="number" clearable v-model="list.commonStr1">
                    </el-input>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>备注</div>
                    <el-input placeholder="请输入内容" clearable v-model="list.remark">
                    </el-input>
                </div>
                <div class="foot" style="margin-top: 30px">
                    <el-button type="primary" size="medium" @click="add()">修改</el-button>
                    <el-button plain size="medium" style="margin-left: 20px" @click="close()">关闭</el-button>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
import { editCarDisposition } from '../../../request/api'
export default {
    data() {
        return {
            list: "",
            show: false,
        };
    },
    created() {
        let that = this;
        this.show = false;
        setTimeout(function () {
            that.show = true;
        }, 10);
        this.list = JSON.parse(localStorage.getItem('setCarDisposition'));
    },
    methods: {
        add() {
            let params = {
                id: parseInt(this.list.id),
                commonStr: this.list.commonStr,
                remark: this.list.remark,
                commonStr1: this.list.commonStr1,
            }

            editCarDisposition(params).then((res) => {
                if (res.data.code == 200) {
                    this.$message({
                        type: "success",
                        message: "修改成功",
                    });
                    this.$router.push("/carDisposition");
                } else {
                    this.$message({
                        type: "warning",
                        message: res.data.msg,
                    });
                }
            })
        },
        close() {
            this.$router.push("/role");
        },
    },
};
</script>

<style scoped>
.top {
    width: 100%;
    height: 50px;
    margin: 0 auto;
    border-bottom: 1px solid #F4F4F4;
    padding-left: 33px;
}

.el-breadcrumb {
    width: 100%;
    height: 50px;
    line-height: 50px;
}

.upload {
    width: 90%;
    min-height: 80px;
    height: auto;
    margin: 0 auto;
    line-height: 80px;
    margin-bottom: 10px;
}

.title {
    width: 100px;
    float: left;
    height: 100%;
    text-align: center;
    font-size: 12px;
}

.el-upload__tip {
    width: auto;
    height: 40px;
    line-height: 40px;
    margin-left: 130px;
    clear: both;
}

.tu {
    width: 200px;
    height: 160px;
    margin-left: 60px;
}

.list {
    width: 90%;
    height: 60px;
    margin: 0 auto;
    margin-bottom: 15px;
}

.l_title {
    width: 100px;
    height: 100%;
    text-align: right;
    line-height: 60px;
    float: left;
    /* background-color: #fbfbfb; */
    font-size: 14px;
    font-weight: 500;
    margin-right: 40px;
    color: #606266;
}

#table>>>.el-form-item__label {
    width: 110px;
    height: 100%;
    text-align: right;
    font-size: 14px;
    line-height: 60px;
    float: left;
    margin-right: 30px;
    font-weight: 500;
}

#table>>>.el-form-item__error {
    margin-left: 12%;
}


.l_title b {
    color: #eb5839;
}

.el-input {
    width: 40%;
    line-height: 60px;
}

.foot {
    width: 90%;
    height: 60px;
    margin: 0 auto;
    padding-left: 6%;
    margin-top: 30px;
}

.image {
    width: 90%;
    min-height: 100px;
    height: auto;
    margin: 0 auto;
    line-height: 100px;
    margin-bottom: 10px;
}

.block {
    height: 120px;
}

.el-image {
    float: left;
    margin-right: 10px;
    width: 146px;
    height: 146px;
    border-radius: 4px;
}

.m_title {
    width: 100%;
    height: 60px;
    line-height: 60px;
    border-bottom: 1px solid #F4F4F4;
    margin-top: 30px;
    margin-bottom: 30px;
}

.cat {
    width: 95%;
    height: 100%;
    margin: 0 auto;
    font-size: 18px;
}

.del {
    float: left;
    width: 30px;
    font-size: 30px;
    line-height: 60px;
    text-align: center;
    margin-left: 2%;
}
</style>