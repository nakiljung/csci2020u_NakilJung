package Client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    //connection information
    String host = "localhost";
    int port = 1234;
    //machine information
    String name;
    String path;

    //constructor to initialize the variables
    public Client(String name, String path) {
        this.path = path+"\\";
        this.name = name;
    }

    //main method used for testing
    public static void main(String[] args) throws Exception {

//        Client c = new Client("","client");
//        c.uploadFile("txt1.txt");
//        c.getServerFileList();
//        c.downloadFile("txt2.txt");
    }

    //get the list of files from the server
    public String getServerFileList() throws IOException {
        //open a connection
        Socket socket = new Socket(host, port);

        //send the request with the DIR directive
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        String mess = "DIR";
        out.write(mess.length());
        out.write((mess).getBytes());
        out.flush();

        //get the response
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        String files = new String(in.readAllBytes(), StandardCharsets.UTF_8);

//        System.out.println(files);
        //close the connection
        out.close();
        in.close();
        socket.close();

        //return the list of files
        return files;
    }

    //get the local filelist
    public String getLocalFileList(){
        //list all the files
        File file = new File(path);
        String[] names = file.list();
        //combine them into a single string
        String namesList = "";
        for(int i = 0; i < names.length; i++){
            if(i > 0) namesList+="/";
            namesList+=names[i];
        }

        //return the string
        return namesList;
    }

    //send a file to the server
    public void uploadFile(String filename) throws Exception{
        //open connection
        Socket socket = new Socket(host, port);

        //open the file
        File file = new File(path+filename);
        byte[] bytes = new byte[4096];
        InputStream in = new FileInputStream(file);
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        String mess = "UPLOAD "+filename;
        //send length of header information
        out.write(mess.length());
        out.write((mess).getBytes());
        out.flush();

        //send the file bit by bit
        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        //close connection
        out.close();
        in.close();
        socket.close();
    }

    //download a file from the server
    public void downloadFile(String filename) throws Exception{
        //open connection
        Socket socket = new Socket(host, port);

        //send request with the DOWNLOAD directive
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
        String mess = "DOWNLOAD "+filename;
        out.write(mess.length());
        out.write((mess).getBytes());
        out.flush();

        //read in the file bit by bit saving it into the local file path
        BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
        FileOutputStream fos = new FileOutputStream(new File(path + filename), false);
        byte[] bytes = new byte[4096];
        int count;
        while ((count = in.read(bytes)) > 0) {
            fos.write(bytes, 0, count);
        }
        //close all connections
        fos.close();
        in.close();
        out.close();
        socket.close();
    }
}

