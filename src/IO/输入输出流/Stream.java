package IO.输入输出流;

import org.junit.Test;
import sun.net.www.content.image.png;
import 反射.pojo.User3;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//IO流
public class Stream {

    /***
     *
        内存向内存写出情况：
                 必须要关闭流才能获取到

        内存向磁盘写出的情况:
            不关闭流的情况：
                字节流：依然可写出
                字符流：写出不了 ()
     */

    /**
     * 流的分类
     * 按操作数据单位不同分为：字节流(8 bit)，字符流(16 bit) [一个字节8bit，一个字符16bit]
     * 按数据流的流向不同分为：输入流，输出流
     * 按流的角色的不同分为：节点流，处理流
     *
     * (抽象基类)   字节流(8bit)    字符流(16bit)
     *   输入流    InputStream      Reader
     *   输出流    OutputStream     Writer
     *
     *
     * 按 "节点流" 和 "处理流"来分:
     *    节点流："直接从数据源或目的地读写数据"
     *          FileInputStream/FileOutputSteam
     *          FileReader/FileWriter
     *
     *    处理流：不直接连接到数据源或目的地，而是“连接”在已存
     * 在的流（节点流或处理流）之上，通过对数据的处理为程序提
     * 供更为强大的读写功能。
     *
     *
     *
     * 1. Java的IO流共涉及40多个类，实际上非常规则，都是从如下4个
     * 抽象基类派生的。
     *
     * 2. 由这四个类派生出来的子类名称都是以其父类名作为子类名后缀。
     */

    /** 输入流：InputStream & Reader
     *
     * InputStream 和 Reader 是所有输入流的"基类"。
     *
     * InputStream（典型实现：FileInputStream）
     *      int read()
     *      int read(byte[] b)
     *      int read(byte[] b, int off, int len)
     * Reader（典型实现：FileReader）
     *      int read()
     *      int read(char [] c)
     *      int read(char [] c, int off, int len)
     * 程序中打开的文件 IO 资源不属于内存里的资源，垃圾回收机制无法回收该资源，所以应该显式关闭文件 IO 资源。
     * FileInputStream
     *      从文件系统中的某个文件中获得输入"字节"。FileInputStream 用于读取非文本数据之类的原始字节流。
     *      要读取"字符流"，需要使用 FileReader
     *
     *
     *
     * //API
     *          输入字节流:  InputStream
     *  int read()
     * 从输入流中读取数据的下一个字节。返回 0 到 255 范围内的 int 字节值。如果因
     * 为已经到达流末尾而没有可用的字节，则返回值 -1。
     *
     *  int read(byte[] b)
     * 从此输入流中将最多 b.length 个字节的数据读入一个 byte 数组中。如果因为已
     * 经到达流末尾而没有可用的字节，则返回值 -1。否则以整数形式返回实际读取
     * 的字节数。
     *
     *  int read(byte[] b, int off,int len)
     * 将输入流中最多 len 个数据字节读入 byte 数组。尝试读取 len 个字节，但读取
     * 的字节也可能小于该值。以整数形式返回实际读取的字节数。如果因为流位于
     * 文件末尾而没有可用的字节，则返回值 -1。
     *
     *  public void close() throws IOException
     * 关闭此输入流并释放与该流关联的所有系统资源。
     *
     *
     *             输入字符流:   Reader
     *  int read()
     * 读取单个字符。作为整数读取的字符，范围在 0 到 65535 之间 (0x00-0xffff)（2个
     * 字节的Unicode码），如果已到达流的末尾，则返回 -1
     *
     *  int read(char[] cbuf)
     * 将字符读入数组。如果已到达流的末尾，则返回 -1。否则返回本次读取的字符数。
     *
     *  int read(char[] cbuf,int off,int len)
     * 将字符读入数组的某一部分。存到数组cbuf中，从off处开始存储，最多读len个字
     * 符。如果已到达流的末尾，则返回 -1。否则返回本次读取的字符数。
     *
     *  public void close() throws IOException
     * 关闭此输入流并释放与该流关联的所有系统资源。
     *
     * */

   // new File(相对路径下) 相对路径给下是指当前工程直辖之下，工程作为根目录
    /**节点流 read读取*/
    @Test
    public void test01()  {

        FileReader fr = null;
        try {
            File file = new File("src" + File.separator + "IO.txt");

//          ClassLoader.getSystemResourceAsStream获取系统类路径下的文件(相对当前项目根目录下)
//        InputStream in = ClassLoader.getSystemResourceAsStream("IO.txt");
            //其实当前的字节流就是FileInputStream 实现类对象，只不过多态给了接口

            FileInputStream in = new FileInputStream(file);

            System.out.println("字节："+in.read());//第一个字节

            fr = new FileReader(file);

            char[] ch  = new char[1024];

            int sum=0;

            int len;

            String strSum="";

            //再当前的[1024]情况下，并不是碰到一个字符没有获取到就返回-1,而是在新一轮的read中，一开始就知道源文结束，才返回-1
            //在最后一轮（还有字符，但是不满组，时候，先全部存储进去，返回这最后一轮的长度（有效读取的字符的长度，不会返回无效的））
            /**重点是在最后一轮，
             * 1024中前一部分是还有字符的，中间没有了，read方法返回前面读取到有效的字符的有效长度,后面的都是数组自己默认的字符
             * ,read既然在数据源中没读到字符，就停止读取了,还是会把前面有效读取的字符的长度返回。
             *
             * 然后到新一轮时，发现读进数组中的第一个字符也没有（在上一轮后面就没有了），直接返回-1，当然，这一轮不会有字符读进数组
             *
             *
             *
             */
            while ((len=fr.read(ch)) != -1){
                strSum=strSum + new String(ch,0,len);
                sum=sum+len;

            }

            System.out.println("sum总字符数"+sum);//原文确实是6684，一字无差
            System.out.println(strSum);

        } catch (IOException e) {
            e.printStackTrace();
        }







        //因为是从磁盘中获取的输入流，不是虚拟机内存自己的，所以需要手动关闭
//        GC只能回收内存中流的，不能回收来源于磁盘中的文件流
        try {
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // new File(相对路径下) 相对路径给下是指当前工程直辖之下，工程作为根目录
    /**写出流(write)*/
    @Test
    public void test02() throws IOException {
        // new File(相对路径下) 相对路径给下是指当前工程直辖之下，工程作为根目录
        //覆盖原同名文件
//        FileWriter fw = new FileWriter(new File("Test.txt"));

        //开启允许末尾追加（追加，不覆盖）
        FileWriter fw = new FileWriter(new File("Test.txt"),true);

        fw.write("Fuck you 张伟嘉");

        fw.close();


    }

    /**注意点
     *  定义文件路径时，注意：可以用“/”或者“\\”。
     *
     *  在写出一个文件时，如果使用构造器<FileOutputStream(file)>，则目录下有”同名文件将被覆盖“。
     *
     *  如果使用构造器<FileOutputStream(file,true)>，则目录下的同名文件不会被覆盖，在 "文件内容末尾追加内容。"
     *
     *  [在读取文件时，必须保证该文件已存在，否则报异常]。
     *
     *  【字节流操作字节】，比如：【.mp3，.avi，.rmvb，mp4，.jpg，.doc，.ppt】
     *
     *  【字符流操作字符】，只能操作普通文本文件。最常见的文本文件：.txt，.java，.c，.cpp 等语言的源代码。
     *
     *  -----尤其注意.doc,excel,ppt这些不是文本文件,不能用字符流来操作，一般用字节流------
     *
     * */

    /**缓冲区(处理流)<需要套接在对应的《节点流（直连磁盘源文件）上》>
     * (提高读写速度，因为原始的节点流是直接从磁盘读取数据的，每一个都需要从源文件读出来
     * 缓冲区的作用是一次性从源文件中读出来一部分存进内春中，然后后面需要用先拿内存中容易拿的缓冲数据
     *
     * 使内存中读写时直接去缓冲区<内存>拿，增快了读写速度(原始的是直接从磁盘中做流来拿《磁盘读取比内存中读取慢很多》)
     * )
     *  为了提高数据读写的速度，Java API提供了带缓冲功能的流类，在使用这些流类时，会创建一个内部缓冲区数组，缺省使用8192个字节(8Kb)的缓冲区。
     *
     *  【缓冲流】要【“套接”】在相应的【节点流】之上，根据数据操作单位可以把缓冲流分为：
     *     缓冲流分类: (依然是四基类的延申，,<输入&输出><字节&字符>)
     *      BufferedInputStream 和 BufferedOutputStream
     *      BufferedReader 和 BufferedWriter
     * */


    /**注意一点：【输入缓冲区】中会【自动刷新缓冲区】，【输出缓冲】【才需要刷新缓冲区】
     *  【输入缓冲不需要手动刷新缓冲】【输出缓冲最后需要手动刷新缓冲区把剩余未满的输出】
     *
     *  当读取数据时，数据【按块】读入【缓冲区】，其后的 【读操作则直接访问缓冲区】，【缓冲区满了才允许读缓冲区的内容】
     *
     *  【缓冲区满了，才允许对其缓冲内容进行读写】
     *
     *  当使用BufferedInputStream读取字节文件时，
     * BufferedInputStream会一次性从文件中读取8192个(8Kb)，存在缓冲区中，直到缓冲区装满了，再读缓冲区，才重新从文件中读取下一个【8192个【字节】】数组。
     *
     *  向流中写出字节时，不会直接写到文件，【先写出到缓冲区】中【直到缓冲区写满】,【满了才允许写出到文件】，
     * （写满缓冲区之后才会继续读写）BufferedOutputStream才会把缓冲区中的数据【一次性】写出到文件里。
     * 使用【方法flush()可以强制将缓冲区的内容】【全部写入输出流】(把最后一轮未满的缓冲也全部强制处理掉)
     *
     * 开启需要从内层流开始开启，往外一层层开启
     * 关闭流只需关闭最外层的，内层里面的流会自动全部关闭
     *  关闭流的顺序和打开流的顺序相反。只要关闭最外层流即可，关闭最外层流也
     * 会相应关闭内层节点流
     *
     * //主要是针对未满缓冲的数据，一般是最后一轮，因为未满缓冲不会允许执行，满缓冲才允许对其读写
     * //所以需要强制手动将最后一轮未满的进行允许读写操作
     *  flush()方法的使用：手动将buffer中内容写入文件
     *
     *
     * //带缓冲区的流，关闭的时候不但会关闭流，还会自动flush()
     *  如果是带缓冲区的流对象的close()方法，不但会关闭流，还会在关闭流之前刷
     *   新缓冲区，关闭后不能再写出
     */
    @Test
    public void test03() throws IOException {

        // 创建缓冲流对象：它是处理流，是对节点流的包装(缓冲输入流BufferedReader)
        BufferedReader br = new BufferedReader(new FileReader("Test.txt"));

        //缓冲输出流 (BufferedWriter)
        BufferedWriter bw = new BufferedWriter(new FileWriter("dest.txt"));

        String str = null;

        //等前面的缓冲读满8192字节

        //才允许下面的对缓存区中的进行操作

        //当最后一行一开始就没有字符，返回null
        while((str = br.readLine()) != null){

            //在写出的过程也一样，先进满缓冲区再进行写出操作
            bw.write(str);//一次写入一行字符串
            bw.newLine();//写入行分隔符

        }//在读取完一轮后，缓存区会再从磁盘再读取一轮出来，才允许继续对缓存操作


        //当到最后一轮，缓冲区中也未存满，磁盘中也读完了，此时还是默认未满缓冲区不允许操作
//        所以要 刷新缓冲区
        bw.flush();//

        //关闭流资源
        //关闭外层流时，会自动关闭内层流
        bw.close();   //注意：在关闭流时候，输出缓冲流（【处理流会自动刷新缓冲区】）
        br.close();



    }

    /**
     * FileInputStream、FileOutputStream和缓冲流：
     * BufferedInputStream、BufferedOutputStream实现文本文件/图片/视频文件的
     * 复制。并比较二者在数据复制方面的效率
     * */

    /**对于字节，字符，字符串以及之间转换的补充
     *
     * 首先，【字节流是万能的】，【可以对任意类型的文件进行处理】
     *
     * 首先，将磁盘文本读入内存的过程：
     * 字符串--->拆分成单独字符--->拆分成字节<字节本身是整数>（不同编码字节数不同）----->字节再往下拆分就是（机器码《01》）
     *
     * 而基本java中操作的是   字符（char） 和 字节(byte)
     *
     * byte和int都属于整数，byte比int小
     *
     * 字节就是整数，整数可以直接赋值给char类型，char本身用字节（整数）表示，所以整数也可以直接表示char
     *
     * (byte \ 整数)<---->char  (三者可以互换)
     *
     * 所以 字节流（byte） 和 字符流（char） 的read()方法都是返回"整数"（byte）
     *
     * 所以操作可以直接操作返回的 "整数"
     *
     * @throws IOException
     */

    /**FileInputStream & FileOutputStream(节点流)  VS BufferedInputStream & BufferedOutputStream(处理流《缓冲流》)*/


    /**节点流 处理文本*/
    @Test
    public void test04() throws IOException {
        File file = new File("Test.txt");
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream(new File("dest.txt"));

        int len = 0;

        byte[] b = new byte[1024];

        //字节 byte就是整数，这里字节流输入进内存面，字节流输出自然可以用字节来输出
        while ((len = fin.read(b)) != -1){
            fout.write(b,0,len);
        }
        fin.close();
        fout.close();


    }

    //注意：加密或解密不要覆盖掉源文件，不然可能会无法打开
    /**节点流 处理图片
     * 字节流万能，可以直接处理图片,或者加密图片
     * */
    @Test
    public void testp() throws IOException {
        File file = new File("C:\\Users\\HP\\Desktop\\实验文件夹\\图片\\tu.mp4");
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream("tu.mp4");



        int len = 0;
        while ((len=fin.read()) != -1){
            fout.write(len);

        }


        fin.close();
        fout.close();




    }


    /**节点流 处理视频 (此处增加缓冲 ”字节“ 处理流)
     * 字节流万能，可以直接处理视频,或者加密视频
     * */
    @Test
    public void test05() throws IOException {
        File file = new File("C:\\Users\\HP\\Desktop\\实验文件夹\\图片\\tu.mp4");
        FileInputStream fin = new FileInputStream(file);
        FileOutputStream fout = new FileOutputStream("tu.mp4");

        //视频和图片等是需要字节流处理，因为字符流是处理文本的，不能拿来处理这些字节文件
       //输入 字节 处理流
        //做处理流需要把内层最外层的流加进构造器来初始化，好让这个处理流来包着内层流
        BufferedInputStream finb = new BufferedInputStream(fin);
        BufferedOutputStream foutb = new BufferedOutputStream(fout);


        int len = 0;
        // 老规矩，字节流不是read(数组) 不是数组块传输，单纯的read()空参方法会返回对应的字节
        //当然，字符流也是返回对应发整数 ，因为字符(char)也是由字节(byte)来表示的 byte本身就是整数，整数和char可以互转，
        // 所以对应char返回对应的字节整数
        while ((len=finb.read()) != -1){
            foutb.write(len);

        }

        //输出缓冲最后需要手动刷新缓冲区，把缓冲区剩余未满的允许输出，
        // 清空掉缓冲区的最后残余的出来，撒完尿要冲厕所
        foutb.flush();


        /**关闭最外层处理流就可以自动关闭内层处理流*/
        finb.close();
        foutb.close();

    }



    /*加密测试*/
    @Test
    public void jiajiemiTest() {

        try {
            encryption("tu.mp4","tu2.mp4",3);
        } catch (IOException e) {
            System.out.println("加密失败");
        }


    }

    /*解密测试*/
    @Test
    public void jiemiTest() {

        try {
            decryption("tu2.mp4","tu3.mp4",3);
        } catch (IOException e) {
            System.out.println("解密失败");
        }


    }


    /**加密和解密需要在字节流及以下进行处理*/

    /**加密通用
     *
     * */
    public void encryption(String oldFile,String newFile,Integer key) throws IOException {


        //不能重名或者空名
        if (oldFile != null && !oldFile.equals(newFile)) {

//            默认为0，其实就是不加密
            Integer keyInt = 0;
            //如果有投入key参数，就赋值
            if (key != null){

                keyInt = key;//默认加密key为0 就不加密
            }

            File file = new File(oldFile);
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(newFile, false);



            int len = 0;
            while ((len = fin.read()) != -1) {
                fout.write(len + keyInt);


            }
            fin.close();
            fin.close();


            System.out.println(
                    "加密完成"
            );
        }else{
            System.out.println("文件名重复或者无文件名");
        }


    }

    /**解密通用
     *
     * */
    public void decryption(String oldFile,String newFile,Integer key) throws IOException {



        //不能重名或者空名
        if (oldFile != null && !oldFile.equals(newFile)) {

//            默认为0，其实就是不加密
            Integer keyInt = 0;
            //如果有投入key参数，就赋值
            if (key != null) {

                keyInt = key;//默认加密key为0 就不加密
            }
            File file = new File(oldFile);
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(newFile, false);

            int len = 0;
            while ((len = fin.read()) != -1) {
                fout.write(len - keyInt);

            }


            fin.close();
            fout.close();
            System.out.println(
                    "解密完成"
            );


        }


    }


//如果本来没有此文件，不会报错

    /**删除文件通用
     *
     */
    public void delFile(String delStr){
        File file = new File(delStr);
        file.delete();
    }


    @Test
    public void delFile(){

        delFile("mn3.jpg");
    }



    /**练习，统计每一个字符的出现次数，存进map中*/
    @Test
    public void testText() throws IOException {
        File file = new File("Test.txt");
        HashMap<Character, Integer> map = new HashMap<>();

        //字符输入流
        FileReader fin = new FileReader(file);
        //字符输出流
        FileWriter fout = new FileWriter("统计字符出现次数.txt");


        //写入并存进map中
        int len = 0;
        while ((len=fin.read())!=-1){

            //int因为范围比较大，比char要大，所以需要往下，需要强转才可以
            char ch = (char)len;


            if (!map.containsKey(ch)){
                //map中暂时还没有此key (添加或修改)
                map.put(ch,1);

            }else{
                //已经有了，只需要修改一下value(添加1)(添加或修改)
                map.put(ch,map.get(ch)+1);

            }

        }


        //写出map到文件

        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            Map.Entry en = entry;

            fout.write("["+en.getKey()+","+en.getValue()+"]\n");



        }



        fin.close();
        fout.close();





    }


    //String字符串的引用就时字符串，就算用引用与其他类型相加也会变成拼接字符串
    @Test
    public void str(){
        String str = "asd.op";

        System.out.println(str.indexOf("."));
        String start = str.substring(0, str.indexOf("."));
        String end = str.substring(str.indexOf("."),str.length());

//        for (){
//
//        }

        System.out.println(start+1+end);


        //
        System.out.println("\n测试map");
        HashMap<Character, Integer> map = new HashMap<>();


        System.out.println(map.get('k'));


    }


    /**转换流 (处理流)
     *转换流提供了在字节流和字符流之间的转换
     *Java API提供了两个转换流：
     *        InputStreamReader：将InputStream转换为Reader
     *        OutputStreamWriter：将Writer转换为OutputStream
     *  字节流中的数据都是字符时，转成字符流操作更高效。
     *  很多时候我们使用转换流来处理文件乱码问题。实现编码和解码的功能。
     *
     */
    @Test
    public void zhuanhuan() throws IOException {

        /**基础字节流*/
        //字节输入流
        FileInputStream fin = new FileInputStream(new File("Testchu.txt"));
        //字节输出流
//        FileOutputStream fout = new FileOutputStream(new File("Testchu.txt"));

        /**套接进对应的处理流,此源文件是UTF-8编码的，就要以UTF-8解码*/
        //字节流转换成字符流，解码，按照源文件的编码规则来解码
        InputStreamReader find = new InputStreamReader(fin,"GBK");


        /**字节流转换成字符流，转换规则此处设置是GBK规则来转换 编码 写出*/
//        OutputStreamWriter foutd = new OutputStreamWriter(fout,"GBK");


        //在转换流处理流之上，还可以包一层 缓冲处理流 ,当然，是字符缓冲流，因为里面的一层流是字符流，所以外层需要是字符处理流
//        不然传过来的是字节，你当字符来处理就不好了
        BufferedReader findb = new BufferedReader(find);//"字符"输入缓冲流
//        BufferedReader foutdb = new BufferedReader(foutd);//"字符"输出缓冲流


        /**编码和解码概念
         * 编码：字符按一套 （规则） 往下打散成对应的字节
         * 解码：字节 按照编码时候的（同一规则） 组装还原成对应的字符
         *
         * 字符往下打散成字节（编码）-------可以自由选择对应的编码规则
         * 字节往上解码还原成字符（解码） ------ 必须按照原来编码时的规则来解码，不然乱码（因为是逆向过程，所以需要编码时的正确规则逆向）
         *
         *
         * 以加密来举例：加密的时候可以自由的放key移位法 或 替换法加密，key自由选择
         * 但是解密的时候； 就不能随意使用值来解密，只能以原来加密时的key值才可以正确解密，不然解出来也是错误的
         *
         *
         */
        /**解码的时候只能以编码时候的编码规则来解码，如果原来是UTF-8编码（字符按）*/
        //读进来的时候设置成
        int len;
        while ((len = findb.read()) != -1){
//            foutdb.write((char)len);
            System.out.print((char)len);


        }


        //关闭资源,关闭外层，内层自动关闭
        findb.close();
//        foutdb.close();



    }


    /**字符集补充 :
     *
     *  编码表的由来
     * 计算机只能识别二进制数据，早期由来是电信号。为了方便应用计算机，让它可以识
     * 别各个国家的文字。就将各个国家的文字用数字来表示，并一一对应，形成一张表。
     * 这就是编码表。
     *  常见的编码表
     *  ASCII：美国标准信息交换码。
     *  用一个字节的7位可以表示。
     *  ISO8859-1：拉丁码表。欧洲码表
     *  用一个字节的8位表示。
     *  GB2312：中国的中文编码表。最多两个字节编码所有字符
     *  GBK：中国的中文编码表升级，融合了更多的中文文字符号。最多两个字节编码
     *  Unicode：国际标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的
     * 字符码。所有的文字都用两个字节来表示。
     *  UTF-8：变长的编码方式，可用1-4个字节来表示一个字符。
     *
     *
     *
     *  在Unicode出现之前，所有的字符集都是和具体编码方案绑定在一起的（即字
     * 符集≈编码方式），都是直接将字符和最终字节流绑定死了。  GBK等双字节编码方式，用最高位是1或0表示两个字节和一个字节。
     *
     *
     *
     *  Unicode不完美，这里就有三个问题，一个是，我们已经知道，英文字母只用
     * 一个字节表示就够了，第二个问题是如何才能区别Unicode和ASCII？计算机
     * 怎么知道两个字节表示一个符号，而不是分别表示两个符号呢？第三个，如果
     * 和GBK等双字节编码方式一样，用最高位是1或0表示两个字节和一个字节，
     * 就少了很多值无法用于表示字符，不够表示所有字符。Unicode在很长一段时
     * 间内无法推广，直到互联网的出现。
     *  面向传输的众多 UTF（UCS Transfer Format）标准出现了，顾名思义，UTF- 8就是每次8个位传输数据，而UTF-16就是每次16个位。这是为传输而设计的
     * 编码，并使编码无国界，这样就可以显示全世界上所有文化的字符了。
     *  Unicode只是定义了一个庞大的、全球通用的字符集，并为每个字符规定了唯
     * 一确定的编号，具体存储成什么样的字节流，取决于字符编码方案。推荐的
     * Unicode编码是UTF-8和UTF-16。
     *
     *
     * Unicode符号范围 | UTF-8编码方式
         (十六进制)    |  （二进制）
     —————————————————————-----------------------------------------
     0000 0000-0000 007F | 0xxxxxxx（兼容原来的ASCII）
     0000 0080-0000 07FF | 110xxxxx 10xxxxxx
     0000 0800-0000 FFFF | 1110xxxx 10xxxxxx 10xxxxxx
     0001 0000-0010 FFFF | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
     *
     *
     *
     *
     * 0和1 ---> ASCII --->[ANSI{GB2312-->GBK,BIG5,JIS}]---->Unicode[UTF-8,UTF-16,UTF-32]
     *
     *
     */




    /**标准 输入 , 输出流
     *  System.in 和 System.out 分别代表了系统标准的输入和输出设备
     *  [默认输入设备]是：[键盘]，[输出设备是]：[显示器]
     *
     *  [System.in]的类型是[InputStream]
     *  [System.out]的类型是[PrintStream]，【其是[OutputStream的子类FilterOutputStream] 的 [子类]】
     *
     *
     * 在System.in类中:public final static InputStream in = null;这样定义 System.in InputStream类型
     * 在System.in类中:public final static PrintStream out = null;这样定义 System.out PrintStream类型
     *
     * InputStream ------ >System.in
     * OutputStream ----子类--->FilterOutputStream----子类----> PrintStream----->System.in
     *
     *
     *
     *  重定向：通过System类的setIn，setOut方法对默认设备进行改变。
     *       public static void setIn(InputStream in)
     *       public static void setOut(PrintStream out)
     * */



    /**单元测试如果不允许输入，其实是IDEA的问题，所以只需配置IDEA就可以*/
    /**注意，当前是测试方法，测试方法需要在IDEA中配置可开启单元测试允许输入才可以在单元测试中输入*/
    @Test
    public void systemIO()  {

        //因为System.in  \ System.out 都是字节流，当输入字符时，会先 编码 打散成字节，然后字节输入虚拟机内存
        //此时需要将字节流用转换流转换成对应的字符

        //只用缓冲字符流来处理  (System.in(键盘输入 字节流)<键盘输入字符会转换成字节流输入> ---->转换成字符流----->包装在缓冲处理流中)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        /**因为此时的输入流控制权交给了键盘（用户），所以read的时候是听取用户的输入，不再是自动读取文件，而是读取键盘输入
         * 所以这里回车不会结束,只是代表readLine当前行读取（输入结束）
         * 关闭输入流是时候才是结束输入的时候
         *
         *
         * */

        String str = null;

        try {
            /**每一次触发readLine()都会开启键盘输入*/
            //现在readLine读取的权力在用户控制的键盘手中(读文件 ----- > 读键盘的输入)
            while ((str = br.readLine()) != null){ //读入用户输入的一行数据 ---->阻塞程序

                //equalsIgnoreCase
                if ("e".equalsIgnoreCase(str) || "exit".equalsIgnoreCase(str)){
                    System.out.println("安全退出");
                    break;

                }

                //不是退出的话就直接操作次Str
                System.out.println("---->"+str.toUpperCase());



            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
//            无论如何，都关闭流
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }


    /**打印流 (了解) [可自定义System.out.print..打印到的位置（文件或控制台） setOut换流]
     * 实现将基本数据类型的数据格式  '转化为字符串' 输出
     * 打印流：'PrintStream'（字节打印） 和 'PrintWriter' （字符打印）
     *       提供了一系列重载的print()和println()方法，用于多种数据类型的输出
     *       PrintStream和PrintWriter的输出 [不会抛出IOException异常]
     *       PrintStream和PrintWriter有  [自动flush功能]
     *       PrintStream 打印的 “所有字符都使用平台的默认字符编码”转换为“字节”。
     *      在"需要写出字符"而 "不是写出字节" 的情况下，应该使用 "PrintWriter 类。"
     *       System.out返回 的是 "PrintStream的实例" 然后调用PrintStream的实例的的方法
     *
     *  凡是System.out.print..原生没改的，都是将所有基本数据类型转换成字符串打印
     * */
    @Test
    public void dayin() throws FileNotFoundException {
        /**原本的System类中的out对应的属性是”控制台流“ 所以直接的System.out.print... 默认的输出都是在控制台
         * 其实可以造一个自定义的流，将此流用对应的PrintStream & PrintWriter,
         * System.setOut(ps);    System的setOut(文件流);将对应的文件流替换掉原来的 控制台流 就可以让其打印到指定的文件
         *
         */

        /**当前是使用PrintStream字节打印流，也可以用来直接输出字符，打印时会 字符 --- 字节 --- 字符 这样转换出来*/
        /**如果想直接点，不想这样字节字符转换，可以直接用PrintWriter处理*/
        /**注意：PrintStream和PrintWrite里面都是有缓冲处理流的*/

        //先造好基础的节点流
        FileOutputStream fos = new FileOutputStream(new File("gtt.txt"));
// PrintStream和PrintWriter有  [自动flush功能]
        //打印字节，设置为自动flush 刷新出缓冲区
//        打印字节流 PrintStream
        PrintStream ps = new PrintStream(fos, true);
        // 创建打印输出流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)



//      虽然打印流PrintStream实例造好了，但是System系统类不知道这个流，还在用打印处控制台的流，
//      所以将PrintStream流实例注册进System中替换掉原来的打印控制台流
        if (ps != null){
            //将System中的out默认打印到控制台流换一下，换成自己自定的文件流，打印到此文件中，而不是打印到控制台
//           System.out返回的是PrintStream的实例,此处设置进了此实例，
//          在输出的时候System.out会返回此实例，此实例的输出方法会输出到对应的位置
            System.setOut(ps);
        }
//        实现将基本数据类型的数据格式转化为  '字符串' 输出
        System.out.println("aoiksydkihdlka黄进shdo");




//        PrintStream 不是节点流  ，是处理流,所以关闭外层处理流就可以
//        节点流是直接数据源传输的，字节或字符
        ps.close();










    }


    /**数据流(了解)
     * 为了方便地操作Java语言的‘基本数据类型’和‘String’的数据，可以使用数据流。
     * 详细解释：在原来的System.out输出流 或其他输出流  中只是将所有类型转换成字符串输出，
     * 当 【读取回来的时候就只是字符串String类型，还要转类型出来】，，，，
     * 而数据流却可以将基本数据类型不转输出，【当读取回来时就时写出时候的类型，不需要转类型出来】
     *
     *  数据流有两个类：(用于‘读取‘和’写出‘--->  基本数据类型、String类的数据）
     *       DataInputStream 和 DataOutputStream  (处理流)
     *       分别“套接”在 InputStream 和 OutputStream 子类的流上
     *
     *  DataInputStream中的方法
     * boolean readBoolean() byte readByte()
     * char readChar() float readFloat()
     * double readDouble() short readShort()
     * long readLong() int readInt()
     * String readUTF() void readFully(byte[] b)
     *
     *  DataOutputStream中的方法
     *  将上述的方法的"read改为相应的write"即可。
     *
     *
     *
     * 凡是System.out.print..原生没改的，都是将所有基本数据类型转换成字符串打印
     */


    /**数据流写出*/
    @Test
    public void shujuliu() throws IOException {
        //将基础输出流包含在处理流（数据流）中
        DataOutputStream dout = new DataOutputStream(new FileOutputStream(new File("gtt.txt")));

        //

        dout.writeUTF("我丢你马"); //写出”UTF 字符串“
        dout.writeBoolean(false);// 写出boolean型
        dout.writeInt(123);     //写出int
        dout.writeLong(89798L); //写出long
        dout.writeLong(8928L); //写出long


        System.out.println("写出成功");

        //关闭最外层流
        dout.close();




    }


    /**数据流 （读入）
     * */
    @Test
    public void shujuliu2() throws IOException {
        //数据流读入
        DataInputStream din = new DataInputStream(new FileInputStream("gtt.txt"));


        //读入API,读入后不需要转换类型，直接就时输出时候的类型
        String str = din.readUTF();
        boolean b = din.readBoolean();
        long l = din.readLong();
        int i = din.readInt();


        System.out.println(str);
        System.out.println(b);
        System.out.println(l);
        System.out.println(i);


        din.close();


    }




    /**对象流 （处理流）
     *
     *  ObjectInputStream和OjbectOutputSteam
     *       用于存储和读取基本数据类型数据或对象的处理流。它的强大之处就是可
     * 以[把Java中的对象写入到数据源中]，也[能把对象从数据源中还原回来]。
     *
     *  序列化(保存\写出)：用ObjectOutputStream类保存基本类型数据或对象的机制
     *  反序列化(读取\写入)：用ObjectInputStream类读取基本类型数据或对象的机制
     *
     *  ObjectOutputStream和ObjectInputStream
     * 【不能序列化static和transient修饰的成员变量】
     *
     * 因为，对象流是针对于对象的，【静态的属性 是 [属于类的] ，不是属于对象的】，故不被对象流序列化
     *  transient是短期临时的，所以也不能被序列化
     *
     * 对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流，从
     * 而允许把这种二进制流持久地保存在磁盘上，或通过网络将这种二进制流传
     * 输到另一个网络节点。//当其它程序获取了这种二进制流，就可以恢复成原
     * 来的Java对象
     * 序列化的好处在于可将任何实现了Serializable接口的对象转化为字节数据，
     * 使其在保存和传输时可被还原
     * 序列化是 RMI（Remote Method Invoke – 远程方法调用）过程的参数和返
     * 回值都必须实现的机制，而 RMI 是 JavaEE 的基础。因此序列化机制是
     * JavaEE 平台的基础
     *
     *
     *此对象可以被序列化的前提，
     *为了让某个类是可序列化的，该类  【必须实现如下两个[接口]【之一】】。
     * 否则，会抛出NotSerializableException异常
     *      Serializable
     *      Externalizable
     *
     *
     *
     *
     * 校验和版本控制  serialVersionUID
     * 凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量：
     *      private static final long serialVersionUID;
     *      serialVersionUID用来表明类的不同版本间的兼容性。简言之，其目的是以序列化对象
     * 进行版本控制，有关各版本反序列化时是否兼容。
     *      如果类没有显示定义这个静态常量，它的值是Java运行时环境根据类的内部细节自
     * 动生成的。若类的实例变量做了修改，serialVersionUID 可能发生变化。故[建议，显式声明]可以明显看到UID情况。
     *
     *
     *  简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来[验证]
     * 版本一致性的。在进行 [反序列化] 时，JVM会把 [传来的][字节流]中的
     * serialVersionUID与本地相应实体类的serialVersionUID进行[比较]，如果相同
     * 就认为是一致的，[可以进行反序列化]，[否则就会出现序列化版本不一致的异常【(InvalidCastException)】
     *
     *
     *
     */


    /***使用对象流序列化对象
     * 若某个类实现了 Serializable 接口，该类的对象就是可序列化的：
     *      创建一个 ObjectOutputStream
     *      调用 ObjectOutputStream 对象的 writeObject(对象) 方法输出可序列化对象
     *      注意写出一次，操作flush()一次 //需要手动更新一次刷新处缓冲区
     *
     * 反序列化
     *      创建一个 ObjectInputStream
     *      调用 readObject() 方法读取流中的对象
     *
     * 强调：如果某个类的属性不是基本数据类型或 String 类型，而是另一个
     * 引用类型，那么这个引用类型  "必须是可序列化"(必须实现对应的接口
     *               Serializable
     *               Externalizable
     * )  的，否则拥有该类型的
     * Field 的类也不能序列化
     *
     *
     *
     *
     *
     *
     * // 会有隐藏的serialVersionUID校验序列和反序列的UID是否一致
     * //    此类变化了，UID就会改变，如果改变了，反序列化就会失败
     * //    比如在序列化写出对象后，把原来的可序列化类改变了，导致类的UID改变
     * //    在反序列化读入时就需要用这个类去接读入的对象，因为UID不同了，会抛异常反序列化失败
     * //    除非你把java中的类改回和目标一样的，UID就会一样,就可以接受
     * // 注意区分:会影响UID只是的代码，属性不会影响UID，比如字符串的【值】，
     * toString打印的值都不会使UID改变，只有代码，比如属性名。修饰符，类名，方法等的改变才会导致UID改变
     *
     *
     *
     */

    //序列化
    @Test
    public void duixangliu() throws IOException {
        //要求对应的对象的类必须可序列化，即其类实现了Serializabl或Externalizable接口
        ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream("data.txt"));

        objOut.writeObject(new User3());

        //注意，对象输出流，[也是有缓冲区的]
        //缓冲区不一定刚好是满缓冲状态，可能第一次就不满，刷新不出去，也可能第二次的没满
//        所以需要手动flush
        objOut.flush();

        //关闭流
        objOut.close();

    }


//    反序列化
    @Test
    public void fanxuliehua() throws IOException, ClassNotFoundException {
        /**如果反序列化之前，此对象的原类被修改，UID改变，JVM校验时发现UID不等就不让反序列
         * 值方面和方法内部怎么样都可以，但是代码一分不准变，不准改原来代码，不准添加或者删除任何成员
         *除了值 和 注释 和方法内部的处理(当然不会有静态的，因为不会序列化静态的成员)，其他的分毫都要一样
         *
         * 方法自然是非静态方法，在序列化出去之后其实只是保留了方法的声明，方法内部的东西并没有序列化出去(所以改原类方法内部的东西是可以的)
         * 方法在方法区，在你的对象调用的时候才去加载类的此方法,
         * 所以改方法内部没关系，【对象中只是存个方法声明】，在实际调用到的时候才会【临时去方法区根据类加载方法（所以方法中内容怎么样还是由类来决定的）】
         *
         *
         * 【序列化和反序列和静态半毛钱关系没有，互不干涉,互不影响】[都写的清楚了 是[对象流]，关类吊事]
         * 【如果想远程传输对象时把静态成员直接带过去是不可能的，因为序列化不会序列化静态的成员，根本没有】(如果想传过去静态的值可以赋值给非静态成员来代为传输过去)
         * 注意，序列化不能序列化静态的成员，因为是属于类的，不是对象的，不能序列化
         * (当然，强行序列化是可以运行的的，但是没吊用，静态是类的东西，不会跟着序列化，就是写出后的文件中其实是没有静态内容的，反序列化调用时的也是类的东西
         * 不关对象的事情，所以怎么修改静态都是类层面的，序列化和反序列都会忽视静态的成员)
         *
         * 序列和反序列就是针对实例的，针对不了类，
         *
         * 【总结就是，要想反序列成功，不准修改原类成员的声明信息】 [内容可以随便改，比如成员是初始属性，方法内部的具体实现]
         *
         */


        ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("data.txt"));

        //反序列化返回的是Object，需要向下强转一下
        User3 user3 = (User3)objIn.readObject();

        //打印对象信息
        System.out.println(user3.toString());

        //读入流不需要手动flush，回自动flush
        objIn.close();




    }


    /**好处(直接传输数据，不同操作系统的对象可以互相传输交流
     * （因为序列化时都是打成字节的，字节是万能的，是计算机底层的，不会因为操作系统不同而不兼容）)
     *  实现了Serializable接口的对象，可将它们转换成一系列字节，并可在以后
     * 完全恢复回原来的样子。这一过程亦可通过网络进行。这意味着序列化机
     * 制能自动补偿操作系统间的差异。换句话说，可以先在Windows机器上创
     * 建一个对象，对其序列化，然后通过网络发给一台Unix机器，然后在那里
     * 准确无误地重新“装配”。不必关心数据在不同机器上如何表示，也不必
     * 关心字节的顺序或者其他任何细节。【字节都一样，不同操作系统也一样，
     * 更何况是跑在JVM跨语言的平台（虽然一个JVM不跨操作系统，但是由多个JVM对接多个不同系统）上的java代码（跨平台的语言），更可以了】
     *  由于大部分作为参数的类如String、Integer等都实现了
     * java.io.Serializable的接口，也可以利用多态的性质，作为参数使接口更
     * 灵活
     *
     */









    /**随机存取文件流
     *
     * RandomAccessFile 类
     * RandomAccessFile 声明在java.io包下，但直接继承于java.lang.Object类。
     * 并且它实现了DataInput、DataOutput这两个接口，也就意味着这个类 [既可以读也可以写]。
     *
     * 虽然这个类既可以输出也可以输入，但不是说一个对象又输出又输入，因为一个对象又读入又输出，会共用一个指针
     * 造两个RandomAccessFile的对象，一个专门读入，一个专门写出，都有自己独立的指针
     *
     * RandomAccessFile 类支持 “随机访问” 的方式，程序可以直接跳到文件的任意地方来读、写文件
     *      支持只访问文件的部分内容
     *      可以向已存在的文件后追加内容
     *
     * RandomAccessFile 对象包含一个记录指针，用以标示当前读写处的位置。
     * RandomAccessFile 类对象可以自由移动记录指针：
     *      long getFilePointer()：[获取]文件[记录指针的当前位置]
     *      void seek(long pos)：将文件记录[指针定位到 pos 位置]
     * */

    /*** RandomAccessFile 类
     *  构造器     mode参数指定模式
     *      public RandomAccessFile(File file, String mode)
     *      public RandomAccessFile(String name, String mode)
     *
     *
     * 创建 RandomAccessFile 类实例需要指定一个 mode 参数，该参数指
     * 定 RandomAccessFile 的访问模式：
     *      r: 以【只读】方式打开
     *      rw：打开以便【读取】和【写入】
     *      rwd:打开以便【读取】和【写入】；【同步文件内容的更新】
     *      rws:打开以便【读取】和【写入】；【同步】文件【内容】和【元数据的更新】
     *
     *  如果模式为【只读r】。则【不会创建文件】，而是【会去读取一个已经存在的文件】，
     * 如果读取的文件【不存在则会出现【异常】】。 如果模式为【rw】读写。【如果文件不存在则会去创建文件，如果存在则不会创建】。
     *
     *
     *
     *
     *
     *
     * 我们可以用RandomAccessFile这个类，来实现一个【多线程断点下载】的功能，
     * 用过下载工具的朋友们都知道，下载前都会建立两个临时文件，一个是与
     * 被下载文件大小相同的空文件，另一个是记录文件指针的位置文件，每次
     * 暂停的时候，都会保存上一次的指针，然后断点下载的时候，会继续从上
     * 一次的地方下载，从而实现断点下载或上传的功能，有兴趣的朋友们可以
     * 自己实现下。
     */

    //读取文件内容
    @Test
    public void RandomRead() throws IOException {
        //创建随机文件处理流  (此处设置可以修改)
        RandomAccessFile raf = new RandomAccessFile("Test.txt", "rw");

        //设置开始指针
        raf.seek(0);


        /**现在是字节流，这样装byte数组然后转换成字符，在数组临界值时组合会乱码，因为一起的字节分开转字符的*/
        /**这种方法只适合高存字节，不要用来搞字符，搞字符就用字符流读进来然后char[]，不要用字节流byte[]转字符*/
        byte[] b = new byte[1024];



        //将字节读进数组b
        raf.read(b,0,12);

        System.out.println(new String(b,0,12));


        raf.close();


    }
    /**就是readLine贱，把字节单个单个全部独立转字符了，导致再后面字符串getBytes()时乱码*/

    /**【指针自始至终都只有一个，只是记录最新的操作的位置】 哪个操作最新执行，指针就记录在那个操作的最新坐标位置，无论是读还是写，只认最新操作*/
    /**随机文件流 （注意；seek的指针不是指字符数下标，而是[字节数下标的指针] 而且读取和写出都会影响指针）
     * RandomAccessFile首先十个字节流，但是比较特殊（比较贱）
     * FileInputStream流这样的文件字节流，首先按byte[]按块写出完全没有问题，会关闭连接后才全部转换成字符，不会乱码
     *    【事实证明，readLine有毒，不知道怎么的转不出来】
     *
     *
     *StringBuilder方式,凡是字符控制的，针对数字，字母是可以不乱码的，
     *字母和数字本身是由一个字节表示的，所以是单字节独立性，不会依赖多个字节，不会乱码
     * 所以针对字母可以随意单个字节就转字符，但是不可以对中文字符这样做，中文字符不止一个字节表示,不能单独字节转字符，需要全部集合在一起在转
     *
     *
     *对于比如System.out.println() 和 readLine()
     *      toString 和 new String()
     *      都是直接转换成字符的，所以如果你是中文，不能这样“分开转”，会乱码
     *
     *
     *
     * 直接将byte[]原生字节，不做转换，直接原生写出中文不会乱码
     *
     * 但是对于中文，而且当前是字节流，中间给转了的话，再getBytes()返回的数组已经不是原生的字节组合了
     * 所以乱码
     *
     * 对于中文字符：
     * 当前是字节流，就他妈的不要中间转字符，一旦转了，就是把单个字节转字符了，
     * 原生的组合规则会被改变，还原不回来的，就算再getBytes()拿会字节数组，已经不是原来的字节排列了
     *new String()可以将字节数组转换成对应的中文字符
     * */
    //写出 & 读取 文件  //ByteArrayOutputStream方式
    @Test
    public void RandomWri() throws IOException {
        //创建随机文件处理流  (此处设置可以修改)
        RandomAccessFile raf = new RandomAccessFile("Test.txt", "rw");


        //先读出来
        //设置开始指针
        raf.seek(0);

        //为什么要这样做？，因为当前是字节流，不是字符流，单个拆开转字符会错误,全部拼起来转才是对的
        //用字符流用多了，忘记字节流的特性了，字节流就是一个一个字节过来的，不能将一个字节直接转字符，因为字符不止一个字节可能
        //单个领出来转肯定会乱码的，只有一起转才不会乱码,如果是字符流就可以单个转，因为传过来时就是一个个字符，返回对应的整数
//        直接拿此整数可以单独转换回来原来的字符，现在RandomAccessFile是字节流，不是单个字符，是单个字节传过来的，不能直接转
//        byte[] b = new byte[1024];


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[10];

        int end = 0 ;



        while ((end = raf.read(buffer)) != -1){

            //先不转，将字节写出到暂时的ByteArrayOutputStream流,字节全部一次性存起来
            baos.write(buffer,0,end);//直接写出不转确实是可以,这里先覆盖掉原来的文章，然后尾添加黄进
        }



        //虽然readLine读取的不是原数据字符，但是可以获取到字节的正确的指针
//        String str = raf.readLine();

//        int length=str.length();

//        设置指针，从此处开始插入,因为当前读进来的是字符不是字节，所以需要转一下,拿字节的长度
//        raf.seek(str.getBytes(StandardCharsets.UTF_8).length);

//        System.out.println(baos.toString());

        //这时才一次性拿出来全部转换就没有问题了
//        raf.write(baos.toString().getBytes(StandardCharsets.UTF_8));

//        raf.write(str.getBytes());


//        想要尾添加就不要自定义指针，seek中的指针会随着 写出或读入 自动在最新处
//        raf.seek(baos.toString().getBytes().length);


        //是自动的指针，前面的read读到哪里，指针就到哪里
        System.out.println(raf.getFilePointer());//76926

//        raf.seek(0);
        raf.write("黄进".getBytes());

        System.out.println(raf.getFilePointer());//76932
        baos.close();
        raf.close();




    }


    //StringBuilder方式,凡是字符控制的，针对数字，字母是可以不乱码的，
//    字母和数字本身是由一个字节表示的，所以是单字节独立性，不会依赖多个字节，不会乱码
//    所以针对字母可以随意单个字节就转字符，但是不可以对中文字符这样做，中文字符不止一个字节表示,不能单独字节转字符，需要全部集合在一起在转
    @Test
    public void RandomWri2() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("Test.txt", "rw");
        RandomAccessFile raf2 = new RandomAccessFile("Test.txt", "rw");


        raf1.seek(0);


        //先创建一个StringBuilder容器
        StringBuilder info = new StringBuilder((int) raf1.length());

        byte[] buffer = new byte[1024];

        int len = 0;
        while ((len=raf1.read(buffer))!=-1){
            info.append(new String(buffer,0,len));
        }

        raf2.write(info.toString().getBytes());

        raf1.close();
        raf2.close();


    }





    //字节与字符流
    @Test
    public void zijie() throws IOException {
        //创建随机文件处理流  (此处设置可以修改)
        RandomAccessFile raf = new RandomAccessFile("Test.txt", "rw");
        RandomAccessFile raf2 = new RandomAccessFile("Test.txt", "rw");

        ByteArrayOutputStream ba = new ByteArrayOutputStream();

        //先读出来
        //设置开始指针
        raf.seek(0);

        byte[] b = new byte[1024];

        //
        int len = 0;
        /**重点是*///因为字节输入流进来的字节是不变的，打散回字节还是一样的
        while((len=raf.read(b))!=-1){
            raf2.write(b,0,len);

        }

        //因为写出也会影响到指针，所以直接是尾添加
        raf2.write("黄进".getBytes(StandardCharsets.UTF_8));

        ba.close();
        raf.close();
        raf2.close();

    }




    /**事实证明，readLine有毒，针对中文肯定乱码，因为已经将原字节组合成乱码字符，再进行打散的时候已经不是原来的字节组合规则
     * 所以转不出来
     *
     * new String()可以将字节数组转换成对应的中文字符
     * */
    @Test
    public void tttest() throws IOException {
        //创建随机文件处理流  (此处设置可以修改)
        RandomAccessFile raf = new RandomAccessFile("Test.txt", "rw");
        RandomAccessFile raf2 = new RandomAccessFile("Test2.txt", "rw");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //先读出来
        //设置开始指针
        raf.seek(0);

        //其实当前是字节流，用readLine,会将读进来的每一个字节都单独转字符，所以就是我们看到的乱码字符,
//        如果用getBytes()整体来打散成字节是不对的，会乱码，单个单个char转回byte就可以
        String s = raf.readLine();

        for (int i=0;i<s.length();i++){
            raf2.write((byte)s.charAt(i));
        }

        baos.write(s.getBytes());


        System.out.println(baos.toString());


        raf.close();
        raf2.close();
        baos.close();

    }


    /**
     * 流的基本应用小节
     *  流是用来处理数据的。
     *  处理数据时，一定要先明确数据源，与数据目的地
     *  数据源可以是文件，可以是键盘。
     *  数据目的地可以是文件、显示器或者其他设备。
     *  而流只是在帮助数据进行传输,并对传输的数据进行处理，比如过滤处理、
     * 转换处理等
     */


    //针对于中文字符 字节的测试

    /**在中文的而言，在原生的基础上对每一个字节进行char转换后，再次还原回来是还原不了的，因为单个字节转字符的时候就都打乱了中文的字节组合规则,
     * 再转回来的时使用getBytes()规则已经被破坏,就会乱码,除非把字符串中的字符一个个还原成字节再处理
     * -23-69-124-24-65-101
     * ￩ﾻﾄ￨﾿ﾛ
     * -17-65-87-17-66-69-17-66-124-17-65-88-17-66-65-17-66-101
     *
     *
     * new String()可以将字节数组转换成对应的中文字符
     *
     * 字母和数字是由一个字节表示的，所以不会乱码，随便转
     *
     *
     *
     */
    @Test
    public void zhowen(){
        String str = "黄进";
        String str2 ="";


        for (byte aByte : str.getBytes()) {
            System.out.print(aByte);
        }

        System.out.println();

//        for (byte aByte : str.getBytes()) {
//            //字母或数字不会乱，中文会乱，因为中文不止一个字节表示
//            //把原本中文多字节组合中的每个字节单独拆分再转化成字符
//           str2 += (char)aByte;
//
//        }


        str2 = new String(str.getBytes());


        System.out.println(str2);



        //str2的getByte = -17-65-87-17-66-69-17-66-124-17-65-88-17-66-65-17-66-101
//        for (int i=0;i<str2.length();i++){
//            char c = str2.charAt(i);
//            System.out.print((byte)c);
//
//
//        }


    }















    /**Java NIO (面向缓冲区) 《IO是面向流的》
     *  Java NIO (New IO，Non-Blocking IO)是从Java 1.4版本开始引入的一套新 的IO API，可以替代标准的Java IO API。NIO与原来的IO有同样的作用和目
     * 的，但是使用的方式完全不同，NIO支持面向缓冲区的(IO是面向流的)、
     * | 基于通道的IO操作。NIO将以更加高效的方式进行文件的读写操作。|
     *
     *  Java API中提供了'两套NIO'，一套是针对 [标准输入输出NIO]，另一套就是 [网络编程NIO]。
     *
     * |-----java.nio.channels.Channel
     *      |-----FileChannel:处理本地文件
     *      |-----SocketChannel：TCP网络编程的客户端的Channel
     *      |-----ServerSocketChannel:TCP网络编程的服务器端的Channel
     *      |-----DatagramChannel：UDP网络编程中发送端和接收端的Channel
     */
    /***NIO. 2
     *
     *  随着 JDK 7 的发布，Java对NIO进行了极大的扩展，增强了对
     * 文件处理和文件系统特性的支持，以至于我们称他们为 NIO.2。
     * 因为 NIO 提供的一些功能，NIO已经成为文件处理中越来越重要
     * 的部分
     *
     *
     *
     * Path、Paths和Files核心API
     *  早期的Java只提供了一个File类来访问文件系统，但File类的功能比较有限，所
     * 提供的方法性能也不高。而且，大多数方法'在出错时仅返回失败'，并'不会提供异
     * 常信息'。
     *
     *  NIO. 2为了'弥补这种不足'，引入了Path接口，代表一个平台无关的平台路径，描
     * 述了目录结构中文件的位置。Path可以看成是File类的'升级版本'，'实际引用的资
     * 源也可以不存在。'
     */











    /**Path 类
     *  在以前IO操作都是这样写的:
     * import java.io.File;
     * File file = new File("index.html");
     *
     *  但在Java7 中，我们可以这样写：
     * import java.nio.file.Path;
     * import java.nio.file.Paths;
     * java.nio.file.Path path = Paths.get("index.html");
     *
     *  同时，NIO.2在java.nio.file包下还提供了Files、Paths工具类，Files包含
     * 了大量静态的工具方法来操作文件；Paths则包含了两个返回Path的静态
     * '工厂方法'。
     *
     *  Paths 类提供的静态 get() 方法用来获取 'Path 对象'：
     *      static Path get(String first, String … more) : 用于将多个字符串串连成路径
     *      static Path get(URI uri): 返回指定uri对应的Path路径
     *
     *
     *
     *
     *  Path接口
     *  Path 常用方法：
     *      String toString() ： 返回调用 Path 对象的字符串表示形式
     *      boolean startsWith(String path) : 判断是否以 path 路径开始
     *      boolean endsWith(String path) : 判断是否以 path 路径结束
     *      boolean isAbsolute() : 判断是否是绝对路径
     *      Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
     *      Path getRoot() ：返回调用 Path 对象的根路径
     *      int getNameCount() : 返回Path 根目录后面元素的数量
     *      Path getFileName() : 返回与调用 Path 对象关联的文件名
     *      Path getName(int idx) : 返回指定索引位置 idx 的路径名称
     *      Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
     *      Path resolve(Path p) :合并两个路径，返回合并后的路径对应的Path对象
     *      File toFile(): 将Path转化为File类的对象
     */
    @Test
    public void javaNIO(){
        /**注意：Path是个接口，[真正干活的是其实现子类]
         *
         * Paths是一个工具类,静态工厂代理，主要负责返回和初始化对应的子类实现类对象，返回给Path接口，让用户多态使用对应实现类的重写方法
         *
         *Path接口下有多个实现类
         *   (一般来说是在操作实现类的对象，只是多态给了Path接口,然后使用重写的方法来 接口代理 执行 实现类的 方法)
         *      比如:     AbstractPath
         *               WindowsPath extends AbstractPath
         *               WindowsPathWithAttributes extends WindowsPath implements BasicFileAttributesHolder
         *               ZipPath
         *
         * */
        //实现类继承Path接口

        //Path子类的对象 比File更强大  面向缓冲区
        Path path = Paths.get("C:\\Users\\HP\\Desktop\\实验文件夹\\图片\\mn.jpg");

        //返回 调用Path子类对象 的 字符串 表示形式  获取完整路径，相对就获取相对路径，绝对就获取绝对路径
        System.out.println(path.toString());//C:\Users\HP\Desktop\实验文件夹\图片\mn.jpg

        //必须从根部挖过来才是true
        //判读是否以对应的path路径开始,指的必须是从绝对路径最原本的根目录 或者 相对路径中的前面第一个路径开始
        System.out.println(path.startsWith("C:\\"));//true   //根目录是 C:\

        //判读是否以对应的path路径结束
        System.out.println(path.endsWith("mn.jpg"));//true

        //判断是否是绝对路径
        System.out.println(path.isAbsolute());//true

        //返回 Path子类 对象的上层路径,除了当前文件，父层和间接父层全部获取
        System.out.println(path.getParent());//C:\Users\HP\Desktop\实验文件夹\图片


        //返回调用；Path子类对象的 根目录
        System.out.println(path.getRoot());//C:\

        /**整条路径除了根目录。后面路径的所有元素C:\Users\HP\Desktop\实验文件夹\图片\mn.jpg中除去C:\ '路径'后面所有的\后面的文件名或目录名*/
        //返回Path根目录后的元素数量,只是指当前的Path的层数,  包括对应的文件（包含当前文件）
        System.out.println(path.getNameCount());//6

        //获取具体文件的文件名
        System.out.println(path.getFileName());//mn.jpg

        //根据索引把路径上对应文件名返回 Path子类 对象,只是关联连User对象
        System.out.println(path.getName(0));//Users    //0索引从[根目录后]一个文件开始(不包含根目录)


        //相对路径 Path子类 对象可以使用toAbsolutePath()方法返回一个对应绝对路径的Path对象
        Path test = Paths.get("Test.txt");
        System.out.println(test.toAbsolutePath().toString());//D:\idea-maven-space\java基础\Test.txt


        //合并
        Path path1 = Paths.get("jji.txt");
        Path path2 = Paths.get("D:\\idea-maven-space\\java基础\\");

        //合并Path子类对象
        System.out.println(path2.resolve(path1));//D:\idea-maven-space\java基础\jji.txt

        //
        File file = path1.toFile();
        System.out.println(file.isFile());//true


    }

    /**一般Files和Paths混合使用
     * Paths主要是针对路径的工具类
     * Files主要是针对文件操作的工具类
     * 分开更好，解耦，文件操作归文件操作，文件路径管理归文件路径管理
     *
     * */


/**
 * Files 类
 *  java.nio.file.Files 用于操作文件或目录的工具类。
 *
 *  Files常用方法：
 *       Path copy(Path src, Path dest, CopyOption … how) : 文件的复制
 *       Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
 *       Path createFile(Path path, FileAttribute<?> … arr) : 创建一个文件
 *       void delete(Path path) : 删除一个文件/目录，如果不存在，执行报错
 *       void deleteIfExists(Path path) : Path对应的文件/目录如果存在，执行删除
 *       Path move(Path src, Path dest, CopyOption…how) : 将 src 移动到 dest 位置
 *       long size(Path path) : 返回 path 指定文件的大小
 *
 *
 *
 *  Files常用方法：用于判断
 *       boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
 *       boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
 *       boolean isRegularFile(Path path, LinkOption … opts) : 判断是否是文件
 *       boolean isHidden(Path path) : 判断是否是隐藏文件
 *       boolean isReadable(Path path) : 判断文件是否可读
 *       boolean isWritable(Path path) : 判断文件是否可写
 *       boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
 *
 *
 *  Files常用方法：用于操作内容
 *       SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连
 *      接，how 指定打开方式。
 *       DirectoryStream<Path> newDirectoryStream(Path path) : 打开 path 指定的目录
 *       InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
 *       OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
 */


 /**Files工具类的常用API*/
   @Test
    public void testFiles() throws IOException {
       //文件复制  (  Files.copy(被复制文件的Path子类对象,目标地址的Path子类对象);  )
//       Path path = Paths.get("Test.txt");
//       Files.copy(path,Paths.get("dest.txt"));


// Path createDirectory(Path path, FileAttribute<?> … attr) : 创建一个目录
//       Path dis = Paths.get("Dis");
//       Files.createDirectories(dis);

       //直接删除，不存在就会报错，比较危险
       //删除文件 或着 目录 [不存在就报错]
//       Files.delete(Paths.get("jji.txt"));

       //安全删除，不存在不会报错，存在就删除
//       Files.deleteIfExists(Paths.get("dis"));


//         移动文件 (File.separator自动适配操作系统，windows就是\  Unix就是/)
       //移动是复制过去，然后删除原来的文件 （相当于剪切）文件原位置上的被删除，
       // 出现在新位置上,返回新位置上文件的Path接口的下实现类对象 move,可以继续针对新位置的此文件进行操作
//       Path move = Files.move(Paths.get("src" + File.separator + "IO.txt"),Paths.get("IO.txt"));


       //根据Path接口下实现类对象，返回此文件的大小，以字节（byte）为单位 ,返回long型
//       long size = Files.size(Paths.get("Test.txt"));
//       System.out.println(size);//38938 [byte] 以字节为单位

   }


    /**Files工具类的判断API*/
    @Test
    public void panduaFiles() throws IOException {
        Path path = Paths.get("Test.txt");

        //判断是否存在
        System.out.println(Files.exists(path));

        //判断是否目录
        System.out.println(Files.isDirectory(path));

        //判断是否是文件
        System.out.println(Files.isRegularFile(path));

        //判断是否是隐藏文件
        System.out.println(Files.isHidden(path));

        //判断是否【可读】
        System.out.println(Files.isReadable(path));

        //判断文件是否【可写】
        System.out.println(Files.isWritable(path));

        //判断文件是否不存在
        System.out.println(Files.notExists(path));

        /**
         * true
         * false
         * true
         * false
         * true
         * true
         * false
         */

    }




    /**Files工具类的操作API*/

    /** Files常用方法：用于操作内容
     *       SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连
     *      接，how 指定打开方式。
     *       DirectoryStream<Path> newDirectoryStream(Path path) : 打开 path 指定的目录
     *       InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
     *       OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
     */
    @Test
    public void czuoFiles() throws IOException {
        Path path = Paths.get("Test.txt");
        //[字节通道]  获取与指定文件的连接，how 指定打开方式。
//        SeekableByteChannel newByteChannel(Path path, OpenOption…how)
//        SeekableByteChannel seekableByteChannel = Files.newByteChannel(path);


//        seekableByteChannel.close();

        //打开path指定的目录,注意path一定要是目录,不然报错
        /**注意,只是获取此目录直接隶属下的文件和目录,不会再进子目录*/
        DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("D:\\IO"));

        //Iterator迭代出来
        Iterator<Path> iterator = paths.iterator();

        //哈希判断读出
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
//        D:\IO\001. png
//        D:\IO\askjdh.txt
//        D:\IO\sa,jdhkja





        //获取 InputStream 对象
//        InputStream inputStream = Files.newInputStream(path);

//        获取输出流对象
//        OutputStream outputStream = Files.newOutputStream(Paths.get("dest.txt"));



        //转换流转换回字符
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);


//        int len;   //read()方法无论是字节流还是字符流，都返回整数
//        while ((len=inputStreamReader.read())!=-1){
//            outputStreamWriter.write((char)len);
//        }


        //面向缓冲区,但缓冲区还是要你自己放的




        //关闭外层，内层自动关闭
//        inputStreamReader.close();
//        outputStreamWriter.close();







    }


}
