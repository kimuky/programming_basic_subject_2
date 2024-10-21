public class BaseballGame {

    InputRequester inputRequester;
    GameAnnouncement gameAnnouncement;
    RandomNumberGenerator randomNumberGenerator;
    Validator validator;
    GameRecorder gameRecorder;
    boolean isRunning = true;

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
            String selectedOption = inputRequester.selectGameOption();

            while (!isValidOption) {
                isValidOption = validator.optionValidator(selectedOption);
                handleOption(selectedOption);
            }
        }
    }

    public void handleOption(String selectedOption) {
        switch (selectedOption) {
            case "0":
                setDigit();
                break;
            case "1":
                gameAnnouncement.printGameStartMessage();
                gameStart();
                break;
            case "2":
                gameAnnouncement.printRecordMessage();
                showGameRecord();
                break;
            case "3":
                gameAnnouncement.printGoodbyeMessage();
                isRunning = false;
        }
    }

    private void setDigit() {
        boolean isSetDigit = false;
        while (!isSetDigit) {
            String digit = inputRequester.selectDigit();
            if (validator.isValidDigit(digit)) {
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
        int answer = randomNumberGenerator.getRandomNumber();
        int[] answerArr = randomNumberGenerator.getNumberArr();

        while (!isCorrect) {
            String stringNumber = inputRequester.inputNumber();
            if (validator.isValidNumber(stringNumber)) {
                if (validator.isAnswer(answerArr, answer)) {
                    gameAnnouncement.printCongratulationMessage();
                    gameRecorder.saveTryCounter(validator.tryCounter);
                    validator.resetTryCounter();
                    isCorrect = true;
                }
            }
        }
    }

    private void showGameRecord() {
        gameRecorder.printRecord();
    }
}
