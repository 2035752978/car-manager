<template>
  <div>
    <transition name="el-fade-in-linear">
      <div>
        <div class="top">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item style="color: #a8a8a8">
              车辆文明统计
            </el-breadcrumb-item>
            <el-breadcrumb-item>
              <a href="" style="color: #409eff">车辆文明统计统计图</a></el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="search">
          <el-form class="demo-form-inline" size="small">
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
            <el-button size="mini" type="primary" @click="search(name)">查询</el-button>
          </el-form>
        </div>
        <h1 align="center" style="color: #00bcff ;">校园各学院违规车辆数量统计图</h1>
        <div>
          <div ref="pieChart" style="width: 100%; height: 400px;"></div>
          <!-- <el-table :data="list" tooltip-effect="dark" style="width: 100%" height="600" border
                :row-style="{ height: '80px' }">
                <el-table-column type="selection" width="68" align="center"></el-table-column>
                <el-table-column prop="deptId" label="部门ID" width="auto" align="center">
                </el-table-column>
                <el-table-column prop="deptName" label="部门名称" show-overflow-tooltip align="center">
                </el-table-column>
                <el-table-column prop="penaltyTotalMoney" label="罚款金额" show-overflow-tooltip align="center">
                </el-table-column>
                <el-table-column prop="createDay" label="创建时间" show-overflow-tooltip align="center">
                </el-table-column>
            </el-table> -->
        </div>
      </div>

    </transition>
  </div>
</template>

<script>
import {getViolate} from '../../../request/api'
import * as echarts from 'echarts';

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

      getViolate(params).then((res) => {
        if (res.data.code == 200) {
          this.list = res.data.data.records;
          this.initChart()
          // this.total = res.data.data.total;
        }
      })
    },
    search(name) {
      let params = {
        page: this.page,
        size: this.pnum,
        relationId: name,
        beginTime: this.startTime,
        endTime: this.endTime
      }

      getViolate(params).then((res) => {
        if (res.data.code == 200) {
          this.list = res.data.data.records;
          this.initChart()
        }
      })
    },

    initChart() {
      const myChart = echarts.init(this.$refs.pieChart)
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            data: this.list
          }
        ]
      };
      myChart.setOption(option);

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

.el-form-item > > > .el-input {
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

#zi > > > .el-form-item__label {
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

.red-text-row td {
  color: red;
}
</style>