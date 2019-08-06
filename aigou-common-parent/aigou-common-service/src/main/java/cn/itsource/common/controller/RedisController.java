package cn.itsource.common.controller;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.RedisUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    /*
    * 从缓存中取值
    * @RequestParam("key"):可以解决请求头太长的问题
    * */
    @GetMapping("/redis")
    public AjaxResult get(@RequestParam("key") String key){
        try {
            String value = RedisUtils.INSTANCE.get(key);
            return AjaxResult.me().setSuccess(true).setMessage("成功").setResultObject(value);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败:"+e.getMessage());
        }
    }
    //把值传入缓存中
    @PostMapping("/redis")
    public AjaxResult set(@RequestParam("key")String key,@RequestParam("value")String value){
        try {
            RedisUtils.INSTANCE.set(key,value );
            return AjaxResult.me().setSuccess(true).setMessage("成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败:"+e.getMessage());
        }
    }

}
