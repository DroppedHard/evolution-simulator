package gen.sim.maps;

import gen.sim.Animal;

public class EarthGlobeVariant implements IMapVariant{
    int width;
    int height;
    public EarthGlobeVariant(int width, int height){
        this.width = width;
        this.height = height;
    }
    @Override
    public void movedToEdge(Animal animal) {
        // if went to left / right side of the map
        if (animal.position.x<0) {
            animal.position.x = width-1;
        } else if (animal.position.x >= width) {
            animal.position.x = 0;
        }
        // if went to top / bottom side of the map
        if (animal.position.y<0) {
            animal.position.y = 0;
            animal.facing = (animal.facing + 4)%8;
        } else if (animal.position.y >= height) {
            animal.position.y = height-1;
            animal.facing = (animal.facing + 4)%8;
        }
    }
}
