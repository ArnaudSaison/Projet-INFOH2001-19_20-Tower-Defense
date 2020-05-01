package towerdefense.view;

import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import towerdefense.game.Drawable;
import towerdefense.game.map.*;

import java.util.ArrayList;

/**
 * Représentation graphique d'une carte
 * Est composée d'une série de Panes représentant les cases
 * Peuvent ensuite venir s'ajouter tous les autres éléments qui doievnt pouvoir être représentés sur la carte
 */
public class MapView extends TilePane implements Printable {
    // ==================== Attributs ====================
    private Map map; // Référence à la carte du modèle

    // Éléments à ajouter et retirer
    private ArrayList<Node> elementsToRemove;
    private ArrayList<Node> elementsToAdd;

    // ==================== Initilisation ====================

    /**
     * Constructeur de MapView
     * <p>
     * Se charge d'appliquer le style à l'objet Pane,
     * puis initialise la représentatin graphique de chaque case, l'ajoute à la carte et la met à jour
     */
    public MapView(Map map) {
        super();

        // Initialisation des attributs
        this.map = map;

        // Réglages graphiques
        this.getStyleClass().add("map");

        // Ajout de toutes les cases qui constituent la carte d'un point de vue grapique
        for (Tile t : map.getTiles()) {
            t.initDrawing();
            this.getChildren().add((Node) t.getDrawing());
            t.updateDrawing();
        }

        setTileAlignment(Pos.CENTER);
        setHgap(0);
        setVgap(0);
//        setWidth(map.getMapTileSizeX() * map.getTileMetricWidth() * map.getPixelsPerMeter());
//        setHeight(map.getMapTileSizeY() * map.getTileMetricWidth() * map.getPixelsPerMeter());
        setPrefRows(map.getMapTileSizeX());
        setPrefColumns(map.getMapTileSizeY());
    }

    // ==================== Fonctionnement ====================

    /**
     * Méthode qui gère le zoom de la carte
     */
    public void updateZoomLevel(ScrollEvent event) {
        double zoomFact = 0.08;
        double deltaScale = 1 + (event.getDeltaY() / Math.abs(event.getDeltaY()) * zoomFact);
        double newScale = getScaleX() * deltaScale;
        System.out.println(newScale);

        if (newScale > 0.01 && newScale < 10) {
            this.setScaleX(newScale);
            this.setScaleY(newScale);
        }
    }

    public void resetZoom() {
        this.setScaleX(1);
        this.setScaleY(1);
        setLayoutX(0);
        setLayoutY(0);
    }

    // ==================== Interface Printable ====================

    /**
     * Mise à jour de toute la représentation de la carte
     */
    public void update() {
        updateTiles();
    }

    /**
     * Mise à jour des cases de la carte uniquement
     */
    public void updateTiles() {
        // Mise à jour de toutes les Tiles
        for (Tile t : map.getTiles()) {
            t.updateDrawing();
        }
    }

    // TODO: ajouter possibilité de supprimer les tiles, les remplacer, ...

    /**
     * Mise à jours de tous les éléments présents sur la carte qui ne sont pas des cases
     */
    public void updateDrawables() {
        getChildren().removeAll(elementsToRemove);
        getChildren().addAll(elementsToAdd);
        elementsToRemove.clear();
        elementsToAdd.clear();

        for (Drawable drawable : map.getElementsOnMap()) {
            drawable.updateDrawing();
        }
    }

    /**
     * Ajouter un élément Printable à cet objet JavaFX
     */
    public void addDrawable(Printable elem) {
        // Liste buffer afin de laisser le thread JavaFX ajouter sans risque
        elementsToAdd.add((Node) elem);
    }

    /**
     * Supprimer un élément Printable déjà ajouté à cet objet JavaFX
     */
    public void removeDrawable(Printable elem) {
        // Liste buffer afin de laisser le thread JavaFX supprimer sans risque
        elementsToRemove.add((Node) elem);
    }
}
