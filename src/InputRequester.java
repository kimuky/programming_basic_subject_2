import java.util.Scanner;

public class InputRequester {

    Scanner sc = new Scanner(System.in);

    public String selectGameOption() {
        System.out.println("0. 자리수 설정 1. 게임 시작하기 2. 게임 기록 보기 3. 종료하기");
        return sc.nextLine();
    }

    public String inputNumber() {
        System.out.println("숫자를 입력해주세요:");
        return sc.nextLine();
    }

    public String selectDigit() {
        System.out.println("설정하고자 하는 자리수를 입력하세요~");
        return sc.nextLine();
    }
}
