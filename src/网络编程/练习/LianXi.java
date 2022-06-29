package 网络编程.练习;

import com.sun.corba.se.impl.orbutil.ObjectUtility;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class LianXi {

    /**
     * read函数只是一个通用的读文件设备的接口。是否阻塞需要由设备的属性和设定所决定。
     *
     * 1.一般来说，读字符终端、网络的socket描述字，管道文件等，这些文件的缺省read都是阻塞的方式。
     *
     * 2.如果是读磁盘上的文件，一般不会是阻塞方式的。但使用锁和fcntl设置取消文件O_NOBLOCK状态，也会产生阻塞的read效果。
     */

    /**Socket套接字是双向的，
     * 首先，此Socket对象在那一台机器上，其Local的地址和端口属性等都是指向其自身的端口和地址
     * 目标的地址和端口就是其远程的 目标地址和端口信息
     * 只要有交互，socket对象中就一定存着本地的信息和目标的信息
     *
     *
     * */


    /**只要清楚socket对象在哪里创建的，哪里就是主场，就是本地（local），输出就是主场向客场输出，输入就是客场向主场输入
     * 套接字初始化后关闭前信息一致有效
     * */



    /**套接字获取的端口号和什么方法，都是根据套接字创建对象的时候绑定的参数属性*/

/**基于TCP的，所以底层是TCP数据包传输过去的，连同客户端的IP+端口等信息发送过去,
 * 客户端往下封装发送，服务器端再往上拆封得到数据的过程（五层协议）[计算机网络]*/
//    客户端 //client
    @Test
    public void client() throws IOException {
        InetAddress localhost = InetAddress.getByName("localhost");
        System.out.println(localhost.getHostAddress());//127.0.0.1,获取地址


//        这个套接字是属于服务器的，记录着服务器的信息
        //IP+端口初始化套接字，传输到对方的 IP+端口
        Socket socket = new Socket(localhost, 8899);

//        getPort目标端口
//        getLocalPort本地流出发端口
        System.out.println("getPort "+socket.getPort()+" getLocalPort:"+socket.getLocalPort());

        OutputStream outputStream = socket.getOutputStream();//往其端口输出的流
        InputStream inputStream = socket.getInputStream();

        //输入流
        FileInputStream fileInputStream = new FileInputStream("Test.txt");

        byte[] b = new byte[1024];
        int len;

        //注意，这里中间并没有转换成字符串，所以这里没有乱码，全部字节过去在对方整齐后其自己组合成字符，整体性，就不会乱码,没被劈开字节
        while ((len=fileInputStream.read(b)) != -1 ){
            outputStream.write(b,0,len);

        }
        /**磁盘过来的流不归内存层面的java管，磁盘过来的流会自动给结束信号read*/

        /**但是已经读进内存中的归java管的数据，在输出到目标内存中的时候，就需要自己手动close给结束标志
         * 或者其他给结束信号的方法给目标的read知道，放行不阻塞，返回-1
         * 如果不给结束信号，文件不会被造出来，因为不完整，
         * 其实主要还是内置的刷新缓冲区的问题,最后那一下因为没有数据，又没有满缓冲区，没有结束信号，所以处于监听阻塞状态
         * 一般是满缓冲就输出，但是最后一轮，因为当前不是磁盘过来的流，
         * 而是内存到内存的流，所以需要手动关闭 或 给结束信号,数据完整了，文件才会被造出来
         *
         *
         * 此处最主要的问题是while（）最后一下卡住了，read没有接收到结束信号，又不是满内置缓冲区，
         * 也没有close流，最后一下刷新不出来，也关不了，因为流是客户端的流，服务器端不能关，（除非客户端关闭流，服务器端也会收到结束信号）
         * 一直监听阻塞，而客户端也直接到等返回了，也在监听，导致死锁
         *
         * */


//        outputStream.close();
        /**注意，这样直接关闭类型为OutputStream抽象基类的类型,会导致整个 socket对象被关闭
         * socket获取的所有的流都失效,也被关闭,后面都不能用socket中的流了
         * 所以不能直接将socket 返回的流对象 来关
         * 要借用socket对象来使用对应的shutdownOutput()等方法来关闭socket中具体的流
         * 直接 close返回的流对象 会导致socket 整个连接套接字对象 被关闭
         *
         * socket的套接字对象返回的流对象不能提前直接关闭,一旦关闭，整个socket包括其中的流全部被关闭,后面也不能用了


         */
        /**重要:告诉服务器端客户端不再发了的 指示,让服务器端的read方法不再线程阻塞，继续读，返回-1然后跳出循环*/
        socket.shutdownOutput();//主要是为了告诉服务器端已经传输完成，让其的read（阻塞方法）不要再等了
/**        因为这里的Output输出流是通过socket对象获取的一个输出流对象，所以关闭就需要借用socket对象来关闭
 *          如果是直接new 的输出流，也可以直接关闭也是一样的
 * */


        /**以前可以是因为发送后马上close关闭了，导致服务器那边直接收到关闭流的指令，服务器的read也不再阻塞，不再等数据包了*/




        /**接收服务器端给客户端反馈*/
//        现在是客户端接收服务器的反馈
//        客户端监听服务器端的反馈socket.getInputStream();就是在监听

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b2 = new byte[1024];
        int len2;
        while ((len2=inputStream.read(b2)) != -1){
            bos.write(b2,0,len2);
        }


        System.out.print(bos.toString());


//        关闭流
//        outputStream.close();
        fileInputStream.close();
        inputStream.close();
        bos.close();
        outputStream.close();

        //当然，套接字也关闭了
        socket.close();



    }



//    服务端 //server 接收 client的数据
    @Test
    public void server() throws IOException {

        //服务器端只需要初始化自己对应的端口就可以
        ServerSocket serverSocket = new ServerSocket(8899);


//        这个套接字本质上是属于服务器的，信息也是在描述服务器
//        获取的输入流是客户端发来的
        /**这一步阻塞并且进行监听，一旦有目标客户有输入流过来，会初始化套接字，自己的地址和端口不用说，自己知道
         * 根据客户端过来的TCP数据包（流），也知道了客户端的 地址和端口
         * 所以accept()方法中触发初始哈，初始化了套接字，然后返回套接字对象,不再阻塞，放行
         *
         * */
        Socket accept = serverSocket.accept();//获取其套接字,注意，由accept方法来监听，由流就去返回套接字





        //获取客户端过来的流,基于TCP协议,所以 套接字对象 中知道客户端的 ip 发送端口 目标端口等信息,也包括其 流
        InputStream inputStream = accept.getInputStream();//





        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] b = new byte[1024];
        int len;


        /**重点：输入流的read是阻塞式的方法，没有接收到停止指示，（read接收到结束指示后才不阻塞线程，继续走，读到没有就返回-1）
         * 直接停住线程，继续监听和接收数据，接收到了停止指示后才结束方法，返回-1*/
        /**注意。这里输入流的读入是一个阻塞式的，没有得到 流关闭 的指示，整个read中的线程就停住等指示*/
        while((len=inputStream.read(b)) != -1){
            byteArrayOutputStream.write(b,0,len);
        }


        FileOutputStream fileOutputStream = new FileOutputStream("clientData");

        fileOutputStream.write(byteArrayOutputStream.toByteArray());


        //客户端的流过来后，目标就是这里，这里设置的监听端口就是客户都的目标端口
        // 服务器端和客户端都知道这个端口（客户端和服务器端的交接点）
//        套接字(用客户端发过来的流初始化的) 中 可以获取客户端目标端口 和 流的出发端口
//        accept.getPort(),,客户端的出发的端口
//        accept.getLocalPort(),,客户端的目标端口
//        accept.getInetAddress(),,客户端的IP
        System.out.println("从"+"IP:"+accept.getInetAddress()+"端口:"+accept.getPort()+"---->发送而来的数据,到本地的 "+serverSocket.getLocalPort()+"端口");


/**        服务端反馈给客户端*/ //套接字的输出是服务器的端口
        OutputStream os = accept.getOutputStream();

        os.write(("服务器 "+accept.getInetAddress().getHostAddress()+" 端口:"+accept.getLocalPort()+"已经收到数据").getBytes());


        System.out.println("服务器端： 本地地址"+accept.getLocalAddress()+"本地出发端口:"+accept.getLocalPort()+"发送到---->目标地址"+accept
                .getInetAddress()+"的目标端口 "+accept.getPort());



//关闭资源

        fileOutputStream.close();
        inputStream.close();

        //这个套接字关闭就是服务器关闭了
        serverSocket.close();
        os.close();

        accept.close();
        byteArrayOutputStream.close();




    }




}
