package cn.manager.system.controller.car;

import cn.manager.common.core.ResultResponse;
import cn.manager.system.service.SystemUnifySetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/car/utils")
public class CarUtilsController {
    @Autowired
    private SystemUnifySetService systemUnifySetService;

    @GetMapping("/availableParking")
    public ResultResponse avaliableParking() {
        log.info(" ===> 查询可用停车位  <===");
        return ResultResponse.ok().setData(systemUnifySetService.availableParking());

    }
    @GetMapping("/wholeParking")
    public ResultResponse wholeParking() {
        log.info(" ===> 查询总停车位  <===");
        return ResultResponse.ok().setData(systemUnifySetService.wholeParking());
    }


}
