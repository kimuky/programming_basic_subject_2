import java.util.*;

public class RandomNumberGenerator {
    int digit = 3;
    int[] numberArr = new int[digit];

    // digit Setter
    public void setDigit(int digit) {
        this.digit = digit;
        this.numberArr = new int[digit];
    }

    // 난수 생성기
    public int getRandomNumber() {
        Set<Integer> numberSet = new HashSet<>();
        int resultNumber = 0;

        while (numberSet.size() < digit) {
            // 1 ~ 9
            int number = (int) (Math.random() * 9) + 1;
            numberSet.add(number);
        }
        List<Integer> numberList = new ArrayList<>(numberSet);

        // 굳이 랜덤으로 섞어줬는데 shuffle 까지 해주는 건 불필요하다고 생각함
        Collections.shuffle(numberList);

        // 정답 출력
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

    // 수정 필요해보입니다.
    // 전역변수로 난수 배열을 받아오는 것
    public int[] getNumberArr() {
        return this.numberArr;
    }

}
