package racingcar.controller;

import racingcar.domain.CarRepository;
import racingcar.domain.RacingCarGame;
import racingcar.domain.RandomBasedMovement;
import racingcar.validator.NumberOfTryValidator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class GameController {
    private final RacingCarGame racingCarGame;
    private final InputView inputView;
    private final OutputView outputView;
    
    public GameController(InputView inputView, OutputView outputView, RacingCarGame racingCarGame) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.racingCarGame = racingCarGame;
    }

    public void run() {
        racingCarGame.addCars(readCarNames());
        runRacingGame(getParsedTryNumber());
        outputView.printWinners(racingCarGame.getWinners());
    }
    
    private String readCarNames() {
        return inputView.readCorrectLine(outputView::printCarNamesInputGuide);
    }
    
    private int getParsedTryNumber() {
        String tryNumber = readTryNumber();
        NumberOfTryValidator.validate(tryNumber);
        return Integer.parseInt(tryNumber);
    }
    
    private String readTryNumber() {
        return inputView.readCorrectLine(outputView::printTryNumberInputGuide);
    }

    private void runRacingGame(int tryNumber) {
        while (tryNumber-- > 0) {
            racingCarGame.race(new RandomBasedMovement());
            outputView.printCarsStatus(racingCarGame.findAllCar());
        }
    }
}
