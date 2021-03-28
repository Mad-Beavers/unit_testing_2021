package pl.lodz.p.zzpj.testing.assertj;

import org.apache.maven.surefire.shared.lang3.tuple.Triple;
import org.assertj.core.groups.Tuple;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.lodz.p.zzpj.testing.assertj.FellowTestFixture.frodo;
import static pl.lodz.p.zzpj.testing.assertj.FellowTestFixture.sauron;

public class FellowshipAssertionTest {

    private Fellowship fellowship = formTheFellowshipOfTheRing();

    @Test
    public void frodoShouldBeIn() {
        assertThat(fellowship.toString().contains("Frodo"));
    }

    @Test
    public void sauronShouldNotBeIn() {
        assertThat(!fellowship.toString().contains("Sauron"));
    }

    @Test
    public void shouldFrodoNameBeCorrectAndNotBeASauronAndBeInFellowship() {
        assertThat(fellowship.toString().contains("Frodo") &&
                !fellowship.toString().contains("Sauron"));
    }

    @Test
    public void aragornShouldBeBeforeBoromir() {
        String[] list = fellowship.toString().split(",");
        int aragornNum = 0, boromirNum = 0;
        for (int i = 0; i < list.length; i++) {
            if (list[i] == "Aragorn") {
                aragornNum = i;
            }
            if (list[i] == "Boromir") {
                boromirNum = i;
            }
        }
        assertThat((aragornNum < boromirNum));
    }

    @Test
    public void shouldNotContainDuplicatedFellows() {
        List<String> list = new ArrayList<>();
        Iterator<Fellow> iterator = fellowship.iterator();
        while(iterator.hasNext()) {
           list.add(iterator.next().getName());
       }
        boolean containsDuplicatedFellows = false;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j < list.size() ; j++) {
                if(list.get(i).equals(list.get(j))) {
                    containsDuplicatedFellows = true;
                }
            }
        }
        assertThat(containsDuplicatedFellows == false);
    }

    @Test
    public void shouldContainOnlyFellowsWithExpectedNamesInProperOrder() {
        List<String> list = new ArrayList<>();
        boolean[] nations = {false, false, false, false ,false};
        boolean result = true;
        Iterator<Fellow> iterator = fellowship.iterator();
        while(iterator.hasNext()) {
            String nation = iterator.next().getRace().toString();
            if(nation == "HOBBIT"){
                if(nations[0] || nations[1] || nations[2] || nations[3]) {
                    result = false;
                }
            } else if(nation == "MAIAR") {
                if(nations[0] || nations[1] || nations[2] || nations[3]) {
                    result = false;
                }
                nations = changeBoolean(1, nations);
            } else if(nation == "ELF") {
                if(nations[0] || nations[1] || nations[2] || nations[3]) {
                    result = false;
                }
                nations = changeBoolean(2, nations);
            } else if(nation == "DWARF") {
                if(nations[0] || nations[1] || nations[2] || nations[3]) {
                    result = false;
                }
                nations = changeBoolean(3, nations);
            } else if(nation == "MAN") {
                if(nations[0] || nations[1] || nations[2] || nations[3]) {
                    result = false;
                }
                nations = changeBoolean(4, nations);
            }

        }
        assertThat(!result);

    }


    private boolean[] changeBoolean(int i, boolean[] bool) {
        for(int j = 0; j < i; j++) {
            bool[j] = true;
        }
        return bool;
    }

    @Test
    public void shouldContainNineFellowsButNoneGiant() {
        boolean isGiant = false;
        Iterator<Fellow> iterator = fellowship.iterator();
        int numberOfFellows = 0;
        while(iterator.hasNext()) {
            iterator.next();
            numberOfFellows++;
        }
        if(fellowship.toString().contains("GIANT")) {
            isGiant = true;
        }
        assertThat(!isGiant && numberOfFellows == 9);
    }

    @Test
    public void shouldContainTupleForBoromirSamAndLegolas() {
        // Extracting multiple values at once (using tuple to group them)
        // Create tuples with name and race name
        Triple<String, String, String> triple = Triple.of("Boromir", "Sam", "Legolas");
        assertThat(fellowship.toString().contains(triple.toString()));
    }


    @Test
    public void shouldContainsFourHobbits() {
        List<String> list = new ArrayList<>();
        int numberOfHobbits = 0;
        Iterator<Fellow> iterator = fellowship.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().toString().contains("HOBBIT")) {
                numberOfHobbits++;
            }
        }
        assertThat(numberOfHobbits == 4);
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
