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

      8. 날짜 쪼개기 (날짜 데이터가 "2024/05/20" 일 때)
       String date = "2024/05/20";
       String[] d = date.split("/"); // ["2024", "05", "20"]
       int year = Integer.parseInt(d[0]); // 숫자로 바꿔서 크기 비교 가능

        9. 수학 계산 (반올림, 올림, 절대값)
           long rounded = Math.round(3.5); // 반올림 -> 4
           double ceiled = Math.ceil(3.1); // 올림 -> 4.0
           int absolute = Math.abs(-10);   // 절대값 -> 10

        10. 문자열 찾기 및 존재 여부 (정밀 체크)
           int index = line.indexOf("검색어"); // 위치 찾기 (없으면 -1)
           boolean ends = line.endsWith(".txt"); // 확장자 확인할 때 꿀팁
           boolean empty = line.isEmpty(); // "" 인지 확인 (길이가 0인지)

        11. 문자열 합치기 (여러 변수를 하나로)
           String join = String.join("-", "010", "1234", "5678"); // "010-1234-5678"
           String build = "ID:" + id + " / Name:" + name; // 단순 합치기

        12. 배열 정렬 (원본 유지 + 새 배열에 저장)
           // (A) String 배열: 원본 names는 두고, sortedNames에 정렬 결과 담기
           String[] names = {"가", "다", "나"};
           String[] sortedNames = Arrays.copyOf(names, names.length); // 1. 복사 먼저!
           Arrays.sort(sortedNames); // 2. 복사본만 정렬 (결과: "가", "나", "다")
           
           // [내림차순]도 마찬가지로 복사본을 정렬
           String[] descNames = Arrays.copyOf(names, names.length); 
           Arrays.sort(descNames, Collections.reverseOrder()); // (결과: "다", "나", "가")

           // (B) int 배열: 원본 nums는 두고, descNums에 내림차순 결과 담기
           int[] nums = {3, 1, 5, 2};
           int[] temp = Arrays.copyOf(nums, nums.length); // 1. 일단 복사
           Arrays.sort(temp); // 2. 복사본을 오름차순 정렬 {1, 2, 3, 5}
           
           int[] descNums = new int[temp.length]; // 3. 최종 내림차순 담을 상자
           int idx = 0;
           for (int i = temp.length - 1; i >= 0; i--) {
               descNums[idx] = temp[i]; // 뒤에서부터 새 상자에 담기
               idx++;
           }
           // 결과: 원본 nums는 {3, 1, 5, 2} 그대로 있고, descNums만 {5, 3, 2, 1}이 됨!

        13. 꿀팁: 데이터에서 숫자만 남기기 (정규식)
           String onlyNum = raw.replaceAll("[^0-9]", ""); // 숫자 빼고 싹 제거
       =============================================================
    */
}
