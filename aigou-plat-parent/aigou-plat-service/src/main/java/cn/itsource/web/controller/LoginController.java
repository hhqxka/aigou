package cn.itsource.web.controller;

import cn.itsource.domain.User;
import cn.itsource.util.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if("admin".equals(username) && "admin".equals(password)){
            return AjaxResult.me().setSuccess(true).setMessage("登录成功");
        }
        return AjaxResult.me().setSuccess(false).setMessage("登录失败！");
    }
}
