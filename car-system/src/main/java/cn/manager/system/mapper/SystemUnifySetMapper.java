package cn.manager.system.mapper;

import cn.manager.system.entity.SystemUnifySet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统统一设置 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
@Mapper
public interface SystemUnifySetMapper extends BaseMapper<SystemUnifySet> {

    int getWholeParking();
}
