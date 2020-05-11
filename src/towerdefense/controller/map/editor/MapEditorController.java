package towerdefense.controller.map.editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import towerdefense.MainApplication;
import towerdefense.controller.generic.GUIController;
import towerdefense.game.Buyable;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.view.map.MapView;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapEditorController implements Initializable, GUIController {
    // ==================== Attributs nécessaires au fonctionnement de javafx ====================
    @FXML
    private Pane mapPlaceHolder;
    @FXML
    private HBox saveNewHBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveNewButton;
    @FXML
    private TextField saveNewTextField;
    @FXML
    private HBox sizeSelectorHBox;
    @FXML
    private TextField newMapColumns;
    @FXML
    private TextField newMapRows;
    @FXML
    private Button createNewMapButton;
    @FXML
    private Button resetViewButton;
    @FXML
    private TextField pixelsPerMeterField;
    @FXML
    private VBox vBoxTilesSideBar;
    @FXML
    private ScrollPane scrollPaneTilesSideBar;

    // ==================== Autres attributs ====================
    private MainApplication mainApplication;
    private boolean isSaved;
    private boolean isNewFile;

    private DirectoryChooser directoryChooser;
    private MapFactory mapFactory;
    private Map map;
    private MapView mapView;
    private String mapPath;

    private String currentFileName;
    private String directoryPath;

    private int newMapRowsValue = 0;
    private int newMapColumnsValue = 0;
    private int maxColumnsRows = 50;

    // Utilisation des RegEx pour vérifier que le nom du fichier et les dimensions de la carte sonts valides
    private Pattern namePattern;
    private Matcher nameMatcher;
    private String NAME_PATTERN;

    private Pattern mapDimensionPattern;
    private Matcher mapDimensionMatcher;
    private String MAP_PATTERN;

    // Attributs pour la carte
    private double deltaXDrag;
    private double deltaYDrag;

    // SideBar
    private int maxPixelsPerMeter = 200;
    private SideBarTilesSelector sideBarTilesSelector;

    // ==================== Initilisations ====================

    /**
     * Constructeur du controller
     */
    public MapEditorController() {
        isSaved = true; // encore aucune modification donc pas besoin de faire les vérifications nécessaires
        isNewFile = false; // pas encore de nouveua fichier

        // Sélection de fichiers
        directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("resources/maps"));
        directoryChooser.setTitle("Choose a map directory to open ...");

        // Vérification du nom de fichier
        NAME_PATTERN = "^[a-zA-Z0-9ÀÁÂÃÄÅàáâãäåÒÓÔÕÖØòóôõöøÈÉÊËèéêëÇçÌÍÎÏìíîïÙÚÛÜùúûüÿÑñ_-]{1,50}$"; // définition du regex
        namePattern = Pattern.compile(NAME_PATTERN);

        MAP_PATTERN = "^[0-9]{1,2}$";
        mapDimensionPattern = Pattern.compile(MAP_PATTERN);

        // MapFactory
        mapFactory = new MapFactory();
    }

    /**
     * Initilisation du controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Connection avec l'appliation
     */
    public void setMainApplication(MainApplication main) {
        this.mainApplication = main;
        directoryPath = mainApplication.getDefaultResourcesPath() + "maps";

        mapPath = mainApplication.getSelectedMapPath();
        mainApplication.setSelectedMapPath(null); // Réinitilisation pour éviter que le jeu ne puisse se lancer sans avoir fait de sélection

        if (mapPath != null) {
            initMap();
        }

        // Sélecteur de cases pour éditer la carte
        sideBarTilesSelector = new SideBarTilesSelector(mapPlaceHolder, vBoxTilesSideBar, map);
        scrollPaneTilesSideBar.setContent(sideBarTilesSelector);
    }

    /**
     * Initiliser une carte à l'écran
     */
    private void initMap() {
        terminateMap();
        mainApplication.getMainWindow().setTitle("Tower Defense. Currently working on: " + mapPath);

        // Initilisation de la map
        try {
            map = mapFactory.getMap(mapPath);

            map.initDrawing(); // initialisation de toutes les raprésentations graphiques
            mapView = (MapView) map.getDrawing(); // Ce casting est parmis par déifnition du MCV parttern
            mapPlaceHolder.getChildren().add(0, mapView); // Ajout de la carte à la vue FXML

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // GUI de l'éditeur
        saveButton.setVisible(true);
        resetViewButton.setVisible(true);
    }

    private void initMap(Map map) {
        terminateMap();
        // Initilisation de la map
        map.initDrawing(); // initialisation de toutes les raprésentations graphiques
        mapView = (MapView) map.getDrawing(); // Ce casting est parmis par déifnition du MCV parttern
        mapPlaceHolder.getChildren().add(0, mapView); // Ajout de la carte à la vue FXML

        // GUI de l'éditeur
        resetViewButton.setVisible(true);
    }

    private void terminateMap() {
        mapPlaceHolder.getChildren().clear();
    }

    // ==================== Fonctionnement du controller ====================

    // ==================== Gestion des éléments FXML ====================
    // ******************** Retour au menu ********************
    /**
     * Gestion du bouton pour retourner au menu principal
     */
    @FXML
    public void handleBackToMenuButtonClicked(MouseEvent event) throws IOException {
        boolean answer = true;
        if (!isSaved) {
            answer = mainApplication.confirmWindow(
                    "Unsaved changes will be lost. Do you want to quit anyway ?",
                    "Yes",
                    "No",
                    "Confirm");
        }

        if (answer) {
            mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
        }
    }

    // ******************** Sauvegarder une carte déjà existante ********************
    /**
     * Gestion du bouton pour sauvegarder
     */
    @FXML
    public void handleSaveButtonClicked(MouseEvent event) throws IOException {
        isSaved = true;
    }

    // ******************** Sauvegarder un nouveau fichier ********************
    /**
     * Gestion du bouton pour sauvegarder un nouvrau fichier
     */
    @FXML
    public void handleSaveNewButtonClicked(MouseEvent event) throws IOException {
        isNewFile = false;
        isSaved = true;
        saveNewHBox.setVisible(false);
        saveButton.setVisible(true);
    }

    /**
     * Chanmp dans lequel on doit entrer le nom de la nouvelle carte
     */
    @FXML
    public void handleSaveNewTextFieldTyped(KeyEvent keyEvent) {
        currentFileName = saveNewTextField.getText();
        if (!validateFileName(currentFileName)) {
            saveNewTextField.setStyle("-fx-text-fill: red;");//TODO: corriger bug saver snas bon nom de fichier
        } else {
            saveNewTextField.setStyle("-fx-text-fill: black;");
        }
    }

    /**
     * Validation du nom de fichier proposé par l'utilisateur
     */
    private boolean validateFileName(String name) {
        nameMatcher = namePattern.matcher(name);
        return nameMatcher.matches();
    }

    // ******************** Créer un nouveau fichier ********************
    /**
     * Gestion du bouton pour créer un nouveau fichier
     */
    @FXML
    public void handleNewFileButtonClicked(MouseEvent event) throws IOException {
        boolean answer = true;
        if (!isSaved) {
            answer = mainApplication.confirmWindow(
                    "Unsaved changes will be lost. Do you want to quit anyway ?",
                    "Yes",
                    "No",
                    "Confirm");
        }

        if (answer) {
            sizeSelectorHBox.setVisible(true);
        }
    }

    /**
     * Gestion du bouton pour créer une nouvelle carte à partir de 0
     */
    @FXML
    public void handleCreateNewMapButtonClicked(MouseEvent event) {
        disableNewMapPrompt();

        // Créatin et initilisation du nouveau fichier
        newFile();
    }

    /**
     * Bouton pour annuler la création d'une nouvelle carte
     */
    @FXML
    public void handleCancelNewMapButtonClicked(MouseEvent event) {
        disableNewMapPrompt();
    }

    /**
     * Champ pour donner le nombre de colonnes de la nouvelle carte
     */
    @FXML
    public void handleNewMapRowsTyped(KeyEvent event) {
        String newDimText = newMapRows.getText();
        if (!validateNewMapDimension(newDimText)) {
            newMapRows.setStyle("-fx-text-fill: red;");
            newMapRowsValue = 0;
        } else {
            newMapRows.setStyle("-fx-text-fill: black;");
            newMapRowsValue = Integer.parseInt(newMapRows.getText());
        }
        validateNewMap();
    }

    /**
     * Champ pour donner le nombre de lignes de la nouvelle carte
     */
    @FXML
    public void handleNewMapColumnsTyped(KeyEvent event) {
        String newDimText = newMapColumns.getText();
        if (!validateNewMapDimension(newDimText)) {
            newMapColumns.setStyle("-fx-text-fill: red;");
            newMapColumnsValue = 0;
        } else {
            newMapColumns.setStyle("-fx-text-fill: black;");
            newMapColumnsValue = Integer.parseInt(newMapColumns.getText());
        }
        validateNewMap();
    }

    /**
     * vérification du contenu des champs pour donner la taille d'une nouvelle carte
     */
    private boolean validateNewMapDimension(String mapDimension) {
        mapDimensionMatcher = mapDimensionPattern.matcher(mapDimension);

        boolean res = false;
        if (mapDimensionMatcher.matches()) {
            if (Integer.parseInt(mapDimension) <= maxColumnsRows) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Vérification que les entrées pour créer une nouvelle carte sont valides
     */
    private void validateNewMap() {
        if (newMapColumnsValue > 0 && newMapColumnsValue <= maxColumnsRows && newMapRowsValue > 0 && newMapRowsValue <= maxColumnsRows) {
            createNewMapButton.setDisable(false);
        } else {
            createNewMapButton.setDisable(true);
        }
    }

    /**
     * Masquer le panneau pour créer une nouvelle carte
     */
    private void disableNewMapPrompt() {
        newMapRows.setText("");
        newMapColumns.setText("");
        sizeSelectorHBox.setVisible(false);
        createNewMapButton.setDisable(true);
    }

    /**
     * Actions graphiques pour créer un nouveau fichier
     */
    public void newFile() {
        map = mapFactory.newMap(newMapColumnsValue, newMapRowsValue);
        initMap(map);
        isNewFile = true;
        isSaved = false;

        saveNewHBox.setVisible(true);
        saveButton.setVisible(false);
    }

    // ******************** Charger un fichier ********************
    /**
     * Gestion du bouton pour charger un fichier
     */
    @FXML
    public void handleLoadFileButtonClicked(MouseEvent event) throws IOException {
        boolean answer = true;
        if (!isSaved) {
            answer = mainApplication.confirmWindow(
                    "Unsaved changes will be lost. Do you want to quit anyway ?",
                    "Yes",
                    "No",
                    "Confirm");
        }

        if (answer) {
            saveNewHBox.setVisible(false);
            saveButton.setVisible(true);

            mainApplication.setNextScene(MainApplication.SceneType.EDITOR);
            mainApplication.setCurrentSceneTo(MainApplication.SceneType.SELECTOR);
        }
    }

    // ******************** Ouvrir le dossier contenant les maps ********************
    /**
     * Ouvrir le dossier contenant toutes les maps
     */
    @FXML
    public void handleOpenMapDirectoryButtonCLicked(MouseEvent event) throws IOException {
        Desktop.getDesktop().open(new File(directoryPath));
    }

    // ******************** Gestion de la SideBar ********************
    @FXML
    public void handlePixelsPerMeterFieldTyped() {
        if (map != null) {
            String newPixelsPerMeter = pixelsPerMeterField.getText();
            if (!validatePixelsPerMeter(newPixelsPerMeter)) {
                pixelsPerMeterField.setStyle("-fx-text-fill: red;");
            } else {
                pixelsPerMeterField.setStyle("-fx-text-fill: black;");
                int newPixelsPerMeterValue = Integer.parseInt(pixelsPerMeterField.getText());
                map.setPixelsPerMeter(newPixelsPerMeterValue);
                map.setSettingsPixelsPerMeter(newPixelsPerMeterValue);
                map.updateDrawing();
                isSaved = false;
            }
        }
    }

    /**
     * Vérification que l'échelle donnée est acceptable (nombre entre 1 et  maxPixelsPerMeter)
     */
    private boolean validatePixelsPerMeter(String pxPerMeter) {
        boolean res = false;
        try {
            int value =  Integer.parseInt(pxPerMeter);
            if (value > 0 && value <= maxPixelsPerMeter) {
                res = true;
            }
        } catch (NumberFormatException ignored) {
        }
        return res;
    }


// ==================== Gestion de la carte ====================
    /**
     * Gestion du zoom sur la carte
     */
    @FXML
    public void handleZoomScroll(ScrollEvent event) {
        if (mapView != null) {
            mapView.updateZoomLevel(event);
        }
    }

    /**
     * Gestion du déplacement de la carte, calcul du déplacement
     */
    @FXML
    public void handleMousePressedDelta(MouseEvent event) {
        if (mapView != null) {
            if (event.isSecondaryButtonDown()) {
                deltaXDrag = mapView.getLayoutX() - event.getX();
                deltaYDrag = mapView.getLayoutY() - event.getY();
            }
        }
    }

    /**
     * Gestion du déplacement de la carte, déplacement
     */
    @FXML
    public void handleMouseDraggedMap(MouseEvent event) {
        if (mapView != null) {
            if (event.isSecondaryButtonDown()) {
                mapView.setLayoutX(event.getX() + deltaXDrag);
                mapView.setLayoutY(event.getY() + deltaYDrag);
            }
        }
    }

    /**
     * Bouton permettant de remettre la carte à son origine et de réinitialiser son zoom
     */
    @FXML
    public void handleResetViewButton(MouseEvent event) {
        if (mapView != null) {
            mapView.setLayoutX(0);
            mapView.setLayoutY(0);
            map.resetPixelsPerMeter();
            map.updateDrawing();
        }
    }

    // ==================== Getters et setters ====================
    /**
     * Changer l'état de sauvegarde
     */
    public void setSavedState(boolean state) {
        isSaved = state;
    }
}