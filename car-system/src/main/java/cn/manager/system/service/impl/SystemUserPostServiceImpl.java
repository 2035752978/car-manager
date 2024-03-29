package cn.manager.system.service.impl;

import cn.manager.system.entity.SystemUserPost;
import cn.manager.system.mapper.SystemUserPostMapper;
import cn.manager.system.service.SystemUserPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与岗位关联表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-10
 */
@Service
public class SystemUserPostServiceImpl extends ServiceImpl<SystemUserPostMapper, SystemUserPost> implements SystemUserPostService {

}
