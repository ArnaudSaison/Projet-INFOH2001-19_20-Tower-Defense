package towerdefense.game.waves;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;
import towerdefense.gui.map.selector.MapClickedListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class WaveFactory {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Références nécessaires à la création de NPC:
    private Map map;
    private GameModel gameModel;

    public enum NPCTypes {STANDARD_NPC, RAPID_NPC, SUPER_HEALTH_NPC, EXPLOSIVE_RESISTANT_NPC, GLUE_RESISTANT_NPC}

    //Paramètres de configuration lus dans le fichier wave.properties:
    private int standardHealth;
    private int standardSpeed;
    private int standardGoldLoot;
    private int standardHealthLoot;
    private int standardExtraHealth;
    private int standardExtraSpeed;
    private int standardExtraGoldLoot;
    private int standardExtraHealthLoot;

    private int specialHealth;
    private int specialSpeed;
    private int specialGoldLoot;
    private int specialHealthLoot;
    private int specialExtraHealth;
    private int specialExtraSpeed;
    private int specialExtraGoldLoot;
    private int specialExtraHealthLoot;

    //Attributs nécessaires à la récursivité de la méthode getWave:
    private String waveFilePath;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public WaveFactory(Map map, GameModel gameModel, String waveFilePath) {
        this.map = map;
        this.gameModel = gameModel;
        this.waveFilePath = waveFilePath;

        //TODO : Lire plusieurs fichiers properties?
        
    }

    /*==================================================================================================================
                                                   METHODES
    ==================================================================================================================*/

    /** En fonction de la difficulté que le joueur a choisi, renvoie un objet Wave correspondant aux spécifications lus
     * dans le fichier wave.properties.*/

    public Wave getWave(String difficulty, int waveIterator) throws IOException {
        //================================Lecture des propriétés de la vague============================================

        //Chemin d'accès au fichier wave.properties:
        final Properties waveProperties = new Properties();
        InputStream wavePropertiesFile = new FileInputStream(waveFilePath + "/wave.properties");
        waveProperties.load(wavePropertiesFile);

        //Nombre d'ennemis pour la première vague:
        int initialEnemyNumber =  Integer.parseInt(waveProperties.getProperty("initialEnemyNumber"));

        //Spécification des attributs de base des NPCs:
        standardHealth = Integer.parseInt(waveProperties.getProperty("standardHealth"));
        standardSpeed = Integer.parseInt(waveProperties.getProperty("standardSpeed"));
        standardGoldLoot = Integer.parseInt(waveProperties.getProperty("standardGoldLoot"));
        standardHealthLoot = Integer.parseInt(waveProperties.getProperty("standardHealthLoot"));
        standardExtraHealth = Integer.parseInt(waveProperties.getProperty("standardExtraHealth"));
        standardExtraSpeed = Integer.parseInt(waveProperties.getProperty("standardExtraSpeed"));
        standardExtraGoldLoot = Integer.parseInt(waveProperties.getProperty("standardExtraGoldLoot"));
        standardExtraHealthLoot = Integer.parseInt(waveProperties.getProperty("standardExtraHealthLoot"));

        specialHealth = Integer.parseInt(waveProperties.getProperty("specialHealth"));
        specialSpeed = Integer.parseInt(waveProperties.getProperty("specialSpeed"));
        specialGoldLoot = Integer.parseInt(waveProperties.getProperty("specialGoldLoot"));
        specialHealthLoot = Integer.parseInt(waveProperties.getProperty("specialHealthLoot"));
        specialExtraHealth = Integer.parseInt(waveProperties.getProperty("specialExtraHealth"));
        specialExtraSpeed = Integer.parseInt(waveProperties.getProperty("specialExtraSpeed"));
        specialExtraGoldLoot = Integer.parseInt(waveProperties.getProperty("specialExtraGoldLoot"));
        specialExtraHealthLoot = Integer.parseInt(waveProperties.getProperty("specialExtraHealthLoot"));

        //Gestion de la difficulté:
        double easyStandardProportion = Double.parseDouble(waveProperties.getProperty("easyStandardProportion"));
        int easyNPCIncrement = Integer.parseInt(waveProperties.getProperty("easyNPCIncrement"));
        double normalStandardProportion = Double.parseDouble(waveProperties.getProperty("normalStandardProportion"));
        int normalNPCIncrement = Integer.parseInt(waveProperties.getProperty("normalNPCIncrement"));
        double hardStandardProportion = Double.parseDouble(waveProperties.getProperty("hardStandardProportion"));
        int hardNPCIncrement = Integer.parseInt(waveProperties.getProperty("hardNPCIncrement"));

        //============================================Variables locales=================================================

        // Liste des NPCs qui vont composer la vague:
        ArrayList<NPC> waveNPCs = new ArrayList<NPC>();

        // Compteur de NPC de type standard:
        int standards = 0;

        // Nombre d'ennemis que doit comporter la vague au total:
        int enemyNumber;

        //===============================================Gestion des cas================================================

        switch (difficulty.toLowerCase()) {
            case "easy":
                enemyNumber = initialEnemyNumber + waveIterator*easyNPCIncrement; //Le nombre d'ennemi total augmente à chaque nouvelle vague.
                while(waveNPCs.size() < enemyNumber) {}
                break;
            case "normal":
                enemyNumber = initialEnemyNumber + waveIterator*normalNPCIncrement;
                while(waveNPCs.size() < enemyNumber) {}
                break;
            case "hard":
                enemyNumber = initialEnemyNumber + waveIterator*hardNPCIncrement;
                while(waveNPCs.size() < enemyNumber) {}

                break;

        waveIterator++;}

        //=================================================Return=======================================================
        return new Wave(waveNPCs, difficulty, waveIterator);
    }

    /** Renvoie sous forme de liste les spécifications des NPCs standards, dans l'ordre: [int health, int speed, int goldLoot]*/
    public ArrayList<Integer> getStandardSpecifications(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(standardHealth);
        res.add(standardSpeed);
        res.add(standardGoldLoot);
        res.add(standardHealthLoot);
        res.add(standardExtraHealth);
        res.add(standardExtraSpeed);
        res.add(standardExtraGoldLoot);
        res.add(standardExtraHealthLoot);

        return res;
    }

    /** Renvoie sous forme de liste les spécifications des NPCs standards, dans l'ordre: [int health, int speed, int goldLoot]*/
    public ArrayList<Integer> getSpecialSpecifications(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(specialHealth);
        res.add(specialSpeed);
        res.add(specialGoldLoot);
        res.add(specialHealthLoot);
        res.add(specialExtraHealth);
        res.add(specialExtraSpeed);
        res.add(specialExtraGoldLoot);
        res.add(specialExtraHealthLoot);
        return res;
    }

    /** Génère la prochaine vague via un appel à la méthode getWave*/
    public Wave getNextWave(Wave lastWave) throws IOException {
        return getWave(lastWave.getDifficulty(), lastWave.getOccurrence());
    }
}