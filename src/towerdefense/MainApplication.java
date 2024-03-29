package towerdefense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import towerdefense.controller.confirmwindow.ConfirmWindow;
import towerdefense.controller.generic.GUIController;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class MainApplication extends Application {
    private final String defaultResourcesPath = "resources/";
    private String appTitle = "Tower Defense";
    private Config config;

    public enum SceneType {MENU, EDITOR, GAME, SELECTOR}

    private String selectedMapPath;
    private SceneType nextScene;

    private Stage mainWindow;
    private Parent currentPane;
    private Scene currentScene;
    private GUIController currentController;

    /**
     * Méthode appelée par JavaFX lors de l'ouverture de l'application.
     * Par défaut, ouvre et active la scène "menu"
     * Le reste du programme est géré par les controllers de chaque scène.
     */
    @Override
    public void start(Stage stage) throws IOException {
        //========== Initialisation du modèle de la l'application ==========
        assertDirectoryStructure();
        config = new Config(defaultResourcesPath);
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.lcdtext", "false");

        //========== Initialisation de javafx ==========
        mainWindow = stage;
        currentScene = new Scene(new Pane());
        setCurrentSceneTo(SceneType.MENU);
        mainWindow.setScene(currentScene);
        mainWindow.setTitle(appTitle);
        //mainWindow.setWidth(windowWidth);
        //mainWindow.setHeight(windowHeight);
        mainWindow.show();
    }

    private void assertDirectoryStructure() throws IOException {
        // Vérification que le dossier resources se trouve au bon endroit
        File file = new File(defaultResourcesPath);
        String errorMsg = "' directory/file missing";

        // Liste des sous-dossiers supposés contenus dans le dossier "resources"
        ArrayList<String> assumedFileStructure = new ArrayList<>();
        assumedFileStructure.add("config");
        assumedFileStructure.add("graphics");
        assumedFileStructure.add("maps");
        assumedFileStructure.add("shops");

        // On vérifie d'abord que le dossier 'resources' existe
        if (file.isDirectory()) {
            // On vérifie que ce dossier contient tous les sous-dossiers nécessaires
            ArrayList<String> subFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(file.list())));
            for (String f : assumedFileStructure) {
                if (!(subFiles.contains(f))) {
                    throw new IOException("'" + f + errorMsg);
                }
            }

            // On vérifie que le dossier config contient tout ce qu'il faut
            File configDir = new File(defaultResourcesPath + "/config");
            ArrayList<String> configDirFiles = new ArrayList<>(Arrays.asList(Objects.requireNonNull(configDir.list())));
            if (!(configDirFiles.contains("config.properties"))) {
                throw new IOException("'" + "config.properties" + errorMsg);
            }
        } else {
            throw new IOException(errorMsg);
        }
    }

    public Config getConfig() {
        return config;
    }

    public String getDefaultResourcesPath() {
        return defaultResourcesPath;
    }

    public String getSelectedMapPath() {
        return selectedMapPath;
    }

    public void setSelectedMapPath(String selectedMap) {
        this.selectedMapPath = selectedMap;
    }

    public SceneType getNextScene() {
        return nextScene;
    }

    public void setNextScene(SceneType nextScene) {
        this.nextScene = nextScene;
    }

    /**
     * Méthode permettant de changer de scène
     */
    public void setCurrentSceneTo(SceneType sceneType) throws IOException {
        String sceneTypePath;
        switch (sceneType) {
            case GAME:
                sceneTypePath = "controller/game/GameFXML.fxml";
                break;
            case MENU:
                sceneTypePath = "controller/menu/MenuFXML.fxml";
                break;
            case EDITOR:
                sceneTypePath = "controller/map/editor/MapEditorFXML.fxml";
                break;
            case SELECTOR:
                sceneTypePath = "controller/map/selector/MapSelectorFXML.fxml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sceneType);
        }

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource(sceneTypePath)); // Initialisation du loader avec le bon chemin
        currentPane = loader.load(); // On récupère le contenu de la scène

        currentController = loader.getController(); // On récupère le controlleur associé au FXML
        currentController.setMainApplication(this); // On passe la référence de l'applicatin au controller

        currentScene.setRoot(currentPane);
    }

    public boolean confirmWindow(String msg, String msgYES, String msgNO, String windowTitle) {
        return ConfirmWindow.askUser(msg, msgYES, msgNO, windowTitle, mainWindow);
    }

    public Stage getMainWindow() {
        return mainWindow;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Méthode main qui est exécutée par Java lors du démarrage du programme.
     * Charge le fichier de réglages (config.properties)
     */
    public static void main(String[] args) {
        launch(args);
    }
}