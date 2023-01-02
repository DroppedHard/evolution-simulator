package gen.sim.gui;

import gen.sim.Animal;
import gen.sim.PositionMechanics;
import gen.sim.Vector2d;
import gen.sim.maps.MapCore;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapVisualizer {
    int spaceWidth;
    int spaceHeight;
    MapCore map;
    GridPane grid;
    int cellSize;
    Shapes shapes;
    Map<Vector2d, StackPane> cells = new HashMap<>();
    Color grassColor = Color.web("#005c03");
    Color healthyAnimalColor = Color.web("#ffc400");
    Color unhealthyAnimalColor = Color.web("#ba9002");
    Color dyingAnimalColor = Color.web("#806d34");
    Color deadAnimalColor = Color.web("#3d3d3d");
    String preferredCell = "-fx-background-color: #06b500;";
    String unpreferredCell = "-fx-background-color: WHITE;";
    List<Vector2d> lastPreferred = new ArrayList<>();
    public MapVisualizer(int spaceWidth, int spaceHeight, MapCore map, GridPane grid){
        this.spaceWidth = spaceWidth;
        this.spaceHeight = spaceHeight;
        this.map = map;
        this.grid = grid;
        cellSize = (int) Math.min(Math.floor((float) spaceHeight / map.config.height),Math.floor((float) spaceWidth / map.config.width));
        shapes = new Shapes(cellSize);
        generateStartingMap();
    }

    public void generateStartingMap() {
        formatGrid();
        addElements();
        showPreferred();
    }
    public void formatGrid(){
//        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        ColumnConstraints cc = new ColumnConstraints(cellSize);
        RowConstraints rc = new RowConstraints(cellSize);
        for (int x = 0; x < map.config.width; x++) {
            grid.getColumnConstraints().add(cc);
        }
        for (int y = 0; y < map.config.height; y++) {
            grid.getRowConstraints().add(rc);
        }
    }
    private void addElements() {
        for (int x = 0; x < map.config.width; x++) {
            for (int y = 0; y < map.config.height; y++) {
                Vector2d pos = new Vector2d(x, y);
                StackPane cell = new StackPane();
                drawElement(pos, cell);
                cell.setAlignment(Pos.CENTER);
                GridPane.setFillHeight(cell, true);
                GridPane.setFillWidth(cell, true);
//                cell.styleProperty().set("-fx-border-color: blue; -fx-border-width: 1; -fx-border-style: solid");
//                cell.getChildren().add(new Label(pos.toString()));
                grid.add(cell, x, map.config.height - y);
                cells.put(pos, cell);
            }
        }
    }

    private void drawElement(Vector2d position, StackPane cell) {
        PositionMechanics element = map.elements.get(position);
        if (element == null) {
            drawEmptyCell(position, cell);
        } else if (element.animals.isEmpty() && element.grass == null) {
            drawEmptyCell(position, cell);
        } else if (element.animals.isEmpty()) {
            drawGrass(cell);
        } else {
            drawAnimal(element.animals.first(), cell);
        }
    }

    private void drawAnimal(Animal animal, StackPane cell) {
        Polygon animalShape = shapes.getAnimal();
        if (animal.energy >= map.config.healthyEnergy) {
            animalShape.setFill(healthyAnimalColor);
        } else if (animal.energy > map.config.healthyEnergy/4) {
            animalShape.setFill(unhealthyAnimalColor);
        } else if (animal.energy > 0) {
            animalShape.setFill(dyingAnimalColor);
        } else {
            animalShape.setFill(deadAnimalColor);
        }
        cell.getChildren().add(animalShape);
    }

    private void drawGrass(StackPane cell) {
        Polygon grassShape = shapes.getGrass();
        grassShape.setFill(grassColor);
        cell.getChildren().add(grassShape);
    }

    private void drawEmptyCell(Vector2d position, StackPane cell) {
        // may be used one day
    }
    private void showPreferred(){
        List<Vector2d> list = map.growth.getPreferredGrassPositions();
        for (Vector2d pos: list) {
            cells.get(pos).styleProperty().set(preferredCell);
//            System.out.println(pos);
        }
        lastPreferred = list;
    }

    public void updateMap() {
        for (int x = 0; x < map.config.width; x++) {
            for (int y = 0; y < map.config.height; y++) {
                Vector2d pos = new Vector2d(x, y);
                StackPane cell = cells.get(pos);
                cell.getChildren().clear();
                drawElement(pos, cell);
            }
        }
    }
    public void updateAcidPreferred(){
        lastPreferred.forEach(el -> cells.get(el).styleProperty().set(unpreferredCell));
        showPreferred();
    }
}
