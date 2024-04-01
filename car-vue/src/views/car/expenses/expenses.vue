<template>
    <div>
        <transition name="el-fade-in-linear">
            <div>
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item style="color: #a8a8a8">
                            停放费用管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="" style="color: #409eff">停车费用列表</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="search">
                    <el-form class="demo-form-inline" size="small">
                        <el-form-item label="规则信息" id="zi" style="width: 250px">
                            <el-input placeholder="请输入" v-model="name" style="width: 150px"></el-input>
                        </el-form-item>
                        <el-button size="mini" type="primary" @click="search(name)">查询</el-button>
                    </el-form>
                </div>
                <el-row style="height: 86px; padding: 0">
                    <div class="btn" @click="add()" style="width: 150px">
                        <el-button type="primary" icon="el-icon-plus">新增停车费用</el-button>
                    </div>
                    <div class="btn" @click="refresh()" style="width: 150px">
                        <el-button type="primary" icon="el-icon-refresh">缓存配置刷新</el-button>
                    </div>
                </el-row>
                <el-table :data="list" tooltip-effect="dark" style="width: 100%" :row-style="{ height: '80px' }" border height="600">
                    <el-table-column type="selection" width="68" align="center"></el-table-column>
                    <el-table-column prop="id" label="停车费用ID" width="auto" align="center"></el-table-column>
                    <el-table-column prop="ruleMsg" label="停车费用规则信息" show-overflow-tooltip align="center">
                    </el-table-column>
                    <el-table-column prop="ruleLevel" label="停车费用等级" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column prop="availableMinParking" label="最小计算停车时间" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column prop="availableMaxParking" label="最大计算停车时间" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column prop="costMoney" label="停车费用(元/小时)" show-overflow-tooltip align="center">
                    </el-table-column>
                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-button type="primary" size="mini" icon="el-icon-edit" @click="edit(scope.row)" >编辑</el-button>
                            <el-button type="danger" size="mini" icon="el-icon-delete" @click="del(scope.$index + 1, scope.row)" style="margin-left: 10px;">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- <el-pagination :total="total" :page-sizes="ye" layout="total,sizes,prev,next,jumper" :page-size="pnum"
                    :current-page="page" @current-change="bian" @size-change="qie"></el-pagination> -->
            </div>
        </transition>
    </div>
</template>

<script>
import { getExpenses, delExpenses, refreshExpenses } from '../../../request/api'
export default {
    data() {
        return {
            list: [],
            // page: 1, //当前页
            // total: 0, //一共多少条
            // pnum: 20, //每页显示多少条
            // ye: [20],
            name: "",
            show: false,
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            let params = {
                // page: this.page,
                // size: this.pnum
            }

            getExpenses(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                }
            })
        },
        add() {
            this.$router.push("/expenses_add");
        },
        search(name) {
            let params = {
                page: this.page,
                size: this.pnum,
                ruleMsg: name
            }

            getExpenses(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                }
            })
        },
        edit(a) {
            localStorage.setItem("setExpensesData", JSON.stringify(a))
            this.$router.push("/expenses_edit");
        },
        del(k, v) {
            this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
            let id = parseInt(v.id)
            delExpenses(id).then((res) => {
                if (res.data.code == 200) {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    setTimeout(() => {
                        this.getData();
                    }, 800)
                } else {
                    this.$message({
                        type: "info",
                        message: res.data.msg,
                    });
                }
            })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
        refresh() {
            let params = {
                page: this.page,
                size: this.pnum,
                ruleMsg: name
            }
            refreshExpenses(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                    this.$message({
                        message: '刷新成功',
                        type: 'success'
                    });
                }
            })
        }
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