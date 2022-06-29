package 网络编程;

import org.junit.Test;

import javax.naming.ldap.SortKey;
import java.io.IOException;
import java.net.*;

/***
 * TCP 和 UDP
 * TCP会先握手，没建立连接就报错
 * UDP不会。发出去的包也不会在管,接收端没开UDP也不管，直接发
 *
 */

/**
 * UDP网络编程
 *       类 【DatagramSocket】 和 【DatagramPacket】 实现了基于 UDP 协议网络程序。
 *       UDP数据报通过【数据报套接字 DatagramSocket】 发送和接收，
 *           【系统不保证UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达】。
 *
 *       DatagramPacket 对象封装了UDP数据报，在数据报中包含了发送端的IP
 *      地址和端口号以及接收端的IP地址和端口号。
 *       UDP协议中每个数据报都给出了完整的地址信息，因此[无须建立发送方]和
 *      [接收方的连接]。如同发快递包裹一样
 */
public class UDPTest {

    /**InetAddress用InetAddress.getLocalHost拿到的不一定是127.0.0.1，
     * (没有网时肯定是127.0.0.1，有网时就是对应的地址了，可以设置127.0.0.1,也可能是自己的局域网地址)
     * 可能是机器内部192.168.176.1（VMware Network Adapter VMnet8的地址）
     *
     * 所以注意一个机器不止一个地址的,内网自己测试自己的时候要注意getLocalHost这个方法和当前的网络环境
     * */


    /**
     * DatagramSocket 类的常用方法  用法
     *       public DatagramSocket(int port)创建数据报套接字并将其【绑定到本地主机上的指定端口】。套接字将被
     *      绑定到通配符地址，IP 地址由内核来选择。
     *
     *       public DatagramSocket(int port,InetAddress laddr)创建数据报套接字，将其
     *      绑定到指定的本地地址(和端口)，本地端口必须在 0 到 65535 之间（包括两者）。。
     *      如果 IP 地址为 0.0.0.0，套接字将被绑定到通配符地
     *      址，IP 地址由内核选择。
     *
     *       public void close()关闭此数据报套接字。
     *
     *       public void send(DatagramPacket p)从此套接字发送数据报包。DatagramPacket
     *      包含的信息指示：将要发送的数据、其长度、远程主机的 IP 地址和远程主机的端口号。
     *
     *       public void receive(DatagramPacket p)从此套接字接收数据报包。
     *      当此方法返回时，DatagramPacket  的缓冲区填充了接收的数据。
     *      [数据报包]也 [包含 发送方 的 IP 地址和 发送方 机器上的 端口号]。 此方法在[接收到数据报前一直阻塞]。
     *     数据报包对象的 length 字段包含所接收信息的长度。如果信息比包的长度长，该信息将被截短。//收到后在根据长度来截断data
     *
     *
     *       public InetAddress getLocalAddress()获取套接字绑定的本地地址。
     *
     *       public int getLocalPort()返回此套接字绑定的本地主机上的端口号。
     *
     *       public InetAddress getInetAddress()返回此套接字连接的地址。[如果套接字未连接，则返回 null]。
     *
     *       public int getPort()返回[此套接字的端口]。  [如果套接字未连接]，则返回 -1。
     */



    /**
     * DatagramPacket类的常用方法
     *           public DatagramPacket(byte[] buf,int length)构造 DatagramPacket，用来接收长
     *          度为 length 的数据包。 length 参数必须小于等于 buf.length。
     *
     *           public DatagramPacket(byte[] buf,int length,InetAddress address,int port)构造数
     *          据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。length
     *          参数必须小于等于 buf.length。
     *
     *           public InetAddress getAddress()返回某台机器的 IP 地址，此数据报将要发往该
     *          机器或者是从该机器接收到的。
     *
     *           public int getPort()返回某台远程主机的端口号，此数据报将要发往该主机或
     *          者是从该主机接收到的。
     *
     *           public byte[] getData()返回数据缓冲区。接收到的或将要发送的数据从缓冲区
     *          中的偏移量 offset 处开始，持续 length 长度。
     *
     *           public int getLength()返回将要[发送或接收到的数据的长度]。
     *
     *          @throws IOException
     */



    /**注意，本地电脑的地址 的 localhost和VM8中的地址 192.168.。。是不同的地址,即使是一台机器，也有不同的地址，
     * 因为一台机器也可以有自己的虚拟局域网
     * 公网地址肯定是唯一的，但是自己电脑内部可能有其他映射地址，一台电脑不止一个地址，[对于自己来说（环回）]
     *  */



//     【系统不保证UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达】。
//    发送端
    @Test
    public void sender() throws IOException {


        //UDP的DatagramSocket套接字只是可以绑定自己的地址和端口
//        远程目标地址和端口封装成InetAddress对象作为参数封装进DatagramPacket数据报对象
//        然后send方法执行后才建立了连接

        //套接字
        /**UDP的DatagramSocket对象
         * 可以指定发出的 地址 和 端口
         *
         * */
        DatagramSocket socket = new DatagramSocket(8989);


        //data数据
        String str = "UPD发送的数据包";
        byte[] data = str.getBytes();

        //获取目标地址对象
//        InetAddress inet = InetAddress.getLocalHost();//这个是获取本地的
//        InetAddress inet = InetAddress.getByName("127.0.0.1");
        InetAddress inet = InetAddress.getLocalHost(); //LAPTOP-5IIJISQ9/127.0.0.1


        //UDP封装数据包
        /**
         * data:数据
         * 0开始索引
         * data.length,结束索引
         * inet 目标地址的InetAddress类对象
         * 9090： 目标地址的端口
         *
         *
         */
        DatagramPacket package01 = new DatagramPacket(data,0,data.length,inet,9090);




//        发送UDP数据包
        /**借用套接字DatagramSocket
         * 因为UDP不需要和对方建立连接，只需要知道对方地址和端口就发过去
         * */
        socket.send(package01);
        /**
         *DatagramSocket (UDP套接字) 和 DatagramPacket (UDP数据包) 是独立的，互不影响
         * 当使用DatagramSocket对象去send发送包到目标的时候才会建立连接，连接到了soket对象才能拿到对方的 端口和地址，没连接到就返回null
         *
         */


     //socket套接字和 DatagramPacket 数据包是独立的，在数据包中填写的目标地址和端口套接字不知道
//        所以 无论是在那一端 ，想要知道交互的对方的端口或地址等信息，只能通过DatagramPacket对象来
        System.out.println("1");
        System.out.println("目标接收地址："+package01.getAddress());
        System.out.println("目标接收端口:"+package01.getPort());


        System.out.println("2");
        System.out.println("得到的反馈:");

        byte[] b = new byte[1024];

        DatagramPacket datagramPacket = new DatagramPacket(b,b.length);

        socket.receive(datagramPacket);


        System.out.println("5");
        System.out.println(datagramPacket.getAddress()+"端口: "+datagramPacket.getPort()+" "+"----->的数据: \n"+new String(datagramPacket.getData(),0,datagramPacket.getLength()));




//        关闭资源，关闭套接字
        socket.close();



    }



//    接收端
    @Test
    public void receiver() throws IOException {


        //注意，没有给套接字地址值，套接字中也拿不到此地址值
        //先造套接字,绑定当前服务器接收端的地址，让程序监听此端口
//
        DatagramSocket socket = new DatagramSocket(9090,InetAddress.getLocalHost());


        byte[] b = new byte[1024];

//        定义数据包
        DatagramPacket datagramPacket = new DatagramPacket(b,0,b.length);

        //接收UDP数据，注入DatagramPacket数据包对象中
        socket.receive(datagramPacket);//


        System.out.println(new String(datagramPacket.getData(),0,datagramPacket.getLength()));



        //需要通过DatagramPacket对象来拿，socket是帮助注入到DatagramPacket的工具，socket在服务端只知道自己的，不知道对方的
//        只有DatagramPacket数据包中知道
        System.out.println("3");
        System.out.println("对方出发端口"+datagramPacket.getPort());
        System.out.println("对方出发地址"+datagramPacket.getAddress());


        System.out.println("4");
        //往客户端反馈
        System.out.println("反馈给客户端------>");

        //目标地址

        byte[] bytes = ("{[接收端:" + InetAddress.getLocalHost() + ":" + socket.getLocalPort() + "]" + "已经收到UDP数据包}").getBytes();

        DatagramPacket datagramPacket1 = new DatagramPacket(bytes,0,bytes.length,datagramPacket.getAddress(),8989);

       //把反馈数据包发回去
        socket.send(datagramPacket1);


        socket.close();


    }


}
