package 网络编程;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class WangluoTest {
    /**     网络编程概述
     *      网络通信要素概述
     *      通信要素1：IP和端口号
     *      通信要素2：网络协议
     *      TCP网络编程
     *      UDP网络编程
     *      URL编程
     */

    /**
     * [Java是 [Internet 上的语言]]，它[从语言级上提供了对网络应用程序的支持]
     * ，[程序员能够很[容易开发常见的网络应用程序]]。
     *
     * [Java[提供的网络类库]]，[可以实现无痛的网络连接]，[联网的底层
     * 细节被[隐藏在 Java 的本机安装系统里]]，[由 JVM 进行控制]。
     * [并且 Java [实现了一个跨平台的网络库]，[程序员面对的是一个[统一的网络编程环境]]
     *
     *
     *
     * 网络基础
     * 计算机网络：
     * 把分布在不同地理区域的计算机与专门的外部设备用通信线路互连成一个规
     * 模大、功能强的网络系统，从而使众多的计算机可以方便地互相传递信息、
     * 共享硬件、软件、数据信息等资源。
     *
     * [网络编程的目的]：
     * [直接或间接地][通过[网络协议]与其它计算机实现数据交换，进行通讯。]
     *
     * [网络编程]中有[两个主要的问题]：
     *      (如何连)如何准确地定位网络上一台或多台主机；[定位主机上的特定的应用]
     *      (如何高效的连)找到主机后[如何可靠高效地进行数据传输]
     */


    /**网络通信要素概述
     *       IP    和   端口号
     *       网络通信  协议
     *
     *
     * 如何[实现网络中的[主机]互相通信]
     *  通信[双方地址]
     *       IP
     *       端口号
     *
     *  一定的[规则]（即：[网络通信协议]。有[两套参考模型]）
     *       [OSI参考模型]：[模型过于理想化，未能在因特网上进行广泛推广]
     *       [TCP/IP参考模型(或TCP/IP协议)]：[事实上的国际标准]。
     */


    /**
     *  通信要素1：[IP] 和 [端口号]
     *  IP 地址：InetAddress
     *      [唯一的标识] [Internet 上的计算机]（通信实体）
     *      [本地回环地址](hostAddress)：[127.0.0.1 主机名(hostName)：localhost]
     *      [IP地址分类方式]1：[IPV4] 和 [IPV6]
     *
     * IPV4：[4个字节组成]，[4个0-255]。[大概42亿]，[30亿都在北美，亚洲4亿]。
     * [2011年初已经用尽]。以[点分十进制表示]，如[192.168.0.1]
     *
     * IPV6：128位（[16个字节]），[写成8个无符号整数]，[每个整数用[四个十六进制位][表示]，
     * 数之间用[冒号（：）]分开，如：3ffe:3201:1401:1280:c8ff:fe4d:db39:1984
     *
     *      IP地址分类方式2：
     *      公网地址(万维网使用)
     *      私有地址(局域网使用)。192.168.开头的就是私有地址
     *      ，范围即为192.168.0.0--192.168.255.255，专门为组织机构内部使用
     *
     *      特点：不易记忆
     *
     *  [端口号]标识正在计算机上运行的进程（程序）
     *
     *       [[不同的进程]有不同的[端口号]]
     *
     *       被[规定为一个 16 位的整数] 0~65535。
     *
     *
     *       端口分类：
     *
     *           公认端口：0~1023。被预先定义的服务通信占用（如：HTTP占用端口
     *          80，FTP占用端口21，Telnet占用端口23）
     *
     *           注册端口：1024~49151。分配给用户进程或应用程序。（如：[Tomcat占
     *          用端口8080]，[MySQL占用端口3306]，[Oracle占用端口1521等]）。
     *
     *           动态/私有端口：49152~65535。
     *
     *
     * [端口号]与[IP地址的组合]得出一个[网络套接字]：[Socket](端口+IP)。
     *
     *
     *
     *
     *
     *
     *
     *
     *
     * InetAddress类
     * Internet上的主机有[两种方式表示地址]：
     *      域名(hostName)：[www.atguigu.com]
     *      IP 地址(hostAddress)：[202.108.35.210]
     *
     * [ [InetAddress类]主要[表示IP地址] ]，两个子类：[Inet4Address]、[Inet6Address]。 (IPv4,IPv6)
     *
     * [InetAddress 类 对 象] [含 有 一 个 Internet [主 机 地 址 的 [域 名] 和 [IP 地 址] ：
     * [www.atguigu.com] 和 [202.108.35.210]。
     *
     * [域名容易记忆]，[当在连接网络时输入一个主机的域名后]，[域名服务器(DNS)]
     * 负责[将域名]转化成[IP地址]，这样[才能和主机建立连接]。 -------[域名解析]
     */




    /** IP 和 端口号
     *
     *[ InetAdress类 ]
     * InetAddress类[没有提供公共的构造器]，而是[提供了如下几个静态方法]来【获取InetAddress实例】
     *      public static InetAddress getLocalHost() //获取本地机器名+内网地址
     *      public static InetAddress getByName(String host) //根据域名获取InetAddress实例
     *
     * InetAddress提供了如下几个常用的方法
     *      public String getHostAddress()：返回 IP 地址字符串（以文本表现形式）。
     *      public String getHostName()：   获取此 IP 地址的主机名
     *      public boolean isReachable(int timeout)：测试是否可以达到该地址,设置多久会视为超时
     */

    @Test
    public void iphost(){
        try {

            System.out.println(InetAddress.getLocalHost());//LAPTOP-5IIJISQ9/192.168.176.1 有网时,自己的VM8局域网
                                                    //LAPTOP-5IIJISQ9/127.0.0.1 没有网时

            //根据域名获取对应的InetAddress实例
            InetAddress address_1 = InetAddress.getByName("www.atguigu.com");

            //InetAddress实例
            System.out.println(address_1);

//            根据InetAddress实例获取对应的域名
            System.out.println(address_1.getHostName());

            //获取InetAddress 对象包含的IP地址
            System.out.println(address_1.getHostAddress());




            //获取本机的域名和IP地址(注意，只是本机的地址)
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);


            //true
            System.out.println(address_1.isReachable(4));


            System.out.println();
//            163.177.20.210

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**网络协议
     * 14.4 通信要素2：网络通信协议
     *
     * 网络通信协议
     * 计算机网络中实现通信必须有一些约定，即通信协议，对速率、传输代码、代
     * 码结构、传输控制步骤、出错控制等制定标准。
     *
     *  问题：网络协议太复杂
     * 计算机网络通信涉及内容很多，比如指定源地址和目标地址，加密解密，压缩
     * 解压缩，差错控制，流量控制，路由控制，如何实现如此复杂的网络协议呢？
     *
     * 通信协议分层的思想
     * 在制定协议时，把复杂成份分解成一些简单的成份，再将它们复合起来。最常
     * 用的复合方式是层次方式，即同层间可以通信、上一层可以调用下一层，而与
     * 再下一层不发生关系。各层互不影响，利于系统的开发和扩展。
     *
     *
     *
     * TCP/IP协议簇
     * 传输层协议中有两个非常重要的协议：
     *       传输控制协议TCP(Transmission Control Protocol)
     *       用户数据报协议UDP(User Datagram Protocol)。
     *
     * TCP/IP 以其两个主要协议：[传输控制]协议(TCP)和[网络互联]协议(IP)而得
     *      名，实际上是一组协议，包括多个具有不同功能且互为关联的协议。
     *
     * IP(Internet Protocol)协议是网络层的主要协议，支持网间互连的数据通信。
     *
     * TCP/IP协议模型从更实用的角度出发，形成了高效的四层体系结构，即
     * 物理链路层、IP层、传输层和应用层
     *
     *
     *
     * TCP 和 UDP
     *  TCP协议：
     *       使用TCP协议前，[须先建立TCP连接]，[形成传输数据通道]
     *       传输前，[采用“三次握手”方式]，[点对点通信]，是[可靠的]
     *       TCP协议[进行通信的两个应用进程]：[客户端、服务端]。
     *       在连接中[可进行大数据量的传输]
     *       传输完毕，[需释放已建立的连接]，[效率低]
     *
     *  UDP协议：
     *       将[数据、源、目的封装成数据包]，[不需要建立连接]
     *       每个数据报的大小[限制在64K内]
     *       发送[不管对方是否准备好]，接收方[收到也不确认]，故是[不可靠]的
     *       [可以广播发送]
     *       发送数据结束时[无需释放资源]，[开销小]，[速度快]
     */















    /**Socket
     *  利用套接字(Socket)开发网络应用程序【早已被广泛的采用】，以至于【成为事实
     * 上的标准】。
     *
     *  【网络上】【具有唯一标识】的【IP地址和端口号】【组合在一起】才能【构成唯一能识别的标
     * 识符套接字】。
     *
     *  【通信的【两端】】【都要有Socket】，【是两台机器间通信的【端点】】。
     *
     *  【网络通信】【其实就是】【Socket间的通信】。
     *
     *  【Socket允许程序】【把网络连接当成一个流】，【数据在两个Socket间】【通过【IO】传输】。
     *
     *  【一般【主动发起通信】的【应用程序】【属客户端】】，【等待通信请求的】为【服务端】。
     *
     * 【Socket分类】：
     *      【流套接字】（stream socket）：【使用【TCP】提供可依赖的【字节流】服务】
     *      【数据报套接字】（datagram socket）：【使用【UDP】提供“尽力而为”的【数据报】服务】
     */





    /**Socket类
     * Socket类的常用构造器：
     *           public Socket(InetAddress address,int port)创建一个【流套接字】并【将其连接到指定 IP 地址】的【指定端口号】。
     *           public Socket(String host,int port)创建一个【流套接字并将其连接到指定主机上的【指定端口号】】。
     *
     *  Socket类的常用方法：
     *           public InputStream getInputStream()返回此套接字的输入流。可以用于接收网络消息
     *           public OutputStream getOutputStream()返回此套接字的输出流。可以用于发送网络消息
     *           public InetAddress getInetAddress()此套接字连接到的远程 IP 地址；如果套接字是未连接的，则返回 null。
     *           public InetAddress getLocalAddress()获取套接字绑定的本地地址。 即本端的IP地址
     *           public int getPort()此套接字连接到的远程端口号；如果尚未连接套接字，则返回 0。
     *           public int getLocalPort()返回此套接字绑定到的本地端口。 如果尚未绑定套接字，则返回 -1。即本端的
     *          端口号。
     *           public void close()关闭此套接字。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接
     *          或重新绑定）。需要创建新的套接字对象。 关闭此套接字也将会关闭该套接字的 InputStream 和
     *          OutputStream。
     *           public void shutdownInput()如果在套接字上调用 shutdownInput() 后从套接字输入流读取内容，则流将
     *          返回 EOF（文件结束符）。 即不能在从此套接字的输入流中接收任何数据。
     *           public void shutdownOutput()禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发
     *          送，并且后跟 TCP 的正常连接终止序列。 如果在套接字上调用 shutdownOutput() 后写入套接字输出流，
     *          则该流将抛出 IOException。 即不能通过此套接字的输出流发送任何数据。
     */


    /**[基于Socket]的[TCP编程]
     *  [客户端Socket]的[工作过程]包含以下[四个基本]的步骤：
     *         【创建 Socket】：根据指定服务端的 IP 地址或端口号构造 Socket 类对象。若服务器端
     *        响应，则建立客户端到服务器的通信线路。若【连接失败】，【会出现异常】。
     *         【打开连接到 Socket 的输入/出流】： 使用 getInputStream()方法获得输入流，使用
     *        getOutputStream()方法获得输出流，进行数据传输
     *         【按照一定的协议对 Socket 进行读/写操作】：通过输入流读取服务器放入线路的信息
     *        （但不能读取自己放入线路的信息），通过输出流将信息写入线程。
     *         【关闭 Socket】：断开客户端到服务器的连接，释放线路
     */




    /**基于Socket的TCP编程
     * TCP网络编程
     * 例子1：客户端发送信息给服务端，服务端将数据显示在控制台上
     *
     * 字节流服务 Socket会提供
     *Socket分类：
     * 流套接字（stream socket）：使用TCP提供可依赖的字节流服务
     * 数据报套接字（datagram socket）：使用UDP提供“尽力而为”的数据报服务
     */



    /**启动时候当然要先启动服务器，待命状态再启动客户端去请求服务器
     * 不然客户端先启动，初始化套接字去发现对应的服务器根本没开
     * ，当然请求失败
     * */
    //客户端
    @Test
    public void client() throws IOException {

        /** 1, 创建socket对象，指定服务器端 IP 和 端口号*/
        //创建socket对象，客户端，需要对方的[IP+端口号]来初始化socket
        //对方IP来初始化InetAddress实例，然后此实例和对方端口号作为参数给Socket构造器来初始化socket套接字对象
        //写目标对方的IP作为InetAddress实例
        InetAddress inet = InetAddress.getByName("127.0.0.1");/**指定对方的IP*/
        //发数据和收数据，都建立在Socket套接字上运行
        Socket socket = new Socket(inet,8899);/**指定对方的端口*/
        /**这里 会去握手 测试连接，连接不了就报错*/


        /**2,从socket套接字对象中直接获取一个输出流对象（以对方[服务器IP+端口]初始化的[输出流]）*/
        //上面拿到对应的服务器的ip和端口的套接字，可以通过套接字socket本身的
//        API来造输出流，主要是向目标IP+端口输出(字节流)
        OutputStream outputStream = socket.getOutputStream();


        /**3,写出具体的信息和关闭资源*/
        //写出
        outputStream.write("张伟卡".getBytes());


        /**socket本身也是一个流资源
         * 除了关闭输出流外，socket也要关闭
         * */
        outputStream.close();
        socket.close();


    }


    /**服务器端开启之后会一直处于线程启动待命监听状态
     *socket = serverSocket.accept();中的socket会一直待命，有客户端发数据来，就会去获取输入流然后执行服务器任务
     *
     * */
    //服务端
    @Test
    public void server()  {
        //服务器端因为只是在对应自己的端口接受请求的，所以首先不需要IP+端口的套接字
//        不需要用什么IP来初始化套接字，因为自己就是服务器，所以只需要用端口
//        用端口来new ServerSocket(8899); 初始化服务套接
//        然后获取对应的socket服务器端套接，
//        可以通过此套接进行输入流将客户端输入到服务器的的信息进行读取

        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;

        try {

            /**1,指明自己的端口号 来初始化serverSocket,服务器作为被动接受方
             * 服务器自己本来就知道自己的端口号
             * */
            //先造对应的服务器的ServerSocket对象
            serverSocket = new ServerSocket(8899);


            /**获取当前服务器的套接字（前面用端口号初始化了）*/
            //一样要获取套接字,利用ServerSocket获取
            socket = serverSocket.accept();/**主要是accept方法在监听*/

            /**2，获取套接字的输入流，接受客户端发来的请求*/
            //读入数据
            inputStream = socket.getInputStream();/**发现又流过来后获取此输入流*/
            /**始终要用套接字来获取输入或输出流*/



            /**注意，这样有问题，在边界中文劈两半会乱码*/
//        byte[] buffer = new byte[20];
//        int len;
//        while((len = inputStream.read(buffer))!=-1){
//            String s = new String(buffer, 0, len);
//            System.out.print(s);
//        }


            /**4，读取请求数据*/
            //借用到一个ByteArrayOutputStream类,先全部写出到这个暂时流
            // 全部字节装起来再转String，之间没有转就不会出现劈一半出现乱码
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[5];
            int len;
            while((len=inputStream.read(buffer))!=-1){

                //先不要组装成字符串
    //            先全部写出到对应的一个ByteArrayOutputStream流中
                baos.write(buffer,0,len);

            }


            //借用ByteArrayOutputStream流，将完整的字节整体组装成字符串
            System.out.println(baos.toString());//toString就会将里面的所有字节转换成字符串了


            System.out.println("来自于："+socket.getInetAddress().getHostAddress()+"的数据");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {


            /**5,关闭资源*/
            //关闭资源
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //注意，关闭serverSocket代表服务器不再接受请求
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }

















}
