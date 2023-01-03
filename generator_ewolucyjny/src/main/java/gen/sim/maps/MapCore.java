package gen.sim.maps;

import gen.config.Config;
import gen.sim.Animal;
import gen.sim.PositionMechanics;
import gen.sim.Vector2d;
import gen.sim.observers.AnimalObserver;
import gen.sim.observers.GrassObserver;

import java.util.*;

public class MapCore {
    public Map<Vector2d, PositionMechanics> elements = new HashMap<>();
    public Config config;
    // wymiary mapy - lewy doly róg to Vector2d(0,0), prawy górny to Vector2d(width-1, height-1)
    private final List<Animal> animalList = new ArrayList<>();
    private final IMapVariant border;
    public final IGrowthVariant growth;
    public final AnimalObserver animalObserver;
    public final GrassObserver grassObserver;
    public MapCore(Config cfg){
        animalObserver = new AnimalObserver();
        grassObserver = new GrassObserver();
        config = cfg;
        if (cfg.mapVariant){
            border = new HellPortalVariant(config.width, config.height, config.populateEnergyTaken);
        }else {
            border = new EarthGlobeVariant(config.width, config.height);
        }
        if (cfg.growingVariant){
            growth = new AcidCorpsesVariant(this);
        } else {
            growth = new WoodedEquatorVariant(this);
        }

        placeAnimals();
        growth.addGrass(config.startGrass);
    }
    private void placeAnimals(){
        for (int i=0; i<config.startAnimals; i++){
            int x = (int) (Math.random()* config.width);
            int y = (int) (Math.random()* config.height);
            Vector2d pos = new Vector2d(x, y);
            ArrayList<Integer> genome = new ArrayList<>();

            for (int a = 0; a<config.genomeLength; a++){
                genome.add(Math.round((long) (Math.random()*8)));
            }
            Animal animal = new Animal(config.startAnimalEnergy, pos, genome, animalObserver, config.behaviourVariant);
            PositionMechanics square = elements.get(pos);
            if (square == null){
                square = new PositionMechanics(this);
                square.animals.add(animal);
                elements.put(pos, square);
            } else {
                square.animals.add(animal);
            }
            animalList.add(animal);
        }
    }
    private boolean outOfBorder(Vector2d pos) {
        return pos.x < 0 || pos.y < 0 || pos.x >= config.width || pos.y >= config.height;
    }

    public void addAnimal(Animal animal) {
        animalList.add(animal);
    }
    public void endDayAndRemoveDeadAnimals(){
        List<Animal> dead = new LinkedList<>();
        animalList.forEach(el -> {
            if (!el.dead){
                el.energy--;
                el.age++;
                el.checkIfDead();
            }
            if (el.dead) {
                dead.add(el);
                elements.get(el.position).animals.remove(el);
            }
        });
        dead.forEach(animalList::remove);   // removes all dead animals
    }
    public void moveAnimals(){
        animalList.forEach(el -> {
            elements.get(el.position).animals.remove(el);
            el.move();
            if (outOfBorder(el.position)) {
                border.movedToEdge(el);    // animal position is changed in movedToEdge method
            }
            PositionMechanics destPosMechanics = elements.get(el.position);
            if (destPosMechanics == null) {
                destPosMechanics = new PositionMechanics(this);
                elements.put(el.position, destPosMechanics);
            }
            destPosMechanics.animals.add(el);
        });
    }
    public void animalsEatingAndPopulating(){
        for (PositionMechanics posMechanics:elements.values()) {
            posMechanics.eatAndPopulate();
        }
    }
    public void growGrass(){
        growth.addGrass(config.growingGrass);
    }


    public int getFreeFields() {
        int fields = config.width * config.height;
        for (PositionMechanics pos : elements.values()) {
            if (pos.grass == null && pos.animals.isEmpty()) {
                fields--;
            }
            pos.clearFromDead();
        }
        return fields;
    }
}
