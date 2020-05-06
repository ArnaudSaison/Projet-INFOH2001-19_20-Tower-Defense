package towerdefense;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private String filePath;
    private Properties settings;

    // GUI
    private int screenWidth;
    private int screenHeight;
    private int frameRate;
    private int UIFrameRate;
    private int ModelFrameRate;
    private double zoomFact;
    private int goldminesProductionRate;
    private int goldminesCost;
    private int wavesTimeBeforeFirstWave;
    private int wavesNewEnemiesPerWave;
    private String arrowGatePathTileImageURL;

    //

    // Constructeur et initialisation
    public Config(String path) throws IOException {
        this.filePath = path + "config/config.properties";
        this.loadSettings();
    }

    public void loadSettings() throws IOException {
        settings = new Properties();
        settings.load(new FileInputStream(filePath));

        // Affichage
        screenWidth = Integer.parseInt(settings.getProperty("screenWidth"));
        screenHeight = Integer.parseInt(settings.getProperty("screenHeight"));
        frameRate = Integer.parseInt(settings.getProperty("frameRate"));
        UIFrameRate = Integer.parseInt(settings.getProperty("UIFrameRate"));
        ModelFrameRate =  Integer.parseInt(settings.getProperty("ModelFrameRate"));
        zoomFact = Double.parseDouble(settings.getProperty("zoomFact"));

        // Cases
        arrowGatePathTileImageURL = settings.getProperty("arrowGatePathTileImageURL");

        // Miens d'or
        goldminesProductionRate = Integer.parseInt(settings.getProperty("goldminesProductionRate"));
        goldminesCost = Integer.parseInt(settings.getProperty("goldminesCost"));
    }

    // getters
    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getFrameRate() {
        return frameRate;
    }

    public int getModelFrameRate() {
        return ModelFrameRate;
    }

    public double getZoomFact() {
        return zoomFact;
    }

    public int getGoldminesProductionRate() {
        return goldminesProductionRate;
    }

    public int getGoldminesCost() {
        return goldminesCost;
    }

    public int getWavesTimeBeforeFirstWave() {
        return wavesTimeBeforeFirstWave;
    }

    public int getWavesNewEnemiesPerWave() {
        return wavesNewEnemiesPerWave;
    }

    public String getArrowGatePathTileImageURL() {
        return arrowGatePathTileImageURL;
    }

    public int getUIFrameRate() {
        return UIFrameRate;
    }
}
