package IO.File;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import org.junit.Test;

public class IoTest {
    private static Long count = 0L;


    /***
     *
     内存向内存写出情况：
     必须要关闭流才能获取到

     内存向磁盘写出的情况:
     不关闭流的情况：
     字节流：依然可写出
     字符流：写出不了
     */



    /**File的获取功能
     *
     */
    @Test
    public void iotest() {
        //常量File.separator 动态提供分隔符    在windows中自动识别为\   在Unix和URL中自动识别为 /
        String path = "D:"+File.separator+"IO"+File.separator+"demo1";
        File f1 = new File(path);

        //获取绝对路径
        System.out.println("绝对路径  	"+f1.getAbsoluteFile());// D:\IO\demo1

        //获相对路径
        System.out.println("路径	"+f1.getPath());//   D:\IO\demo1

        //获取名称
        System.out.println("名称	"+f1.getName());// demo1

        //获取上层目录
        System.out.println("父目录	"+f1.getParent());// D:\IO

        //获取文件的长度（注意只能获取文件的长度，不能获取目录的长度）
        //所以拿文件路径看文件的长度（目录不能获取长度）
        System.out.println("文件长度	"+new File(path,"fu.txt").length());//11

        //获取最后一次修改的时间，毫秒值  .lastModified(
        System.out.println("最后的修改时间	"+new Date(new File(path,"fu.txt").lastModified()));//Tue Mar 29 14:56:35 CST 2022


        /**获取目录下的所有文件和目录的 ”名称“ 数组*/
        //获取指定目录下，所有的文件和文件目录的 “名称” 组成的String[]数组
        String[] demoList = f1.list();
        //迭代出demo目录下的 所有文件和文件目录的 “名称”组成的 数组
        for (String indemo : demoList) {
            System.out.println(f1.getName()+"目录下的   "+indemo+"  文件或目录");// fu.txt  fu2.txt

        }

        /**获取对应当前目录(demo1)下的所有文件和文件目录 将他们全部生成File对象 ，组成数组 返回 File[] 对象数组
         *注意：《获取目录下文件和目录是看字典排序来决定进数组先后顺序》
         * */
        File[] listFiles = f1.listFiles();
        for (File file : listFiles) {
            System.out.println(file.getAbsoluteFile());
//			 D:\IO\demo1\fu.txt
//			 D:\IO\demo1\fu2.txt

        }


        /**File重命名     renameTo(File) 注意，renameTo方法的 <参数> 一定要是 "File类型的对象"  才可以*/
        File frename = new File(path,"fu.txt");

        //当期那的frename就是指fu.txt 文件
        frename.renameTo(new File(path+File.separator+"NewAdress"+File.separator,"fuNewName.txt"));//磁盘的文件被改名(并移动)
        /**写的是全路径的File对象,小心，需要写全*/







    }


    /***
     * File的判断功能
     */
    @Test
    public void test2(){

        String path = "D:"+File.separator+"IO"+File.separator+"demo1";
        File f1 = new File(path);

        //判断是否是目录
        System.out.println(f1.isDirectory());//true

        //判断是否是文件
        System.out.println(f1.isFile());//false

        //判断是否存在
        System.out.println(f1.exists());//true

        //判断是否可读
        System.out.println(f1.canRead());//true

        //判断是否可写
        System.out.println(f1.canWrite());//true

        //判断是否隐藏
        System.out.println(f1.isHidden());//false
    }

    /***
     * File的创建
     */

    //1，只创建文件，不创建目录，没有上层目录 或 文件已经存在 就不创建，否则创建
    @Test
    public void testCreate(){
        String path = "D:"+File.separator+"IO"+File.separator+"demo1";

        File fnew = new File(path+File.separator+"demo002"+File.separator+"fu3.txt");
        try {
            fnew.createNewFile();
        } catch (IOException e) {
            System.out.println("没有上层目录，创建文件失败");
        }





    }

    /**
     * 异常<只是针对>没有上层目录的异常，已经存在不创建不是异常，不会抛出
     * <只有没有上层目录的时候才抛出没有上层目录异常>
     *
     *
     * */



    /**注意，如果写的路径不是绝对路径，写了个相对路径，创建的文件或目录会创建在当前项目下*/
    //2,创建目录,只是创建目录,不创建文件,遇到文件名也创建成目录
    @Test
    public void testCreate2(){
        String path = "D:"+File.separator+"IO"+File.separator+"demo1";

        //没上层目录就不创建
        File fnew = new File(path+File.separator+"demo002"+File.separator+"nei");


        //mkdir()  上层不存在，不创建，此目录已存在，不创建
//		fnew.mkdir()

        //mkdirs() 目录层次上，目录不存在就连上层目录一起创建,如果已经存在，不创建
        fnew.mkdirs();

        /**可以先创建目录，在调用方法创建文件*/
        try {
            //创建文件
            new File(fnew.getAbsolutePath()+File.separator+"张伟嘉sb.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("没有上层目录，创建失败");
        }

        //使用 <相对路径>创建目录或文件的时候会出现在当前项目下
        File file = new File("陈锋");
        file.mkdir();

        File file2  = new File("黄进.txt");

        try {

            file2.createNewFile();
            System.out.println(".............");
        } catch (IOException e) {
            System.out.println("上层目录不存在，不创建");//只有上层目录不存在时才报这个异常
        }



    }


    //删除文件 / 目录
    /**注意，java 的 删除不走回收站，直接清除在磁盘的数据
     */
    @Test
    public void deleteTest(){

        new File("陈锋").delete();
        new File("黄进.txt").delete();




    }



    /**
     * 对于File对象在堆中
     * 首先：无论此文件存不存在，name，和path属性都是有的，根据投入的路径参数
     * 当此文件不存在时：除了name和Path属性存在，其他的属性 比如length和isFile等全都是默认的属性值，毕竟你的文件不存在
     * 当此文件存在的时候：基本都有值，根据你的文件属性初始化成员属性的值
     * 但是有一些方法需要抛好异常，以免空指针，比如createNewFile，如果层目录为空，就出异常
     *
     *
     */



    //练习


    @Test
    public void test23(){
        String[] list = new File("D:"+File.separator+"IO"+File.separator+"demo1").list();
        for (String name : list) {

            if(name.contains(".jpg")){
                System.out.println(name);

            }

        }

    }










    //遍历指定目录下的所有的文件和目录名称
    @Test
    public void testioj(){
        File file = new File("D:"+File.separator+"IO");

        //DFS
//        check(file);

        //BFS
//        checkBFS(file);

        //递归删除
//        deleteDIgui(file);

//      统计目录占用的总空间
        countByte(file);

        System.out.println("文件总大小:"+count+" byte");
        count = 0L;




    }




    //DFS
    public static void check(File file){
        String name = file.getName();


        //如果是文件夹，就进去
        if(file.isDirectory()){
            System.out.println("目录："+name);//输出当前文件/目录的名字
            File[] listFiles = file.listFiles();  //获取的时候是先获取目录在获取文件的
            for (File fileBoy : listFiles) {
                check(fileBoy);

            }

        }else{
            //如果当前文件不是目录就直接输出名字就可以了
            System.out.println("文件："+name);

        }


    }




    //BFS
    public static void checkBFS(File file){

        if(!file.isDirectory()){
            System.out.println(file.getName());
            return;//当前第一个就不是目录直接输出name，结束

        }

        //先把当前第一个，头文件夹输出.
        System.out.println(file.getName());//IO

        //先定一个队列
        LinkedList<File> list = new LinkedList<File>();

        //如果是文件夹，for循环BFS
        File[] listFiles = file.listFiles();  //获取的时候是先获取目录在获取文件的

        //先将头文件夹里面的第一层全进队列
        addDuilie(listFiles,list);




        //开始出队列，一边出一边进队
        while(!list.isEmpty()){

            //出队列
            File removeFirst = list.removeFirst();


//				先输出，再判断是否文件夹,是文件夹直接扫描进队列
            if(removeFirst.isDirectory()){
                System.out.println("目录:"+removeFirst.getName());

                File[] files = removeFirst.listFiles();

                //出栈的时候把当前文件夹里面的所有文件进队列
                addDuilie(files,list);

            }else{
                System.out.println("文件:"+removeFirst.getName());
            }

        }



    }


    /**进队列方法*/
    public static void addDuilie(File[] listFiles,LinkedList<File> list){
        for (File fileBoy : listFiles) {

            //进队列
            list.addLast(fileBoy);


        }
    }


    /**
     * 递归删除
     */
    public void deleteDIgui(File file){
        if(file.isDirectory()){
            //将文件夹中的文件遍历出来
            File[] files = file.listFiles();


            //将文件夹中的文件再递归进去
            for (File file1 : files) {
                deleteDIgui(file1);

            }



            file.delete();//当前目录下为空就删除掉当前目录
            System.out.println("删除目录:"+file.getName());



        }else{
            //如果是文件，直接删除
            file.delete();
            System.out.println("删除文件:"+file.getName());
        }



    }


    /** 计算机目录结构层次以byte 字节存,目录不占字节
     * 1 byte == 8 bit
     *
     *时刻留意File对象的空指针错误，因为当你放的路径中的文件不存在的时候，
     * 虽然File对象中成员属性和很多方法都可以被File使用，但是
     *
     * @param file
     */
    public static void countByte(File file){
        String name = file.getName();


        //如果是文件夹，就进去
        if(file.isDirectory()){
            System.out.println(file.getName()+" 目录大小：" + file.length()+" Byte");
            count = count + file.length();
            File[] listFiles = file.listFiles();  //获取的时候是先获取目录在获取文件的
            for (File fileBoy : listFiles) {
                countByte(fileBoy);

            }

        }else{
            //如果当前文件不是目录就直接输出名字就可以了
            System.out.println(name+"文件大小："+file.length()+" Byte");
            count = count + file.length();

        }


    }



    @Test
    public void testjiedianhuanchon() throws IOException {
//        FileInputStream fin = new FileInputStream("src" + File.separator + "IO.txt");
//        后面的第二个参数是  是否追加，false就覆盖全部，true就尾追加
//        FileOutputStream fout = new FileOutputStream("Testppo.txt",false);
        FileWriter fout = new FileWriter("Testppo.txt");
        BufferedWriter bufferedWriter= new BufferedWriter(fout);


        bufferedWriter.write("张玮伽");

//        bufferedWriter.flush();




        //不关闭流
//        fin.close();

        bufferedWriter.close();




    }


}






















