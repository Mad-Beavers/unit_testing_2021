package pl.lodz.p.zzpj.testing.junit.junit5;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.lodz.p.zzpj.testing.junit.tdd.FizzBuzz;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
    @Tag("FizzBuzz")
    @MethodSource("parameters")
    public void getFizzBuzzNumberMethodSourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @ParameterizedTest
    @Tag("FizzBuzz")
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
    @Tag("FizzBuzz")
    @CsvSource({
            "30, FizzBuzz",
            "5, Buzz",
            "9, Fizz",
    })
    public void getFizzBuzzNumberCSV_SourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @ParameterizedTest
    @Tag("FizzBuzz")
    @CsvFileSource(resources = "FizzBuzz.csv")
    public void getFizzBuzzNumberCSV_FileSourceTest(int a, String name) {
        assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @ParameterizedTest
    @CsvSource({
            "12, 14, 15",
            "15, 22, 12",
            "9, 84, 125"
    })
    public void assertAllTest(int a, int b, int c) {
        //fails on purpose, to demonstrate MultipleFailuresError achievable by assertAll
        assertAll("Should all give 0:",
                () -> assertEquals(0, a % 3),
                () -> assertEquals(0, b % 7),
                () -> assertEquals(0, c % 5)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"+42 123 123 713", "+91 755 5331 418", "+42 184 123 322"})
    public void phoneNumberTest(String phoneNumber) {
        Assumptions.assumeTrue(phoneNumber.contains("+42")); //only checking polish numbers
        assertTrue(phoneNumber.contains("123"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12", "jajeczko", "30", "dwa jajeczka"})
    public void isEven(String number) {
        Assumptions.assumingThat(number.matches("\\d+"), () ->
                assertEquals(0, Integer.parseInt(number) % 2));
        //checking for even only if string matches integer number pattern
    }

    @Nested
    class VeryUsefulTestClass {
        @BeforeEach
        void before() {
            System.out.println("Performing another useful test!!!");
        }

        @AfterEach
        void after() {
            System.out.println("The test finished!!!");
        }

        @Test
        @DisplayName("Testing if zero division throws ArithmeticException")
        public void zeroDivisionTest() {
            assertThrows(ArithmeticException.class, () -> {
                int x = 15 / 0;
            });
        }

        @Test
        public void timeoutTest() {
            assertTimeout(Duration.ofMillis(500), () -> Thread.sleep(300));
        }
    }

    @Test
    @Disabled
    public void pleaseDontRunMe() {
        throw new RuntimeException("Y are u doing this? :(");
    }

    @ParameterizedTest
    @EnabledForJreRange(max = JRE.JAVA_13) // Use new switch syntax if you're using JAVA >= 14 !!
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    public void oldSwitchTest(int a) {
        String mysteriousString;
        switch (a) {
            case 1:
                mysteriousString = ":)";
                break;
            case 2:
                mysteriousString = ":o";
                break;
            case 3:
                mysteriousString = ":P";
                break;
            case 4:
            case 5:
                mysteriousString = "xD";
                break;
            default:
                mysteriousString = ":(";
                break;
        }
        System.out.println(mysteriousString);
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    public void linuxTest() {
        System.out.println("Oh wow, you're on Linux, I guess you're good at hacking, aren't you?");
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(9, "Fizz")
        );
    }
}

