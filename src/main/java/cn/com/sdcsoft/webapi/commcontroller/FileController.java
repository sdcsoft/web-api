package cn.com.sdcsoft.webapi.commcontroller;

import cn.com.sdcsoft.webapi.annotation.Auth;
import cn.com.sdcsoft.webapi.commservice.CookieService;
import cn.com.sdcsoft.webapi.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 接口提供文件上传功能
 * 接口负责把每个项目的图片放入每个项目的指定文件夹
 * 文件夹中的文件资源由前端项目自己获取，接口不负责
 */
@RestController
@RequestMapping(value = "/webapi/file")
@Auth
public class FileController {
    @Value("${fileupload.boiler.path}")
    private String boilerPath;//锅炉厂上传图片存储位置
    @Value("${fileupload.boiler.return-image-prefix}")
    private String boilerReturnImagePrefix;//上传完成后反馈的图片路径前缀
    /**
     * 文件上传
     * @param picture
     * @param request
     * @return
     */
    @RequestMapping("/upload/boiler")
    public Result boilerUpload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) throws FileNotFoundException {
        String orgId = request.getAttribute(CookieService.USER_INFO_FIELD_NAME_OrgID).toString();
        String fileType = request.getParameter("type");
        try {
            File path  = new File(ResourceUtils.getURL(boilerPath).getPath());
            //如果上传目录为/static/images/upload/，则可以如下获取：
            //File upload = new File(path.getAbsolutePath(),"\\"+orgId+"\\");
            File upload = new File(path.getAbsolutePath(),orgId);
            if(!upload.exists()){
                upload.mkdirs();
            }
            //获取文件在服务器的储存位置
            File filePath = new File(upload.getAbsolutePath());

            //获取原始文件名称(包含格式)
//            String originalFileName = picture.getOriginalFilename();

            //设置文件新名称: 当前时间+文件名称（不包含格式）
            String fileName = String.format("%s.jpg",fileType);

            //在指定路径下创建一个文件
            File targetFile = new File(filePath, fileName);
            picture.transferTo(targetFile);
            //将文件在服务器的存储路径返回
            return Result.getSuccessResult(null,
                    String.format("%s/%s/%s",
                            boilerReturnImagePrefix,
                            orgId,
                            fileName));
        } catch (Exception e){
            e.printStackTrace();
            return Result.getFailResult("上传失败");
        }
    }
    // 删除文件
    @PostMapping("/delete/boiler")
    public Result boilerDelete(@RequestParam("orgId") String orgId){
        return Result.getFailResult("暂不支持文件删除操作！");
    }
}
