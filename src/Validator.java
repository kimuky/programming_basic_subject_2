import java.util.*;

public class Validator {
    // 전역 변수를 통해 값을 반환해주고
    // 기본값을 지정
    // validNumber 는 빼주어야 할 것 (피드백 중 하나 상태를 가지면 X -> 이 클래스는 판별해주는 클래스)
    int validNumber;
    int tryCounter = 0;
    int digit = 3;

    // digit Setter
    public void setDigit(int digit) {
        this.digit = digit;
    }

    // 시도횟수 초기화
    public void resetTryCounter() {
        this.tryCounter = 0;
    }

    // 정답인지를 판별해주는 클래스
    // 이 함수가 호출될때마다 시도횟수가 증가
    // 틀리면 입력값이 문제없는지 확인하고 printCountStrikeAndBall() 호출 -> 좋지 않은 구조?
    public boolean isAnswer(int[] answerArr, int answer) {
        tryCounter += 1;
        if (answer == validNumber) {
            return true;
        } else {
            int[] validNumberArr = getValidNumberArr(answerArr.length);
            printCountStrikeAndBall(answerArr, validNumberArr);
            return false;
        }
    }

    // 문제 없는 숫자에 대해 배열로 변환 -> 비교해주기 위함
    public int[] getValidNumberArr(int answerLength) {
        int index = answerLength-1;
        int validNumberCopy = validNumber;
        int[] validNumberArr = new int[answerLength];

        while (validNumberCopy > 0) {
            int num = validNumberCopy%10;
            validNumberArr[index--] = num;
            validNumberCopy/=10;
        }
        return validNumberArr;
    }

    // 스트라이크 볼을 판별하고 출력해주는 함수
    public void printCountStrikeAndBall(int[] answerArr, int[] validNumberArr) {
        int strikeCounter = 0;
        int ballCounter = 0;
        List<Integer> integerList = Arrays.stream(answerArr)
                .boxed()
                .toList();

        for (int i = 0; i < answerArr.length; i++) {
            int arrIndex = integerList.indexOf(validNumberArr[i]);
            if (arrIndex >= 0) {
                if (arrIndex == i) {
                    strikeCounter += 1;
                } else {
                    ballCounter += 1;
                }
            }
        }

        // 사용자에게 스트라이크, 볼이 몇개인지 알려줌
        // 스트라이크, 볼이 없으면 아웃을 출력
        String temp = "";
        if (strikeCounter > 0) {
            temp += strikeCounter + "스트라이크 ";
        }
        if (ballCounter > 0) {
            temp += ballCounter + "볼";
        }
        if (strikeCounter == 0 && ballCounter == 0) {
            temp = "아웃";
        }
        System.out.println(temp);
    }

    // 숫자가 문제없는지 확인
    // 추가적으로 isDuplicateNumber()을 두어 오류를 구분해주기 위함
    public boolean isValidNumber(String stringNumber) {
        if (isDuplicateNumber(stringNumber)) {
            try {
                validNumber = Integer.parseInt(stringNumber);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력해주세요!");
                return false;
            }
        } else {
            System.out.printf("숫자를 잘못 입력하셨습니다. 서로 다른 숫자 %d개가 아닙니다.\n", digit);
            return false;
        }
    }

    // 중복된 숫자를 판별
    // 4456, "abd" 를 판별해주기 위함
    private boolean isDuplicateNumber(String stringNumber) {
        if (stringNumber.length() == digit) {
            Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));
            return numberSet.size() == digit;
        } else {
            return false;
        }
    }

    // 3,4,5만을 화이트리스트로 처리
    public boolean isDigitValid(String digit) {
        return switch (digit) {
            case "3", "4", "5" -> true;
            default -> false;
        };
    }
}
