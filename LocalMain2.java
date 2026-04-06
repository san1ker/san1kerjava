import java.util.*; 
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // [1] 파일은 루프 돌기 전에 딱 한 번만 읽어서 메모리에 올려둬!
            // (input_data.txt 자리에 문제에서 준 파일명 넣기)
            String[] lines = LocalUtil.readFile("input_data.txt", true);
            System.out.println("총 " + lines.length + "줄의 데이터를 읽어왔어.");
            System.out.println("=========================================");

            // [2] 무한 루프로 계속 입력받기
            while (true) {
                System.out.print("검색할 키워드 입력 (종료하려면 EXIT 입력): ");
                String keyword = scanner.nextLine();

                // 탈출 조건 (대소문자 구분 없이 처리)
                if ("EXIT".equalsIgnoreCase(keyword)) {
                    System.out.println("프로그램을 종료할게!");
                    break; 
                }

                // ==========================================
                // 3. 배열로 파싱 및 검색 로직 (작업대)
                // ==========================================
                // 정답을 담을 배열을 원본 데이터 크기만큼 아주 넉넉하게 생성
                String[] tempResult = new String[lines.length];
                int count = 0; // 진짜로 찾은 정답 개수 카운터
                
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    if (line == null || line.trim().isEmpty()) continue;
                    
                    // [참고] 필요한 도구(split, parseInt 등)는 로컬메인1의 레시피 참조!
                    if (line.contains(keyword)) {
                        tempResult[count] = line;
                        count++;
                    }
                }

                // 남는 빈 공간(null) 날려버리고 실제 개수만큼만 새 배열로 빼냄
                String[] finalResult = Arrays.copyOf(tempResult, count);

                // ==========================================
                // 4. 검색 결과 콘솔 출력
                // ==========================================
                System.out.println("\n[검색 결과: 총 " + count + "건]");
                for (int i = 0; i < finalResult.length; i++) {
                    System.out.println(" -> " + finalResult[i]);
                }
                System.out.println("-----------------------------------------\n");
            }

        } catch (Exception e) {
            System.out.println("에러 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 프로그램 종료 시 스캐너 깔끔하게 닫아주기
            scanner.close(); 
        }
    }
}
