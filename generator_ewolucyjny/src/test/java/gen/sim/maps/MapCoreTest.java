package gen.sim.maps;

import gen.config.Config;
import gen.sim.Animal;
import gen.sim.PositionMechanics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapCoreTest {
    @Test
    void AnimalPlacementTest(){
        Config config = new Config("Default", 50,50,false,30,
                10,20,false,30,50,
                30,20,0,10,
                false,40,false);
        MapCore map = new MapCore(config);
        int animalNumber = 0;
        for (PositionMechanics element: map.elements.values()) {
            int size = element.animals.size();
            animalNumber += size;
            for (Animal animal: element.animals){
                assertEquals(animal.age, 0);
                assertEquals(animal.energy, config.startAnimalEnergy);
                assertFalse(animal.dead);
                assertTrue(animal.activeGene >= 0 && animal.activeGene < config.genomeLength);
                assertTrue(animal.facing >= 0 && animal.facing < 8);
                assertEquals(animal.kids, 0);
                assertEquals(animal.genome.size(), config.genomeLength);
                assertTrue(animal.position.x >= 0 && animal.position.x < config.width);
                assertTrue(animal.position.y >= 0 && animal.position.y < config.height);
//                System.out.println(animal.position);
            }
        }
        assertEquals(animalNumber, config.startAnimals);
    }
}