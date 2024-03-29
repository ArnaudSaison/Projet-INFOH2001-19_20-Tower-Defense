package towerdefense.view.editor;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import towerdefense.controller.map.editor.MapEditorController;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.TileType;

import java.io.InputStream;

public class SideBarTilesSelector extends VBox {
    // ==================== Attriuts ====================
    private Map map;
    private int tilesReprHeight = 50; //px
    private MapEditorController controller;

    // ==================== Attriuts ====================

    /**
     * Constructeur qui crée la HBox et y insère tous les éléments nécessaires
     */
    public SideBarTilesSelector(Pane targetPane, VBox parentBox, Map map, MapEditorController controller) {
        super(); // Constructeur JavaFX
        this.map = map;
        this.controller = controller;

        this.getStyleClass().add("tile-selector-vbox");

        for (TileType type : MapFactory.getTileTypesList()) {
            this.getChildren().add(getTileSidebarRepr(type));
        }

        this.setPrefWidth(260);
    }

    private VBox getTileSidebarRepr(TileType type) {
        String imagePath = "../../../resources/graphics/" + MapFactory.getTileGraphics(type);

        // Chargement de l'image
        InputStream input = getClass().getResourceAsStream(imagePath);
        javafx.scene.image.Image image = new Image(input, 100, 100, true, false);
        ImageView texture = new ImageView();

        // Réglages de l'image
        texture.setImage(image);
        texture.setFitHeight(tilesReprHeight);
        texture.setPreserveRatio(true);
        texture.setSmooth(false);
        texture.setCache(true);

        // Ajout de l'iamge au conteneur
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getStyleClass().add("tile-selector-container-hbox");

        // Ajout du nom de la case
        Label name = new Label(MapFactory.getTileName(type));
        name.getStyleClass().add("tile-label-name");

        // Ajout d'une desciption
        Label description = new Label(MapFactory.getTileDescription(type));
        description.getStyleClass().add("tile-label-description");

        // Assemblage
        hBox.getChildren().add(texture);
        hBox.getChildren().add(description);

        VBox vBox = new VBox();
        vBox.getStyleClass().add("tile-selector-container-vbox");
        vBox.getChildren().add(name);
        vBox.getChildren().add(hBox);

        // Attacher le listener
        vBox.setOnMouseClicked(new PlaceTileListener(map, type, vBox, this, controller));

        return vBox;
    }

    // Sélection d'une tile (doit se trouver dans cette classe afin de pouvoir modifier tous les boutons)
    public void setSelectedItem(Node item) {
        deselectItems();
        item.getStyleClass().add("tile-selector-container-vbox-selected");
    }

    public void deselectItems() {
        for (Node elem : this.getChildren()) {
            elem.getStyleClass().remove("tile-selector-container-vbox-selected");
        }
    }

    public boolean getSelectedState(Node item) {
        return item.getStyleClass().contains("tile-selector-container-vbox-selected");
    }


}
