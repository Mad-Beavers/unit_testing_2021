package pl.lodz.p.zzpj.testing.junit.junit5;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.lodz.p.zzpj.testing.junit.tdd.FizzBuzz;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {


    FizzBuzz fizzBuzz;

    @BeforeEach
    public void before() {
        fizzBuzz = new FizzBuzz();
    }

    @AfterEach
    public void after() {
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void getFizzBuzzNumberMethodSourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @ParameterizedTest
    @ValueSource(ints = {30, 5, 9})
    public void getFizzBuzzNumberValueSourceTest(int a) {
        String correspondingString; // creating variable for correspondingString
        // as ValueSource cannot feed 2 arguments at the same time

        switch (a) {
            case 30:
                correspondingString = "FizzBuzz";
                break;
            case 5:
                correspondingString = "Buzz";
                break;
            case 9:
                correspondingString = "Fizz";
                break;
            default:
                correspondingString = "";
        }

        assertEquals(fizzBuzz.getFizzBuzzNumber(a), correspondingString);
    }

    @ParameterizedTest
    @CsvSource({
            "30, FizzBuzz",
            "5, Buzz",
            "9, Fizz",
    })
    public void getFizzBuzzNumberCSV_SourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "FizzBuzz.csv")
    public void getFizzBuzzNumberCSV_FileSourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(9, "Fizz")
        );
    }

}
