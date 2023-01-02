package gen.sim.observers;

import gen.sim.Grass;

import java.util.LinkedList;
import java.util.List;

public class GrassObserver {
    public List<Grass> grasses = new LinkedList<>();    // do przemyślenia czy ma sens
    public int grassCount = 0;
    public void removeGrass(Grass grass){
        grasses.remove(grass);
        grassCount--;
    }
    public void addGrass(Grass grass){
        grasses.add(grass);
        grassCount++;
    }
}
