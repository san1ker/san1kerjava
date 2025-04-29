import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    /**
     * 텍스트 파일을 읽어 String 배열로 반환합니다.
     *
     * @param path   읽을 파일의 경로 (String)
     * @param byLine true면 라인별로 나누어 반환, false면 전체를 하나의 요소로 반환
     * @return byLine==true일 때: 파일의 각 라인을 요소로 갖는 String[]
     *         byLine==false일 때: 파일 전체 내용을 하나의 요소로 갖는 String[]
     * @throws IOException 파일 입출력 오류 발생 시
     */
    public static String[] readFile(String path, boolean byLine) throws IOException {
        Path filePath = Paths.get(path);

        if (byLine) {
            // 파일을 라인 단위로 읽어서 List<String>으로 가져온 뒤 배열로 변환
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            return lines.toArray(new String[0]);
        } else {
            // 파일 전체를 바이트 배열로 읽어 UTF-8 문자열로 변환하고, 배열에 담아 반환
            byte[] bytes = Files.readAllBytes(filePath);
            String content = new String(bytes, StandardCharsets.UTF_8);
            return new String[]{ content };
        }
    }

    // 사용 예시
    public static void main(String[] args) {
        try {
            // 라인별로 읽기
            String[] lines = readFile("example.txt", true);
            System.out.println("== byLine=true ==");
            for (String line : lines) {
                System.out.println(line);
            }

            // 전체를 하나의 문자열로 읽기
            String[] whole = readFile("example.txt", false);
            System.out.println("\n== byLine=false ==");
            System.out.println(whole[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
