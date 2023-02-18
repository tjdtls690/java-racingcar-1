package racingcar.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CarRepositoryTest {
    private CarRepository carRepository;
    
    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
        Car pobi = new Car("pobi");
        Car mery = new Car("mery");
        Car abel = new Car("abel");
        
        moveByCount(pobi, 2);
        moveByCount(mery, 3);
        moveByCount(abel, 2);
        
        List<Car> cars = List.of(pobi, mery, abel);
        carRepository.updateCars(cars);
    }
    
    private void moveByCount(Car car, int moveOfTry) {
        IntStream.range(0, moveOfTry)
                .forEach(count -> car.move(() -> true));
    }

    @Test
    @DisplayName("레포지토리에 정상적으로 모든 자동차가 저장되었는지 확인한다.")
    void findAll() {
        List<String> carNames = carRepository.findAll()
                .stream()
                .map(Car::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(carNames).isEqualTo(List.of("pobi", "mery", "abel"));
    }

    @Test
    @DisplayName("최대 포지션에 위치한 자동차를 반환하는지 확인한다.")
    void findMaxPosition() {
        assertThat(carRepository.findMaxPosition(3)).isEqualTo(List.of("mery"));
    }

    @ParameterizedTest(name = "cars : {0}")
    @DisplayName("레포지토리에 저장할 자동차 리스트가 비어있거나 공백인 경우 예외를 발생시키는지 확인한다.")
    @MethodSource("provideCars")
    void numberOfCarsException(List<Car> cars) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> carRepository.updateCars(cars));
    }

    private static Stream<Arguments> provideCars() {
        return Stream.of(
                Arguments.of(Collections.emptyList()),
                Arguments.of(List.of(new Car("pobi")))
        );
    }
    
    @Test
    @DisplayName("중복되는 차 이름이 있을 때 예외를 발생시킨다.")
    void validateDuplicatedCarNames() {
        List<Car> cars = List.of(new Car("aa"), new Car("aa"));
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> carRepository.updateCars(cars));
    }
    
    @Test
    @DisplayName("입력한 차가 한 대인 경우 예외를 발생시킨다.")
    void validateLessThanMinimumNumberOfCars() {
        List<Car> cars = List.of(new Car("aa"));
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> carRepository.updateCars(cars));
    }

    @AfterEach
    void clear() {
        carRepository.clear();
    }
}