package gen.sim;

import gen.sim.observers.AnimalObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void move_forward() {
        Vector2d startPos = new Vector2d(0,0);
        ArrayList<Integer> genome = new ArrayList<>();
        genome.add(0);
        AnimalObserver obs = new AnimalObserver();
        Animal animal = new Animal(10, startPos, genome, obs, false);
        int facing = animal.facing;
        Vector2d directionVect;
        switch (facing) {
            case 0 -> directionVect = MapDirection.NORTH.toUnitVector();
            case 1 -> directionVect = MapDirection.NORTH_EAST.toUnitVector();
            case 2 -> directionVect = MapDirection.EAST.toUnitVector();
            case 3 -> directionVect = MapDirection.SOUTH_EAST.toUnitVector();
            case 4 -> directionVect = MapDirection.SOUTH.toUnitVector();
            case 5 -> directionVect = MapDirection.SOUTH_WEST.toUnitVector();
            case 6 -> directionVect = MapDirection.WEST.toUnitVector();
            case 7 -> directionVect = MapDirection.NORTH_WEST.toUnitVector();
            default -> directionVect = new Vector2d(0,0);
        }
        Vector2d finalPos = startPos.add(directionVect);
        animal.move();
        System.out.println(facing);
        assertEquals(finalPos, animal.position);
        assertEquals(animal.activeGene, 0);
    }

    @Test
    void checkIfDead() {
        AnimalObserver obs = new AnimalObserver();
        Animal animal = new Animal(0,new Vector2d(0,0), new ArrayList<>(), obs, false);
        animal.checkIfDead();
        assertTrue(animal.dead);
        assertEquals(0, animal.energy);
    }
}