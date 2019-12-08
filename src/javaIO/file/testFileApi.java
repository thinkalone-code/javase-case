package javaIO.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author thinkalone-code
 * @decription 用于测试File类的api
 * @date 2019/12/1
 */
public class testFileApi {

    public static void main(String[] args) throws Exception{
        //创建File对象，使用相对路径（相对于当前工作目录路径）创建。绝对路径依赖机器，不推荐使用
        File welcome = new File("src/javaIO/file/welcome.txt");
        File file = new File("src/javaIO/file");

        /* 获取文件属性 */
        //判断文件是否存在
        System.out.println("welcome.txt是否存在："+welcome.exists());
        //获取文件大小
        System.out.println("welcome.txt文件大小："+welcome.length()+" bytes");
        //获取文件最后修改时间
        System.out.println("welcome.txt文件最后修改时间："+new Date(welcome.lastModified()));
        //判断文件是否可读
        System.out.println("welcome.txt是否可读："+welcome.canRead());
        //判断文件是否可写
        System.out.println("welcome.txt是否可写："+welcome.canWrite());
        //判断文件是否可执行
        System.out.println("welcome.txt是否可执行："+welcome.canExecute());
        //是否为隐藏文件
        System.out.println("welcome.txt是否为隐藏文件："+welcome.isHidden());
        //是否为目录
        System.out.println("welcome.txt是否为目录："+welcome.isDirectory());
        //是否为文件
        System.out.println("welcome.txt是否为文件："+welcome.isFile());
        //获取文件绝对路径
        System.out.println("welcome.txt的绝对路径："+welcome.getAbsolutePath());

        /*操作文件*/
        //创建文件夹 mkdirs父目录不存在也可以创建
        new File("src/javaIO/file/javacreate").mkdir();
        new File("src/javaIO/file/aaa/bbb/ccc").mkdirs();
        //创建文件
        new File("src/javaIO/file/javacreate/javacreate.txt").createNewFile();
        //创建临时文件
        File.createTempFile("javacreate","txt",file);


    }
}
