package towerdefense.view.npc;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class LoadBar extends Pane {

    private double height;
    private int max;
    private double width;
    private double border = 0;

    private Rectangle container;
    private Rectangle bar;

    public LoadBar(double width, double height, int max){
        this.width = width;
        this.height = height;
        this.max = max;

        container = new Rectangle();

        this.getChildren().add(container);
    }

    public void updateTo(int value) {

    }
}
