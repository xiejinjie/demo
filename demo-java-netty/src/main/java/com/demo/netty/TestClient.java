package com.demo.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8080);
        OutputStream os = socket.getOutputStream();
        os.write("hello".getBytes());
        InputStream is = socket.getInputStream();
        byte[] buf = new byte[1024];
        int read = is.read(buf);
        System.out.println(new String(buf, 0, read));
        socket.close();
    }
}
