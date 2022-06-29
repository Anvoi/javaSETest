package 网络编程;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLTest {
    /** URL （比如：种子，就是一个URL）
     *URL类
     *      URL(Uniform Resource Locator)：统一资源定位符，它表示 Internet 上某一
     *      资源的地址。
     *
     *      它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate
     *      这个资源。
     *
     *      通过 URL 我们可以访问 Internet 上的各种网络资源，比如最常见的 www，ftp
     *      站点。浏览器通过解析给定的 URL 可以在网络上查找相应的文件或其他资源。
     *
     *       URL的基本结构由5部分组成：
     *          <传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表
     *          例如:
     *          http://192.168.1.100:8080/helloworld/index.jsp#a?username=shkstart&password=123
     *          #片段名：即锚点，例如看小说，直接定位到章节
     *          参数列表格式：参数名=参数值&参数名=参数值....
     *
     *
     *      14.7 URL网络编程：URL类构造器
     *          为了表示URL，java.net 中实现了类 URL。我们可以通过下面的构造器来初
     *          始化一个 URL 对象：
     *              public URL (String spec)：通过一个表示URL地址的字符串可以构造一个URL对象。例
     *              如：URL url = new URL ("http://www. atguigu.com/");
     *
     *              public URL(URL context, String spec)：通过基 URL 和相对 URL 构造一个 URL 对象。
     *              例如：URL downloadUrl = new URL(url, “download.html")
     *
     *              public URL(String protocol, String host, String file); 例如：new URL("http",
     *              "www.atguigu.com", “download. html");
     *
     *              public URL(String protocol, String host, int port, String file); 例如: URL gamelan = new
     *              URL("http", "www.atguigu.com", 80, “download.html");
     *           URL类的构造器都声明抛出非运行时异常，必须要对这一异常进行处理，通
     *          常是用 try-catch 语句进行捕获。
     */


    /**URL的构造器
     *     public URL (String spec)：通过一个表示URL地址的字符串可以构造一个URL对象。例
     *     如：URL url = new URL ("http://www. atguigu.com/");
     *
     *     public URL(URL context, String spec)：通过基 URL 和相对 URL 构造一个 URL 对象。
     *     例如：URL downloadUrl = new URL(url, “download.html")
     *
     *     public URL(String protocol, String host, String file); 例如：new URL("http",
     *     "www.atguigu.com", “download. html");
     *
     *     public URL(String protocol, String host, int port, String file); 例如: URL gamelan = new
     *     URL("http", "www.atguigu.com", 80, “download.html");
     *
     *  URL类的构造器都声明抛出非运行时异常，必须要对这一异常进行处理，通
     * 常是用 try-catch 语句进行捕获。
     */

    /**URL常用方法
     * 一个URL对象生成后，其属性是不能被改变的，但可以通过它给定的
     * 方法来获取这些属性：
     *      public String getProtocol( ) 获取该URL的协议名
     *      public String getHost( ) 获取该URL的主机名
     *      public String getPort( ) 获取该URL的端口号
     *      public String getPath( ) 获取该URL的文件路径
     *      public String getFile( ) 获取该URL的文件名
     *      public String getQuery( ) 获取该URL的查询名
     */

    /** <传输协议>://<主机名>:<端口号>/<文件名>#片段名?参数列表 */


    @Test
    public void urltset01(){

        //构造器的异常必须要处理
        try {
            URL url = new URL("http://192.168.1.100:8080/helloworld/index.jsp#a?username=shkstart&password=123");

            //获取协议名
            System.out.println(url.getProtocol());

            //获取主机名
            System.out.println(url.getHost());

            //获取端口号
            System.out.println(url.getPort());

            //获取URL的文件路径,即端口号后的
            System.out.println(url.getPath());

            //获取URL的文件名
            System.out.println(url.getFile());

            //获取URL的查询名
            System.out.println(url.getQuery());



//            http
//            192.168.1.100
//            8080
//            /helloworld/index.jsp  文件路径
//            /helloworld/index.jsp 文件名
//            null


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


//    URL远程下载
    @Test
    public void download(){
//        http://anvoi.cn/book/static/uploads/banner6.jpg

        try {
            //造URL对象
            URL url = new URL("http://anvoi.cn/book/static/uploads/banner6.jpg");

//            获取一个连接,转换成HTTPURLConnection连接
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            //获取流
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("banner6.jpg");

            byte[] b = new byte[1024];
            int len;
            while((len=inputStream.read(b)) != -1){
                fileOutputStream.write(b,0,len);
            }

            inputStream.close();
            fileOutputStream.close();

            /**注意你的urlConnection url的连接也是要关闭断开*/
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



















}
