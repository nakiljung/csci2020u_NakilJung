package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    //hold the socket
    ServerSocket socket;

    //constructor to initialize the socket
    public Server() throws IOException {
        socket = new ServerSocket(1234);
    }

    //start the server
    public static void main(String[] args) throws IOException {

        //create a new server and run it continuously
        Server server = new Server();
        System.out.println("Server started");
        while(true){
            //receive any incoming connections
            Socket s = server.socket.accept();
            //create a new thread and start it
            ClientConnectionHandler c = new ClientConnectionHandler(s);
            Thread t = new Thread(c);
            t.start();
        }
    }

}

//class to handle requests
class ClientConnectionHandler extends Thread{
    //instance variables
    private String command;
    BufferedInputStream bis;
    BufferedOutputStream bos;
    Socket s;
    String path = "server\\";

    //constructor to initialize variables
    public ClientConnectionHandler(Socket s) throws IOException {
        this.s = s;
        this.bis = new BufferedInputStream(s.getInputStream());
        this.bos = new BufferedOutputStream(s.getOutputStream());
    }

    //method run by thread
    public void run(){
        try {
            //read the size of the header information
            int len = bis.read();
            byte b[] = new byte[len];
            //read the header information
            bis.read(b, 0, b.length);
            //convert it into a string
            command = new String(b, StandardCharsets.UTF_8);
            String[] info = command.split(" ");
            //get the type of action to be taken
            if(command.startsWith("UPLOAD")){
                //if uploading open a new file stream
                FileOutputStream fos = new FileOutputStream(new File(path + info[1]), false);
                //and write out the file bit by bit
                b = new byte[4096];
                int count;
                while ((count = bis.read(b)) > 0) {
                    fos.write(b, 0, count);
                }
                //close the file
                fos.close();
            }else if(command.startsWith("DIR")){
                //if getting the file list
                File file = new File(path);
                //get the files
                String[] names = file.list();
                String namesList = "";
                //combine them to form one string
                for(int i = 0; i < names.length; i++){
                    if(i > 0) namesList+="/";
                    namesList+=names[i];
                }
                //send the string
                bos.write(namesList.getBytes());
                bos.flush();
            }else if(command.startsWith("DOWNLOAD")){
                //if downloading
                //open a new inout stream
                File file = new File(path+info[1]);
                //receive and store the data bit by bit
                byte[] bytes = new byte[4096];
                InputStream in = new FileInputStream(file);
                int count;
                while ((count = in.read(bytes)) > 0) {
                    bos.write(bytes, 0, count);
                    bos.flush();
                }
                in.close();
            }
            //close the socket
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
