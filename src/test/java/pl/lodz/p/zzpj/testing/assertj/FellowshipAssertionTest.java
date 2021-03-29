package pl.lodz.p.zzpj.testing.assertj;

import org.assertj.core.groups.Tuple;
import org.assertj.core.util.Streams;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.lodz.p.zzpj.testing.assertj.FellowTestFixture.frodo;

public class FellowshipAssertionTest {

    private final Fellowship fellowship = formTheFellowshipOfTheRing();

    @Test
    public void frodoShouldBeIn() {
        assertThat(fellowship.hobbits().stream()
                .anyMatch(hobbit -> hobbit.getName().equalsIgnoreCase("Frodo"))).isTrue();
    }

    @Test
    public void sauronShouldNotBeIn() {
        assertThat(Streams.stream(fellowship.iterator())
                .noneMatch(fellow -> fellow.getName().equalsIgnoreCase("Sauron"))).isTrue();
    }

    @Test
    public void shouldFrodoNameBeCorrectAndNotBeASauronAndBeInFellowship() {
        Fellow frodo = frodo();
        assertThat(frodo.getName().equals("Frodo")
                && Streams.stream(fellowship.iterator())
                .anyMatch(hobbit -> hobbit.equals(frodo))).isTrue();
    }

    @Test
    public void aragornShouldBeBeforeBoromir() {
        List<String> names = Arrays.asList("Aragorn", "Boromir");

        Iterator<String> names_it = names.iterator();

        assertThat(Streams.stream(fellowship.iterator())
                .allMatch(
                        fellow -> !names.contains(fellow.getName())
                                || fellow.getName().equals(names_it.next())
                )
        ).isTrue();
    }

    @Test
    public void shouldNotContainDuplicatedFellows() {
        long allFellowsCount = Streams.stream(fellowship.iterator()).count();
        long distinctFellowsCount = Streams.stream(fellowship.iterator()).distinct().count();

        assertThat(allFellowsCount).isEqualTo(distinctFellowsCount);
    }

    @Test
    public void shouldContainOnlyFellowsWithExpectedNamesInProperOrder() {

        List<String> expectedNamesList = Arrays.asList("Frodo", "Sam", "Merry", "Pippin", "Gandalf", "Legolas", "Gimli", "Aragorn", "Boromir");

        Iterator<String> expectedNamesIt = expectedNamesList.iterator();
        assertThat(Streams.stream(fellowship.iterator())
                .count() == expectedNamesList.size() &&
                Streams.stream(fellowship.iterator())
                        .allMatch(
                                fellow -> fellow.getName().equals(expectedNamesIt.next())
                        )
        ).isTrue();
    }

    @Test
    public void shouldContainNineFellowsButNoneGiant() {
        assertThat(Streams.stream(fellowship.iterator())).noneMatch(fellow -> fellow.getRace().equals(Fellow.Race.GIANT));
        assertThat(Streams.stream(fellowship.iterator())).hasSize(9);
    }

    @Test
    public void shouldContainTupleForBoromirSamAndLegolas() {
//         Extracting multiple values at once (using tuple to group them)
//         Create tuples with name and race name
        List<Tuple> expectedTuples = new ArrayList<Tuple>() {
            {
                add(new Tuple(Fellow.Race.MAN, "Boromir"));
                add(new Tuple(Fellow.Race.HOBBIT, "Sam"));
                add(new Tuple(Fellow.Race.ELF, "Legolas"));
            }
        };

        assertThat(fellowship).extracting(Fellow::getRace, Fellow::getName).containsAll(expectedTuples);
    }


    @Test
    public void shouldContainsFourHobbits() {
        assertThat(Streams.stream(fellowship.iterator())
                .filter(fellow -> fellow.getRace() == Fellow.Race.HOBBIT))
                .hasSize(4);
    }


    private Fellowship formTheFellowshipOfTheRing() {
        return new Fellowship(
                frodo(),
                FellowTestFixture.sam(),
                FellowTestFixture.merry(),
                FellowTestFixture.pippin(),
                FellowTestFixture.gandalf(),
                FellowTestFixture.legolas(),
                FellowTestFixture.gimli(),
                FellowTestFixture.aragorn(),
                FellowTestFixture.boromir());
    }
}
