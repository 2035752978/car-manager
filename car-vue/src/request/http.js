import axios from 'axios';
import eventBus from '@/utils/EventBus';

// 根据不同环境切换接口路径
let baseURL = 'http://localhost:8888/';

if (process.env.NODE_ENV === 'development') {
    // 可能会有开发环境特定的baseURL
}
else if (process.env.NODE_ENV === 'debug') {
    // 可能会有debug环境特定的baseURL
}
else if (process.env.NODE_ENV === 'production') {
    // 生产环境可能使用不同的baseURL或策略
}

const token = localStorage.getItem('token');  // 获取token

// 创建axios实例
const request = axios.create({
    baseURL: baseURL,  // 使用变量代替直接指定值
    headers: {
        Authorization: token ? `Bearer ${token}` : '',  // 格式化Token字符串 
        token: token,
        Accept: "application/json",
        "Content-Type": "application/json;charset=UTF-8"
    },
    timeout: 20000,  // 设定超时时间更长一些，默认1000太短了
});

// 请求拦截器，在发送请求之前做些什么(如果需要)
request.interceptors.request.use(config => {
    const updatedToken = localStorage.getItem('token');
    if (updatedToken) config.headers['Authorization'] = `Bearer ${updatedToken}`;
    return config;
}, error => Promise.reject(error));

// ...（其他代码）...

// 响应拦截器，在获取到响应数据后做些什么(比如统一错误处理)
request.interceptors.response.use(
    response => {
        // 直接返回响应数据，而不进行任何业务状态码判断
        return response;
    },
    error => {
        // 检查请求是否针对登录或登出端点
        const isAuthUrl = ['/login', '/logout'].some(path => error.config.url.includes(path));

        if (error.code === 'ECONNABORTED') {  
            // 超时错误  
            console.error('当前网络波动, 请重试!');  
            // 在这里，您可以考虑触发一个UI提示，或者进行其他用户友好的操作 
            eventBus.$emit('showMessage', { type: 'error', message: '当前网络波动哟~, 请重试!' });
        }else if
         (!isAuthUrl) { // 如果不是登录或登出请求，则检查HTTP状态码
            const status = error.response ? error.response.status : null;

            if (status === 401) {
                eventBus.$emit('showMessage', { type: 'error', message: '你好用户，请先登录哟' });
                redirectToLogin();
            } else if (status === 403) {
                eventBus.$emit('showMessage', { type: 'error', message: '您无权操作!' });
                //redirectToLogin();
            }
        }

        return Promise.reject(error);
    }
);

function redirectToLogin() {
    router.replace({ path: '/login', query: { redirect: router.currentRoute.fullPath } });
}

function showMessage(type, message) {
    Message({
        type: type,
        message: message,
        duration: 3000, // 显示时间为5秒
    });
}

export default request;