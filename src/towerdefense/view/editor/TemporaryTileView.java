package towerdefense.view.editor;

import javafx.scene.Node;
import towerdefense.controller.map.editor.MapEditorController;
import towerdefense.game.map.*;
import towerdefense.view.ElementView;
import towerdefense.view.TemporaryItem;
import towerdefense.view.map.MapView;

public class TemporaryTileView extends ElementView implements TemporaryItem {
    // ==================== Attributs ====================
    // Références
    private TileType type;
    private Position tilePosition;
    private Map map;
    private Position correctionPos;

    // ==================== Initilisation ====================

    public TemporaryTileView(TileType type, Map map, SideBarTilesSelector sideBarView, MapEditorController controller) {
        super(new Position(0, 0, map), map, MapFactory.getTileGraphics(type), 1.0);

        this.type = type;
        this.map = map;
        double correction = map.getTileMetricWidth() / 2.0;
        this.correctionPos = new Position(correction, correction, map);
        setPosition(new Position(0, 0, map));

        // Réglage graphique
        this.setOpacity(0.5);

        // listener qui appelle shop quand on clique
        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown()) {
                int X =  tilePosition.getTileX();
                int Y =  tilePosition.getTileY();

                Tile oldTile = map.getTile(X, Y); // ancienne case
                Tile newTile = MapFactory.getTile(type, X, Y, map); // nouvelle case

                map.setTile(newTile); // on met la nouvelle tile

                if (!oldTile.equals(map.getTile(X, Y))) { // vérification de si on a pu placer la nouvelle case

                    ((MapView) map.getDrawing()).getChildren().remove((Node) oldTile.getDrawing()); // suppression de la représentation de l'ancienne case
                    newTile.initDrawing(); // initilisatin de la représentation de la nouvelle case
                    ((MapView) map.getDrawing()).getChildren().add((Node) newTile.getDrawing()); // ajout de la représentation
                    newTile.updateDrawing(); // màj de la représentation de la case

//                    System.out.println(newTile.getDrawing());
//                    System.out.println(oldTile.getDrawing());
//                    System.out.println(((MapView) map.getDrawing()).getChildren());
//                    System.out.println(newTile);
//                    System.out.println(oldTile);
//                    System.out.println(map.getTiles());

                    ((MapView) map.getDrawing()).update(); // màj de toute la représentation de la carte
                    ((MapView) map.getDrawing()).initListeners();

                    controller.notifyModification();

//                    // suppression de l'élément temporaire
//                    ((MapView) map.getDrawing()).removeTempElement();
//                    sideBarView.deselectItems();
                }
            }
        });
    }

    // ==================== Getters / setters ====================

    /**
     * Permet de changer la positon associée à l'élément graphique
     */
    public void setPosition(Position pos) {
        tilePosition = pos; // position de la case qui sert de référence
        this.position = pos.getAdded(correctionPos); //position du centre
    }
}