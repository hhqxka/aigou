package cn.itsource.common.controller;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.VelocityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StaticPageController {

    @PostMapping("/getStaticPage")
    public AjaxResult getStaticPage(@RequestBody Map<String,Object> map){
        Object model = map.get("model");
        String templatePath = (String) map.get("templatePath");
        String targetPath = (String) map.get("targetPath");
        try {
            VelocityUtils.staticByTemplate(model, templatePath, targetPath);
            return AjaxResult.me().setSuccess(true).setMessage("成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }
}
