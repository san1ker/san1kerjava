import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; // ★ 절대 누락 금지

public class LocalUtil {

    // 1. 텍스트 파일 읽기 (byLine=true면 한 줄씩 배열로, false면 통째로 1칸 배열로)
    public static String[] readFile(String path, boolean byLine) throws IOException {
        Path filePath = Paths.get(path);
        if (byLine) {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            return lines.toArray(new String[0]);
        } else {
            byte[] bytes = Files.readAllBytes(filePath);
            return new String[]{ new String(bytes, StandardCharsets.UTF_8) };
        }
    }

    // 2. 텍스트 파일 쓰기 (결과 배열을 엔터로 구분해서 파일로 저장)
    public static void writeLines(String[] inpStrArr, String path) throws IOException {
        Path filePath = Paths.get(path);
        Files.write(filePath, Arrays.asList(inpStrArr), StandardCharsets.UTF_8);
    }

    // 3. 디렉토리 내 파일 목록 가져오기 (폴더 안에 파일이 여러 개일 때 사용)
    public static String[] listFilesInDirectory(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) return new String[0];
        
        File[] files = dir.listFiles(File::isFile);
        if (files == null) return new String[0];

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

    // =========================================================
    // ★ [추가된 유틸] 문자열 배열 예쁘게 출력하기 (디버깅 필수템)
    // =========================================================
    public static void printStringArray(String[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println(">> [LocalUtil] 출력할 배열이 비어있음!");
            return;
        }

        System.out.println("---------- String Array (Size: " + arr.length + ") ----------");
        for (int i = 0; i < arr.length; i++) {
            // 인덱스 번호를 붙여서 몇 번째 줄인지 바로 확인 가능하게 함
            System.out.println("[" + i + "] " + arr[i]);
        }
        System.out.println("--------------------------------------------------");
    }
}
