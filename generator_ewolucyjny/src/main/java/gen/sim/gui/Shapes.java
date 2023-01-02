package gen.sim.gui;

import javafx.scene.shape.Polygon;

public class Shapes {
    public static Polygon animal;
    public static Polygon grass;
    int cellSize;
    public Shapes(int cellSize) {
        this.cellSize = cellSize;
        animal = new Polygon(0.2*cellSize, 0.0*cellSize,
                0.0*cellSize, 0.6*cellSize,
                0.4*cellSize, 1.0*cellSize,
                0.6*cellSize, 1.0*cellSize,
                1.0*cellSize, 0.6*cellSize,
                0.8*cellSize, 0.0*cellSize,
                0.6*cellSize, 0.2*cellSize,
                0.4*cellSize, 0.2*cellSize
        );
        grass = new Polygon(0.0*cellSize, 1.0*cellSize,
                0.0*cellSize, 0.0*cellSize,
                0.2*cellSize, 1.0*cellSize,
                0.2*cellSize, 0.0*cellSize,
                0.4*cellSize, 1.0*cellSize,
                0.4*cellSize, 0.0*cellSize,
                0.6*cellSize, 1.0*cellSize,
                0.6*cellSize, 0.0*cellSize,
                0.8*cellSize, 1.0*cellSize,
                0.8*cellSize, 0.0*cellSize,
                1.0*cellSize, 1.0*cellSize
        );
    }
    public Polygon getAnimal(){
        return new Polygon(0.2*cellSize, 0.0*cellSize,
                0.0*cellSize, 0.6*cellSize,
                0.4*cellSize, 1.0*cellSize,
                0.6*cellSize, 1.0*cellSize,
                1.0*cellSize, 0.6*cellSize,
                0.8*cellSize, 0.0*cellSize,
                0.6*cellSize, 0.2*cellSize,
                0.4*cellSize, 0.2*cellSize
        );
    }
    public Polygon getGrass(){
        return new Polygon(0.0*cellSize, 1.0*cellSize,
                0.0*cellSize, 0.0*cellSize,
                0.2*cellSize, 1.0*cellSize,
                0.2*cellSize, 0.0*cellSize,
                0.4*cellSize, 1.0*cellSize,
                0.4*cellSize, 0.0*cellSize,
                0.6*cellSize, 1.0*cellSize,
                0.6*cellSize, 0.0*cellSize,
                0.8*cellSize, 1.0*cellSize,
                0.8*cellSize, 0.0*cellSize,
                1.0*cellSize, 1.0*cellSize
        );
    }
}
