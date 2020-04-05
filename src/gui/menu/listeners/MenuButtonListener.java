package gui.menu.listeners;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuButtonListener implements EventHandler<MouseEvent> {
    private Stage window;
    private Scene scene;

    public MenuButtonListener(Stage window, Scene scene){
        this.window = window;
    }

    @Override
    public void handle(MouseEvent event){
        window.setScene(scene);
    }
}
