package towerdefense.view.shop;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import towerdefense.game.map.Map;
import towerdefense.game.model.Shop;

import java.io.InputStream;
import java.util.ArrayList;

public class ShopView extends VBox {
    // ==================== Attributs ====================
    private Map map;
    private Shop shop;
    private Pane targetPane;
    private int reprHeight = 50; //px

    // ==================== Initialisation ====================
    public ShopView(Pane targetPane, Map map, Shop shop) {
        super(); // Constructeur JavaFX
        this.shop = shop;
        this.map = map;
        this.targetPane = targetPane;

        this.getStyleClass().add("shop-vbox");

        for (Shop.ShopCases item : Shop.getBuyableItems()) {
            this.getChildren().add(getSidebarRepr(item));
        }

        this.setPrefWidth(260);
    }

    private VBox getSidebarRepr(Shop.ShopCases item) {
        String imagePath = "../../../resources/graphics/" + Shop.getIconPath(item);

        // Chargement de l'image
        InputStream input = getClass().getResourceAsStream(imagePath);
        javafx.scene.image.Image image = new Image(input, 100, 100, true, false);
        ImageView texture = new ImageView();

        // Réglages de l'image
        texture.setImage(image);
        texture.setFitHeight(reprHeight);
        texture.setPreserveRatio(true);
        texture.setSmooth(false);
        texture.setCache(true);

        // Ajout de l'iamge au conteneur
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getStyleClass().add("shop-container-hbox");

        // Ajout du nom de la case
        Label name = new Label(Shop.getItemName(item));
        name.getStyleClass().add("item-label-name");

        hBox.getChildren().add(texture);

        VBox propBox = new VBox();
        hBox.getChildren().add(propBox);
        propBox.getStyleClass().add("shop-properties-box");

        // Ajout de des attributs
        ArrayList<Shop.ItemProp> itemProps = Shop.getPropertiesOfItem(item);
        for (Shop.ItemProp prop : itemProps) {
            Label description = new Label(Shop.getPropName(item, prop) + " : " + shop.getItemProp(item, prop, 1));
            description.getStyleClass().add("item-label-description");
            propBox.getChildren().add(description);
        }

        VBox vBox = new VBox();
        vBox.getStyleClass().add("shop-container-vbox");
        vBox.getChildren().add(name);
        vBox.getChildren().add(hBox);

        // Attacher le listener
        vBox.setOnMouseClicked(new ItemClicked(map, item, vBox, shop, this));

        return vBox;
    }

    // Sélection d'un item (doit se trouver dans cette classe afin de pouvoir modifier tous les boutons)
    public void setSelectedItem(Node item) {
        deselectItems();
        item.getStyleClass().add("shop-container-vbox-selected");
    }

    public void deselectItems() {
        for (Node elem : this.getChildren()) {
            elem.getStyleClass().remove("shop-container-vbox-selected");
        }
    }

    public boolean getSelectedState(Node item) {
        return item.getStyleClass().contains("shop-container-vbox-selected");
    }
}
