package cn.nju.edu.se.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "papers")
public interface PapersBusiness {

    @RequestMapping(method = RequestMethod.GET, value = "/api/service/test")
    void health();

}
