package com.demo.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        OutputStream os = socket.getOutputStream();
        os.write("hello".getBytes());
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) != -1) {
            System.out.print(new String(bytes, 0, len, "utf-8"));
        }
        os.close();

    }
}
