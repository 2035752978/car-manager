<template>
    <div>
        <transition name="el-fade-in-linear">
            <div>
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item style="color: #a8a8a8">
                            菜单管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="" style="color: #409eff">菜单列表</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <!-- <div class="search">
                </div> -->

                <div class="body">
                    <el-tree :data="list.slice((page - 1) * pnum, page * pnum)" :expand-on-click-node="false"
                        node-key="id" :props="defaultProps">
                        <span class="custom-tree-node" slot-scope="{ node, data }">
                            <span style="display: block;">{{ node.label }}</span>
                            <span>
                                <el-button type="text" size="mini" @click="() => add(data)">
                                    新增
                                </el-button>
                                <el-button type="text" size="mini" @click="() => edit(data)">
                                    编辑
                                </el-button>
                                <el-button type="text" size="mini" @click="() => del(node, data)">
                                    删除
                                </el-button>
                            </span>
                        </span>
                    </el-tree>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
import { getMenu, delMenu } from '../../../request/api'
export default {
    data() {
        return {
            list: [],
            page: 1, //当前页
            total: 0, //一共多少条
            pnum: 100, //每页显示多少条
            ye: [100],
            name: "",
            show: false,
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            let params = {
                keyId: ''
            }

            getMenu(params).then((res) => {
                if (res.data.code == 200) {
                    // this.flattenMenu(res.data.data)
                    this.list = res.data.data.reverse();
                }
            })
        },
        flattenMenu(data) {
            data.forEach(item => {
                const { children, ...rest } = item; // 分离子菜单和当前项的其他信息
                this.list.push(rest); // 添加当前项到结果中

                if (children && children.length) {
                    this.flattenMenu(children);
                }
            });
            console.log(this.list.length);
        },
        add() {
            this.$router.push("/Menu_add");
        },
        bian(v) {
            let params = {
                page: v,
                size: this.pnum
            }
            getMenu(params).then((res) => {
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
        edit(a) {
            localStorage.setItem("setMenuData", JSON.stringify(a))
            this.$router.push("/Menu_edit");
        },
        del(k, v) {


            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let id = parseInt(v.id)
                delMenu(id).then((res) => {
                    if (res.data.code == 200) {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        setTimeout(() => {
                            this.getData();
                        }, 800)
                    } else if (res.data.code == 401) {
                        localStorage.clear();
                        this.$message.error('你好，请登录');
                        setTimeout(() => {
                            this.$router.push("/login");
                        }, 2000)
                    } else if (res.data.code == 403) {
                        localStorage.clear();
                        this.$message.error('登陆已过期，请重新登陆');
                        setTimeout(() => {
                            this.$router.push("/login");
                        }, 2000)
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
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

.body /deep/ {
    width: 100%;
    min-height: 78vh;
    font-size: 22px;

    button{
        font-size: 18px;
        margin-right: 20px;
    }

    .el-tree {
        width: 97%;
        height: 100%;
        padding-left: 3%;

        .el-tree-node__content {
            display: flex;
            align-items: center;
            height: 100px;
            cursor: pointer;
        }
    }
}
</style>