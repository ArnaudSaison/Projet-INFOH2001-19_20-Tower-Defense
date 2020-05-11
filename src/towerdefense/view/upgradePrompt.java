package towerdefense.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import towerdefense.game.Upgradable;
import towerdefense.game.model.Shop;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;

public class upgradePrompt extends VBox {

    public upgradePrompt(Shop.ShopCases itemID, Upgradable item, Shop shop, Printable parent) {
        super();
        // style ddu popup
        this.getStyleClass().add("info-bubble-vbox");

        Button closeButton = new Button("Close");
        ImageView closeImage = new ImageView(new Image("./resources/graphics/UI/close.png"));
        closeImage.setFitWidth(10);
        closeImage.setFitHeight(10);
        closeButton.setGraphic(closeImage);
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnMouseClicked(mouseEvent -> {
            ((Pane) parent).getChildren().remove(this); // cast autorisé car tout élément sur la carte est contenu dans un pane ou une sous classe de pane
        });

        Label nameLabel = new Label(Shop.getItemName(itemID));
        nameLabel.getStyleClass().add("prompt-item-name");

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_LEFT);

        hBox.getChildren().add(closeButton);
        hBox.getChildren().add(nameLabel);

        // création du gridpane qui contient les informations sur les attributs actuels et ceux de l'upgrade suivant
        GridPane gridPane = new GridPane();

        int xprop0 = 1;
        int yprop0 = 0;

        // Récupération des propriétés et niveau
        int level = item.getLevel();
        ArrayList<Shop.ItemProp> propertiesIDs = Shop.getPropertiesOfItem(itemID);
        propertiesIDs.remove(Shop.ItemProp.PRICE);
        int i = 0; // colonne
        for (Shop.ItemProp propID : propertiesIDs) {
            Label name = new Label(Shop.getPropName(itemID, propID));
            Label current = new Label(String.valueOf(shop.getItemProp(itemID, propID, level)));

            GridPane.setConstraints(name, i, 0);
            GridPane.setConstraints(current, i, 1);

            gridPane.getChildren().addAll(name, current);

            if (level < item.getMaxLevel()) { // si on n'est pas au niveau maximum
                Label next = new Label(String.valueOf(shop.getItemProp(itemID, propID, level + 1)));
                next.getStyleClass().add("next-level-label");
                GridPane.setConstraints(next, i, 2);
                gridPane.getChildren().add(next);
            }

            i++;
        }

        if (level < item.getMaxLevel()) {
            // Bouton pour upgrade
            Button upgradeButton = new Button("Upgrade for " + shop.getItemProp(itemID, Shop.ItemProp.PRICE, level + 1));
            upgradeButton.setGraphic(new ImageView(new Image("./resources/graphics/UI/ingot.png")));
            upgradeButton.setContentDisplay(ContentDisplay.RIGHT);
            upgradeButton.getStyleClass().add("upgrade-button");

            GridPane.setConstraints(upgradeButton, 1, 3, propertiesIDs.size(), 1);
            gridPane.getChildren().add(upgradeButton);

            // EventHandler pour le levelUp
            upgradeButton.setOnMousePressed(mouseEvent -> {
                if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    item.levelUp();
                    ((Pane) parent).getChildren().remove(this); // cast autorisé car tout élément sur la carte est contenu dans un pane ou une sous classe de pane
                }
            });

        } else {
            // Label pour dire qu'on ne peut plus upgrade
            Label noUpgrade = new Label("This element can't be upgraded.");
            noUpgrade.getStyleClass().add("no-upgrade-label");

            GridPane.setConstraints(noUpgrade, 0, 2, propertiesIDs.size(), 1);
            gridPane.getChildren().add(noUpgrade);
        }

        // Style
        gridPane.getStyleClass().add("level-gridpane");

        // Ajout du gridpane
        this.getChildren().add(hBox);
        this.getChildren().add(gridPane);

        // Placement au bon endroit
        gridPane.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);
    }
}
