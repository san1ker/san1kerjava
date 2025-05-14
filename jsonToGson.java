import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

// 예시용 데이터 클래스
class Person {
    private String name;
    private int age;
    // 필요한 필드들 추가

    // getter/setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

public class JsonUtil {
    private static final Gson gson = new Gson();

    // 제네릭 메서드: json 문자열을 임의의 타입으로 변환
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonSyntaxException {
        return gson.fromJson(json, clazz);
    }

    // JsonObject 로 받는 예시
    public static JsonObject toJsonObject(String json) throws JsonSyntaxException {
        return gson.fromJson(json, JsonObject.class);
    }

    // 테스트용 main
    public static void main(String[] args) {
        // 1) 커스텀 객체로 변환
        String jsonStr = "{\"name\":\"홍길동\",\"age\":30}";
        try {
            Person p = fromJson(jsonStr, Person.class);
            System.out.println(p);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        // 2) JsonObject 로 변환
        String rawJson = "{\"status\":\"ok\",\"data\":{\"value\":123}}";
        try {
            JsonObject obj = toJsonObject(rawJson);
            System.out.println("status: " + obj.get("status").getAsString());
            System.out.println("value: " + obj.getAsJsonObject("data").get("value").getAsInt());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        
        // string array 받기
        String json = "{ \"data\": [\"aaa\", \"bbb\"] }";

        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        JsonArray dataArray = obj.getAsJsonArray("data");

        String[] result = new String[dataArray.size()];
        for (int i = 0; i < dataArray.size(); i++) {
            result[i] = dataArray.get(i).getAsString();
        }

        // 확인
        for (String s : result) {
            System.out.println(s);
        }

        String json = "{ \"data\": [1, 2, 3] }";

        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        JsonArray dataArray = obj.getAsJsonArray("data");

        int[] result = new int[dataArray.size()];
        for (int i = 0; i < dataArray.size(); i++) {
            result[i] = dataArray.get(i).getAsInt();
        }

        for (int n : result) {
            System.out.println(n);
        }


        
    }
}
