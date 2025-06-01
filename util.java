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
