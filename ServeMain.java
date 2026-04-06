import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String[] args) {
        try {
            // 문제에서 요구하는 내 서버 포트 번호로 시작
            ServerUtil.startServer(8080); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =================================================================
    // [라우팅] API 주소에 따라 함수 연결해주기
    // =================================================================
    public static JsonObject route(String method, String path, JsonObject requestData) {
        System.out.println("\n[" + method + "] " + path + " 요청 들어옴!");
        
        try {
            if ("GET".equals(method) && "/api/info".equals(path)) {
                return getInfoLogic(requestData);
            } 
            else if ("POST".equals(method) && "/api/result".equals(path)) {
                return postResultLogic(requestData);
            }
            // 필요한 API가 있으면 여기에 else if 로 계속 추가해!
            
        } catch (Exception e) {
            e.printStackTrace();
            JsonObject error = new JsonObject();
            error.addProperty("status", "ERROR");
            return error;
        }

        JsonObject notFound = new JsonObject();
        notFound.addProperty("status", "FAIL");
        return notFound;
    }

    // =================================================================
    // [로직 1] GET 요청 처리 예시
    // =================================================================
    private static JsonObject getInfoLogic(JsonObject requestData) throws Exception {
        JsonObject response = new JsonObject();
        
        // 1. 내 서버로 들어온 GET 쿼리 파라미터 빼기 (?keyword=apple)
        // String keyword = requestData.get("keyword").getAsString();
        
        // 2. 외부 서버로 GET 요청 쏘기 (주소 뒤에 ? 붙여서)
        // String targetUrl = "127.0.0.1:9090/data?type=book";
        // String apiRes = ServerUtil.sendGet(targetUrl);
        // JsonObject extData = ServerUtil.gson.fromJson(apiRes, JsonObject.class);

        // 3. 배열 반복문 돌리기 예시
        // JsonArray items = extData.getAsJsonArray("items");
        // for (JsonElement el : items) {
        //     JsonObject item = el.getAsJsonObject();
        //     int price = item.get("price").getAsInt();
        // }

        response.addProperty("status", "SUCCESS");
        return response; 
    }

    // =================================================================
    // [로직 2] POST 요청 처리 예시
    // =================================================================
    private static JsonObject postResultLogic(JsonObject requestData) throws Exception {
        JsonObject response = new JsonObject();
        
        // 1. 내 서버로 들어온 POST 바디 JSON 파싱
        // String userId = requestData.get("userId").getAsString();
        // int score = requestData.get("score").getAsInt();
        
        // 2. 외부 서버로 POST 요청 쏘기 (데이터 포장해서)
        // JsonObject payload = new JsonObject();
        // payload.addProperty("id", "user123");
        // String postRes = ServerUtil.sendPost("127.0.0.1:9090/update", payload);
        // JsonObject extData = ServerUtil.gson.fromJson(postRes, JsonObject.class);

        // 3. 4번 문제 필살기: 배열 정렬 (예: price 기준 desc 내림차순)
        // JsonArray rawItems = extData.getAsJsonArray("items");
        // JsonArray sortedItems = ServerUtil.sortJsonArray(rawItems, "price", "desc");
        
        // 4. 정렬된 배열을 결과 JSON에 통째로 넣기
        // response.add("data", sortedItems);

        response.addProperty("status", "SUCCESS");
        return response; 
    }
}
