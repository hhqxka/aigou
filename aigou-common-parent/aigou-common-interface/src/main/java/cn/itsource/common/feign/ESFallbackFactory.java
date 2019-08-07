package cn.itsource.common.feign;

import cn.itsource.common.domain.ProductDoc;
import cn.itsource.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ESFallbackFactory implements FallbackFactory<ESFeignClient> {

    @Override
    public ESFeignClient create(Throwable throwable) {
        return new ESFeignClient() {
            @Override
            public AjaxResult save(ProductDoc productDoc) {
                return null;
            }

            @Override
            public AjaxResult saveAll(List<ProductDoc> list) {
                return null;
            }

            @Override
            public AjaxResult delete(Long productId) {
                return null;
            }

            @Override
            public AjaxResult deleteAll(List<Long> ids) {
                return null;
            }
        };
    }
}
