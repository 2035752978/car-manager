import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    //登录页
    {
        path: '/login',
        name: 'login',
        component: () => import('../views/login/login.vue'),
        meta: {
            requireAuth: false
        },
    },
    // 后台首页
    {
        path: '/',
        name: '',
        component: () => import('../views/index.vue'),
        meta: {
            requireAuth: true
        },
        children: [
            {
                path: '/index',
                name: 'home',
                component: () => import('../views/index/home.vue'),
                meta: {
                    title: "首页",
                    requireAuth: true
                }
            },
            // 用户页
            {
                path: '/user',
                name: 'user',
                component: () => import('../views/system/user/user.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/user_add',
                name: 'user_add',
                component: () => import('../views/system/user/user_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/user_edit',
                name: 'user_edit',
                component: () => import('../views/system/user/user_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
			{
			    path: '/user_car',
			    name: 'user_car',
			    component: () => import('../views/system/user/user_car.vue'),
			    meta: {
			        requireAuth: true
			    },
			},
            // 角色页
            {
                path: '/role',
                name: 'role',
                component: () => import('../views/system/role/role.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/role_add',
                name: 'role_add',
                component: () => import('../views/system/role/role_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/role_edit',
                name: 'role_edit',
                component: () => import('../views/system/role/role_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/role_assign/:id',
                name: 'role_assign',
                component: () => import('../views/system/role/role_assign.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 菜单页
            {
                path: '/menu',
                name: 'menu',
                component: () => import('../views/system/menu/menu.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/menu_add',
                name: 'menu_add',
                component: () => import('../views/system/menu/menu_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/menu_edit',
                name: 'menu_edit',
                component: () => import('../views/system/menu/menu_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 部门页
            {
                path: '/department',
                name: 'department',
                component: () => import('../views/system/department/department.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/department_add',
                name: 'department_add',
                component: () => import('../views/system/department/department_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/department_edit',
                name: 'department_edit',
                component: () => import('../views/system/department/department_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 岗位页
            {
                path: '/post',
                name: 'post',
                component: () => import('../views/system/post/post.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/post_add',
                name: 'post_add',
                component: () => import('../views/system/post/post_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/post_edit',
                name: 'post_edit',
                component: () => import('../views/system/post/post_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 消息页
            {
                path: '/notice',
                name: 'notice',
                component: () => import('../views/system/notice/notice.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/notice_add',
                name: 'notice_add',
                component: () => import('../views/system/notice/notice_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/notice_edit',
                name: 'notice_edit',
                component: () => import('../views/system/notice/notice_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 车辆管理开始
            //车配置页
            {
                path: '/carDisposition',
                name: 'carDisposition',
                component: () => import('../views/car/carDisposition/carDisposition.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/carDisposition_edit',
                name: 'carDisposition_edit',
                component: () => import('../views/car/carDisposition/carDisposition_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 进出口页
            {
                path: '/exim',
                name: 'exim',
                component: () => import('../views/car/exim/exim.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/exim_add',
                name: 'exim_add',
                component: () => import('../views/car/exim/exim_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 停放费用页
            {
                path: '/expenses',
                name: 'expenses',
                component: () => import('../views/car/expenses/expenses.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/expenses_add',
                name: 'expenses_add',
                component: () => import('../views/car/expenses/expenses_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/expenses_edit',
                name: 'expenses_edit',
                component: () => import('../views/car/expenses/expenses_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 访客预约管理页
            {
                path: '/booking',
                name: 'booking',
                component: () => import('../views/car/booking/booking.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/booking_add',
                name: 'booking_add',
                component: () => import('../views/car/booking/booking_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/booking_edit',
                name: 'booking_edit',
                component: () => import('../views/car/booking/booking_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 违停车辆页
            {
                path: '/parking',
                name: 'parking',
                component: () => import('../views/car/parking/parking.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/parking_add',
                name: 'parking_add',
                component: () => import('../views/car/parking/parking_add.vue'),
                meta: {
                    requireAuth: true
                },
            },
            {
                path: '/parking_edit',
                name: 'parking_edit',
                component: () => import('../views/car/parking/parking_edit.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 违规信息
            {
                path: '/carViolate',
                name: 'carViolate',
                component: () => import('../views/datas/carViolate/carViolate.vue'),
                meta: {
                    requireAuth: true
                },
            },
            // 车辆评比s
            {
                path: '/rating',
                name: 'rating',
                component: () => import('../views/datas/rating/rating.vue'),
                meta: {
                    requireAuth: true
                },
            },
        ]
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router