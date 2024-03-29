package cn.manager.system.entity;


import cn.manager.common.core.Lists;
import cn.manager.common.dto.response.PostRespDTO;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户对象 sys_user
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SystemUser extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 角色id
     */
    @NotNull(message = "角色不可为空!")
    private Long roleId;

    /**
     * 用户真实姓名
     */
    @NotBlank(message = "真实姓名不能为空!")
    private String realName;

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空!")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空!")
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

    /**
     * 角色组
     */
    @TableField(exist = false)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    private Long[] postIds;

    /**
     * 多个岗位
     */
    @TableField(exist = false)
    private List<PostRespDTO> postLists = Lists.newArrayList();

    /**
     * 用户车列表
     */
    @TableField(exist = false)
    private List<CarUserRecord> userCarsList = Lists.newArrayList();


    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginDate;

    /**
     * 部门名
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 岗位名
     */
    @TableField(exist = false)
    private String postName;

    public SystemUser(Long userId, String realName, String username) {
        this.userId = userId;
        this.realName = realName;
        this.username = username;
    }

    public SystemUser(Long userId) {
        this.userId = userId;
    }


}
