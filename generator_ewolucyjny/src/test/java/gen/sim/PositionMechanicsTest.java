package gen.sim;

import gen.config.Config;
import gen.sim.maps.MapCore;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class PositionMechanicsTest {
    @Test
    void testAnimalSorting() {
        Config config = new Config("",10,10,false,0,0,0,
                false,0,0,0,0,0,0,
                false,0,false);
        MapCore map = new MapCore(config);
        PositionMechanics posMech = new PositionMechanics(map);
        Animal[] animals = {
                new Animal(20, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
                new Animal(19, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
                new Animal(19, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
                new Animal(19, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
                new Animal(19, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
                new Animal(18, new Vector2d(0,0), new ArrayList<>(), map.animalObserver, false),
        };
        animals[1].age = 100;
        animals[1].kids = 0;

        animals[2].age = 50;
        animals[2].kids = 100;

        animals[3].age = 50;
        animals[3].kids = 100;

        animals[4].age = 50;
        animals[4].kids = 50;

        Collections.addAll(posMech.animals, animals);

        Iterator<Animal> iter = posMech.animals.iterator();
        int i = 0;
        while (iter.hasNext()){
            if (i == 2 || i == 3){
                iter.next();
                continue;
            }
            assertEquals(animals[i], iter.next());
            i++;
        }
    }
}