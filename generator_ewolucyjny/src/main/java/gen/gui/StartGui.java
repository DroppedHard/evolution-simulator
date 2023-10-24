package gen.gui;

import gen.config.Config;
import gen.config.Configs;
import gen.sim.gui.SimulationApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class StartGui extends Application {
    public VBox content;
    private final int prefConfigWidth = 200;
    private final int prefConfigHeight = 100;

    public Map<String, Integer> data = new HashMap<>();
    private final Map<String, Object> inputs = new HashMap<>();
    private final ArrayList<SimulationApp> simulations = new ArrayList<>();   // do 5 wątków by nie zżerało zasobów za bardzo
    private final int simulationLimit = 5;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Evolution Generator");
        content = new VBox(5);
//        content.fillWidthProperty().setValue(false);
        addInputs();
        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToHeight(true);

        Scene scene = new Scene(content);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addInputs(){
        addConfigSelect();
        addNumericInput("width");
        addNumericInput("height");
        addBooleanInput("mapVariant","Earth Globe","Hell Portal");
        addNumericInput("startGrass");
        addNumericInput("grassEnergy");
        addNumericInput("growingGrass");
        addBooleanInput("growingVariant","Wooded Equator", "Acid Corpses");
        addNumericInput("startAnimals");
        addNumericInput("startAnimalEnergy");
        addNumericInput("healthyEnergy");
        addNumericInput("populateEnergyTaken");
        addNumericInput("minMutations");
        addNumericInput("maxMutations");
        addBooleanInput("mutationVariant","Full Random", "Small Correction");
        addNumericInput("genomeLength");
        addBooleanInput("behaviourVariant","Full Fore-ordination", "A Bit Of Chaos");
        addSubmit();
    }

    private void addSubmit() {
        Button submit  =  new Button("Start Simulation");
        submit.setOnAction(action -> {
//            for (Map.Entry<String, Integer> detail : data.entrySet()){
//                System.out.println(detail.getKey() + " - " + detail.getValue());
//            }
            String check = verifyData();
            if (check.equals("OK")) {
                startSimulation();
            }else {
                System.out.println(check);
            }
        });
        HBox box = new HBox(0,submit);
        box.alignmentProperty().setValue(Pos.CENTER);
        content.getChildren().add(box);
    }

    private String verifyData() {
        for (Map.Entry<String, Integer> detail : data.entrySet()){  // podwójne zabezpieczenie przed ujemnymi wartościami
//            System.out.println(detail.getKey() + " - " + detail.getValue());
            if (!detail.getKey().contains("Variant") && !detail.getKey().contains("Mutations")
                    && detail.getValue() <= 0){
                return "Any numeric value cannot be negative or equal to 0!";
            }
        }
        if (data.get("populateEnergyTaken") >= data.get("healthyEnergy")){
            return "Energy taken to populate cannot be greater than healthy energy requirement!";
        }
        if (data.get("growingVariant") == 0 && data.get("height")%5 != 0){
            return "Plant-preferable area will not fulfill the Pareto 80/20 rule, and will not create correct " +
                    "equator. PLease change map height to be dividable by 5.";
        }
        return "OK";
    }

    private void addBooleanInput(String mainLabel, String falseLabel, String trueLabel) {
        VBox inputWithLabel = new VBox(2);
        inputWithLabel.alignmentProperty().setValue(Pos.CENTER);
        Label mLabel = new Label(mainLabel);

        HBox inputSpace = new HBox(2);
        inputSpace.alignmentProperty().setValue(Pos.CENTER);
        Label fLabel = new Label(falseLabel);
        SwitchButton toggle = new SwitchButton(mainLabel, data);
        data.put(mainLabel, 0);     // dodajemy początkowe wartości
        inputs.put(mainLabel, toggle);
        toggle.setToValue(false);
        Label tLabel = new Label(trueLabel);
        inputSpace.getChildren().addAll(tLabel, toggle, fLabel);
        inputWithLabel.getChildren().addAll(mLabel, inputSpace);
        content.getChildren().add(inputWithLabel);
    }

    private void addNumericInput(String label) {
        HBox inputSpace = new HBox(2);
        inputSpace.alignmentProperty().setValue(Pos.CENTER);
        Label lab = new Label(label);
        TextField input = new TextField();
        input.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        input.textProperty().addListener((obs, oldv, newv) -> {
            try {
                input.getTextFormatter().getValueConverter().fromString(newv);
                data.put(label, (int) input.getTextFormatter().getValueConverter().fromString(newv));
            } catch (NumberFormatException e) {
                input.setText(oldv);
            } catch (NullPointerException e) {
                data.put(label, 0);
            }
        });
        data.put(label, 0);     // dodajemy początkowe wartości
        inputs.put(label, input);
        inputSpace.getChildren().addAll(lab, input);
        content.getChildren().add(inputSpace);
    }

    private void addConfigSelect() {
//        ObservableList<String> items = FXCollections.observableArrayList("raz","dwa","trzy");
        ObservableList<Config> items = FXCollections.observableArrayList(Configs.getConfigs());
        ListView<Config> list = new ListView<>(items);
        list.setPrefSize(prefConfigWidth, prefConfigHeight);
        VBox box = new VBox(0, list);
        box.fillWidthProperty().setValue(false);
        box.alignmentProperty().setValue(Pos.CENTER);
        content.getChildren().add(box);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)-> setConfigData(list.getSelectionModel().getSelectedItems().get(0)));
    }

    private void setConfigData(Config list) {
        System.out.println("\n" + list);
        updateNumericInput("width", list.width);
        updateNumericInput("height", list.height);
        updateBooleanInput("mapVariant", list.mapVariant);
        updateNumericInput("startGrass", list.startGrass);
        updateNumericInput("grassEnergy", list.grassEnergy);
        updateNumericInput("growingGrass", list.growingGrass);
        updateBooleanInput("growingVariant", list.growingVariant);
        updateNumericInput("startAnimals", list.startAnimals);
        updateNumericInput("startAnimalEnergy", list.startAnimalEnergy);
        updateNumericInput("healthyEnergy", list.healthyEnergy);
        updateNumericInput("populateEnergyTaken", list.populateEnergyTaken);
        updateNumericInput("minMutations", list.minMutations);
        updateNumericInput("maxMutations", list.maxMutations);
        updateBooleanInput("mutationVariant", list.mutationVariant);
        updateNumericInput("genomeLength", list.genomeLength);
        updateBooleanInput("behaviourVariant", list.behaviourVariant);

//        for (Map.Entry<String, Integer> el:data.entrySet()) {
//            System.out.println(el.getKey()+" - "+el.getValue());
//        }
    }

    private void updateBooleanInput(String label, boolean value) {
        SwitchButton bt = (SwitchButton) inputs.get(label);
        bt.setToValue(value);
        data.put(label, value ? 1 : 0);
    }

    private void updateNumericInput(String label, int value) {
        TextField field = (TextField) inputs.get(label);
        field.setText(Integer.toString(value));
        data.put(label, value);
    }
    private void startSimulation() {
        Config config = new Config("running",
                data.get("width"),
                data.get("height"),
                data.get("mapVariant") == 1,
                data.get("startGrass"),
                data.get("grassEnergy"),
                data.get("growingGrass"),
                data.get("growingVariant") == 1,
                data.get("startAnimals"),
                data.get("startAnimalEnergy"),
                data.get("healthyEnergy"),
                data.get("populateEnergyTaken"),
                data.get("minMutations"),
                data.get("maxMutations"),
                data.get("mutationVariant") == 1,
                data.get("genomeLength"),
                data.get("behaviourVariant") == 1
        );
//        if (simulations.size() >= simulationLimit){
//            System.out.println("Too much simulations opened - I do not want to take responsibility for destroyed hardware");
//        } else {
//            System.out.println(config.mapVariant + " - " + data.get("mapVariant"));
//        System.out.println(config.growingVariant + " - " + data.get("growingVariant"));
//        System.out.println(config.mutationVariant + " - " + data.get("mutationVariant"));
//        System.out.println(config.behaviourVariant + " - " + data.get("behaviourVariant"));
        SimulationApp sim = new SimulationApp(config);
            simulations.add(sim);
//        }
    }
}
