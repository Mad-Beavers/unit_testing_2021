package pl.lodz.p.zzpj.testing.junit.stack;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class StackExerciseTest {
    StackExercise stackExercise;

    @BeforeClass
    public static void beforeClass() {

    }

    @Before
    public void before() {
        stackExercise = new StackExercise();
    }

    @Test(expected = StackEmptyException.class)
    public void popEmptyStack() throws StackEmptyException {
        stackExercise.pop();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getTopFromEmptyStack() throws StackEmptyException {

        exception.expect(StackEmptyException.class);
        stackExercise.pop();
    }

    @Test
    public void pop() throws StackEmptyException {
        stackExercise.push("First");
        Assert.assertEquals(stackExercise.top(), "First");
    }

    @Test()
    public void push() throws StackEmptyException {
        stackExercise.push("First");
        stackExercise.push("Second");
        stackExercise.pop();
        Assert.assertEquals(stackExercise.top(), "First");

    }

    @Test()
    public void isEmpty() {
        Assert.assertTrue(stackExercise.isEmpty());
    }


    @Test
    @Parameters({"First, First", "Second, Second"})
    public void popTest(String first, String second) throws StackEmptyException {
        stackExercise.push(first);
        Assert.assertEquals(stackExercise.top(), second);
    }

    @Test
    @Parameters(method = "parameters")
    public void popTestSecond(String first, String second) throws StackEmptyException {
        stackExercise.push(first);
        Assert.assertEquals(stackExercise.top(), second);
    }

    private Object[] parameters() {
        return new Object[]{
                new Object[]{"First", "First"},
                new Object[]{"Second", "Second"}
        };
    }

}
