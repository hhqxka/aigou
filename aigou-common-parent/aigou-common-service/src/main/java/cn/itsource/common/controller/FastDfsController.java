package cn.itsource.common.controller;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;

//图片的上传的统一管理，因为采取的集群，所以把文件需要上传到FastDFS做统一管理，这里暴露一个借口给其他服务调用
@RestController
public class FastDfsController {

    /**
     *图片上传的统一借口
     */
    @PostMapping("/fastdfs")
    public AjaxResult upload(MultipartFile file){
        try {
            //我们需要获取文件的后缀名
            String filename = file.getOriginalFilename();
            //截取,从倒数碰到的第一个点的索引位置+1的位置开始截取
            filename = filename.substring(filename.lastIndexOf(".")+1);
            //返回的这个fileid就是文件的标识符，我们以后进行操作，就是带着它去查找
            String fileid = FastDfsApiOpr.upload(file.getBytes(), filename);
            return AjaxResult.me().setSuccess(true).setMessage("成功上传！").setResultObject(fileid);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败了啊上传！");
        }
    }

    //删除文件
    @DeleteMapping("/fastdfs")
    public AjaxResult delete(String fileid){
        //对field进行解析，是有一个分组和路径组成 /group1/M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg

        try {
            fileid = fileid.substring(1); //   group1/M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg
            String groupName = fileid.substring(0,fileid.indexOf("/")); //   group1
            String fileName = fileid.substring(fileid.indexOf("/")+1);//     M00/00/01/rBAEtF1ESveAJIrtAATb0rfoOcM166.jpg
            FastDfsApiOpr.delete(groupName,fileName);
            //成功
            return AjaxResult.me().setSuccess(true).setMessage("成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败!"+e.getMessage());
        }

    }

}
