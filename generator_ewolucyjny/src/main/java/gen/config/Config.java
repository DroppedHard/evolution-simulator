package gen.config;

public class Config {
    public String name;
    public int width;
    public int height;
    public boolean mapVariant;
    public int startGrass;
    public int grassEnergy;
    public int growingGrass;
    public boolean growingVariant;
    public int startAnimals;
    public int startAnimalEnergy;
    public int healthyEnergy;
    public int populateEnergyTaken;
    public int minMutations;
    public int maxMutations;
    public boolean mutationVariant;
    public int genomeLength;
    public boolean behaviourVariant;

    public Config( String configName,
            int width, int height, boolean mapVariant, int startGrass, int grassEnergy,
            int growingGrass, boolean growingVariant, int startAnimals, int startAnimalEnergy,
            int healthyEnergy, int populateEnergyTaken, int minMutations, int maxMutations,
            boolean mutationVariant, int genomeLength, boolean behaviourVariant
    ){
        this.name = configName;
        this.width = width;
        this.height = height;
        this.mapVariant = mapVariant;
        this.startGrass = startGrass;
        this.grassEnergy = grassEnergy;
        this.growingGrass = growingGrass;
        this.growingVariant = growingVariant;
        this.startAnimals = startAnimals;
        this.startAnimalEnergy = startAnimalEnergy;
        this.healthyEnergy = healthyEnergy;
        this.populateEnergyTaken = populateEnergyTaken;
        this.minMutations = minMutations;
        this.maxMutations = maxMutations;
        this.mutationVariant = mutationVariant;
        this.genomeLength = genomeLength;
        this.behaviourVariant = behaviourVariant;
    }
    @Override
    public String toString(){
        return name;
    }
}
