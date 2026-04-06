import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ServerUtil {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void startServer(int port) throws Exception {
        Server server = new Server();
        ServerConnector httpConnector = new ServerConnector(server);
        httpConnector.setHost("127.0.0.1");
        httpConnector.setPort(port);
        server.addConnector(httpConnector);

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(MyServlet.class, "/*");
        server.setHandler(servletHandler);

        System.out.println(">> 서버가 " + port + " 포트에서 정상적으로 실행 중!");
        server.start();
        server.join();
    }

    public static class MyServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
            processRequest("GET", req, res);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
            processRequest("POST", req, res);
        }

        private void processRequest(String method, HttpServletRequest req, HttpServletResponse res) throws IOException {
            String path = req.getRequestURI(); 
            JsonObject requestJson = new JsonObject();

            if ("POST".equals(method)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) sb.append(line);
                
                if (sb.length() > 0) {
                    try { requestJson = gson.fromJson(sb.toString(), JsonObject.class); } 
                    catch (Exception e) { /* 무시 */ }
                }
            } else if ("GET".equals(method)) {
                String query = req.getQueryString();
                if (query != null) {
                    for (String param : query.split("&")) {
                        String[] pair = param.split("=");
                        if (pair.length == 2) requestJson.addProperty(pair[0], pair[1]);
                    }
                }
            }
            
            JsonObject responseJson = Main.route(method, path, requestJson);

            res.setStatus(200);
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(responseJson.toString());
            res.getWriter().flush();
        }
    }

    public static String sendGet(String urlString) throws IOException {
        URL url = new URL(urlString.startsWith("http") ? urlString : "http://" + urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        return readResponse(conn);
    }

    public static String sendPost(String urlString, Object data) throws IOException {
        String jsonPayload = gson.toJson(data);
        URL url = new URL(urlString.startsWith("http") ? urlString : "http://" + urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return readResponse(conn);
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line).append('\n');
        reader.close();
        conn.disconnect();
        return sb.toString().trim();
    }

    public static JsonArray sortJsonArray(JsonArray inputArray, String key, String order) {
        if (inputArray == null || inputArray.size() == 0) return new JsonArray();
        List<JsonObject> list = new ArrayList<>();
        for (JsonElement el : inputArray) if (el.isJsonObject()) list.add(el.getAsJsonObject());

        JsonElement firstVal = list.get(0).get(key);
        Comparator<JsonObject> comparator;

        if (firstVal == null || firstVal.isJsonNull()) {
            comparator = Comparator.comparing(obj -> obj.get(key).toString());
        } else if (firstVal.isJsonPrimitive()) {
            JsonPrimitive prim = firstVal.getAsJsonPrimitive();
            if (prim.isNumber()) {
                try { comparator = Comparator.comparingDouble(obj -> obj.get(key).getAsDouble()); } 
                catch (Exception e) { comparator = Comparator.comparing(obj -> obj.get(key).getAsString()); }
            } else if (prim.isBoolean()) {
                comparator = Comparator.comparing(obj -> obj.get(key).getAsBoolean());
            } else {
                comparator = Comparator.comparing(obj -> obj.get(key).getAsString());
            }
        } else {
            comparator = Comparator.comparing(obj -> obj.get(key).toString());
        }

        if ("desc".equalsIgnoreCase(order)) comparator = comparator.reversed();
        list.sort(comparator);

        JsonArray sortedArray = new JsonArray();
        for (JsonObject obj : list) sortedArray.add(obj);
        return sortedArray;
    }
}
