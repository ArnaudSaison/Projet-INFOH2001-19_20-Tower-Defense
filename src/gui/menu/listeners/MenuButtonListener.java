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
        this.scene = scene;
    }

    @Override
    public void handle(MouseEvent event){
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            window.setScene(scene);
            System.out.println(scene);
        }
    }
}
