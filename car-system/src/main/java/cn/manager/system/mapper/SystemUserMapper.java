package cn.manager.system.mapper;

import cn.manager.system.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-04
 */
public interface SystemUserMapper extends BaseMapper<SystemUser> {

    /**
     * 初始化后(使用)连接池配置
     *
     * @return
     */
    @SuppressWarnings("all")
    @Select("SELECT 1 FROM DUAL")
    int initialHikariPool();

    /**
     * 查询用户信息
     */
    IPage<SystemUser> selectUserList(IPage<SystemUser> iPage, @Param(Constants.WRAPPER) QueryWrapper<SystemUser> queryWrapper);
}
