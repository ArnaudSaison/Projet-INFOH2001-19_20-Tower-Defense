package towerdefense.game.waves;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import towerdefense.MainApplication;
import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;
import towerdefense.gui.map.selector.MapClickedListener;

import java.io.*;
import java.util.*;

public class WaveFactory {
    /*==================================================================================================================
                                                   ATTRIBUTS
    ==================================================================================================================*/
    //Références nécessaires à la création de NPC:
    private Map map;
    private GameModel gameModel;

    public enum NPCTypes {STANDARD_NPC, RAPID_NPC, SUPER_HEALTH_NPC, EXPLOSIVE_RESISTANT_NPC, GLUE_RESISTANT_NPC}

    private ArrayList<File> allMapSpecificationsFiles;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public WaveFactory(Map map, GameModel gameModel, String mapFolderPath) {
        this.map = map;
        this.gameModel = gameModel;

        File mapFolder = new File(mapFolderPath);
        allMapSpecificationsFiles = getAllMapSpecificationsFiles(mapFolder);
    }

    /*==================================================================================================================
                                                   METHODES
    ==================================================================================================================*/

    /** En fonction de la difficulté que le joueur a choisi, renvoie un objet Wave correspondant aux spécifications lus
     * dans le fichier wave.properties.*/

    public Wave getWave(String difficulty, int waveIterator, int cycleIterator) throws IOException {
        File npcProperties = allMapSpecificationsFiles.get(2);
        File wave1Properties = allMapSpecificationsFiles.get(3);
        File wave2Properties = allMapSpecificationsFiles.get(4);
        File wave3Properties = allMapSpecificationsFiles.get(5);

        ArrayList<ArrayList<Integer>> npcSpecifications = getNpcSpecifications(npcProperties);

        ArrayList<ArrayList<Double>> wave1Specifications = getWaveSpecifications(wave1Properties);
        ArrayList<ArrayList<Double>> wave2Specifications = getWaveSpecifications(wave2Properties);
        ArrayList<ArrayList<Double>> wave3Specifications = getWaveSpecifications(wave3Properties);
        ArrayList<ArrayList<ArrayList<Double>>> wavesSpecifications = new ArrayList<ArrayList<ArrayList<Double>>>();

        int enemyNumber;
        //Compteur:
        int standard = 0;
        int rapid = 0;
        int superHealth = 0;
        int explosiveResistant = 0;
        int glueResistant = 0;

        ArrayList<NPC> npcList = new ArrayList<NPC>();
        NPCFactory npcFactory = new NPCFactory();
        ArrayList<ArrayList<Double>> waveSpecifications = wavesSpecifications.get(waveIterator);
        //===============================================Gestion des cas================================================

        switch (difficulty.toLowerCase()) {
            case "easy":
                int easyIncrement = npcSpecifications.get(2).get(0);
                int initialEnemyNumber = (int)waveSpecifications.get(0).get(0); //TODO : pourquoi le cast ne prend pas ?
                enemyNumber = initialEnemyNumber + cycleIterator*easyIncrement;
                    while(standard < (int)enemyNumber*waveSpecifications.get(1).get(0)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.STANDARD_NPC, map, gameModel, npcSpecifications.get(0)));
                        standard++;
                    }
                    while(rapid < (int)enemyNumber*waveSpecifications.get(1).get(1)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.RAPID_NPC, map, gameModel, npcSpecifications.get(0)));
                        rapid++;
                    }
                    while(superHealth < (int)enemyNumber*waveSpecifications.get(1).get(2)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.SUPER_HEALTH_NPC, map, gameModel, npcSpecifications.get(0)));
                        superHealth++;
                    }
                    while(explosiveResistant < (int)enemyNumber*waveSpecifications.get(1).get(3)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.EXPLOSIVE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        explosiveResistant++;
                    }
                    while(glueResistant < (int)enemyNumber*waveSpecifications.get(1).get(4)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.GLUE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        glueResistant++;
                    }
                break;
            case "normal":
                int normalIncrement = npcSpecifications.get(2).get(1);
                int initialEnemyNumber = (int)waveSpecifications.get(0).get(0);
                enemyNumber = initialEnemyNumber + cycleIterator*normalIncrement;

                    while(standard < (int)enemyNumber*wave1Specifications.get(1).get(0)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.STANDARD_NPC, map, gameModel, npcSpecifications.get(0)));
                        standard++;
                    }
                    while(rapid < (int)enemyNumber*waveSpecifications.get(1).get(1)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.RAPID_NPC, map, gameModel, npcSpecifications.get(0)));
                        rapid++;
                    }
                    while(superHealth < (int)enemyNumber*waveSpecifications.get(1).get(2)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.SUPER_HEALTH_NPC, map, gameModel, npcSpecifications.get(0)));
                        superHealth++;
                    }
                    while(explosiveResistant < (int)enemyNumber*waveSpecifications.get(1).get(3)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.EXPLOSIVE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        explosiveResistant++;
                    }
                    while(glueResistant < (int)enemyNumber*waveSpecifications.get(1).get(4)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.GLUE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        glueResistant++;
                    }
                break;
            case "hard":
                int hardIncrement = npcSpecifications.get(2).get(2);
                int initialEnemyNumber = (int)waveSpecifications.get(0).get(0);
                enemyNumber = initialEnemyNumber + cycleIterator*hardIncrement;
                    while(standard < (int)enemyNumber*waveSpecifications.get(1).get(0)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.STANDARD_NPC, map, gameModel, npcSpecifications.get(0)));
                        standard++;
                    }
                    while(rapid < (int)enemyNumber*waveSpecifications.get(1).get(1)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.RAPID_NPC, map, gameModel, npcSpecifications.get(0)));
                        rapid++;
                    }
                    while(superHealth < (int)enemyNumber*waveSpecifications.get(1).get(2)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.SUPER_HEALTH_NPC, map, gameModel, npcSpecifications.get(0)));
                        superHealth++;
                    }
                    while(explosiveResistant < (int)enemyNumber*waveSpecifications.get(1).get(3)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.EXPLOSIVE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        explosiveResistant++;
                    }
                    while(glueResistant < (int)enemyNumber*waveSpecifications.get(1).get(4)) {
                        npcList.add(npcFactory.getInstance(NPCTypes.GLUE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1)));
                        glueResistant++;
                    }
                break;
        }
        if (waveIterator == 3){
            waveIterator=0;
            cycleIterator++;
        }else{waveIterator++; }

        //=================================================Return=======================================================
        return new Wave(npcList, difficulty, waveIterator, cycleIterator);
    }

    /** Génère la prochaine vague via un appel à la méthode getWave*/
    public Wave getNextWave(Wave lastWave) throws IOException {
        return getWave(lastWave.getDifficulty(), lastWave.getOccurrence(),lastWave.getCycle());
    }

    /**Après lecture d'un fichier wave(n).properties, renvoie une liste de liste tel que : [constants, easySpecifications , normalSpecifications, hardSpecifications]*/
    public ArrayList<ArrayList<Double>> getWaveSpecifications(File wavePropertiesFile){
        final Properties waveProperties = new Properties(); //TODO: comment lire le fichier properties?
        waveProperties.load(wavePropertiesFile);

        ArrayList<ArrayList<Double>> res = new ArrayList<ArrayList<Double>>();
        //Nombre total d'ennemi:
        double totalEnemyNumber = Double.parseDouble("totalEnemyNumber");

        //Porte d'entré:
        double mapGate = Double.parseDouble("mapGate");

        //Difficulté "easy":
        double easyStandardProportion = Double.parseDouble("easyStandardProportion");
        double easyRapidProportion = Double.parseDouble(waveProperties.getProperty("easyRapidProportion"));
        double easySuperHealthProportion = Double.parseDouble("easySuperHealthProportion");
        double easyExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("easyExplosiveResistantProportion"));
        double easyGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("easyGlueResistantProportion"));

        //Difficulté "normal":
        double normalStandardProportion = Double.parseDouble("normalStandardProportion");
        double normalRapidProportion = Double.parseDouble(waveProperties.getProperty("normalRapidProportion"));
        double normalSuperHealthProportion = Double.parseDouble("normalSuperHealthProportion");
        double normalExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("normalExplosiveResistantProportion"));
        double normalGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("normalGlueResistantProportion"));

        //Difficulté "hard":
        double hardStandardProportion = Double.parseDouble("hardStandardProportion");
        double hardRapidProportion = Double.parseDouble(waveProperties.getProperty("hardRapidProportion"));
        double hardSuperHealthProportion = Double.parseDouble("hardSuperHealthProportion");
        double hardExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("hardExplosiveResistantProportion"));
        double hardGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("hardGlueResistantProportion"));

        //Créer sous forme de liste les contantes pour chaque niveau de difficuté:
        ArrayList<Double> constants = new ArrayList<Double>();
        constants.add(totalEnemyNumber);
        constants.add(mapGate);

        //Créer sous forme de liste les spécifications pour la difficulté easy:
        ArrayList<Double> easySpecifications = new ArrayList<Double>();
        easySpecifications.add(easyStandardProportion);
        easySpecifications.add(easyRapidProportion);
        easySpecifications.add(easySuperHealthProportion);
        easySpecifications.add(easyExplosiveResistantProportion);
        easySpecifications.add(easyGlueResistantProportion);

        //Créer sous forme de liste les spécifications dpour la difficulté normal:
        ArrayList<Double> normalSpecifications = new ArrayList<Double>();
        normalSpecifications.add(normalStandardProportion);
        normalSpecifications.add(normalRapidProportion);
        normalSpecifications.add(normalSuperHealthProportion);
        normalSpecifications.add(normalExplosiveResistantProportion);
        normalSpecifications.add(normalGlueResistantProportion);

        //Créer sous forme de liste les spécifications pour la difficulté hard:
        ArrayList<Double> hardSpecifications = new ArrayList<Double>();
        hardSpecifications.add(hardStandardProportion);
        hardSpecifications.add(hardRapidProportion);
        hardSpecifications.add(hardSuperHealthProportion);
        hardSpecifications.add(hardExplosiveResistantProportion);
        hardSpecifications.add(hardGlueResistantProportion);

        res.add(constants);
        res.add(easySpecifications);
        res.add(normalSpecifications);
        res.add(hardSpecifications);

        return res;
    }



    /**Après lecture du fichier npc.properties, renvoie une liste de liste tel que : [standardSpecifications , specialSpecifications, difficultyIncrements]*/
    public ArrayList<ArrayList<Integer>> getNpcSpecifications(File npcPropertiesFile){
        final Properties npcProperties = new Properties();
        npcProperties.load(npcPropertiesFile);

        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();

        //Spécification des attributs de base des NPCs:
        int standardHealth = Integer.parseInt(npcProperties.getProperty("standardHealth"));
        int standardSpeed = Integer.parseInt(npcProperties.getProperty("standardSpeed"));
        int standardGoldLoot = Integer.parseInt(npcProperties.getProperty("standardGoldLoot"));
        int standardHealthLoot = Integer.parseInt(npcProperties.getProperty("standardHealthLoot"));
        int standardExtraHealth = Integer.parseInt(npcProperties.getProperty("standardExtraHealth"));
        int standardExtraSpeed = Integer.parseInt(npcProperties.getProperty("standardExtraSpeed"));
        int standardExtraGoldLoot = Integer.parseInt(npcProperties.getProperty("standardExtraGoldLoot"));
        int standardExtraHealthLoot = Integer.parseInt(npcProperties.getProperty("standardExtraHealthLoot"));

        int specialHealth = Integer.parseInt(npcProperties.getProperty("specialHealth"));
        int specialSpeed = Integer.parseInt(npcProperties.getProperty("specialSpeed"));
        int specialGoldLoot = Integer.parseInt(npcProperties.getProperty("specialGoldLoot"));
        int specialHealthLoot = Integer.parseInt(npcProperties.getProperty("specialHealthLoot"));
        int specialExtraHealth = Integer.parseInt(npcProperties.getProperty("specialExtraHealth"));
        int specialExtraSpeed = Integer.parseInt(npcProperties.getProperty("specialExtraSpeed"));
        int specialExtraGoldLoot = Integer.parseInt(npcProperties.getProperty("specialExtraGoldLoot"));
        int specialExtraHealthLoot = Integer.parseInt(npcProperties.getProperty("specialExtraHealthLoot"));

        //Increment de d'ennemi au prochain cycle de vague, selon la difficulté:
        int easyIncrement = Integer.parseInt(npcProperties.getProperty("easyIncrement"));
        int normalIncrement = Integer.parseInt(npcProperties.getProperty("normalIncrement"));
        int hardIncrement = Integer.parseInt(npcProperties.getProperty("hardIncrement"));

        //Créer sous forme de liste les spécifications des NPCs standards:
        ArrayList<Integer> standardSpecifications = new ArrayList<Integer>();
        standardSpecifications.add(standardHealth);
        standardSpecifications.add(standardSpeed);
        standardSpecifications.add(standardGoldLoot);
        standardSpecifications.add(standardHealthLoot);
        standardSpecifications.add(standardExtraHealth);
        standardSpecifications.add(standardExtraSpeed);
        standardSpecifications.add(standardExtraGoldLoot);
        standardSpecifications.add(standardExtraHealthLoot);

        //Créer sous forme de liste les spécifications des NPCs spéciaux:
        ArrayList<Integer> specialSpecifications = new ArrayList<Integer>();
        specialSpecifications.add(specialHealth);
        specialSpecifications.add(specialSpeed);
        specialSpecifications.add(specialGoldLoot);
        specialSpecifications.add(specialHealthLoot);
        specialSpecifications.add(specialExtraHealth);
        specialSpecifications.add(specialExtraSpeed);
        specialSpecifications.add(specialExtraGoldLoot);
        specialSpecifications.add(specialExtraHealthLoot);

        //Créer sous forme de liste les incréments liés à la difficulté:
        ArrayList<Integer> difficultyIncrements = new ArrayList<Integer>();
        difficultyIncrements.add(easyIncrement);
        difficultyIncrements.add(normalIncrement);
        difficultyIncrements.add(hardIncrement);

        res.add(standardSpecifications);
        res.add(specialSpecifications);
        res.add(difficultyIncrements);

        return res;
    }


    /**Renvoie une liste, triée dans l'ordre alphabétique, contenant tous les fichiers spécifiant les propriètés d'une carte.*/
    public ArrayList<File> getAllMapSpecificationsFiles(File mapFolder){
        ArrayList<File> res = new ArrayList<File>();
        for (File fileEntry : mapFolder.listFiles()) {
            if (fileEntry.isDirectory()) {
                getAllMapSpecificationsFiles(fileEntry);
            } else {
                res.add(fileEntry);
            }
        }
        return res;
    }
}