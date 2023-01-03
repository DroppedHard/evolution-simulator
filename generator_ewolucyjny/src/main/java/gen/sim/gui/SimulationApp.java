package gen.sim.gui;

import gen.config.Config;
import gen.sim.maps.MapCore;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulationApp implements Runnable{
    Config configuration;
    BorderPane content;
    GridPane grid;
    VBox statistics;
    int mapWidth = 500;
    int mapHeight = 500;
    int infoWidth = 300;
    final Thread simulationThread;
    MapCore map;
    MapVisualizer mapVisualizer;
    DataVisualizer dataVisualizer;
    int delay = 100;
    boolean duringSimulation;
    boolean isPaused;
    public SimulationApp(Config cfg){
        configuration = cfg;
        simulationThread = new Thread(this);
        duringSimulation = true;
        start();
    }
    public void start() {
        Stage simulationStage = new Stage();
        simulationStage.setOnCloseRequest((val) -> exitSimulation());
        simulationStage.setTitle("Simulation");
        content = new BorderPane();

        statistics = new VBox();
        content.setLeft(statistics);

        grid = new GridPane();
        content.setCenter(grid);
//        content.fillWidthProperty().setValue(false);
        addMap();

        addStatistics();

        Scene scene = new Scene(content);
        simulationStage.setScene(scene);
        simulationStage.show();
        simulationThread.start();
    }
    private void addMap(){
        map = new MapCore(configuration);
        mapVisualizer = new MapVisualizer(mapWidth, mapHeight, map, grid);
    }
    private void addStatistics(){
        dataVisualizer = new DataVisualizer(map, statistics, this);
    }

    @Override
    public void run() { // day handler
        while (duringSimulation) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Simulation thread stopped");
            }
            map.moveAnimals();
            map.animalsEatingAndPopulating();
            map.growGrass();
            Platform.runLater(()-> {
                mapVisualizer.updateMap();
                if (map.config.growingVariant) {
                    mapVisualizer.updateAcidPreferred();
                }
                dataVisualizer.updateData();
            });
            map.endDayAndRemoveDeadAnimals();
            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println("Simulation thread stopped");
                    }
                }
            }
        }
    }
    private void exitSimulation() {
        duringSimulation = false;
        resumeSim();
        simulationThread.interrupt();
    }
    synchronized void pauseSim(){
        isPaused = true;
    }
    synchronized void resumeSim(){
        isPaused = false;
        notify();
    }
}
