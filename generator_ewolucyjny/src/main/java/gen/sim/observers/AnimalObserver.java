package gen.sim.observers;

import gen.sim.Animal;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AnimalObserver {
    public List<Animal> aliveAnimals = new LinkedList<>();
    public List<Animal> deadAnimals = new ArrayList<>();
    public double averageEnergy;
    public String mostPopularGenome;
    public double averageLifeSpan;

    public void updateData() {
        AtomicInteger sumEnergy = new AtomicInteger();
        AtomicInteger sumLifespan = new AtomicInteger();
        AtomicInteger genomeCount = new AtomicInteger();
        AtomicReference<String> theGenome = new AtomicReference<>("");
        Map<String, Integer> genomes = new HashMap<>();
        for (Animal el: aliveAnimals) {
            sumEnergy.addAndGet(el.energy);
            String genome = genomeToString(el.genome);
            Integer count = genomes.get(genome);
            if (count == null) {
                count = 0;
            }
            if (genomeCount.get() < count+1) {
                theGenome.set(genome);
                genomeCount.set(count+1);
            }
            genomes.put(genome, count+1);
        }
        deadAnimals.forEach(el -> sumLifespan.addAndGet(el.age));
        averageEnergy = (double) sumEnergy.get() / aliveAnimals.size();
        averageLifeSpan = (double) sumLifespan.get() / deadAnimals.size();
        mostPopularGenome = theGenome.get();

    }
    public String genomeToString(List<Integer> genome){
        StringBuilder out = new StringBuilder();
        genome.forEach(out::append);
        return out.toString();
    }
}
