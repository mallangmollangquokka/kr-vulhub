import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * CVE-2021-44228 (Log4Shell) 재현용 취약 앱.
 * 요청 헤더 X-Api-Version 값을 검증 없이 그대로 로깅한다.
 * log4j-core:2.14.1 사용 (패치 전 버전).
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new Handler());
        server.setExecutor(null);
        server.start();
        System.out.println("[app] Vulnerable Log4j2 demo app listening on :8080");
    }

    static class Handler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String apiVersion = exchange.getRequestHeaders().getFirst("X-Api-Version");
            if (apiVersion == null) {
                apiVersion = "unknown";
            }

            // 취약 지점: 사용자 입력을 검증 없이 로깅 -> Lookup 문자열이 평가됨
            logger.info("Request received. X-Api-Version: {}", apiVersion);

            String response = "OK\n";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
