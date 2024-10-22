# 숫자 야구 게임

## 프로젝트 정보
- 스파르타 내일배움캠프 Spring 7기
- 자바 기초 과제 2
- 개발 기간: 2024.10.22 (12:00 ~ 22:00)
  
## 프로젝트 소개

- 숫자를 통해 야구를 하는 게임

![image](https://github.com/user-attachments/assets/d7da27fe-a8d5-4f92-bbb1-1059db9b22cb)

  - 0)자리수 설정: 3, 4, 5 자리수 선택 가능
    
    ![image](https://github.com/user-attachments/assets/e744425e-8a7e-4445-8092-001d4ad122e1)

  - 1)게임 시작하기: 생성된 난수를 맞추는 게임
       - 스트라이크: 몇 번째 자리수, 숫자가 동일
       - 볼: 자리수 틀림, 숫자는 동일
         
       ![image](https://github.com/user-attachments/assets/36914624-420e-4e65-b679-27b168a3c082)

  - 2)게임 기록 보기: 시도횟수를 확인 가능

    ![image](https://github.com/user-attachments/assets/a15a9722-7eeb-4810-8537-9ce68a72cfa9)

  - 3)게임 종료

    ![image](https://github.com/user-attachments/assets/211c7072-88fa-4a52-8c79-11eeb1677860)


## 트러블슈팅

<details>
  <summary><b>1) 스트라이크, 볼 판별 로직 문제</b></summary>
  
  - 1.개요
    
    - 스트라이크 볼 판별에서 Arrays.BinarySearch()를 통해 탐색을 진행

  - 2.문제 상황

    - HashSet을 통해 난수를 저정하고 값 비교를 위해 배열로 변환 (HashSet -> Array)
    - 이후 사용자가 입력한 값을 배열로 변환하고 Arrays.BinarySearch()를 통해 탐색을 진행을 하며 스트라이크 볼 판별
    - but, 난수를 저장한 HashSet->Array는 정렬되지 않음 Arrays.BinarySearch()를 쓰면 정상적으로 이진탐색하지 못함
    - 해당 수가 있음에도 내가 구현한 로직에서는 작동하지 않음 => 스트라이크, 볼 판별 불가
      
      -> 전체적인 로직을 수정해야함

    ```java
    // 난수 생성 클래스
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

    // 스트라이크 볼 카운트 클래스
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
    }
    
    ```
   
  - 3.해결

    - 스트라이크, 볼 판별 시에는 자리수, 수가 있는지 판별해야함
    - 그렇기에 인덱스와 그 수가 있는지 탐색을 해야함
    - 배열은 앞서 했던 것처럼 Arrays.BinarySearch()을 지원하지만 <b>정렬이 되어진 배열만!</b> 정상적으로 작동
    - set은 iterator가 있지만 해당 인덱스, 수가 있는지 로직을 짜면 복잡해질 것을 우려
    - set -> list 변환 후, Collections.shuffle()을 통해 섞고 indexOf() 통해 인덱스와 그 수가 있는지를 판별

    ```java
    // 난수 생성 클래스
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

  - 4.결론
    - 컬렉션 프레임워크에 대한 개념 부족
    - 메소드에 대한 개념 부족
  
       
</details>

<details>
  <summary><b>2) next(), nextLine() 스캔 문제</b></summary>

  - 1.개요
    - 사용자가 게임을 시작하고 수를 입력할 때, Whitespace와 함께 입력하면 경고가 여러번 출력
  
  - 2.문제 상황
    - next()는 공백을 기준으로 데이터를 입력 받음
    - 스페이스에 따른 입력이 많아짐 -> 그에 따른 로직도 경고가 다중으로 출력 ex.) 4 5 6

    ```java
    // inputNumber()
    public String inputNumber() {
        System.out.println("숫자를 입력해주세요:");
        return sc.next();
    }
    // 게임 로직
     private void gameStart() {
      boolean isCorrect = false;
      int answer = randomNumberGenerator.getRandomNumber();
      while (!isCorrect) {
          String stringNumber = inputRequester.inputNumber();
          if (validator.isValidNumber(stringNumber)) {
              if (validator.isAnswer(randomNumberGenerator, answer)) {
                  gameAnnouncement.printCongratulationMessage();
                  gameRecorder.saveTryCounter(validator.tryCounter);
                  isCorrect = true;
              }
          }
      }
  }
  ```
   
  - 3.해결
    - next() -> nextLine()으로 바꿔 개행문자 기준으로 입력을 받음

  - 4.결론
    - next, nextLine에 대한 개념 정립이 정확히 되지않아 이런 실수가 발생하는 것
    - 그렇기에 다중으로 입력을 원하지 않는 이상 nextLine()을 활용
      
</details>

<details>
  <summary><b>3) 사용자 입력값에 중복 숫자, 문자열 입력 시, 오류 출력 문제</b></summary>

  - 1.개요
    - 테스트케이스를 넣어보던 도중, "4456"을 입력 시, 오류가 뜨지 않음
      
  - 2.문제 상황
    - 로직의 처리가 아주 느슨함
    - 로직의 순서가 이상

    ```java
    private boolean isDuplicateNumber(String stringNumber) {
      Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));
  
      if (stringNumber.length() == 3) {          
            return numberSet.size() == 3;
      } else {
            return false;
      }
    }
    ```
    
  - 3.해결
    - 로직을 생각해서 순서를 교체
    - 이렇게 된다면 4456 입력 시에도 오류를 출력해줄 수 있을 것
   
    ```java
    private boolean isDuplicateNumber(String stringNumber) {
     
      if (stringNumber.length() == 3) {
         Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));          
            return numberSet.size() == 3;
      } else {
            return false;
      }
    }
    ```
    
  - 4.결론
    - 문제가 발생될만한 상황에 적절한 오류메시지를 출력해주는 것도 좋지만 먼저 큰 범위부터 좁혀나가면서 출력해줄 것
    
</details>

