package cn.manager.system.service;

import cn.manager.common.core.PageVo;
import cn.manager.system.entity.SystemUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-04
 */
public interface SystemUserService extends IService<SystemUser> {

    IPage<SystemUser> showUserList(SystemUser systemUser, PageVo pageVo);

    void addUser(SystemUser systemUser);

    void updateUser(SystemUser systemUser);
}
