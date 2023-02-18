package racingcar.domain;

import racingcar.domain.factory.CarFactory;

import java.util.List;

public class RacingCarGame {
    private static final int START_POSITION = 0;
    
    private final CarRepository carRepository;
    
    public RacingCarGame(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    public void addCars(String carNames) {
        carRepository.updateCars(CarFactory.from(carNames));
    }

    public void race(Movement movement) {
        for (Car car : carRepository.findAll()) {
            car.move(movement);
        }
    }
    
    public List<Car> findAllCar() {
        return carRepository.findAll();
    }
    
    public List<String> getWinners() {
        return carRepository.findMaxPosition(getMaxPosition());
    }
    
    private int getMaxPosition() {
        return carRepository.findAll().stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElse(START_POSITION);
    }
}
