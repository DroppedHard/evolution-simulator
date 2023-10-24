package gen.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class SwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(30, 10, Color.BLUEVIOLET);
    private final Button button = new Button();
    private final String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private final String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 0.2, 0.0, 0.0, 2); -fx-background-color: #dadada;";
    private boolean state;

    private void init() {
        getChildren().addAll(back, button);
        setMinSize(30, 15);
        back.maxWidth(30);
        back.minWidth(30);
        back.maxHeight(10);
        back.minHeight(10);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#cfe2f3"));
        double r = 2.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(15, 15);
        button.setMinSize(15, 15);
        button.setStyle(buttonStyleOff);
    }

    public SwitchButton(String id, Map<String, Integer> data) {
        init();
        EventHandler<Event> click = e -> {
            if (state) {
                button.setStyle(buttonStyleOff);
                back.setFill(Color.valueOf("#cfe2f3"));
                setAlignment(button, Pos.CENTER_LEFT);
                state = false;
                data.put(id, 1);
            } else {
                button.setStyle(buttonStyleOn);
                back.setFill(Color.valueOf("#6fa8dc"));
                setAlignment(button, Pos.CENTER_RIGHT);
                state = true;
                data.put(id, 0);
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }
    public void setToValue(boolean val){
        if (val) {
            button.setStyle(buttonStyleOff);
            back.setFill(Color.valueOf("#cfe2f3"));
            setAlignment(button, Pos.CENTER_LEFT);
            state = false;
        } else {
            button.setStyle(buttonStyleOn);
            back.setFill(Color.valueOf("#6fa8dc"));
            setAlignment(button, Pos.CENTER_RIGHT);
            state = true;
        }
    }
}
