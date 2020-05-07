package towerdefense.controller.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import towerdefense.Config;
import towerdefense.MainApplication;
import towerdefense.controller.generic.GUIController;
import towerdefense.game.map.Map;
import towerdefense.game.map.MapFactory;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.model.Player;
import towerdefense.game.model.Shop;
import towerdefense.game.npcs.StandardNPC;
import towerdefense.game.towers.StandardTower;
import towerdefense.game.waves.WaveFactory;
import towerdefense.view.map.MapView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Le GameController est instancié par JavaFX lors de la lecture du fichier FXML
 */
public class GameController implements Initializable, GUIController {
    // ==================== Attributs nécessaires au fonctionnement de javafx ====================
    @FXML
    private HBox gameInfoBar;
    @FXML
    private HBox gameHealthInfoBar;
    @FXML
    private Text gameHealthInfoBarText;
    @FXML
    private HBox gameGoldInfoBar;
    @FXML
    private Text gameGoldInfoBarText;
    @FXML
    private HBox gameRoundInfoBar;
    @FXML
    private Text gameRoundInfoBarText;
    @FXML
    private HBox gameScoreInfoBar;
    @FXML
    private Text gameScoreInfoBarText;
    @FXML
    private Pane mapPlaceHolder;
    @FXML
    private StackPane gameBox;
    @FXML
    private VBox sidebar;
    @FXML
    private Button ResetViewButton;
    @FXML
    private VBox shopItemsBox;
    @FXML
    private Button pauseButton;

    // ==================== Attributs nécessaires à la liaison avec le modèle ====================
    // Liaison avec le modèle
    private MapFactory mapFactory;
    private Map map;
    private MapView mapView;

    private GameModel gameModel;
    private Shop shop;
    private Player player;

    // liaison avec l'application principale
    private Config config;
    private MainApplication mainApplication;

    // Timers
    private Timeline UITimer;
    private Timeline ViewTimer;

    // Déplacement de la carte
    private double deltaXDrag;
    private double deltaYDrag;

    // Nécessaire pour les boutons
    private Image pauseIcon;
    private Image resumeIcon;
    private ImageView pauseButtonView;

    // ==================== Initilisations ====================
    // Méthodes requises par FXML

    /**
     * Constructeur du controller : est exécuté avant la lecture du FXML
     */
    public GameController() {
    }

    /**
     * Méthode appellée par JavaFX après initilisation du FXML
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // Méthodes rajoutées pour permettre l'initilisation du jeu

    /**
     * Méthode appelée par le MainApplication pour passer des arguments à ce controller
     *
     * @param main application qui a créé le controller
     */
    public void setMainApplication(MainApplication main) {
        this.mainApplication = main;

        // Initilisation du modèle
        String mapPath = mainApplication.getSelectedMapPath();
        mainApplication.setSelectedMapPath(null); // Réinitilisation pour éviter que le jeu ne puisse se lancer sans avoir fait de sélection

        config = mainApplication.getConfig();
        try {
            gameModel = new GameModel(mainApplication.getConfig(), mapPath);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        map = gameModel.getMap();
        shop = gameModel.getShop();
        player = gameModel.getPlayer();
//        shop.initDrawing();

        // Initilisation du bouton de pause
        initPauseButton();

        // Initilisation du shop
        initShopView();

        // Initialisation de la map
        map.initDrawing(); // initialisation de toutes les raprésentations graphiques
        mapView = (MapView) map.getDrawing(); // Ce casting est parmis par déifnition du MCV parttern
        mapPlaceHolder.getChildren().add(0, mapView); // Ajout de la carte à la vue FXML

        // Démarrage de la GUI
        startAllTimers();
    }

    private void initPauseButton() {
        pauseIcon = new Image("/resources/graphics/UI/pause.png", 21, 21, true, true);
        resumeIcon = new Image("/resources/graphics/UI/play.png", 21, 21, true, true);
        pauseButtonView = new ImageView(resumeIcon);
        pauseButtonView.setFitHeight(20);
        pauseButtonView.setFitWidth(20);
        pauseButton.setGraphic(pauseButtonView);
    }

    private void initShopView() {
//        shopItemsBox.initDrawing();
//        shopItemsBox.getChildren().addAll(shop.getDrawing);
    }

    // ==================== Fonctionnement du controller ====================
    // Updates

    /**
     * Méthode qui définit l'ensemble des actions à effectuer pour mettre à jour la GUI
     */
    private void updateUI() {
//        gameRoundInfoBarText.setText("Round" + gameModel.getCurrentRound());
        gameHealthInfoBarText.setText(player.getHealth() + "/" + player.getMaxHealth());
        gameGoldInfoBarText.setText(String.valueOf(player.getGold()));
        gameScoreInfoBarText.setText("Score: " + player.getScore());
//        j ++;
    }

    /**
     * Méthode qui déifnit l'ensemble des actions à effectuer pour mettre à jour l'UI de la carte seulement
     */
    private void updateView() {
        mapView.updateDrawables();
//        i ++;
    }

    // Initilisation des timers

    /**
     * Initialisation d'un obet Timeline qui met à jour la GUI
     */
    private void runUI() {
        UITimer = new Timeline(new KeyFrame(Duration.millis((int) (1.0 / config.getUIFrameRate() * 1000)), actionEvent -> updateUI()));
        UITimer.setCycleCount(Animation.INDEFINITE);
        UITimer.play();
    }

    /**
     * Initialisation d'un obet Timeline qui met à jour tous les éléments sur la carte (mais pas les tiles)
     */
    private void runView() {
        ViewTimer = new Timeline(new KeyFrame(Duration.millis((int) (1.0 / config.getFrameRate() * 1000)), actionEvent -> updateView()));
        ViewTimer.setCycleCount(Animation.INDEFINITE);
        ViewTimer.play();
    }

    // Gestion des timers

    /**
     * Méthode qui arrête tous les timers
     */
    private void stopAllTimers() {
        UITimer.stop();
        ViewTimer.stop();
//        double k = i / j;
//        System.out.println("modèle : " + i + ", UI :" + j + ", proportion modèle / UI (attendu : 1.5) : " + k);
    }
//    double i = 0.0;
//    double j = 0.0;

    /**
     * Méthode qui démarre tous les timers
     */
    private void startAllTimers() {
        runUI();
        runView();
    }

    // Arrêter le jeu et revenir au menu
    private void quitGame() throws IOException {
        gameModel.stopGame();
        stopAllTimers();
        mainApplication.setCurrentSceneTo(MainApplication.SceneType.MENU);
    }

    // ==================== Gestion des éléments FXML ====================
    // Boutons de la GUI

    /**
     * Bouton permettant de quitter le jeu : affiche une boîte de dialogue pour demander confirmation
     */
    @FXML
    public void handleQuitGameButtonClicked(MouseEvent event) throws IOException {
//        if (!gameModel.getIsRunning) {
        if (gameModel.getRunning()) {
            boolean answer = mainApplication.confirmWindow(
                    "The Game is not finished yet. Do you want to quit anyway ?",
                    "Quit Game",
                    "Go back to Game",
                    "Confirm");
            if (answer) {
                quitGame();
            }
        } else {
            quitGame();
        }
    }

    //TODO: mauvais bouton
    /**
     * Bouton qui gère le démarrage, la mise en pause et la reprise du jeu
     */
    @FXML
    public void handlePauseGameButtonClicked(MouseEvent event) {
        if (gameModel.getPaused()) {
            pauseButton.setText("Pause Game");
            pauseButtonView.setImage(pauseIcon);

            gameModel.resumeGame();

        } else {
            pauseButton.setText("Resume Game");
            pauseButtonView.setImage(resumeIcon);

            gameModel.pauseGame();
        }

        if (!gameModel.getRunning()) {
            gameModel.initialize();

            //TODO: supprimer (test)
            gameModel.initializeElement(new StandardNPC(map, gameModel, 100, 10, 100, 10, map.getGates().get(0), WaveFactory.NPCTypes.STANDARD_NPC));
            gameModel.getNPCsOnMap().get(0).decreaseHealth(20);
            boolean placed1 = shop.buyPlaceable(Shop.ShopCases.STANDARD_TOWER, new Position(3, 4, map));
            player.increaseGold(500);
            boolean placed2 = shop.buyPlaceable(Shop.ShopCases.GOLD_MINE, new Position(3, 6, map));
            System.out.println(placed1 + " " + placed2);
        }
    }

    // Déplacements et zoom de la carte

    /**
     * Gestion du zoom sur la carte
     */
    @FXML
    public void handleZoomScroll(ScrollEvent event) {
        mapView.updateZoomLevel(event);
    }

    /**
     * Gestion du déplacement de la carte, calcul du déplacement
     */
    @FXML
    public void handleMousePressedDelta(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            deltaXDrag = mapView.getLayoutX() - event.getX();
            deltaYDrag = mapView.getLayoutY() - event.getY();

        }
    }

    /**
     * Gestion du déplacement de la carte, déplacement
     */
    @FXML
    public void handleMouseDraggedMap(MouseEvent event) {
        if (event.isSecondaryButtonDown()) {
            mapView.setLayoutX(event.getX() + deltaXDrag);
            mapView.setLayoutY(event.getY() + deltaYDrag);
        }
    }

    /**
     * Bouton permettant de remettre la carte à son origine et de réinitialiser son zoom
     */
    @FXML
    public void handleResetViewButton(MouseEvent event) {
        mapView.setLayoutX(0);
        mapView.setLayoutY(0);
        map.resetPixelsPerMeter();
        map.updateDrawing();
    }
}
