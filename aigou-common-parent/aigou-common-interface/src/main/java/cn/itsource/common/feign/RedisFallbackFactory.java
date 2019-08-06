package cn.itsource.common.feign;

import cn.itsource.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisFallbackFactory implements FallbackFactory<RedisFeignClient> {
    @Override
    public RedisFeignClient create(Throwable throwable) {
        return new RedisFeignClient() {
            @Override
            public AjaxResult get(String key) {
                return AjaxResult.me().setSuccess(false).setMessage("系统异常");

            }
            @Override
            public AjaxResult set(String key, String value) {
                return AjaxResult.me().setSuccess(false).setMessage("系统异常");
            }
        };
    }
}
