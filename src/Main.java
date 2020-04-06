import gui.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage mainWindow;
    private Parent pane;

    @Override
    public void start(Stage stage) throws Exception {
        mainWindow = stage;
        pane = FXMLLoader.load(getClass().getResource("gui/menu/MenuFXML.fxml"));
        Scene scene = new Scene(pane);
        mainWindow.setScene(scene);

        mainWindow.setTitle("Tower Defense");
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
