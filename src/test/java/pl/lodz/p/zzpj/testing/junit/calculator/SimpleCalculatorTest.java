package pl.lodz.p.zzpj.testing.junit.calculator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class SimpleCalculatorTest {

    SimpleCalculator simpleCalculator;


    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void setUp() {
        simpleCalculator = new SimpleCalculator();
    }


    @Test
    public void shouldAddTwoNumbers() {
        assertEquals(12, simpleCalculator.add(5, 7), 0);
    }

    @Test
    @Parameters({"3", "7", "73"})
    public void shouldCheckIfNumberIsPrime(int prime) {
        assertTrue(simpleCalculator.isPrime(prime));
    }

    @Test
    @Parameters(method = "getTestSet")
    public void shouldCheckIfNumberIsPrimeOrNot(int prime, boolean resultFlag) {
        assertEquals(resultFlag, simpleCalculator.isPrime(prime));
    }

    public Object[] getTestSet() {
        return new Object[]{
                new Object[]{3, true},
                new Object[]{5, true},
                new Object[]{21, false}
        };
    }

    @Test(expected = CannotDivideByZeroException.class)
    public void shouldThrowAnException() throws CannotDivideByZeroException {
        simpleCalculator.divide(2, 0);
    }

    @Test
    public void shouldThrowAnExceptionSecond() {
        assertThatExceptionOfType(CannotDivideByZeroException.class)
                .isThrownBy(() -> simpleCalculator.divide(4, 0)).withMessage("Can't by zero!");

    }

    @Test
    public void shouldThrowAnExceptionThird() {
        assertThatThrownBy(() -> simpleCalculator.divide(4, 0))
                .isInstanceOf(CannotDivideByZeroException.class)
                .hasMessageContaining("Can't by zero!");
    }
}