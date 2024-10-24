import java.util.ArrayList;

public class GameRecorder {
    // 순서가 중요, 어레이리스트 활용,
    ArrayList<Integer> record = new ArrayList<>();

    // 어레이리스트에 시도횟수를 넣어줌 /1111111
    public void saveTryCounter(int tryCounter) {
        record.add(tryCounter);
    }

    // 기록들을 프린트해줌
    public void printRecord() {
        int index = 1;
        for (Integer i : record) {
            System.out.printf("%d번째 게임 : 시도 횟수 - %d\n", index++, i);
        }
        System.out.println();
    }
}
