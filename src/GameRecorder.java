import java.util.ArrayList;

public class GameRecorder {

    ArrayList <Integer> record = new ArrayList<>();

    public void saveTryCounter (int tryCounter) {
        record.add(tryCounter);
    }

    public void printRecord () {
        int index = 1;
        for (Integer i : record) {
            System.out.printf("%d번째 게임 : 시도 횟수 - %d\n", index++, i);
        }
        System.out.println();
    }
}
