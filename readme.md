#  权限架构整体功能说明
## 校园车辆管理系统
> 1.1 系统管理
>>- 用户管理
>>>- 新增用户
>>>- 删除用户
>>>- 修改用户
>>>- 删除用户
>>- 角色管理
>>>- 新增角色
>>>- 删除角色
>>>- 修改角色
>>>- 删除角色
>>- 菜单管理
>>- 部门管理
>>- 岗位管理
>>- 通知管理 
>>- 车辆管理

> 1.2 车辆管理
>> -  停车位管理
>> -  进出口管理
>> -  停放费用管理
>> -  违停车辆管理
>> -  访客预约管理

> 1.3 数据分析
>> -  违规信息报表
>> -  车辆文明评比
>> -  其他

> 1.4 首页
>> 静态页

ps: 其余上同

## `数据库`表介绍
细化到`按钮级`别 增删改查 

### 系统权限
- system_user 用户
- system_dept 部门
- system_dict 字典表
- system_menu 菜单
- system_notice  公告
- system_post  岗位
- system_role  角色
- system_role_menu  角色对菜单
- system_user_post  用户对岗位

### 业务
car_user_record 

## 解释
1. 用户 -(一对一)> 部门
2. 用户 -(一对一)-> 角色 <-(多对多)- 菜单)
3. 用户-> 岗位(多对多)
4. 字典表统一标准
## 实现逻辑
角色是超级管理员 直接全部权限
用超级管理员配置出 管理员 老师 学生 商户等角色
根据角色分配不同菜单实现 不同角色不同功能

## sql逻辑实现(设计稿)
[==> 传送门: sql逻辑设计稿](sql说明.txt)


















