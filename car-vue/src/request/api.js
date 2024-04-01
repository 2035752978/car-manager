import request from "./http";

// get方式 
// export const GetAPI = () => request.get('/prod-api/system/user/getInfo');

// 登录
export const getLogin = (params) => request.post('/login', params);

// 登录
export const getLogout = (params) => request.post('/logout', params);

// 用户管理
export const getUser = (params) => request.get('/system/user/showUserList', { params, params });

//用户添加
export const addUser = (params) => request.post('/system/user/addUser', params);

// 用户删除
export const delUser = (id) => request.delete(`/system/user/delUser/${id}`);

//用户修改
export const editUser = (params) => request.put('/system/user/editUser', params);


// 角色管理
export const getRole = (params) => request.get('/system/role/showRoleList', { params, params });

//角色添加
export const addRole = (params) => request.post('/system/role/addRole', params);

// 角色删除
export const delRole = (id) => request.delete(`/system/role/delRole/${id}`);

//角色添加
export const editRole = (params) => request.put('/system/role/editRole', params);

//角色添加权限
export const addRoleMenu = (params) => request.post('/system/role/allotRoleMenu', params);

//查询菜单
export const getMenu = (params) => request.get('/system/menu/showMenuList', { params, params });

// 菜单删除
export const delMenu = (id) => request.delete(`/system/menu/delMenu/${id}`);

//菜单添加
export const addMenu = (params) => request.post('/system/menu/addMenu', params);

//菜单修改
export const editMenu = (params) => request.put('/system/menu/editMenu', params);

// 部门管理
export const getDept = (params) => request.get('/system/dept/showDeptList', { params, params });

// 部门删除
export const delDept = (id) => request.delete(`/system/dept/delDept/${id}`);

//部门添加
export const addDept = (params) => request.post('/system/dept/addDept', params);

//部门修改
export const editDept = (params) => request.put('/system/dept/editDept', params);

// 岗位管理
export const getPost = (params) => request.get('/system/post/showPostList', { params, params });

// 岗位删除
export const delPost = (id) => request.delete(`/system/post/delPost/${id}`);

//岗位添加
export const addPost = (params) => request.post('/system/post/addPost', params);

//岗位添加
export const editPost = (params) => request.put('/system/Post/editPost', params);

// 公告管理
export const getNotice = (params) => request.get('/system/notice/showNoticeList', { params, params });

// 公告删除
export const delNotice = (id) => request.delete(`/system/notice/delNotice/${id}`);

//公告添加
export const addNotice = (params) => request.post('/system/notice/addNotice', params);

//公告修改
export const editNotice = (params) => request.put('/system/notice/editNotice', params);

// 车配置管理
export const getCarDisposition = (params) => request.get('/system/unifySet/showUnifySetList', { params, params });

//车配置修改
export const editCarDisposition = (params) => request.put('/system/unifySet/editUnifySet', params);

//车配置刷新
export const refreshCarDisposition = (params) => request.put('/system/unifySet/refreshUnifySet', params);

// 违停车辆管理
export const getParking = (params) => request.get('/car/carViolation/showViolationRecordList', { params, params });

// 访客预约管理
export const getBooking = (params) => request.get('/car/appoint/showAppointList', { params, params });

// 预约删除
export const delBooking = (id) => request.delete(`/car/appoint/delAppoint/${id}`);

//预约添加
export const addBooking = (params) => request.post('/car/appoint/addAppoint', params);

//预约修改
export const editBooking = (params) => request.put('/car/appoint/editAppoint', params);

// 停放费用管理
export const getExpenses = (params) => request.get('/car/carParkRule/showParkingPayRule', { params, params });

// 停放费用删除
export const delExpenses = (id) => request.delete(`/car/carParkRule/delPayRule/${id}`);

//停放费用添加
export const addExpenses = (params) => request.post('/car/carParkRule/addParkingPayRule', params);

//停放费用修改
export const editExpenses = (params) => request.put('/car/carParkRule/editParkingPayRule', params);

//停放费用刷新
export const refreshExpenses = (params) => request.put('/car/carParkRule/refreshPayRule', params);

// 进出口管理
export const getExim = (params) => request.get('/car/stallRecord/showStallRecordList', { params, params });

// 进出口类型
export const getEximType = (params) => request.get('/car/stallRecord/getAccessInOrOutType', { params, params });

//进出口添加
export const addExim = (params) => request.post('/car/stallRecord/addStallRecord', params);

//进出口添加
export const showRoleList = (params) => request.get('/system/dict/showRoleList?dictLevel=4');

// 违规信息报表管理
export const getViolate = (params) => request.get('/car/carViolation/showViolationReport', { params, params });

// 车辆评比管理
export const getRating = (params) => request.get('/car/carViolation/showViolationRecordList', { params, params });

// 查询车牌
export const getCar = (params) => request.get(`/system/carUserRecord/showPostList/${params}`);
// 添加车牌
export const addCard = (params) => request.post('/system/carUserRecord/addPlateNumber',  params );

// 车牌删除
export const delCard = (id) => request.delete(`/system/carUserRecord/del/${id}`);

// 添加车牌
export const addPark = (params) => request.post('/car/carViolation/addViolation',  params );

// 添加车牌
export const editPark = (params) => request.put('/car/carViolation/editViolation',  params );
// 查询车牌
export const delPark = (params) => request.delete(`/car/carViolation/delViolation/${params}`);

export const getAvailableParking = (params) => request.get('/car/utils/availableParking');

export const getWholeParking = (params) => request.get('/car/utils/wholeParking');
