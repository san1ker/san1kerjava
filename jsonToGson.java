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
    }
}
