package gen.sim;

import gen.sim.maps.IGrowthVariant;
import gen.sim.observers.GrassObserver;

public class Grass {
    public int nourishment;
    public Vector2d position;
    public GrassObserver observer;
    IGrowthVariant growth;
    public Grass(int grassEnergy, GrassObserver observer, Vector2d position, IGrowthVariant growth){
        nourishment = grassEnergy;
        this.position = position;
        this.observer = observer;
        this.growth = growth;
        observer.addGrass(this);
    }
    public void isEaten(){
        nourishment = 0;
        observer.removeGrass(this);
        growth.freePosition(position);
    }
}
