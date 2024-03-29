package cn.manager.system.service;

import cn.manager.system.entity.SystemUnifySet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统统一设置 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-15
 */
public interface SystemUnifySetService extends IService<SystemUnifySet> {

    List<SystemUnifySet> showUnifySetList(SystemUnifySet systemUnifySet);

    void refreshUnifySet();

    void editUnifySet(SystemUnifySet systemUnifySet);
}
