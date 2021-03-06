package pl.lodz.p.zzpj.testing.junit.tdd;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JUnitParamsRunner.class)
public class DivisibilityTest {

    Divisibility divisibility;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        divisibility = new Divisibility();
    }

    @Test(expected = CantByZeroException.class)
    public void throwException() throws CantByZeroException {
        divisibility.isDivisible(10, 0);
    }

    @Test
    @Parameters({"8, 2", "10, 2"})
    public void isDivisibleTest(int a, int b) throws CantByZeroException {
        Assert.assertTrue(divisibility.isDivisible(a, b));
    }

    @Test
    @Parameters(method = "parameters")
    public void isDivisibleSecondTest(int a, int b) throws CantByZeroException {
        Assert.assertTrue(divisibility.isDivisible(a, b));
    }

    private Object[] parameters() {
        return new Object[]{
                new Object[]{8, 2},
                new Object[]{10, 2}};
    }
}
