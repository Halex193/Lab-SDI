package ro.sdi.lab24.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import ro.sdi.lab24.exception.ConnectionException;

public class TCPClient {
    public static Message sendAndReceive(Message request) {
        try (Socket socket = new Socket(ServerInformation.HOST, ServerInformation.PORT);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()
        ) {
            Message.write(request, outputStream);
            return Message.read(inputStream);
        } catch (IOException e) {
            throw new ConnectionException("Connection to server failed");
        }
    }
}
