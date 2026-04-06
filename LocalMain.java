// 필요한 유틸리티(Arrays, Scanner 등) 한 번에 다 가져오기
import java.util.*; 

public class Main {

    public static void main(String[] args) {
        // 스캐너는 여기서 한 번만 열고 닫는 게 안전해
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. 콘솔에서 문제의 조건(검색어 등) 입력받기
            System.out.print("검색할 키워드나 명령어 입력: ");
            String keyword = scanner.nextLine();

            // 2. 파일 읽어오기 (input_data.txt 자리에 문제에서 준 파일명 넣기)
            String[] lines = LocalUtil.readFile("input_data.txt", true);
            System.out.println("총 " + lines.length + "줄의 데이터를 읽어왔어.");

            // ==========================================
            // 3. ★ 핵심 로직: 쌩 배열로 파싱하기 ★
            // ==========================================
            // 정답을 담을 배열을 원본 데이터 크기만큼 아주 넉넉하게 생성해
            String[] tempResult = new String[lines.length];
            int count = 0; // 진짜로 찾은 정답 개수만 세는 카운터
            
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                
                // [파싱 예시] 콤마로 쪼개야 한다면?
                // String[] parts = line.split(",");
                
                // [조건 예시] 입력받은 키워드가 포함된 줄이라면?
                if (line.contains(keyword)) {
                    // tempResult 배열의 count 인덱스 자리에 정답 저장!
                    tempResult[count] = "정답포함: " + line;
                    count++; // 하나 저장했으니 카운터 1 증가
                }
            }

            // ==========================================
            // 4. ★ 배열 다이어트 (핵심) ★
            // ==========================================
            // 남는 빈 공간(null)을 날려버리고 딱 count 개수만큼만 새 배열로 빼냄
            String[] finalResult = Arrays.copyOf(tempResult, count);

            // 5. 결과를 파일로 저장하기
            LocalUtil.writeLines(finalResult, "output.txt");
            System.out.println("처리 완료! output.txt 파일에 총 " + count + "개의 결과가 저장됐어.");

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 프로그램 끝날 때 스캐너 깔끔하게 닫아주기
            scanner.close(); 
        }
    }
}
