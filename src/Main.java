import gui.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    private static Properties settings;
    private Stage mainWindow;
    private Parent pane;

    /*
    Fonction appelée par JavaFX lors de l'ouverture de l'application.
    Par défaut, ouvre et active la scène "menu"
    Le reste du programme est géré par les controllers de chaque scène.
     */
    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        pane = FXMLLoader.load(getClass().getResource("gui/menu/MenuFXML.fxml"));
        Scene scene = new Scene(pane);
        mainWindow.setScene(scene);

        mainWindow.setTitle("Tower Defense");
        mainWindow.show();
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
