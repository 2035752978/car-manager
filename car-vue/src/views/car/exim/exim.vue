<template>
    <div>
        <transition name="el-fade-in-linear">
            <div>
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item style="color: #a8a8a8">
                            进出口管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="" style="color: #409eff">进出口列表</a></el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="search">
                    <el-form class="demo-form-inline" size="small">
                        <el-form-item label="进出口车牌号" id="zi" style="width: 250px">
                            <el-input placeholder="请输入" v-model="name" style="width: 150px"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间" id="zi" style="width: 250px">
                            <el-date-picker v-model="startTime" type="date" placeholder="选择日期" style="width: 150px"
                                value-format="yyyy-MM-dd">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间" id="zi" style="width: 250px">
                            <el-date-picker v-model="endTime" type="date" placeholder="选择日期" style="width: 150px"
                                value-format="yyyy-MM-dd">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="进出口" id="zi" style="width: 250px">
                            <el-select v-model="value" placeholder="请选择" style="width: 150px">
                                <el-option v-for="item in options" :key="item.value" :label="item.label"
                                    :value="item.value">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-button size="mini" type="primary" @click="search(name)">查询</el-button>
                    </el-form>
                </div>
                <el-row style="height: 86px; padding: 0">
                    <div class="btn" @click="add()" style="width: 150px">
                        <el-button type="primary" icon="el-icon-plus">新增记录</el-button>
                    </div>
                </el-row>
                <el-table :data="list.slice((page - 1) * pnum, page * pnum)" tooltip-effect="dark" style="width: 100%"
                    border :row-style="{ height: '80px' }">
                    <el-table-column type="selection" width="68" align="center"></el-table-column>
                    <el-table-column label="姓名" width="auto" align="center">
                        <template slot-scope="scope">
                            {{ scope.row.realName == '' ? scope.row.apRealName : scope.row.realName }}
                        </template>
                    </el-table-column>
                    <el-table-column label="电话" show-overflow-tooltip align="center">
                        <template slot-scope="scope">
                            {{ scope.row.phone == '' ? scope.row.apPhone : scope.row.phone }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="plateNumber" label="进出口车牌号" width="auto" align="center">
                    </el-table-column>
                    <el-table-column prop="inGarageName" label="进出口大门" width="auto" align="center">
                    </el-table-column>
                    <el-table-column prop="totalTime" label="耗时(秒)" width="auto" align="center">
                    </el-table-column>
                    <el-table-column prop="payMoney" label="费用" width="auto" align="center">
                    </el-table-column>
                    <el-table-column prop="createTime" label="进口时间" show-overflow-tooltip align="center"></el-table-column>
                    <el-table-column prop="createTime" v-if="value==2" label="出口时间" show-overflow-tooltip align="center"></el-table-column>
                </el-table>
                <el-pagination :total="total" :page-sizes="ye" layout="total,sizes,prev,next,jumper" :page-size="pnum"
                    :current-page="page" @current-change="bian" @size-change="qie"></el-pagination>
            </div>
        </transition>
    </div>
</template>

<script>
import { getExim } from '../../../request/api'
export default {
    data() {
        return {
            list: [],
            page: 1, //当前页
            total: 0, //一共多少条
            pnum: 10, //每页显示多少条
            ye: [10],
            name: "",
            show: false,
            startTime: "",
            endTime: "",
            options: [
                {
                    label: '进口',
                    value: 1
                },
                {
                    label: '出口',
                    value: 2
                }
            ],
            value: 1
        };
    },
    created() {
        this.getData();
    },
    methods: {
        getData() {
            let params = {
                page: this.page,
                size: this.pnum
            }

            getExim(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data.records;
                    this.total = res.data.data.total;
                }
            })
        },
         add() {
            this.$router.push("/exim_add");
        },
        bian(v) {
            let params = {
                page: v,
                size: this.pnum
            }
            getExim(params).then((res) => {
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
            localStorage.setItem("setEximData", JSON.stringify(a))
            this.$router.push("/exim_type");
        },
        search(name) {
            let params = {
                page: this.page,
                size: this.pnum,
                searchName: name,
                beginTime: this.startTime,
                endTime: this.endTime,
                commonType: this.value
            }

            getExim(params).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data.records;
                }
            })
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