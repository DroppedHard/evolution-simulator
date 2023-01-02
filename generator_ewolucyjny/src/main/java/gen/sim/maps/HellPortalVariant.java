package gen.sim.maps;

import gen.sim.Animal;
import gen.sim.Vector2d;

import java.util.Random;

public class HellPortalVariant implements IMapVariant{
    int height;
    int width;
    int energyTaken;
    Random rand = new Random();
    public HellPortalVariant(int width, int height, int energyTaken) {
        this.height = height;
        this.width = width;
        this.energyTaken = energyTaken;
    }

    @Override
    public void movedToEdge(Animal animal) {
        int x = rand.nextInt(width);
        int y = rand.nextInt(height);
        Vector2d newPos = new Vector2d(x, y);
        animal.energy -= energyTaken;
        animal.position = newPos;
    }
}
