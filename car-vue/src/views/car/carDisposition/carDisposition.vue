<template>
    <div>
        <transition name="el-fade-in-linear">
            <div>
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item style="color: #a8a8a8">
                            车配置管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="" style="color: #409eff">车配置列表</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="search">
                    <el-form class="demo-form-inline" size="small">
                        <el-form-item label="配置ID" id="zi" style="width: 250px">
                            <el-input placeholder="请输入" v-model="name" style="width: 150px"></el-input>
                        </el-form-item>
                        <el-button size="mini" type="primary" @click="search(name)">查询</el-button>
                    </el-form>
                </div>
                <el-row style="height: 86px; padding: 0">
                    <div class="btn" @click="refresh()" style="width: 150px">
                        <el-button type="primary" icon="el-icon-refresh">缓存配置刷新</el-button>
                    </div>
                </el-row>
                <el-table :data="list.slice((page - 1) * pnum, page * pnum)" tooltip-effect="dark" style="width: 100%"
                    border :row-style="{ height: '80px' }">
                    <el-table-column type="selection" width="68" align="center"></el-table-column>
                    <el-table-column prop="id" label="id" show-overflow-tooltip align="center">
                    </el-table-column>
                  <el-table-column prop="settingName" label="规则名称" show-overflow-tooltip align="center"></el-table-column>
                  <el-table-column prop="commonStr" label="配置1" show-overflow-tooltip align="center"></el-table-column>
                  <el-table-column prop="commonStr1" label="配置2" show-overflow-tooltip align="center"> </el-table-column>
                  <el-table-column prop="remark" label="备注" show-overflow-tooltip align="center"> </el-table-column>

                    <el-table-column label="操作" align="center">
                        <template slot-scope="scope">
                            <el-link icon="el-icon-edit-outline" v-if="scope.row.roleId != 1"
                                style="font-size: 12px; color: #409eff" @click="edit(scope.row)"
                                :underline="false">编辑</el-link>
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
import { getCarDisposition, refreshCarDisposition } from '../../../request/api'
export default {
    data() {
        return {
            list: [],
            page: 1, //当前页
            total: 0, //一共多少条
            pnum: 20, //每页显示多少条
            ye: [20],
            name: "",
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

            getCarDisposition(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                }
            })
        },
        bian(v) {
            let params = {
                page: v,
                size: this.pnum
            }
            getCarDisposition(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
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
                id: parseInt(name)
            }

            getCarDisposition(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                }
            })
        },
        edit(a) {
            localStorage.setItem("setCarDisposition", JSON.stringify(a))
            this.$router.push("/carDisposition_edit");
        },
        refresh() {
            refreshCarDisposition().then((res) => {
                this.$message({
                    message: '刷新成功',
                    type: 'success'
                });
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