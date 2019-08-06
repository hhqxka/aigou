package cn.itsource.common.feign;

import cn.itsource.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
@FeignClient(value = "COMMON-SERVICE",fallbackFactory = StaticPageFallbackFactory.class)
public interface StaticPageFeignClient {
    @PostMapping("/getStaticPage")
    public AjaxResult getStaticPage(Map<String,Object> map);
}
