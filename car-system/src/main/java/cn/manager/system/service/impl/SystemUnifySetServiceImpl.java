package cn.manager.system.service.impl;

import cn.manager.common.constant.CacheConstants;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.entity.SystemUnifySet;
import cn.manager.system.mapper.CarStallRecordMapper;
import cn.manager.system.mapper.SystemUnifySetMapper;
import cn.manager.system.service.SystemUnifySetService;
import cn.manager.system.utils.redis.RedisCacheUtils;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 系统统一设置 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SystemUnifySetServiceImpl extends ServiceImpl<SystemUnifySetMapper, SystemUnifySet> implements SystemUnifySetService {

    private final RedisCacheUtils redisCacheUtils;
    @Autowired
    private SystemUnifySetMapper systemUnifySetMapper;
    @Autowired
    private CarStallRecordMapper carStallRecordMapper;

    @Override
    public List<SystemUnifySet> showUnifySetList(SystemUnifySet systemUnifySet) {

        //位置1 开放时间 位置2最大车位  位置3 停车费用封顶
        List<SystemUnifySet> unifyCacheList = redisCacheUtils.getCacheObject(CacheConstants.SYSTEM_CAR_SETTINGS);

        /*校验: 有缓存走缓存*/
        if (CollectionUtils.isEmpty(unifyCacheList)) {
            unifyCacheList = baseMapper.selectList(queryUnifySetWrapperBuild(systemUnifySet));
            redisCacheUtils.setCacheObject(CacheConstants.SYSTEM_CAR_SETTINGS, unifyCacheList);

            /*只要存在key就不操作*/
            if (!redisCacheUtils.hasKey(CacheConstants.CAR_PARKING_USED_COUNTS)) {
                redisCacheUtils.setCacheObject(CacheConstants.CAR_PARKING_USED_COUNTS, 0);
            }

        }

        log.info("===> 系统设置: {}", JSONObject.toJSONString(unifyCacheList));
//        SystemUnifySet systemUnifyBaseSet = unifyCacheList.get(1);
//        String maxCarParking = systemUnifyBaseSet.getCommonStr1();//最大停车数量
//        System.out.println(maxCarParking);

        return unifyCacheList;
    }

    @Override
    public void editUnifySet(SystemUnifySet systemUnifySet) {
        if (!SqlHelper.retBool(baseMapper.updateById(systemUnifySet))) {
            throw new ServiceException(ResponseEnum.A10001, "修改配置失败!");
        }

        //刷新缓存
        refreshUnifySet();
    }

    @Override
    public int availableParking() {
        //获取当前可用停车位
        int wholeParking = systemUnifySetMapper.getWholeParking();
        int usedParking = carStallRecordMapper.getUsedParking();
        return wholeParking - usedParking;

    }

    @Override
    public int wholeParking() {
        return systemUnifySetMapper.getWholeParking();
    }

    @Override
    public void refreshUnifySet() {
        //删除缓存
        redisCacheUtils.deleteObject(CacheConstants.SYSTEM_CAR_SETTINGS);

        //刷新缓存
        showUnifySetList(new SystemUnifySet());
    }


    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<SystemUnifySet> queryUnifySetWrapperBuild(SystemUnifySet systemUnifySet) {

        return Wrappers.<SystemUnifySet>lambdaQuery()
                .eq(systemUnifySet.getId() != null, SystemUnifySet::getId, systemUnifySet.getId());

    }

}
