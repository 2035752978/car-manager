package cn.manager.common.dto.request;

import cn.manager.common.core.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色与菜单分配请求dto
 *
 * @author ljc
 * @version 0.1.0
 * @description: ok
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleAllotReqDTO {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限菜单ids
     */
    private List<Long> menuIds = Lists.newArrayList();


}
