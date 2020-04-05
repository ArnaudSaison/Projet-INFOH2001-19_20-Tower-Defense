import gui.game.GameController;
import gui.menu.MenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController extends Application {
    private Scene menu;
    private Parent menuPane;
    private Stage mainWindow;


    @Override
    public void start(Stage stage) throws Exception {
        this.mainWindow = stage;

        menuPane = FXMLLoader.load(getClass().getResource("gui/menu/MenuFXML.fxml"));
        menu = new Scene(menuPane);

        mainWindow.setTitle("Tower Defense");
        mainWindow.setScene(menu);
        mainWindow.show();


        /*mainWindow = stage;

        menuPane = new BorderPane();
        gamePane = new BorderPane();

        mainMenuScene = new Scene(menuPane, windowWidth, windowHeight);
        gameScene = new Scene(gamePane, windowWidth, windowHeight);
        mainWindow.setScene(mainMenuScene);

        CreateMainMenuContent();
        CreateGameSceneContent();

        mainWindow.setTitle("Tower Defense");
        mainWindow.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
