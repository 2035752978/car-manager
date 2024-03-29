package cn.manager.system.service.impl;

import cn.manager.common.core.Lists;
import cn.manager.common.core.PageVo;
import cn.manager.common.dto.response.PostRespDTO;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.entity.CarUserRecord;
import cn.manager.system.entity.SystemPost;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.entity.SystemUserPost;
import cn.manager.system.mapper.CarUserRecordMapper;
import cn.manager.system.mapper.SystemUserMapper;
import cn.manager.system.mapper.SystemUserPostMapper;
import cn.manager.system.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-04
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    private final SystemUserPostMapper systemUserPostMapper;

    private final CarUserRecordMapper carUserRecordMapper;

    @Override
    public IPage<SystemUser> showUserList(SystemUser systemUser, PageVo pageVo) {

        IPage<SystemUser> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        String plateNumber = pageVo.getPlateNumber();

        List<Long> userIds = Lists.newArrayList();

        //模糊搜索
        if (StringUtils.isNotBlank(plateNumber)) {
            List<CarUserRecord> carUserRecords = carUserRecordMapper.selectList(
                    Wrappers.<CarUserRecord>lambdaQuery()
                            .like(CarUserRecord::getPlateNumber, plateNumber)
            );
            if (CollectionUtils.isNotEmpty(carUserRecords)) {
                userIds = carUserRecords.stream().map(CarUserRecord::getUserId).distinct().collect(Collectors.toList());
            }
        }

        iPage = baseMapper.selectUserList(iPage, new QueryWrapper<SystemUser>()
                .in(CollectionUtils.isNotEmpty(userIds), "system_user.user_id", userIds)
                .eq(systemUser.getUserId() != null, "system_user.user_id", systemUser.getUserId())
                .eq(StringUtils.isNotBlank(systemUser.getUsername()), "system_user.username", systemUser.getUsername())
                .eq(StringUtils.isNotBlank(systemUser.getPhone()), "system_user.phone", systemUser.getPhone())
                .eq(StringUtils.isNotBlank(systemUser.getStatus()), "system_user.status", systemUser.getStatus())
                .eq(systemUser.getRoleId() != null, "system_user.role_id", systemUser.getRoleId())
                .eq(systemUser.getDeptId() != null, "system_user.dept_id", systemUser.getDeptId())
                .eq("system_user.del_flag", "0")
                .like(StringUtils.isNotBlank(systemUser.getRealName()), "system_user.real_name", systemUser.getRealName())
        );

        //岗位数据处理
        return selectPostByUsers(iPage);
    }

    /**
     * 查询每一个用户的岗位
     */
    private IPage<SystemUser> selectPostByUsers(IPage<SystemUser> iPage) {
        List<SystemUser> userList = iPage.getRecords();
        if (CollectionUtils.isEmpty(userList)) {
            return iPage;
        }

        List<Long> userIds = userList.stream().map(SystemUser::getUserId).collect(Collectors.toList());

        List<PostRespDTO> postList = systemUserPostMapper.selectPostByUser(
                new QueryWrapper<PostRespDTO>().in("sup.user_id", userIds)
        );

        if (CollectionUtils.isEmpty(postList)) {
            return iPage;
        }

        //分组查询
        Map<Long, List<PostRespDTO>> postMap = postList.stream().collect(Collectors.groupingBy(PostRespDTO::getUserId));

        userList.forEach(userDto -> {
            if (postMap.containsKey(userDto.getUserId())) {
                userDto.setPostLists(postMap.get(userDto.getUserId()));
            }
        });

        return iPage;
    }


    @Override
    public void addUser(SystemUser systemUser) {

        try {
            baseMapper.insert(systemUser);
        } catch (DuplicateKeyException e) {
            log.warn("数据重复: =>{}", e.getCause().getMessage());
            throw new ServiceException(ResponseEnum.A10001, "用户名已经存在!");
        }

        //批量添加 用户岗位
        saveUserPostBatch(false, systemUser);


        saveUserCarBatch(false, systemUser);

    }

    /**
     * 批量添加用户车辆
     *
     * @param flag
     * @param systemUser
     */
    private void saveUserCarBatch(boolean flag, SystemUser systemUser) {
    }

    @Override
    public void updateUser(SystemUser systemUser) {
        baseMapper.updateById(systemUser);

        //批量添加 用户岗位
        saveUserPostBatch(true, systemUser);
    }

    /**
     * 批量添加 用户岗位
     *
     * @param systemUser 系统角色id
     */
    private void saveUserPostBatch(boolean flag, SystemUser systemUser) {

        Long[] postIds = systemUser.getPostIds();

        /*校验: 如果length啥也没有  返回*/
        if (postIds.length <= 0) return;

        Long userId = systemUser.getUserId();

        if (flag) {
            //更新时开启删除
            removePostByUserBatch(userId);
        }

        List<SystemUserPost> userPostList = Lists.newArrayList();
        Arrays.stream(postIds).forEach(postId -> userPostList.add(new SystemUserPost(userId, postId)));

        //批量添加用户->岗位
        systemUserPostMapper.insertBatchSomeColumn(userPostList);
    }

    /**
     * 根据用户删除岗位
     *
     * @param userId 用户id
     */
    private void removePostByUserBatch(Long userId) {
        systemUserPostMapper.delete(Wrappers.lambdaQuery(new SystemUserPost(userId, null)));
    }


}
