package cn.itsource.aigou.service;

import cn.itsource.aigou.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author yhh
 * @since 2019-07-30
 */
public interface IProductTypeService extends IService<ProductType> {

    List<ProductType> loadTypeTree();

    void genHomePage();
}
