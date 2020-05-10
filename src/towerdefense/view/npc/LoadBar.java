package towerdefense.view.npc;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoadBar extends Pane {

    private double height;
    private int max;
    private Color color1;
    private Color color2;
    private double width;


    public LoadBar(double width, double height, int max, Color color1, Color color2) {
        this.width = width;
        this.height = height;
        this.max = max;
        this.color1 = color1;
        this.color2 = color2;

        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setMaxWidth(width);
        this.setMaxHeight(height);
        this.setMinWidth(width);
        this.setMinHeight(height);

        this.setBackground(new Background(new BackgroundFill(color2, new CornerRadii(0), new Insets(0))));
        updateTo(max);
    }

    /**
     * Régler la taille de la barre à un certain pourcentage de la taille de son conteneur
     * en fournissant la nouvelle valeur de la variable affichée
     *
     * @param value entre 0 et 1
     */
    public void updateTo(int value) {
        double bw = width * (max - value) / max;
        this.setBorder(new Border(new BorderStroke(color1, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(0, bw, 0, 0))));
    }
}
