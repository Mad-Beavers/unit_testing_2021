package pl.lodz.p.zzpj.testing.junit.calculator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class SimpleCalculatorTest {

    SimpleCalculator sut;


    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void setUp() {
        sut = new SimpleCalculator();
    }


    @Test
    public void shouldAddTwoNumbers() {
        double result = sut.add(1, 5);
        assertEquals(6.0, result, 0);
    }

    @Test
    @Parameters({"3", "7", "73"})
    public void shouldCheckIfNumberIsPrime(int prime) {
        boolean isNumberPrime = sut.isPrime(prime);
        assertEquals(true, isNumberPrime);
    }

    @Test
    @Parameters(method = "getTestSet")
    public void shouldCheckIfNumberIsPrimeOrNot(int prime, boolean resultFlag) {
        assertEquals(resultFlag, sut.isPrime(prime));
    }

    public Object[] getTestSet() {
        return $(
                $(3, true),
                $(5, true),
                $(21, false)
        );
    }

    @Test(expected = CannotDivideByZeroException.class)
    public void shouldThrowAnException() throws CannotDivideByZeroException {
        sut.divide(2, 0);
    }

    @Test
    public void shouldThrowAnExceptionSecond() {
        assertThatExceptionOfType(CannotDivideByZeroException.class)
                .isThrownBy(() -> {
                    sut.divide(4, 0);
                }).withMessage("Can't by zero!");
    }

    @Test
    public void shouldThrowAnExceptionThird() {
        assertThatThrownBy(() -> {
            sut.divide(4, 0);
        }).isInstanceOf(CannotDivideByZeroException.class)
                .hasMessageContaining("Can't by zero!");
    }
}