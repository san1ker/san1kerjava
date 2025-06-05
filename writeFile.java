import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.Arrays;

public class FileUtil {

    /**
     * 문자열 배열을 받아 지정한 경로에 텍스트 파일로 저장합니다.
     * 배열의 각 요소는 줄바꿈(엔터)으로 구분됩니다.
     *
     * @param inpStrArr 저장할 문자열 배열
     * @param path      파일을 저장할 전체 경로 (예: "output.txt" 또는 "/tmp/output.txt")
     * @throws IOException 파일 쓰기 중 오류가 발생할 경우
     */
    public static void writeLines(String[] inpStrArr, String path) throws IOException {
        Path filePath = Paths.get(path);
        // Arrays.asList로 List<String>을 만들고 UTF-8 인코딩으로 한 줄씩 기록
        Files.write(filePath, Arrays.asList(inpStrArr), StandardCharsets.UTF_8);
    }

    // 사용 예시
    public static void main(String[] args) {
        String[] data = {
            "첫 번째 줄",
            "두 번째 줄",
            "세 번째 줄"
        };
        String outPath = "example_output.txt";

        try {
            writeLines(data, outPath);
            System.out.println("파일이 성공적으로 저장되었습니다: " + outPath);
        } catch (IOException e) {
            System.err.println("파일 저장 중 오류 발생:");
            e.printStackTrace();
        }
    }
}
