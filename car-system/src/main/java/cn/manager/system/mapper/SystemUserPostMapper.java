package cn.manager.system.mapper;

import cn.manager.common.dto.response.PostRespDTO;
import cn.manager.system.entity.SystemPost;
import cn.manager.system.entity.SystemUserPost;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户与岗位关联表 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-10
 */
public interface SystemUserPostMapper extends BaseMapper<SystemUserPost> {

    @SuppressWarnings("all")
    void insertBatchSomeColumn(List<SystemUserPost> userPostList);

    /**
     * 根据用户查询岗位
     */
    List<PostRespDTO> selectPostByUser(@Param(Constants.WRAPPER) QueryWrapper<PostRespDTO> queryWrapper);
}
