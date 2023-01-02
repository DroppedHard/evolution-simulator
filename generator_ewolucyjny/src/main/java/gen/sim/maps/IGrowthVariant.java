package gen.sim.maps;

import gen.sim.Vector2d;

import java.util.List;


public interface IGrowthVariant {

    void addGrass(int n);
    List<Vector2d> getPreferredGrassPositions();
    void freePosition(Vector2d pos);
}
