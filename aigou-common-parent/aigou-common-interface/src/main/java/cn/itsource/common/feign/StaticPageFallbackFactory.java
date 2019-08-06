package cn.itsource.common.feign;

import cn.itsource.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
@Component
public class StaticPageFallbackFactory implements FallbackFactory<StaticPageFeignClient> {

    @Override
    public StaticPageFeignClient create(Throwable throwable) {
        return new StaticPageFeignClient() {
            @Override
            public AjaxResult getStaticPage(@RequestBody Map<String, Object> map) {
                return AjaxResult.me().setSuccess(true).setMessage("系统异常");
            }
        };
    }
}
