package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SystemNotice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @TableId(type = IdType.AUTO)
    private Integer noticeId;

    /**
     * 公告标题
     */
    @NotBlank(message = "公告标题不能为空!")
    private String noticeTitle;

    /**
     * 公告内容
     */
    @NotBlank(message = "公告内容不能为空!")
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private Integer status;

    /**
     * 删除状态(0正常 1删除)
     */
    @TableLogic(value = "0", delval = "1")
    private Integer delFlag;


}
