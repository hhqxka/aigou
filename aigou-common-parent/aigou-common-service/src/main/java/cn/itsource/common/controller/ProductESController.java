package cn.itsource.common.controller;

import cn.itsource.common.domain.ProductDoc;
import cn.itsource.common.repository.ProductRepository;
import cn.itsource.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/es")
public class ProductESController {
    @Autowired
    private ProductRepository productRepository ;

    @PostMapping("/save")
    public AjaxResult save(@RequestBody ProductDoc productDoc){
        try {
            productRepository.save(productDoc);
            return AjaxResult.me().setSuccess(true).setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败"+e.getMessage());
        }
    }
    @PostMapping("/saveAll")
    public AjaxResult saveAll(@RequestBody List<ProductDoc> list){
        try {
            productRepository.saveAll(list);
            return AjaxResult.me().setSuccess(true).setMessage("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败"+e.getMessage());
        }
    }
    @PostMapping("/delete")
    public AjaxResult delete(@RequestParam Long productId){
        try {
            productRepository.deleteById(productId);
            return AjaxResult.me().setSuccess(true).setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败"+e.getMessage());
        }
    }

    @PostMapping("/deleteAll")
    public AjaxResult deleteAll(@RequestBody List<Long> ids){
        try {
            for(Long id: ids){
                productRepository.deleteById(id);
            }
            return AjaxResult.me().setSuccess(true).setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败"+e.getMessage());
        }
    }
}
