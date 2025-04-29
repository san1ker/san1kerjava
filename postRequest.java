import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
    private static final Gson gson = new Gson();

    /**
     * 주어진 URL로 POST 요청을 보내고,
     * body에 Gson 객체 배열을 JSON 배열 형태로 담아 전송한 뒤,
     * 응답 바디를 문자열로 반환한다.
     *
     * @param urlString 요청을 보낼 전체 URL (예: "http://example.com:8080/api/items")
     * @param data      Gson으로 직렬화할 객체 배열
     * @param <T>       배열 요소 타입
     * @return 응답 바디 전체 문자열
     * @throws IOException 요청·응답 처리 중 오류 발생 시
     */
    public static <T> String postJsonArray(String urlString, T[] data) throws IOException {
        // 객체 배열을 JSON 배열 문자열로 변환
        String json = gson.toJson(data);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // 바디에 JSON 쓰기
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 상태 코드에 따라 적절한 스트림 선택
        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        // 응답 읽어서 문자열로 합치기
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        reader.close();
        conn.disconnect();

        return sb.toString().trim();
    }

    // 사용 예시
    public static void main(String[] args) {
        class Item {
            String name;
            int qty;
            Item(String name, int qty) { this.name = name; this.qty = qty; }
        }

        Item[] items = {
            new Item("apple", 10),
            new Item("banana", 5)
        };

        try {
            String response = postJsonArray("http://example.com:8080/api/items", items);
            System.out.println("Response Body:");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
