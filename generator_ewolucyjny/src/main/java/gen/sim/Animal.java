package gen.sim;

import gen.sim.observers.AnimalObserver;

import java.util.ArrayList;

public class Animal {
    public int age;
    public int energy;
    public int kids;
    public boolean dead = false;
    public Vector2d position;
    public ArrayList<Integer> genome;
    public int activeGene;
    private final AnimalObserver observer;
    private final boolean behaviourVariant;    // false - pełna predestynacja, true - nieco szlaeństwa
    public int facing;

    public Animal(int startEnergy, Vector2d position, ArrayList<Integer> genome,
                  AnimalObserver observer, boolean behaviourVariant) {
        energy = startEnergy;
        age = 0;
        kids = 0;
        this.position = position;
        this.observer = observer;
        this.genome = genome;
        activeGene = (int) (Math.random()*genome.size());
        this.behaviourVariant = behaviourVariant;
        this.facing = (int) (Math.random() * 8);
        observer.aliveAnimals.add(this);
    }
    public void move() {
        int gene = genome.get(activeGene);
        Vector2d directionVect = new Vector2d(0,0);
        gene = (gene + facing)%8;
        switch (gene) {
            case 0 -> directionVect = MapDirection.NORTH.toUnitVector();
            case 1 -> directionVect = MapDirection.NORTH_EAST.toUnitVector();
            case 2 -> directionVect = MapDirection.EAST.toUnitVector();
            case 3 -> directionVect = MapDirection.SOUTH_EAST.toUnitVector();
            case 4 -> directionVect = MapDirection.SOUTH.toUnitVector();
            case 5 -> directionVect = MapDirection.SOUTH_WEST.toUnitVector();
            case 6 -> directionVect = MapDirection.WEST.toUnitVector();
            case 7 -> directionVect = MapDirection.NORTH_WEST.toUnitVector();
            default -> {}
        }
        position = position.add(directionVect);
        if (behaviourVariant){
            int chance = (int) (Math.random()*5);
            if (chance == 0) { // 20% chance
                activeGene = (int) (Math.random() * genome.size());
            } else {    // 80% chance
                activeGene = (activeGene+1)%genome.size();
            }
        } else {
            activeGene = (activeGene+1)%genome.size();
        }
    }
    public void checkIfDead(){
        if (energy <= 0){
            dead = true;
            observer.aliveAnimals.remove(this);
            observer.deadAnimals.add(this);
        }
    }
}
