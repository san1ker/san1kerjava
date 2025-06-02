/////////// Console input
import java.util.Scanner;

public class ConsoleInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // 입력 스트림 열기

        System.out.print("입력하세요: ");
        String input = scanner.nextLine();  // 한 줄 전체 입력 받기

        System.out.println("입력한 내용: " + input);

        scanner.close();  // 리소스 정리
    }
}


//////////// JSON SORT
import com.google.gson.*;
import java.util.*;

public class JsonArraySorter {

    public static JsonArray sortJsonArray(JsonArray inputArray, String key, String order) {
        if (inputArray == null || inputArray.size() == 0) return new JsonArray();

        List<JsonObject> list = new ArrayList<>();

        for (JsonElement el : inputArray) {
            if (el.isJsonObject()) {
                list.add(el.getAsJsonObject());
            }
        }

        // 값 타입 확인 (첫 번째 요소 기준)
        JsonElement firstVal = list.get(0).get(key);
        Comparator<JsonObject> comparator;

        if (firstVal == null || firstVal.isJsonNull()) {
            comparator = Comparator.comparing(obj -> obj.get(key).toString()); // fallback
        } else if (firstVal.isJsonPrimitive()) {
            JsonPrimitive prim = firstVal.getAsJsonPrimitive();
            if (prim.isNumber()) {
                // int인지 double인지 판단해서 정렬
                try {
                    comparator = Comparator.comparingDouble(obj -> obj.get(key).getAsDouble());
                } catch (Exception e) {
                    comparator = Comparator.comparing(obj -> obj.get(key).getAsString());
                }
            } else if (prim.isBoolean()) {
                comparator = Comparator.comparing(obj -> obj.get(key).getAsBoolean());
            } else {
                comparator = Comparator.comparing(obj -> obj.get(key).getAsString());
            }
        } else {
            // 객체나 배열이면 그냥 문자열로 정렬
            comparator = Comparator.comparing(obj -> obj.get(key).toString());
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        list.sort(comparator);

        JsonArray sortedArray = new JsonArray();
        for (JsonObject obj : list) {
            sortedArray.add(obj);
        }
        return sortedArray;
    }

    // 테스트
    public static void main(String[] args) {
        String json = "[" +
            "{\"name\":\"banana\", \"price\":500, \"rating\":4.2}," +
            "{\"name\":\"apple\", \"price\":300, \"rating\":4.8}," +
            "{\"name\":\"orange\", \"price\":400, \"rating\":4.0}" +
        "]";

        JsonArray arr = JsonParser.parseString(json).getAsJsonArray();
        JsonArray sorted = sortJsonArray(arr, "price", "asc");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(sorted));
    }
}


///////////// File listing
import java.io.File;

public class FileLister {
    /**
     * 특정 디렉토리 내 파일 이름 목록을 문자열 배열로 반환
     * 
     * @param dirPath 디렉토리 경로 (상대 또는 절대)
     * @return 파일 이름들 (String 배열)
     */
    public static String[] listFilesInDirectory(String dirPath) {
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("유효한 디렉토리가 아님: " + dirPath);
            return new String[0];
        }

        // 파일 객체 배열 → 이름만 뽑아서 문자열 배열로
        File[] files = dir.listFiles(File::isFile);
        if (files == null) return new String[0];

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }

        return names;
    }

    // 테스트용 main
    public static void main(String[] args) {
        // 예: 프로젝트 루트의 "data" 폴더
        String[] files = listFilesInDirectory("data");

        System.out.println("파일 목록:");
        for (String name : files) {
            System.out.println(name);
        }
    }
}

