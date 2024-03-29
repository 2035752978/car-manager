<template>
    <div>
        <transition name="el-fade-in-linear">
            <div v-show="show">
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="/user" style="font-weight: 400">用户列表</a></el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="javascript:" style="color: #409eff">用户修改</a>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="hr-30" style="width: 100%; height: 30px"></div>
                <div class="list">
                    <div class="l_title"><b>*</b>用户名称</div>
                    <el-input placeholder="请输入内容" clearable v-model="list.realName">
                    </el-input>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>手机号</div>
                    <el-input placeholder="请输入内容" clearable v-model="list.phone">
                    </el-input>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>性别</div>
                    <div style="padding-top: 20px;">
                        <el-radio v-model="list.sex" label="0">男</el-radio>
                        <el-radio v-model="list.sex" label="1">女</el-radio>
                        <el-radio v-model="list.sex" label="2">未知</el-radio>
                    </div>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>用户邮箱</div>
                    <el-input placeholder="请输入内容" clearable v-model="list.email">
                    </el-input>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>配置角色</div>
                    <el-select v-model="value" placeholder="请选择" style="margin-top: 10px;width:150px">
                        <el-option v-for="item in roleList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>配置部门</div>
                    <el-select v-model="value1" placeholder="请选择" style="margin-top: 10px;width:150px">
                        <el-option v-for="item in deptList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="list">
                    <div class="l_title"><b>*</b>配置岗位</div>
                    <el-select v-model="value2" multiple placeholder="请选择" style="margin-top: 10px;width:150px">
                        <el-option v-for="item in postList" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
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
import { getRole, getDept, getPost, editUser } from '@/request/api';
export default {
    data() {
        return {
            list: "",
            show: false,
            fileList: [],
            roleList: [],
            deptList: [],
            postList: [],
            value: '',
            value1: '',
            value2: '',
            page: 1, //当前页
            pnum: 100, //每页显示多少条
        };
    },
    created() {
        let that = this;
        this.show = false;
        setTimeout(function () {
            that.show = true;
        }, 10);
        this.list = JSON.parse(localStorage.getItem('setUserData'));
        this.value = this.list.roleId;
        this.value1 = this.list.deptId;
        this.value2 = this.list.postLists;
        this.getRoles();
        this.getDepts();
        this.getPosts();
    },
    methods: {
        getRoles() {
            let params = {
                page: this.page,
                size: this.pnum
            }

            getRole(params).then((res) => {
                if (res.data.code == 200) {
                    let arr = res.data.data.records;
                    arr.forEach(v => {
                        this.roleList.push({
                            value: v.roleId,
                            label: v.roleName
                        })
                    });
                }
            })
        },
        getDepts() {
            let params = {
                page: this.page,
                size: this.pnum
            }

            getDept(params).then((res) => {
                if (res.data.code == 200) {
                    let arr = res.data.data.records;
                    arr.forEach(v => {
                        this.deptList.push({
                            value: v.deptId,
                            label: v.deptName
                        })
                    });
                }
            })
        },
        getPosts() {
            let params = {
                page: this.page,
                size: this.pnum
            }

            getPost(params).then((res) => {
                if (res.data.code == 200) {
                    let arr = res.data.data.records;
                    arr.forEach(v => {
                        this.postList.push({
                            value: v.postId,
                            label: v.postName
                        })
                    });
                }
            })
        },
        add() {
            let params = {
                userId: parseInt(this.list.userId),
                realName: this.list.realName,
                phone: this.list.phone,
                roleId: parseInt(this.value),
                sex: this.list.sex,
                email: this.list.email,
                // avatar:this.fileList[0],
                avatar: '',
                deptId: this.value1,
                postIds: this.value2
            }

            editUser(params).then((res) => {
                if (res.data.code == 200) {
                    this.$message({
                        type: "success",
                        message: "修改成功",
                    });
                    this.$router.push("/user");
                } else {
                    this.$message({
                        type: "warning",
                        message: res.data.msg,
                    });
                }
            })
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        },
        handleExceed(files, fileList) {
            this.$message.warning(`头像仅能上传一张`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        close() {
            this.$router.push("/user");
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