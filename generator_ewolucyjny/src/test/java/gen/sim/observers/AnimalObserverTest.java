package gen.sim.observers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalObserverTest {

    @Test
    void genomeToString() {
        AnimalObserver obs = new AnimalObserver();
        List<Integer> in = new ArrayList<>();
        in.add(1);
        in.add(2);
        in.add(3);
        String out = obs.genomeToString(in);
        assertEquals("123", out);
    }
}