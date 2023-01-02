package gen.sim;

import gen.sim.maps.MapCore;
import gen.sim.observers.AnimalObserver;

import java.util.*;

public class PositionMechanics {
    private final AnimalObserver observer;
    private final boolean behaviourVariant;
    private final MapCore map;
    public SortedSet<Animal> animals = new TreeSet<>((a, b) -> {        // do zweryfikowania jak sortuje
        int compareEnergy = Integer.compare(a.energy, b.energy);
        if (compareEnergy == 0){
            int compareAge = Integer.compare(a.age, b.age);
            if (compareAge == 0) {
                return -Integer.compare(a.kids, b.kids);
            }else {
                return -compareAge;
            }
        }else {
            return -compareEnergy;
        }
    });
    public Grass grass;
    int healthyEnergy;
    int populateEnergyTaken;
    int minMutations;
    int maxMutations;
    boolean mutationVariant;
    int genomeLength;
    public PositionMechanics(MapCore map){
        this.map = map;
        this.populateEnergyTaken = map.config.populateEnergyTaken;
        this.healthyEnergy = map.config.healthyEnergy;
        this.minMutations = map.config.minMutations;
        this.maxMutations = map.config.maxMutations;
        this.mutationVariant = map.config.mutationVariant;
        this.behaviourVariant = map.config.behaviourVariant;
        this.genomeLength = map.config.genomeLength;
        this.observer = map.animalObserver;
    }
    // Dodawanie zwierzątka poprzez this.animals.add - jest publiczne, więc można dodawać zwierzęta na spokojnie
    public void eatAndPopulate(){
        if (animals.size() == 0){
            return;
        }
        if (grass != null) {
            animals.first().energy += grass.nourishment;
            grass.isEaten();
            grass = null;
        }

        List<Animal> fertileAnimals = getFertileAnimals();

        while (fertileAnimals.size()>1){
            Animal[] parents = {null, null};

            Animal parent1 = fertileAnimals.get((int)(Math.random() * fertileAnimals.size()));
            fertileAnimals.remove(parent1);
            int idx1 = animals.headSet(parent1).size();    // zwraca index elementu

            Animal parent2 = fertileAnimals.get((int) (Math.random() * fertileAnimals.size()));
            fertileAnimals.remove(parent2);
            int idx2 = animals.headSet(parent2).size();

            if (idx1 > idx2){
                parents[0] = parent1;
                parents[1] = parent2;
            }else {
                parents[1] = parent1;
                parents[0] = parent2;
            }
            createNewborn(parents);
        }
    }

    private void createNewborn(Animal[] parents) {
        ArrayList<Integer> genome = generateGenome(parents);

        Animal newborn = new Animal(populateEnergyTaken*2, parents[0].position, genome, observer, behaviourVariant);
        map.addAnimal(newborn);
        animals.add(newborn);
        for (Animal el:parents){
            el.kids++;
            el.energy-=populateEnergyTaken;
        }
    }

    private ArrayList<Integer> generateGenome(Animal[] parents) {
        ArrayList<Integer> genome = new ArrayList<>();
        int sumEnergy = parents[0].energy + parents[1].energy;
        float betterPercent = (float) parents[0].energy/sumEnergy;
        Random rand = new Random();
        boolean side = rand.nextBoolean();      // true - lewa strona lepszego, false - prawa strona lepszego
        if (side) {
            int margin = (int) betterPercent * genomeLength;   // długość genomu jest stała
            for (int i=0; i<=margin; i++) {
                genome.add(parents[0].genome.get(i));
            }
            for (int i = margin+1; i< genomeLength; i++) {
                genome.add(parents[1].genome.get(i));
            }
        } else {
            int margin = (int) (genomeLength - (betterPercent * genomeLength));   // długość genomu jest stała
            for (int i=0; i<=margin; i++) {
                genome.add(parents[1].genome.get(i));
            }
            for (int i = margin+1; i< genomeLength; i++) {
                genome.add(parents[0].genome.get(i));
            }
        }
        mutateGenome(genome);

        return genome;
    }

    private void mutateGenome(ArrayList<Integer> genome) {
        int mutationCount = Math.round((long) (minMutations + (Math.random()*(maxMutations - minMutations + 1))));
        for (int i=0; i < mutationCount; i++ ) {    // there can be a multiple-mutated gene
            int randIdx = Math.round((long) (Math.random()*genomeLength));
            if (mutationVariant) {
                int modifier = (Math.round((float) Math.random())+1)*2 - 3;     // returns -1 or 1
                genome.set(randIdx, genome.get(randIdx) + modifier);
            } else {
                genome.set(randIdx, Math.round((long) (Math.random()*8)));
            }
        }
    }

    private List<Animal> getFertileAnimals() {
        List<Animal> fertileAnimals = new ArrayList<>();
        for (Animal animal: animals){
            if (animal.energy >= healthyEnergy) {
                fertileAnimals.add(animal);
            } else{
                break;
            }
        }
        return fertileAnimals;
    }

    public void clearFromDead() {
        List<Animal> trash = new LinkedList<>();
        for (Animal animal:animals) {
            animal.checkIfDead();
            if (animal.dead) {
                trash.add(animal);
            }
        }
        trash.forEach(el -> animals.remove(el));
    }
}
