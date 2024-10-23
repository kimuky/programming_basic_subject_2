public class BaseballGame {

    // 필드
    InputRequester inputRequester; // InputRequester -> 사용자의 값을 리턴해주는 클래스
    GameAnnouncement gameAnnouncement; // GameAnnouncement -> 사용자에게 안내메시지를 출력해주는 클래스
    RandomNumberGenerator randomNumberGenerator; // RandomNumberGenerator -> 난수 생성을 해주는 클래스
    Validator validator; // Validator -> 메뉴 선택, 정수 값을 판변해주고, 스트라이크 볼을 세어주는 클래스
    GameRecorder gameRecorder; // GameRecorder -> 시도횟수를 저장하고, 출력해주는 클래스
    boolean isRunning = true; // isRunning -> 실제 메인이 돌아가게 해주는 불리언 타입 true -> play() , false -> 종료

    //생성자
    public BaseballGame(InputRequester inputRequester, GameAnnouncement gameAnnouncement, RandomNumberGenerator randomNumberGenerator, Validator validator, GameRecorder gameRecorder) {
        this.inputRequester = inputRequester;
        this.gameAnnouncement = gameAnnouncement;
        this.randomNumberGenerator = randomNumberGenerator;
        this.validator = validator;
        this.gameRecorder = gameRecorder;
    }

    public static void main(String[] args) {
        BaseballGame baseballGame = new BaseballGame(new InputRequester(), new GameAnnouncement(), new RandomNumberGenerator(), new Validator(), new GameRecorder());
        baseballGame.play();
    }

    public void play() {
        while (isRunning) {
            boolean isValidOption = false;
            gameAnnouncement.printWelcomeMessage();

            // 0, 1, 2, 3 중 유요하지 않은 값들을 입력했을 때, 무한 루프
            while (!isValidOption) {
                String selectedOption = inputRequester.selectGameOption();
                isValidOption = handleOption(selectedOption);
            }
        }
    }

    public boolean handleOption(String selectedOption) {
        switch (selectedOption) {
            case "0":
                // 자릿수 설정
                setDigit();
                return true;
            case "1":
                // 스타트 메시지와 함께 게임 시작
                gameAnnouncement.printGameStartMessage();
                gameStart();
                return true;
            case "2":
                // 리코드메시지와 함께 리코드 출력
                gameAnnouncement.printRecordMessage();
                showGameRecord();
                return true;
            case "3":
                // 리코드 메시지와 함께 메인에서 동작하는 isRunning 을 false
                gameAnnouncement.printGoodbyeMessage();
                isRunning = false;
                return true;
            default:
                // 잘못된 입력을 한경우 오류 메시지 출력
                gameAnnouncement.printWrongOptionMessage();
                return false;
        }
    }

    private void setDigit() {
        boolean isSetDigit = false;
        while (!isSetDigit) {
            String digit = inputRequester.selectDigit();

            // digit 3, 4, 5중 입력하지 않으면 오류메시지를 출력하고 무한루프
            if (validator.isDigitValid(digit)) {
                /*
                - validator, randomNumberGenerator 을 setter 를 통해 지정?
                - 국이 이렇게 해야할까? 다른 구조를 가지는게 좋지 않을까 생각합니다.
                - validator 는 판별해주는 클래스이지 상태를 가지면 안된다고는 생각합니다. (튜터님 피드백 중 하나)
                - randomNumberGenerator 를 할 때마다 새로운 객체를 만들어서 옮겨주면 되지 싶은데....
                - 저렇게  setter 를 통해 지정해주는 것은 좋지 않은 구조로 보여집니다.
                -> 당장은 저렇게 설계를 해뒀지만 수정해보겠습니다. 현재 24.10.23.11:00
                */
                validator.setDigit(Integer.parseInt(digit));
                randomNumberGenerator.setDigit(Integer.parseInt(digit));
                isSetDigit = true;
            } else {
                gameAnnouncement.printWrongDigitMessage();
            }

        }
    }

    private void gameStart() {
        boolean isCorrect = false;
        // 정답은 난수생성기를 통해 정수형을 반환 받음
        // 스트라이크, 볼 판별을 위해 배열도 반환 받음
        // 굳이 메소드를 두개 만들지 말고 dto 등을 만들어서 반환해주는게 좋은 구조라고 생각합니다.
        // 이것 때문에 randomNumberGenerator 에서 numberArr 도 전역변수로 되어 있기에... 수정 필요
        int answer = randomNumberGenerator.getRandomNumber();
        int[] answerArr = randomNumberGenerator.getNumberArr();

        // isCorrect 정답일 때까지 false
        while (!isCorrect) {
            String stringNumber = inputRequester.inputNumber();
            // 1번재 if: 이게 올바른 숫자인가 판별
            // isValidNumber : 중복 숫자 판별, 문자형인지 판별
            if (validator.isValidNumber(stringNumber)) {
                // 2번째 if : 이게 정답인가?
                // isAnswer : 조건문이 실행될때마다 시도 횟수가 증가하며, 틀리면 printStrikeAndBall() 을 해줌
                // 안에서 프린트해주는 구조 좋지 않아 보임..
                // 수정할려면 전체적으로 수정해주어야 된다고 생각하기에 조금만 더 고민해보겠습니다.
                if (validator.isAnswer(answerArr, answer)) {
                    // 축하메시지
                    gameAnnouncement.printCongratulationMessage();
                    // 시도횟수 저장하고 리셋
                    gameRecorder.saveTryCounter(validator.tryCounter);
                    validator.resetTryCounter();
                    // true로 되며 무한루프를 벗어나고 gameStart()는 종료
                    isCorrect = true;
                }
            }
        }
    }

    private void showGameRecord() {
        // 시도횟수를 출력
        gameRecorder.printRecord();
    }
}
