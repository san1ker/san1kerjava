import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {

    /**
     * addr로 GET 요청을 보내고 응답 바디를 문자열로 반환한다.
     *
     * @param addr "host:port[/path]" 형식의 주소 (예: "example.com:8080/api/data")
     * @return 응답 바디 전체 문자열
     * @throws IOException 요청 또는 응답 처리 중 오류가 발생했을 때
     */
    public static String sendHttpRequest(String addr) throws IOException {
        // http:// 접두사 붙여 URL 객체 생성
        URL url = new URL("http://" + addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int status = conn.getResponseCode();
        // 성공 코드(200~299)면 input stream, 아니면 error stream 읽음
        InputStream is = (status >= 200 && status < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        conn.disconnect();

        return sb.toString().trim();  // 마지막 줄바꿈 제거
    }

    // 테스트용 main
    public static void main(String[] args) {
        try {
            String addr = "example.com:8080/test";
            String response = sendHttpRequest(addr);
            System.out.println("Response Body:");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
