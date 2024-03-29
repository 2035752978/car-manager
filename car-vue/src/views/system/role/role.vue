<template>
    <div>
        <transition name="el-fade-in-linear">
            <div>
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item style="color: #a8a8a8">
                            角色管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="" style="color: #409eff">角色列表</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="search">
                    <el-form class="demo-form-inline" size="small">
                        <el-form-item label="角色名称" id="zi" style="width: 250px">
                            <el-input placeholder="请输入" v-model="name" style="width: 150px"></el-input>
                        </el-form-item>
                        <el-button size="mini" type="primary" @click="search(name)">查询</el-button>
                    </el-form>
                </div>
                <el-row style="height: 86px; padding: 0">
                    <div class="btn" @click="add()" style="width: 150px">
                        <el-button type="primary" icon="el-icon-plus">新增角色</el-button>
                    </div>
                </el-row>
                <el-table :data="list.slice((page - 1) * pnum, page * pnum)" tooltip-effect="dark" style="width: 100%"
                    border :row-style="{ height: '80px' }">
                    <el-table-column type="selection" width="68" align="center"></el-table-column>
                    <el-table-column prop="roleName" label="角色名称" width="auto" align="center">
                    </el-table-column>
                    <el-table-column label="角色状态" width="auto" align="center">
                    <template slot-scope="scope">
                      {{ scope.row.status == 0 ? '正常' : '停用' }}
                    </template>
                    </el-table-column>
                    <el-table-column prop="remark" label="备注" show-overflow-tooltip align="center">
                    </el-table-column>
                  <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-link icon="el-icon-edit-outline" v-if="scope.row.roleId != 1"
                                style="font-size: 12px; color: #409eff" @click="edit(scope.row)"
                                :underline="false">编辑</el-link>
                            <el-link icon="el-icon-edit-outline"
                                style="font-size: 12px; margin-left: 20px; color: #67c23a" @click="assign(scope.row)"
                                v-if="scope.row.roleId != 1" :underline="false">分配权限</el-link>
                            <el-link icon="el-icon-delete" v-if="scope.row.roleId != 1"
                                style="margin-left: 20px; font-size: 12px; color: #f56c6c"
                                @click="del(scope.$index + 1, scope.row)" :underline="false">删除</el-link>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination :total="total" :page-sizes="ye" layout="total,sizes,prev,next,jumper" :page-size="pnum"
                    :current-page="page" @current-change="bian" @size-change="qie"></el-pagination>
            </div>
        </transition>
    </div>
</template>

<script>
import { getRole, delRole } from '../../../request/api'
export default {
    data() {
        return {
            list: [],
            page: 1, //当前页
            total: 0, //一共多少条
            pnum: 10, //每页显示多少条
            ye: [10],
            name: "",
            phone: "",
            sname: "",
            show: false,
        };
    },
    created() {
        this.getData();
    },
    methods: {
        add() {
            this.$router.push("/role_add");
        },
        getData() {
            let params = {
                page: this.page,
                size: this.pnum
            }

            getRole(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data.records;
                    this.total = res.data.data.total;
                }
            })
        },
        bian(v) {
            let params = {
                page: v,
                size: this.pnum
            }
            getRole(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data.records;
                    this.total = res.data.data.total;
                }
            })
        },
        qie(v) {
            this.pnum = v;
            this.page = 1;
        },
        search(name) {
            let params = {
                page: this.page,
                size: this.pnum,
                roleName: name
            }

            getRole(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data.records;
                }
            })
        },
        edit(a) {
            localStorage.setItem("setRoleData", JSON.stringify(a))
            this.$router.push("/role_edit");
        },
        assign(a) {
            this.$router.push("/role_assign/" + a.roleId);
        },
        del(k, v) {
            this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    let id = parseInt(v.roleId)
                    delRole(id).then((res) => {
                        console.log(res);
                        if (res.data.code == 200) {
                            this.$message({
                                message: '操作成功',
                                type: 'success'
                            });
                            this.getData();
                        } else if (res.data.code == 401) {
    
                            this.$message.error('你好，请登录');
                            setTimeout(() => {
                                this.$router.push("/login");
                            }, 800)
                        } else if (res.data.code == 403) {
    
                            this.$message.error('登陆已过期，请重新登陆');
                            setTimeout(() => {
                                this.$router.push("/login");
                            }, 2000)
                        }
                    })
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
    },
};
</script>

<style scoped lang="scss">
.top {
    width: 20%;
    height: 60px;
    position: absolute;
    top: 0;
    margin-left: 1%;
}

.el-breadcrumb {
    width: 100%;
    height: 60px;
    line-height: 60px;
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #A8A8A8;
}

.search {
    width: 100%;
    height: 72px;
    margin: 0 auto;
    line-height: 72px;
    border-top: 1px solid #F2F2F2;
    background-color: #fff;
}

.el-form {
    width: 95%;
    height: 100%;
    margin: 0 auto;
    line-height: 72px;
}

.el-form-item {
    width: 360px;
    float: left;
    margin-top: 20px;
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #38393A;
}

#search {
    width: 112px;
    height: 32px;
    background: #FEB822;
    border-radius: 4px;
    font-size: 13px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #242832;
}

.el-form-item>>>.el-input {
    width: 281px;
    border-radius: 4px;
}

.el-select {
    width: 85px;
}

.el-input {
    width: 70px;
}

.el-row {
    width: 95%;
    height: 45px;
    margin: 0 auto;
    padding-top: 10px;
}

#add {
    float: right;
}

.el-table-column img {
    width: 50px;
    height: 50px;
}

.el-pagination {
    text-align: center;
}

#zi>>>.el-form-item__label {
    font-weight: 700;
}

.el-icon--right {
    margin: 0px;
}

.btn {
    float: right;
    width: 100px;
    height: 85px;
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: #6F6F6F;
    line-height: 85px;
}

.btn img {
    width: 16px;
    height: 16px;
    vertical-align: middle;
    margin: 0 auto;
}
</style>