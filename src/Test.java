// Ceci est juste un test pour voir si tout est bien installé

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class
Test extends Application {

    private Parent createContent() {
        BorderPane borderPane = new BorderPane();
        Button clear = new Button("Clear");
        TextField field = new TextField("Here!");
        Text myText = new Text("Hello");
        borderPane.setTop(myText);
        borderPane.setCenter(field);
        borderPane.setBottom(clear);
        return borderPane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
