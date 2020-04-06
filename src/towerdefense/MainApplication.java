package towerdefense;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import towerdefense.gui.gui.GUIController;

import java.io.IOException;
import java.util.Properties;


public class MainApplication extends Application {
    private static Properties settings;

    public enum SceneType {MENU, EDITOR, GAME}
    private Stage mainWindow;
    private Parent currentPane;
    private GUIController currentController;

    /*
    Méthode appelée par JavaFX lors de l'ouverture de l'application.
    Par défaut, ouvre et active la scène "menu"
    Le reste du programme est géré par les controllers de chaque scène.
     */
    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        setCurrentSceneTo(SceneType.MENU);
        mainWindow.setTitle("Tower Defense");
        mainWindow.show();
    }

    /*
    Méthodes permettant de changer de scène
     */
    public void setCurrentSceneTo(SceneType sceneType) throws IOException {
        String sceneTypePath;
        switch (sceneType) {
            case GAME:
                sceneTypePath = "gui/game/GameFXML.fxml";
                break;
            case MENU:
                sceneTypePath = "gui/menu/MenuFXML.fxml";
                break;
            case EDITOR:
                sceneTypePath = "gui/map/editor/MapEditorFXML.fxml";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sceneType);
        }

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource(sceneTypePath)); // Initialisation du loader avec le bon chemin
        currentPane = loader.load(); // On récupère le contenu de la scène

        currentController = loader.getController(); // On récupère le controlleur associé au FXML
        currentController.setMainApplication(this);

        Scene scene = new Scene(currentPane);
        mainWindow.setScene(scene);
    }

//    private static void loadSettings() throws IOException{
//        Properties settings = new Properties();
//        try {
//
//        } catch (IOException) {
//            ex.printStackTrace();
//        }
//    }

    /*
    Fonction main qui est exécutée par Java lors du démarrage du programme.
    Charge le fichier de réglages (config.properties)
     */
    public static void main(String[] args) {
//        loadSettings();
        launch(args);
    }
}
