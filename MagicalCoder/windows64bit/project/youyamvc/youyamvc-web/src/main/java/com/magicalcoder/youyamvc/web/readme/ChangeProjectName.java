package com.magicalcoder.youyamvc.web.readme;


import com.magicalcoder.youyamvc.core.common.file.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by www.magicalcoder.com on 2016/3/22.
 * 799374340@qq.com
 * 更改项目名称 大家根据自家的项目自行改名称吧
 * 包名称大家重命名吧
 */
public class ChangeProjectName {
    private static String source = "youyamvc";//这个不用变
    private static String target = "newprojectname";//你的新项目名称
    private static String filePath = "D:\\youyamvc\\";//你下载的youyamvc的路径
    public static void main(String[] args) {
        File file = new File(filePath);
        List<File> fileList = new ArrayList<File>();
        findAllFile(fileList,file);

        for(File f:fileList){
            String text = FileHelper.fastReadAllLineFile(f,"UTF-8");
            if(text.contains(source)){
                System.out.println(f.getName());
                text = text.replace(source,target);
                FileHelper.fastWriteFileUTF8(f,text);
            }
        }
    }

    static void findAllFile(List<File> fileList,File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                findAllFile(fileList,f);
            }
        }else{
            fileList.add(file);
        }
    }
}
