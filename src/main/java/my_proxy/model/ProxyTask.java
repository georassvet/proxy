package my_proxy.model;

import lombok.Data;
import my_proxy.service.DelayService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

@Data
public class ProxyTask implements Runnable {

    private ProxyModel proxyModel;
    private ServerSocket s;
    private boolean enable = true;

    public ProxyTask(ProxyModel proxyModel) throws IOException {
        this.proxyModel=proxyModel;
        this.s = new ServerSocket(proxyModel.getLocalPort());
    }
    @Override
    public void run(){
        final byte[] request = new byte[1024];
        byte[] reply = new byte[4096];
        while (enable) {
            Socket client = null, server = null;
            try {
                // It will wait for a connection on the local port
                client = s.accept();
                final InputStream streamFromClient = client.getInputStream();
                final OutputStream streamToClient = client.getOutputStream();

                try {
                    server = new Socket(proxyModel.getRemoteHost(), proxyModel.getRemotePort());
                } catch (IOException e) {
                    PrintWriter out = new PrintWriter(streamToClient);
                    out.print("Proxy server cannot connect to " + proxyModel.getRemoteHost() + ":"
                            + proxyModel.getRemotePort() + ":\n" + e + "\n");
                    out.flush();
                    client.close();
                    continue;
                }

                // Get server streams.
                final InputStream streamFromServer = server.getInputStream();
                final OutputStream streamToServer = server.getOutputStream();

                // a thread to read the client's requests and pass them
                // to the server. A separate thread for asynchronous.
                Thread t = new Thread() {
                    public void run() {
                        int bytesRead;
                        try {
                            while ((bytesRead = streamFromClient.read(request)) != -1) {
                                streamToServer.write(request, 0, bytesRead);
                                streamToServer.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // the client closed the connection to us, so close our
                        // connection to the server.
                        try {
                            streamToServer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // Start the client-to-server request thread running
                t.start();
                // Read the server's responses
                // and pass them back to the client.
                int bytesRead;
                try {
                    long delay = DelayService.getDelay(proxyModel.getLocalPort());
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    while ((bytesRead = streamFromServer.read(reply)) != -1) {

                        streamToClient.write(reply, 0, bytesRead);
                        streamToClient.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // The server closed its connection to us, so we close our
                // connection to our client.
                streamToClient.close();
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                try {
                    if (server != null)
                        server.close();
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void stop() throws IOException {
        s.close();
    }
}
