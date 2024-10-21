import java.util.Scanner;

public class InputRequester {

    Scanner sc = new Scanner(System.in);

    public String selectGameOption  () {
        System.out.println("환영합니다~ 원하시는 번호를 입력해주세요.");
        System.out.println("1. 게임 시작하기 2. 게임 기록 보기 3. 종료하기");
        return sc.next();
    }

    public String inputNumber() {
        System.out.println("숫자를 입력해주세요:");
        return sc.next();
    }
}
