package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.ProductType;
import cn.itsource.aigou.mapper.ProductTypeMapper;
import cn.itsource.aigou.service.IProductTypeService;
import cn.itsource.common.feign.RedisFeignClient;
import cn.itsource.common.feign.StaticPageFeignClient;
import cn.itsource.util.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author yhh
 * @since 2019-07-30
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private RedisFeignClient redisFeignClient ;
    @Autowired
    private StaticPageFeignClient staticPageFeignClient ;


    @Override
    public void genHomePage() {
        //第一步 ： 生成product.type.vm.html
        Map<String,Object> map = new HashMap<>();
        //模板的路径
        String templatePath = "E:\\2019.0220java\\ideaProject\\aigou\\aigou-product-parent\\aigou-product-service\\src\\main\\resources\\template\\product.type.vm";
        //生成product.type.vm.html的路径
        String targetPath = "E:\\2019.0220java\\ideaProject\\aigou-parent\\aigou-product-parent\\aigou-product-service\\src\\main\\resources\\template\\product.type.vm.html";
        //model 就是List 存放所有的商品类型
        List<ProductType> productTypes = loadTypeTree();
        map.put("model",productTypes);
        map.put("templatePath",templatePath);
        map.put("targetPath",targetPath);
        staticPageFeignClient.getStaticPage(map);

        //第二步 ： 生成home.html
        map = new HashMap<>();
        templatePath = "E:\\2019.0220java\\ideaProject\\aigou\\aigou-product-parent\\aigou-product-service\\src\\main\\resources\\template\\home.vm";
        //前台需要静态化的页面，开始是空的，然后把product.type.vm.html填充进去
        targetPath = "E:\\ideaProject\\aigou-web-parent\\aigou-web-home\\home.html";
        //model 中要有一个数据是staticRoot
        Map<String,String> model = new HashMap<>();
        model.put("staticRoot","E:\\2019.0220java\\ideaProject\\aigou\\aigou-product-parent\\aigou-product-service\\src\\main\\resources");
        map.put("model",model);
        map.put("templatePath",templatePath);
        map.put("targetPath",targetPath);

        staticPageFeignClient.getStaticPage(map);

    }

    @Override
    public List<ProductType> loadTypeTree() {
        //先从缓存去拿取数据
        AjaxResult result = redisFeignClient.get("productTypes");
        String resultStr = (String) result.getResultObject();
        //用fastJson转换成集合
        List<ProductType> productTypes = JSON.parseArray(resultStr, ProductType.class);
        //如果数据为空就去数据库拿，并且把查询出来的值放到缓存
        if(productTypes==null || productTypes.size()<=0){
            productTypes = cycle();
            redisFeignClient.set("productTypes",JSON.toJSONString(productTypes) );
        }
        return productTypes;
    }


    public List<ProductType> cycle(){
        //查询所有的数据
        List<ProductType> productTypes = baseMapper.selectList(null);
        //用来装所有的一级菜单
        List<ProductType> list = new ArrayList<>();
        //定义一个map用id 和值得方式存储
        Map<Long,ProductType> map = new HashMap<>();
        //循环所有的值，装进map
        for (ProductType productType : productTypes) {
            map.put(productType.getId(),productType);
        }
        //循环所有的值，判断是否为父集菜单
        for (ProductType type : productTypes) {
            if(type.getPid()==0L){
                list.add(type);
            }else {
                //如果当前不是一级菜单，就去找自己的上一级，然后把自己放进去
                ProductType p = map.get(type.getPid());
                //在这里判断是否为空
                List<ProductType> children = p.getChildren();
                if(children==null){
                    //避免父类没有子类的时候会返回一个空的数组
                    children=new ArrayList<>();
                }
                children.add(type);
                p.setChildren(children);
            }
        }
        return list ;
    }
    //递归
    public List<ProductType> recursive(Long id){
        //拿到所有的一级菜单
        List<ProductType> productTypes = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid", id));
        for (ProductType productType : productTypes) {
            //拿到一级菜单的id
            Long parentId = productType.getId();
            List<ProductType> recursive = recursive(parentId);
            //如果菜单有子类就添加
            if(!recursive.isEmpty()){
                productType.setChildren(recursive);
            }
        }
        return productTypes ;
    }
}
