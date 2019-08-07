package cn.itsource.common.feign;

import cn.itsource.common.domain.ProductDoc;
import cn.itsource.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(value = "COMMON-SERVICE",fallbackFactory = ESFallbackFactory.class)
public interface ESFeignClient {

    @PostMapping("/save")
    public AjaxResult save(@RequestBody ProductDoc productDoc);
    @PostMapping("/saveAll")
    public AjaxResult saveAll(@RequestBody List<ProductDoc> list);
    @PostMapping("/delete")
    public AjaxResult delete(@RequestParam Long productId);
    @PostMapping("/deleteAll")
    public AjaxResult deleteAll(@RequestBody List<Long> ids);
}
