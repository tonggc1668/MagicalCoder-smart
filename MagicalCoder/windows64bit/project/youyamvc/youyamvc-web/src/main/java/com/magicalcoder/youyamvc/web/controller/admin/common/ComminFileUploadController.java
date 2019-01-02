package com.magicalcoder.youyamvc.web.controller.admin.common;

import com.magicalcoder.youyamvc.core.common.utils.MapUtil;
import com.magicalcoder.youyamvc.core.spring.admin.AdminLoginController;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hedongyu on 2015/9/8.
 * 799374340@qq.com
 */
@Controller
@RequestMapping(value="/admin/commonfile")
public class ComminFileUploadController extends AdminLoginController {


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toIndex(ModelMap model)
    {
        return "";
    }

    @RequestMapping(value = "/fileupload/{folder}")
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

        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        for (MultipartFile myfile : myfiles) {
            String url = "upload/"+folder+"/";
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

                        toSimpleJson(response, MapUtil.buildMap("code",0,"url",url));
                    } else {
                        toSimpleJson(response, MapUtil.buildMap("code", 3));
                    }
                } catch (IOException e) {
                    toSimpleJson(response, MapUtil.buildMap("code", 4));
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "/ckfileupload/{folder}")
    public void ckfileUpload(@RequestParam MultipartFile[] imgFile,@PathVariable String folder,
                             HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String realPath = request.getRealPath("/upload/"+folder);
        String url = "/upload/"+folder+"/";

        //如果文件夹不存在就新建一个文件夹
        File dirPath = new File(realPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        // 设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        // 设置响应给前台内容的PrintWriter对象
//        PrintWriter out = response.getWriter();
        // 上传文件的原名(即上传前的文件名字)
        String originalFilename = null;

        // 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        // 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        // 上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
        Map<String,Object> result = new HashMap<String, Object>();
        for (MultipartFile myfile : imgFile) {

            System.out.println("===图片大小:===" + myfile.getSize());

            if (myfile.isEmpty()) {
                return;
            } else if (myfile.getSize() > 2 * 1024 * 1024) {
                return;
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
                        Image img = ImageIO.read(myfile.getInputStream());
                        int width = img.getWidth(null);    // 得到源图宽
                        int height = img.getHeight(null);  // 得到源图长
                        String maxWidth = request.getParameter("maxWidth");
                        /*if(StringUtils.isNotBlank(maxWidth) && width > Integer.valueOf(maxWidth)){ // 需要压缩
                            int maxHeigth = height * Integer.valueOf(maxWidth) / width;
                            width = Integer.valueOf(maxWidth);
                            height = maxHeigth;
                            // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
                            BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
                            image.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
                            File destFile = file;
                            FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
                            // 可以正常实现bmp、png、gif转jpg
                            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                            encoder.encode(image); // JPEG编码
                            out.close();
                        }else{ //不需要压缩*/
                        FileUtils.copyInputStreamToFile(
                                myfile.getInputStream(), file);//上传文件到磁盘
                       /* }*/

                        // 文件上传成功返回文件路径跟文件名
                        String ctx = request.getContextPath();
                        result.put("url",ctx+url);
                        result.put("error",0);
                    } else {
                    }

                } catch (IOException e) {
                    System.out.println("文件[" + newFileName
                            + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();

                }
            }
        }
        toSimpleJson(response,result);
    }
}
