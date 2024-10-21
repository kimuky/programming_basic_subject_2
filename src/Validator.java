import java.sql.Array;
import java.util.*;

public class Validator {
    int validNumber;
    int tryCounter= 0;

    public boolean isAnswer(RandomNumberGenerator randomNumberGenerator , int answer) {
        if (answer == validNumber) {
            return true;
        } else {
            int[] answerArr = randomNumberGenerator.getNumberArr();
            int[] validNumberArr = getValidNumberArr(answerArr.length);
            tryCounter+=1;
            countStrikeBall(answerArr, validNumberArr);

            return false;
        }
    }

    public void countStrikeBall(int[] answerArr, int[] validNumberArr) {
        int strikeCounter = 0;
        int ballCounter = 0;
        for (int i = 0; i < answerArr.length; i++) {
            int arrIndex = Arrays.binarySearch(answerArr, validNumberArr[i]);
            if (arrIndex >= 0) {
                if (arrIndex == i) {
                    strikeCounter += 1;
                } else {
                    ballCounter += 1;
                }
            }
        }
        String temp = "";
        if (strikeCounter >0) {
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

    public int[] getValidNumberArr(int answerLength) {
        int pow = 2;
        int index = 0;
        int validNumberCopy = validNumber;
        int[] validNumberArr = new int[answerLength];

        while (validNumberCopy > 0) {
            int division = (int) (Math.pow(10, pow--));
            int number = validNumberCopy / division;
            validNumberCopy %= division;
            validNumberArr[index++] = number;
        }
        return validNumberArr;
    }

    public boolean optionValidator(String option) {
        return switch (option) {
            case "1", "2", "3" -> true;
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
            System.out.println("숫자를 잘못 입력하셨습니다. 서로 다른 숫자 3개가 아닙니다.");
            return false;
        }
    }

    private boolean isDuplicateNumber(String stringNumber) {
        if (stringNumber.length() == 3) {
            Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));
            return numberSet.size() == 3;
        } else {
            return false;
        }
    }
}
