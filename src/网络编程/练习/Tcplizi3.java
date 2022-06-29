package 网络编程.练习;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Tcplizi3 {

    //客户端
    @Test
    public void client() throws IOException {

        /** 1, 创建socket对象，指定服务器端 IP 和 端口号*/
        //创建socket对象，客户端，需要对方的[IP+端口号]来初始化socket
        //对方IP来初始化InetAddress实例，然后此实例和对方端口号作为参数给Socket构造器来初始化socket套接字对象
        //写目标对方的IP作为InetAddress实例
        InetAddress inet = InetAddress.getByName("127.0.0.1");/**指定对方的IP*/
        //发数据和收数据，都建立在Socket套接字上运行
        Socket socket = new Socket(inet, 8899);/**指定对方的端口*/


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


    /**
     * 服务器端开启之后会一直处于线程启动待命监听状态
     * socket = serverSocket.accept();中的socket会一直待命，有客户端发数据来，就会去获取输入流然后执行服务器任务
     */
    //服务端
    @Test
    public void server() {
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
            while ((len = inputStream.read(buffer)) != -1) {

                //先不要组装成字符串
                //            先全部写出到对应的一个ByteArrayOutputStream流中
                baos.write(buffer, 0, len);

            }


            //借用ByteArrayOutputStream流，将完整的字节整体组装成字符串
            System.out.println(baos.toString());//toString就会将里面的所有字节转换成字符串了


            System.out.println("来自于：" + socket.getInetAddress().getHostAddress() + "的数据");


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
