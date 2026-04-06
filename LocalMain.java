import java.util.*; 
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // [1] 콘솔 입력 받기
            System.out.print("검색 키워드 입력: ");
            String keyword = scanner.nextLine().trim(); // 앞뒤 공백 제거

            // [2] 데이터 파일 읽기 (LocalUtil 사용)
            String[] lines = LocalUtil.readFile("input_data.txt", true);
            
            // ---------------------------------------------------------
            // ★ [작업대] 여기서부터 로직을 조립해!
            // ---------------------------------------------------------
            
            String[] tempResult = new String[lines.length]; // 넉넉하게 준비
            int count = 0; // 실제 담긴 정답 개수 카운트

            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line == null || line.trim().isEmpty()) continue; // 빈 줄 패스

                // [여기서 아래 '실전 도구 레시피'를 보고 코딩하면 돼!]
                // 예시: String[] p = line.split(",");
                // 예시: if (p[1].contains(keyword)) { ... }

                // (임시 로직: 키워드가 포함되면 담기)
                if (line.contains(keyword)) {
                    tempResult[count] = line; 
                    count++; 
                }
            }

            // [3] 마무리: 실제 개수만큼 배열 다이어트 (필수!)
            String[] finalResult = Arrays.copyOf(tempResult, count);

            // [4] 파일 저장 및 결과 확인
            LocalUtil.writeLines(finalResult, "output.txt");
            LocalUtil.printStringArray(finalResult); // 배열 내용 출력 유틸
            System.out.println(">> 총 " + count + "건 처리 완료!");

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close(); // 스캐너 닫기
        }
    }

    /* =============================================================
       📚 [실전 도구 레시피] - 필요한 코드만 위 '작업대'로 복사해서 써!
       =============================================================
       1. 쪼개기 (Split)
          - String[] p = line.split(",");  // 콤마 기준
          - String[] p = line.split("\\|"); // 파이프(|) 기준 (역슬래시 2개 필수)

       2. 자르기 (Substring)
          - String sub = line.substring(0, 4); // 0번부터 3번 글자까지 ("2024")

       3. 바꾸기 (Replace)
          - String rep = line.replace("-", ""); // 하이픈 제거

       4. 숫자 변환 (Convert)
          - int num = Integer.parseInt("123");   // 문자 -> 숫자 (비교/계산용)
          - String s = String.valueOf(123);      // 숫자 -> 문자 (합치기용)
          - double d = Double.parseDouble("3.14"); // 문자 -> 실수

       5. 비교 및 확인 (Check)
          - boolean has = line.contains("키워드"); // 포함 여부 (true/false)
          - boolean isSame = str.equals("Apple"); // 문자열 비교 (== 쓰지마!)
          - boolean starts = line.startsWith("ID"); // 특정 문자로 시작하는지

       6. 대소문자 & 공백 (Trim/Case)
          - String clean = line.trim();        // 앞뒤 공백 제거
          - String upper = line.toUpperCase(); // 대문자로 변환

       7. 포맷팅 (Format)
          - String fmt = String.format("%03d", 5);  // "005" (자릿수 맞추기)
          - String price = String.format("%, d", 1000); // "1,000" (콤마 찍기)
       =============================================================
    */
}
