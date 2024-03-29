package cn.manager.system.mapper;

import cn.manager.system.entity.SystemMenu;
import cn.manager.system.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {

    List<String> selectParamsByRole(@Param("systemUser") SystemUser systemUser);
}
