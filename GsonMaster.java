import com.google.gson.*;
import java.util.*;

/**
 * [가이드] 이 파일은 실행용이라기보다, 3~4번 문제를 풀 때 
 * "어떻게 꺼내고 어떻게 넣는지" 헷갈리면 복사해서 쓰기 위한 참조용이야!
 */
public class GsonMaster {

    public static void main(String[] args) {
        // [테스트 데이터] 객체 안에 객체도 있고, 배열도 있는 복잡한 구조
        String jsonStr = "{"
            + "\"storeName\": \"강남점\","
            + "\"info\": { \"manager\": \"김철수\", \"open\": true }," 
            + "\"items\": ["                                           
            + "  { \"id\": 1, \"name\": \"키보드\", \"tags\": [\"IT\", \"입력\"] },"
            + "  { \"id\": 2, \"name\": \"마우스\", \"tags\": [\"무선\"] }"
            + "]"
        + "}";

        System.out.println("--- [1. 데이터 읽기(Read) 예시] ---");
        readExample(jsonStr);

        System.out.println("\n--- [2. 데이터 쓰기(Write) 예시] ---");
        writeExample();
    }

    /**
     * 1. 데이터 읽기 (Read): JSON 문자열에서 값 꺼내기
     */
    public static void readExample(String jsonStr) {
        // [공식] 문자열을 일단 JsonObject(큰 상자)로 바꾼다.
        JsonObject root = ServerUtil.gson.fromJson(jsonStr, JsonObject.class);

        // (A) 글자(String) 꺼내기 -> .getAsString()
        String name = root.get("storeName").getAsString(); // 결과: "강남점"
        
        // (B) 중첩된 객체 { } 꺼내기 -> .getAsJsonObject()
        JsonObject info = root.getAsJsonObject("info");
        
        // (B-1) 객체 안의 글자 꺼내기
        String manager = info.get("manager").getAsString(); // 결과: "김철수"
        
        // (B-2) 객체 안의 불리언(Boolean) 꺼내기 -> .getAsBoolean() ★추가
        boolean isOpen = info.get("open").getAsBoolean();   // 결과: true

        // (C) 중첩된 배열 [ ] 꺼내기 -> .getAsJsonArray()
        JsonArray items = root.getAsJsonArray("items");

        // (D) 배열 반복문 돌리기 (리스트 안에 있는 것들 하나씩 요리)
        for (JsonElement el : items) {
            JsonObject item = el.getAsJsonObject(); // 배열 한 칸을 객체{}로 변환
            
            // (D-1) 배열 안의 객체에서 숫자(Integer) 꺼내기 -> .getAsInt() ★추가
            int id = item.get("id").getAsInt();     // 결과: 1 또는 2
            
            String itemName = item.get("name").getAsString();
            
            // (E) 배열 속의 배열 꺼내기 (tags)
            JsonArray tags = item.getAsJsonArray("tags");
            String firstTag = tags.get(0).getAsString(); // "IT" 또는 "무선"
            
            System.out.println("ID: " + id + ", 상품명: " + itemName + " (첫번째 태그: " + firstTag + ")");
        }
    }

    /**
     * 2. 데이터 쓰기 (Write): 새로운 JSON 조립하기 (응답용)
     * 포인트: 단순 값은 addProperty(), 덩어리({ }, [ ])는 add()
     */
    public static void writeExample() {
        // [단계 1] 가장 겉면이 될 큰 상자 만들기
        JsonObject myRes = new JsonObject(); // {}
        myRes.addProperty("status", "SUCCESS"); // {"status": "SUCCESS"}

        // [단계 2] 내부에 넣을 작은 상자(객체) 조립
        JsonObject subObj = new JsonObject();
        subObj.addProperty("msg", "완료"); // {"msg": "완료"}
        
        // ★ [중요] 큰 상자에 작은 상자 넣기 (add 사용!)
        // 결과: {"status": "SUCCESS", "detail": {"msg": "완료"}}
        myRes.add("detail", subObj); 

        // [단계 3] 내부에 넣을 리스트(배열) 상자 조립
        JsonArray list = new JsonArray();
        list.add("데이터1"); // ["데이터1"]
        
        // 배열 안에 또 객체 넣기
        JsonObject inner = new JsonObject();
        inner.addProperty("id", 100);
        list.add(inner); // ["데이터1", {"id": 100}]

        // ★ [중요] 큰 상자에 리스트 상자 넣기 (add 사용!)
        // 결과: {"status": "SUCCESS", "detail": {...}, "listData": ["데이터1", {"id": 100}]}
        myRes.add("listData", list);

        // 최종 출력 (서버 응답으로 보낼 때 사용)
        String finalJson = ServerUtil.gson.toJson(myRes);
        System.out.println(finalJson);
    }
}
