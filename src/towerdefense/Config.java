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
    private double zoomFact;
    private int goldminesProductionRate;
    private int goldminesCost;
    private int wavesTimeBeforeFirstWave;
    private int wavesNewEnemiesPerWave;

    //

    // Constructeur et initialisation
    public Config(String path) throws IOException {
        this.filePath = path + "config/config.properties";
        this.loadSettings();
    }

    public void loadSettings() throws IOException {
        settings = new Properties();
        settings.load(new FileInputStream(filePath));

        screenWidth = Integer.parseInt(settings.getProperty("screenWidth"));
        screenHeight = Integer.parseInt(settings.getProperty("screenHeight"));
        frameRate = Integer.parseInt(settings.getProperty("frameRate"));
        zoomFact = Double.parseDouble(settings.getProperty("zoomFact"));

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
}
