import java.util.HashSet;
import java.util.Set;

public class RandomNumberGenerator {
    int[] numberArr = new int[3];

    public int getRandomNumber () {
        Set<Integer> numberSet = new HashSet<>();
        int resultNumber = 0;

        while (numberSet.size()<3) {
            // 1 ~ 9
            int number = (int)(Math.random()*8)+1;
            numberSet.add(number);
        }

        System.out.println(numberSet);

        int pow = 2;
        int index =0;
        for (Integer i : numberSet) {
            int num = (int)Math.pow(10, pow--)*i;
            resultNumber += num;
            numberArr[index++] = i;
        }
        return resultNumber;
    }


    public int[] getNumberArr() {
        return this.numberArr;
    }

}
