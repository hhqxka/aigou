package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.Specification;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author yhh
 * @since 2019-08-04
 */

public interface IProductService extends IService<Product> {

    PageList<Product> queryPage(ProductQuery query);

    List<Specification> getViewProperties(Long productId);

    void updateViewProperties(int productId, String viewProperties);

    List<Specification> getSkuProperties(Long productId);

}
