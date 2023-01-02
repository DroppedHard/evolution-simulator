package gen.sim.gui;

import gen.sim.maps.MapCore;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class DataVisualizer {
    MapCore map;
    VBox statistics;
    Label animalNumberLabel;
    Label grassNumberLabel;
    Label freeFieldsLabel;
    Label mostPopularGenomeLabel;
    Label avgEnergyLabel;
    Label avgLifespanLabel;
    private final DecimalFormat df = new DecimalFormat("0.00");
    public DataVisualizer(MapCore map, VBox stats){
        this.map = map;
        statistics = stats;
        addStatisticFields();
    }

    private void addStatisticFields() {
        addDataLabel("Animal number");
        addDataLabel("Grass number");
        addDataLabel("Free fields number");
        addDataLabel("Average animal energy");
        addDataLabel("Average animal lifespan");
        addDataLabel("Most popular genome");
        updateData();
    }

    private void addDataLabel(String info) {
        Label label = new Label(info + ": ");
        Label data = new Label();
        switch (info) {
            case "Animal number" -> animalNumberLabel = data;
            case "Grass number" -> grassNumberLabel = data;
            case "Free fields number" -> freeFieldsLabel = data;
            case "Most popular genome" -> {
                mostPopularGenomeLabel = data;
                data.setMaxWidth(35.0);
                data.setWrapText(true);
                data.setMaxHeight(200.0);
            }
            case "Average animal energy" -> avgEnergyLabel = data;
            case "Average animal lifespan" -> avgLifespanLabel = data;
        }
//        data.textProperty().setValue("sample data");
        HBox box = new HBox(5, label, data);
        statistics.getChildren().add(box);
    }
    public void updateData() {
        animalNumberLabel.textProperty().setValue(String.valueOf(map.animalObserver.aliveAnimals.size()));
        grassNumberLabel.textProperty().setValue(String.valueOf(map.grassObserver.grassCount));
        freeFieldsLabel.textProperty().setValue(String.valueOf(map.getFreeFields()));

        map.animalObserver.updateData();

        avgEnergyLabel.textProperty().setValue(df.format(map.animalObserver.averageEnergy));
        avgLifespanLabel.textProperty().setValue(df.format(map.animalObserver.averageLifeSpan));
        mostPopularGenomeLabel.textProperty().setValue(map.animalObserver.mostPopularGenome);
    }
}
