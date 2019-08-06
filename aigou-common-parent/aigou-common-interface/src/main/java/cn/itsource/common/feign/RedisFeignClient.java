package cn.itsource.common.feign;

import cn.itsource.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//实现内部服务之间的相互调用
@FeignClient(value = "COMMON-SERVICE",fallbackFactory = RedisFallbackFactory.class)
public interface RedisFeignClient {

    @GetMapping("/redis")
    public AjaxResult get(@RequestParam("key") String key);
    //把值传入缓存中
    @PostMapping("/redis")
    public AjaxResult set(@RequestParam("key")String key,@RequestParam("value")String value);
}
