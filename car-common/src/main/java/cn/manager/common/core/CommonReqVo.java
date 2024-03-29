package cn.manager.common.core;

import lombok.Data;

/**
 * 继承子类: PageVo
 * ps: 父级类不要设置与子集类相同属性名称
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
@Data
public class CommonReqVo {
    /**
     * 分页页码
     */
    private Long page = 1L;

    /**
     * 分页条数
     */
    private Long size = 10L;

}
