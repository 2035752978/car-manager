package cn.manager.system.service;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
public interface SystemMenuService extends IService<SystemMenu> {

    ResultResponse showMenuList(boolean mCFlag, PageVo pageVo);

}
