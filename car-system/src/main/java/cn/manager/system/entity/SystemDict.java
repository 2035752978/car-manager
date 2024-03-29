package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemDict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @TableId(type = IdType.AUTO)
    private Long dictCode;

    /**
     * 字典详情名(例子: 男女或不详等)
     */
    private String dictName;

    /**
     * 不同类型 用这个等级区分
     */
    private Integer dictLevel;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典英文名
     */
    private String dictKey;

    /**
     * 字典分类
     */
    private String dictType;

    /**
     * 字典变量
     */
    private String dictVariable;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;


}
