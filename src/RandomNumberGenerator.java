import java.util.*;

public class RandomNumberGenerator {
    int digit = 3;
    int[] numberArr = new int[digit];

    public void setDigit(int digit) {
        this.digit = digit;
        this.numberArr = new int[digit];
    }

    public int getRandomNumber() {
        Set<Integer> numberSet = new HashSet<>();
        int resultNumber = 0;

        while (numberSet.size() < digit) {
            // 1 ~ 9
            int number = (int) (Math.random() * 9) + 1;
            numberSet.add(number);
        }
        List<Integer> numberList = new ArrayList<>(numberSet);
        Collections.shuffle(numberList);

        System.out.println(numberList);

        int pow = digit - 1;
        int index = 0;
        for (Integer i : numberList) {
            int num = (int) Math.pow(10, pow--) * i;
            resultNumber += num;
            numberArr[index++] = i;
        }
        return resultNumber;
    }

    public int[] getNumberArr() {
        return this.numberArr;
    }

}
