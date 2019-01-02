package com.magicalcoder.youyamvc.web.controller.web.common;

import com.magicalcoder.youyamvc.app.userweb.util.ValidCodeUtil;
import com.magicalcoder.youyamvc.core.common.dto.JsonData;
import com.magicalcoder.youyamvc.core.common.file.FileHelper;
import com.magicalcoder.youyamvc.core.common.utils.MapUtil;
import com.magicalcoder.youyamvc.web.common.BaseController;
import com.magicalcoder.youyamvc.web.common.StatusConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by www.magicalcoder.com on 2015/9/8.
 * 799374340@qq.com
 */
@Controller
@RequestMapping(value="/web/common/")
public class WebCommonController extends BaseController {

    @RequestMapping(value = "fileupload/{folder}")
    public void fileUpload(@RequestParam MultipartFile[] myfiles,@PathVariable String folder,
                             HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String realPath = request.getRealPath("/upload/"+folder);

        //如果文件夹不存在就新建一个文件夹
        File dirPath = new File(realPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // 设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        // 设置响应给前台内容的PrintWriter对象
        String originalFilename = null;

        List<Map<String,String>> imgList = new ArrayList<Map<String,String>>();

        for (MultipartFile myfile : myfiles) {

            System.out.println(FileHelper.fastReadFile(myfile.getInputStream(),"UTF-8"));

            String url = "/upload/"+folder+"/";
            if (myfile.isEmpty()) {
                toSimpleJson(response, MapUtil.buildMap("code", 1));
            } else if (myfile.getSize() > 2 * 1024 * 1024) {
                toSimpleJson(response, MapUtil.buildMap("code", 2));
            } else {

                // 获取文件名
                originalFilename = myfile.getOriginalFilename();
                // 文件名后缀处理
                String suffix = originalFilename.substring(
                        originalFilename.lastIndexOf("."),
                        originalFilename.length());

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Timestamp(System.currentTimeMillis())) + "_"
                        + (int)(Math.random()*900000+100000) + suffix;
                url+=newFileName;

                File file = new File(realPath,newFileName);
                try {
                    // 判断文件名是否是图片
                    if (".jpg".equals(suffix) || ".gif".equals(suffix)
                            || ".png".equals(suffix)) {
                        FileUtils.copyInputStreamToFile(
                                myfile.getInputStream(), file);//上传文件到磁盘
                        imgList.add(MapUtil.buildStringMap("code", "0", "url", url));
                    } else {
                        imgList.add(MapUtil.buildStringMap("code","-1","message","格式不支持"));
                    }
                } catch (IOException e) {
                    imgList.add(MapUtil.buildStringMap("code","-1","message","异常"));
                    e.printStackTrace();
                }
            }
        }
        toJsonData(response, new JsonData.Builder(imgList).build());
    }

    //发送验证码
    @RequestMapping(value = "send_valid_code/{useType}/{mobile}",method = RequestMethod.GET)
    public void sendValidCode(@PathVariable String useType,@PathVariable String mobile,
                              HttpServletRequest request,HttpServletResponse response){
        if(StringUtils.isBlank(mobile)){
            toWebFailureJson(response,"入参为空");
            return;
        }
        //1调用手机发送验证码 TODO
        String validCode = "1234";
        //2验证码存入缓存
        String sessionId = request.getSession().getId();
        ValidCodeUtil.setValidCodeToCache(useType, sessionId, validCode);
        toJsonData(response, new JsonData.Builder(null).code(StatusConstant.SUCCESS).message("发送成功").build());
    }
}
