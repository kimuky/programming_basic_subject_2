import java.util.*;

public class Validator {
    int validNumber;
    int tryCounter = 0;
    int digit = 3;

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public void resetTryCounter() {
        this.tryCounter = 0;
    }
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



    public boolean optionValidator(String option) {
        return switch (option) {
            case "0", "1", "2", "3" -> true;
            default -> false;
        };
    }

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

    private boolean isDuplicateNumber(String stringNumber) {
        if (stringNumber.length() == digit) {
            Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));
            return numberSet.size() == digit;
        } else {
            return false;
        }
    }

    public boolean isValidDigit(String digit) {
        return switch (digit) {
            case "3", "4", "5" -> true;
            default -> false;
        };
    }
}
