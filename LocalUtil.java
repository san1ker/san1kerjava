import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    // 3. 디렉토리 내 파일 목록 가져오기 (폴더 안에 txt 파일이 여러 개일 때 사용)
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
}
