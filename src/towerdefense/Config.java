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

    // Jeu
    private int initPlayerGold;
    private int initPlayerHealth;
    private int timeBetweenRounds;
    private int timeBetweenNPCs;

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
        ModelFrameRate = Integer.parseInt(settings.getProperty("ModelFrameRate"));
        zoomFact = Double.parseDouble(settings.getProperty("zoomFact"));

        // Jeu
        initPlayerGold = Integer.parseInt(settings.getProperty("initPlayerGold"));
        initPlayerHealth = Integer.parseInt(settings.getProperty("initPlayerHealth"));
        timeBetweenRounds = Integer.parseInt(settings.getProperty("timeBetweenRounds"));
        timeBetweenNPCs = Integer.parseInt(settings.getProperty("timeBetweenNPCs"));
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

    public int getUIFrameRate() {
        return UIFrameRate;
    }

    public int getInitPlayerGold() {
        return initPlayerGold;
    }

    public void setInitPlayerGold(int initPlayerGold) {
        this.initPlayerGold = initPlayerGold;
    }

    public int getInitPlayerHealth() {
        return initPlayerHealth;
    }

    public void setInitPlayerHealth(int initPlayerHealth) {
        this.initPlayerHealth = initPlayerHealth;
    }

    public int getTimeBetweenRounds() {
        return timeBetweenRounds;
    }

    public int getTimeBetweenNPCs() {
        return timeBetweenNPCs;
    }
}
