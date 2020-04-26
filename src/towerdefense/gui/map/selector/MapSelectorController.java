package towerdefense.gui.map.selector;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import towerdefense.MainApplication;
import towerdefense.gui.generic.GUIController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

public class MapSelectorController implements Initializable, GUIController {
    private MainApplication mainApplication;
    private String directoryPath;
    private HBox selectedMapElement;

    // Attributs FXML
    @FXML private ScrollPane scrollPane;
    @FXML private VBox mapListBox;

    // Initialisation du controller
    public MapSelectorController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedMapElement = null;
    }

    public void updateMaps() throws IOException {
        mapListBox.getChildren().clear();
        directoryPath = mainApplication.getDefaultResourcesPath() + "maps";
        File mapsDirectory = new File(directoryPath);

        if (mapsDirectory.isDirectory()) {
            ArrayList<String> fileList = new ArrayList<>(Arrays.asList(mapsDirectory.list()));
            Collections.sort(fileList);
            for (String file : fileList) {
                File probedFile = new File(directoryPath + "/" + file);
                if (probedFile.isDirectory()) {
                    HBox mapElement = new HBox();
                    mapElement.getStyleClass().add("mapelement");
                    mapElement.getChildren().add(new Text(file));
                    mapElement.setOnMouseClicked(new MapClickedListener(this, mapElement, directoryPath + "/" + file));

                    mapListBox.getChildren().add(mapElement);
                }
            }
        }
    }

    //Getters et Setters
    public void setMainApplication(MainApplication main){
        this.mainApplication = main;

        // On peut à présent initialiser tout ce qui requiert une référence à mainApplication
        try {
            updateMaps();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void setSelectedMapPath(String mapPath, HBox mapElement){
        if (selectedMapElement != null) {
            selectedMapElement.setStyle("-fx-background-color: #a6a6a6;");
        }
        selectedMapElement = mapElement;
        mapElement.setStyle("-fx-background-color: #33cc33;");
        mainApplication.setSelectedMapPath(mapPath);
    }

    // Gestion des éléments FXML
    @FXML
    public void handleBackToMenuButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
    }

    @FXML
    public void handleRefreshButtonClicked(MouseEvent event) throws IOException {
        updateMaps();
    }

    @FXML
    public void handleContinueButtonClicked(MouseEvent event) throws IOException {
        mainApplication.setCurrentSceneTo(mainApplication.getNextScene());
    }
}