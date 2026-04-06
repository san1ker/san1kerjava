import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String[] args) {
        try {
            // 1. 문제에서 요구하는 내 서버 포트 번호 넣기
            ServerUtil.startServer(8080); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =================================================================
    // [길잡이] API 주소에 따라 함수 연결해주기
    // =================================================================
    public static JsonObject route(String method, String path, JsonObject requestData) {
        System.out.println("\n[" + method + "] " + path + " 요청 들어옴!");
        
        try {
            // 주최측에서 /api/info 주소로 GET 요청을 하라고 했다면?
            if ("GET".equals(method) && "/api/info".equals(path)) {
                return getInfoLogic(requestData);
            } 
            // 주최측에서 /api/result 주소로 POST 요청을 하라고 했다면?
            else if ("POST".equals(method) && "/api/result".equals(path)) {
                return postResultLogic(requestData);
            }
            // 문제 조건에 따라 주소 분기를 계속 추가하면 됨!
            
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
    // [로직] ★★★ 네가 실제로 코딩해야 할 곳! ★★★
    // =================================================================
    
    private static JsonObject getInfoLogic(JsonObject requestData) throws Exception {
        JsonObject response = new JsonObject();
        
        // -------------------------------------------------------------
        // 여기서 파이썬 짜듯이 편하게 파싱하고 계산하면 돼!
        // (필요하면 아래쪽 치트시트 참고해서 외부 API도 호출해!)
        // -------------------------------------------------------------

        response.addProperty("status", "SUCCESS");
        return response; // 정답 리턴
    }

    private static JsonObject postResultLogic(JsonObject requestData) throws Exception {
        JsonObject response = new JsonObject();
        
        // -------------------------------------------------------------
        // 여기도 마찬가지! 데이터 받아서 요리하는 곳.
        // -------------------------------------------------------------

        response.addProperty("status", "SUCCESS");
        return response; // 정답 리턴
    }
}

/* =====================================================================
   💡 [Gson & 외부 통신 치트시트] - 필요할 때 복사해서 로직 안에 붙여넣기 💡
   =====================================================================

// 1. 외부 서버로 GET 요청 쏘기
String apiRes = ServerUtil.sendGet("127.0.0.1:9090/data?key=val");
JsonObject resJson = ServerUtil.gson.fromJson(apiRes, JsonObject.class);

// 2. 외부 서버로 POST 요청 쏘기
JsonObject payload = new JsonObject();
payload.addProperty("id", 123);
String postRes = ServerUtil.sendPost("127.0.0.1:9090/update", payload);

// 3. JSON에서 기본 데이터 빼기
String name = requestData.get("name").getAsString();
int age = requestData.get("age").getAsInt();

// 4. JSON에서 껍데기(객체)나 배열 빼기
JsonObject info = requestData.getAsJsonObject("info");
JsonArray items = requestData.getAsJsonArray("items");

// 5. 배열 반복문 돌리기
int total = 0;
for (JsonElement el : items) {
    JsonObject item = el.getAsJsonObject();
    total += item.get("price").getAsInt();
}

// 6. 배열 정렬하기 (예: price 기준 내림차순)
JsonArray sortedItems = ServerUtil.sortJsonArray(items, "price", "desc");

// 7. 정답 JSON 만들 때 객체나 배열 통째로 집어넣기
JsonObject response = new JsonObject();
response.addProperty("status", "OK"); // 기본 타입은 addProperty
response.add("data", sortedItems);    // 배열/객체는 add
===================================================================== */
