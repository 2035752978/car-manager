package cn.manager.common.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 岗位信息响应对象
 * </p>
 *
 * @author ljc
 * @since 2024-03-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PostRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 用户id
     */
    private Long userId;

}
