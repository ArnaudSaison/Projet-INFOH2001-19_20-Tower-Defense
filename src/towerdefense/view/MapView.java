package towerdefense.view;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import towerdefense.game.Drawable;
import towerdefense.game.map.*;

import java.util.ArrayList;

/**
 * Représentation graphique d'une carte
 * Est composée d'une série de Panes représentant les cases
 * Peuvent ensuite venir s'ajouter tous les autres éléments qui doievnt pouvoir être représentés sur la carte
 */
public class MapView extends Pane implements Printable {
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
        // Initialisation des attributs
        this.map = map;
        elementsToRemove = new ArrayList<>();
        elementsToAdd = new ArrayList<>();

        // Réglages graphiques
        String graphicsPath = "towerdefense/controller/game/graphics.css";
        this.getStylesheets().add(graphicsPath);
        this.getStyleClass().add("map");

        // Ajout de toutes les cases qui constituent la carte d'un point de vue grapique
        for (Tile t : map.getTiles()) {
            t.initDrawing();
            this.getChildren().add((Node) t.getDrawing());
            t.updateDrawing();
        }
    }

    // ==================== Fonctionnement ====================

    /**
     * Méthode qui gère le zoom de la carte
     */
    public void updateZoomLevel(ScrollEvent event) {
        double pixelsPerMeter = map.getPixelsPerMeter();

        double zoomFact = 0.2; // facteur multiplicatif du déplacement de la molette de la souris
        double zoomExp = 2; // facteur qui détermine combien le zoom est exponentiel
        double minPixelsPerMeter = 1;
        double maxPixelsPerMeter = 300;

        // zoom mesuré par le ScrollEvent
        double zoom = event.getDeltaY();
        double deltaPPM = zoom * zoomFact;
//        double deltaPPM = zoom * Math.pow(zoomFact, map.getSettingsPixelsPerMeter() / map.getPixelsPerMeter() * 2);
        double oldPPM = pixelsPerMeter;

        // Vérification de la validité du zoom et la cas échéant, mise à la valeur par défaut
        if (pixelsPerMeter + deltaPPM < minPixelsPerMeter) {
            deltaPPM = minPixelsPerMeter - pixelsPerMeter;
        } else if (pixelsPerMeter + deltaPPM > maxPixelsPerMeter) {
            deltaPPM = maxPixelsPerMeter - pixelsPerMeter;
        }

        // Calcul de la nouvelle échelle
        map.setPixelsPerMeter(pixelsPerMeter + deltaPPM);

        // origine
        double x0 = this.getLayoutX();
        double y0 = this.getLayoutY();

        // position de la souris par rapport à l'origine
        Position pos1 = new Position(event.getX() - x0, event.getY() - y0, map);
        // Changement de position après le zoom
        Position deltaPos = pos1.getMultiplied((-1) * deltaPPM / oldPPM);

        // Mise à jour des tiles
        updateTiles();

        // Translation de Map
        this.setLayoutX(this.getLayoutX() + deltaPos.getX());
        this.setLayoutY(this.getLayoutY() + deltaPos.getY());
    }

    // ==================== Interface Printable ====================

    /**
     * Mise à jour de toute la représentation de la carte
     */
    public void update() {
        updateTiles();
        updateDrawables();
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

    // Liaison directe avec le modèle sans interaction avec JavaFX
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
