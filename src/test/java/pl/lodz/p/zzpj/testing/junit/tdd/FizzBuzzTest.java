package pl.lodz.p.zzpj.testing.junit.tdd;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class FizzBuzzTest {

    FizzBuzz fizzBuzz;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    @Parameters({"30, FizzBuzz", "5, Buzz", "9, Fizz"})
    public void getFizzBuzzNumberTest(int a, String name) {
        Assert.assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    @Test
    @Parameters(method = "parameters")
    public void getFizzBuzzNumberSecondTest(int a, String name) {
        Assert.assertEquals(fizzBuzz.getFizzBuzzNumber(a), name);
    }

    private Object[] parameters() {
        return new Object[]{
                new Object[]{30, "FizzBuzz"},
                new Object[]{5, "Buzz"},
                new Object[]{9, "Fizz"}
        };
    }
}
