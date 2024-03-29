<template>
    <div>
        <transition name="el-fade-in-linear">
            <div v-show="show">
                <div class="top">
                    <el-breadcrumb separator="/">
                        <el-breadcrumb-item> 用户管理</el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="/user" style="font-weight: 400">用户列表</a></el-breadcrumb-item>
                        <el-breadcrumb-item>
                            <a href="javascript:" style="color: #409eff">车牌维护</a>
                        </el-breadcrumb-item>
                    </el-breadcrumb>
                </div>
                <div class="hr-30" style="width: 100%; height: 30px"></div>
				<el-table :data="list" tooltip-effect="dark" style="width: 100%"
				    border :row-style="{ height: '80px' }">
				    <el-table-column prop="plateNumber" label="车牌号" width="auto" align="center"></el-table-column>
				    </el-table-column>
				    <el-table-column label="操作" align="center">
				        <template slot-scope="scope">
				            <el-link icon="el-icon-delete" style="margin-left: 20px; font-size: 12px; color: #f56c6c"
				                @click="del(scope.$index + 1, scope.row)" :underline="false">删除</el-link>
				        </template>
				    </el-table-column>
				</el-table>
				<el-dialog title="添加车牌" :visible.sync="outerVisible">
					<div>
						<div class="list">
						    <div class="l_title"><b>*</b>车牌号</div>
						    <el-input placeholder="请输入内容" clearable type="text" v-model="addCard">
						    </el-input>
						</div>
					</div>
				    <div slot="footer" class="dialog-footer">
				      <el-button  @click="outerVisible = false">取消</el-button>
					  <el-button type="primary" @click="save()">确认</el-button>
				    </div>
				  </el-dialog>


                <div class="foot" style="margin-top: 30px">
                    <el-button type="primary" size="medium" @click="add()">添加</el-button>
                    <el-button plain size="medium" style="margin-left: 20px" @click="close()">关闭</el-button>
                </div>
            </div>
        </transition>
    </div>
</template>

<script>
import { getCar,addCard,delCard } from '@/request/api';

export default {
    data() {
        return {
            list:[],
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
			addCard:'',
			outerVisible:false
        };
    },
    created() {
        let that = this;
        this.show = false;
        setTimeout(function () {
            that.show = true;
        }, 10);
        this.getCarList();
    },
    methods: {
        getCarList() {
			let carId = JSON.parse(localStorage.getItem('setUserCar'));
			console.log(carId)
            getCar(carId.userId).then((res) => {
                if (res.data.code == 200) {
                    this.list = res.data.data;
                    
                }
            })
        },
		add(){
			this.outerVisible=true;
		},
		save(){
			let that=this;
			let carId = JSON.parse(localStorage.getItem('setUserCar'));
			let info={
				userId: carId.userId, //用户id
				plateNumber: this.addCard //车牌
			}
			addCard(info).then((res) => {
			    if (res.data.code == 200) {
			        that.getCarList()
					that.outerVisible=false;
			    }
			})
		},
		del(k, v) {
			console.log(v)
		    let token = localStorage.getItem("token");
		    this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
		        confirmButtonText: "确定",
		        cancelButtonText: "取消",
		        type: "warning",
		    }).then(() => {
		            let id = v.id;
		            delCard(id).then((res) => {
		                console.log(res);
		                if (res.data.code == 200) {
		                    setTimeout(() => {
		                        this.$message({
		                            message: '操作成功',
		                            type: 'success'
		                        });
		                        this.getCarList();
		                    }, 1000)
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
		                } else  {
						    this.$message.error(res.data.msg);
						   
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