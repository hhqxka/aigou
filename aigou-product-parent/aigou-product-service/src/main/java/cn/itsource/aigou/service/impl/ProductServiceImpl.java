package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.Product;
import cn.itsource.aigou.domain.ProductExt;
import cn.itsource.aigou.domain.Specification;
import cn.itsource.aigou.mapper.ProductExtMapper;
import cn.itsource.aigou.mapper.ProductMapper;
import cn.itsource.aigou.mapper.SpecificationMapper;
import cn.itsource.aigou.query.ProductQuery;
import cn.itsource.aigou.service.IProductExtService;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.util.PageList;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author yhh
 * @since 2019-08-04
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductExtMapper productExtMapper ;

    @Autowired
    private SpecificationMapper specificationMapper;

    //从写save方法，保存两张表的信息
    @Override
    public boolean save(Product entity) {
        //设置一些初始化的值
        entity.setCreateTime(new Date().getTime());
        //设置状态是下架
        entity.setState(0);
        int i = baseMapper.insert(entity);
        Long ProductId = entity.getId();
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(ProductId);
        int i1 = productExtMapper.insert(productExt);
        if(i!=0 && i1!=0){
            return true ;
        }
        return false ;
    }

    @Override
    public PageList<Product> queryPage(ProductQuery query) {
        IPage<Product> iPage = baseMapper.queryPage(new Page( query.getPageNum(), query.getPageSize()), query);
        return new PageList<Product>(iPage.getTotal(),iPage.getRecords());
    }

    @Override
    public List<Specification> getViewProperties(Long productId) {
        //查询详情中的ViewProperties
        ProductExt productExt = productExtMapper.selectOne(new QueryWrapper<ProductExt>().eq("productId", productId));
        String viewProperties = productExt.getViewProperties();
        if(viewProperties!=null){
            //FastJson转数组
            List<Specification> specifications = JSON.parseArray(viewProperties, Specification.class);
            return specifications ;
        }
        //如果存在直接查询
        Product product = baseMapper.selectById(productId);
        //拿到类型的id
        Long productTypeId = product.getProductTypeId();
        //直接在specification中查找
        List<Specification> specifications = specificationMapper.selectList(new QueryWrapper<Specification>().eq("product_type_id", productTypeId).eq("isSku", 0));
        return specifications;
    }
//修改显示属性
    @Override
    public void updateViewProperties(int productId, String viewProperties) {
        productExtMapper.updateViewProperties(productId,viewProperties);
    }
    @Override
    public List<Specification> getSkuProperties(Long productId) {
        //查询详情中的ViewProperties
        ProductExt productExt = productExtMapper.selectOne(new QueryWrapper<ProductExt>().eq("productId", productId));
        String skuProperties = productExt.getSkuProperties();
        if(skuProperties!=null){
            //FastJson转数组
            List<Specification> specifications = JSON.parseArray(skuProperties, Specification.class);
            return specifications ;
        }
        //如果存在直接查询
        Product product = baseMapper.selectById(productId);
        //拿到类型的id
        Long productTypeId = product.getProductTypeId();
        //直接在specification中查找
        List<Specification> specifications = specificationMapper.selectList(new QueryWrapper<Specification>().eq("product_type_id", productTypeId).eq("isSku", 1));
        return specifications;
    }
}
