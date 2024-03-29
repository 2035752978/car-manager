import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Vuex from './store/index';


Vue.config.productionTip = false
Vue.use(ElementUI);

// 路由拦截器
router.beforeEach((to, from, next) => {
  let token = localStorage.getItem("token");
  if (to.matched.length != 0) {
    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
      if (token != null) { // 通过vuex state获取当前的user是否存在
        next();
      } else {
        next({
          path: '/login',
          query: {
            redirect: to.fullPath
          } // 将跳转的路由path作为参数，登录成功后跳转到该路由
        })
      }
    } else {
      if (token != null) { // 判断是否登录
        if (to.path != "/" && to.path != "/login") { //判断是否要跳到登录界面
          next()
        } else {
          /**
           * 防刷新，如果登录，修改路由跳转到登录页面，修改路由为登录后的首页 
           */
          next({
            path: '/index'
          })
        }
      } else {
        next();
      }
    }
  } else {
    next({
      path: '/login',
      query: {
        redirect: to.fullPath
      } // 将跳转的路由path作为参数，登录成功后跳转到该路由
    })
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
