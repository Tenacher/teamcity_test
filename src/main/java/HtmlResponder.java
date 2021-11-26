import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HtmlResponder {
    private static String html = "";
    public static void main() throws IOException {
        try {
            File response = new File("response.html");
            Scanner reader = new Scanner(response);
            while(reader.hasNextLine()) {
                html += reader.nextLine() + '\n';
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/test", new MyHttpHandler());
        server.setExecutor(null);
        server.start();
    }
    static class MyHttpHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, html.length());
            OutputStream os = exchange.getResponseBody();
            os.write(html.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }
}
