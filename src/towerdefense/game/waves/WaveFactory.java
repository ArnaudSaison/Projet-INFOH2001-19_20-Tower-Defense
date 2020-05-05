package towerdefense.game.waves;

import towerdefense.game.map.Map;
import towerdefense.game.map.Tile;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

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
    public WaveFactory(Map map, GameModel gameModel, String mapsFolderPath) {
        this.map = map;
        this.gameModel = gameModel;

        File mapFolder = new File(mapsFolderPath);
        allMapSpecificationsFiles = new ArrayList<>();
        getAllMapSpecificationsFiles(mapFolder);
    }

    /*==================================================================================================================
                                                   METHODES
    ==================================================================================================================*/

    /**Retourne un objet Wave contenant une liste de NPC dont la proportion et les caractéristiques de chaque type est
     * défini ici sur base de la lecture des fichiers properties correspondant*/
    //TODO: enlever iterator
    public Wave getWave(String difficulty, int waveIterator, int cycleIterator) throws IOException {
        //====================================Variables locales=========================================================
        //Récupérations des fichiers properties:
        File npcProperties = allMapSpecificationsFiles.get(2);
        File waveProperties = allMapSpecificationsFiles.get(3+waveIterator);

        //Récupération des spécifications des NPCs:
        ArrayList<ArrayList<Integer>> npcSpecifications = getNpcSpecifications(npcProperties);

        //Récupération des spécifications de la vague en cours:
        ArrayList<ArrayList<Double>> waveSpecifications = getWaveSpecifications(waveProperties);

        //Nombre total d'ennemi de la vague:
        int enemyNumber;

        //Variables fonctions de la difficulté:
        int increment = 0;
        ArrayList<Double> proportions = null;

        //Compteur par type de NPC:
        int standard = 0;
        int rapid = 0;
        int superHealth = 0;
        int explosiveResistant = 0;
        int glueResistant = 0;

        //Liste des NPC qui composent la vague:
        ArrayList<NPC> npcList = new ArrayList<>();

        //Instanciation de la NPC Factory:
        NPCFactory npcFactory = new NPCFactory();

        //Tile par laquelle les NPCs entre sur la carte:
        Tile gatePathTile = map.getGates().get(waveSpecifications.get(0).get(1).intValue());

        //===============================================Gestion des cas================================================
        switch (difficulty.toLowerCase()) {
            case "easy":
                increment = npcSpecifications.get(2).get(0);
                proportions = waveSpecifications.get(1);
                    break;
            case "normal":
                increment = npcSpecifications.get(2).get(1);
                proportions = waveSpecifications.get(2);
                break;
            case "hard":
                increment = npcSpecifications.get(2).get(2);
                proportions = waveSpecifications.get(3);
                break;
        }

        //Remplissage de la liste de NPC:
        int initialEnemyNumber = waveSpecifications.get(0).get(0).intValue();
        enemyNumber = initialEnemyNumber + cycleIterator*increment;

        while(standard < enemyNumber*proportions.get(0)) {
            npcList.add(npcFactory.getInstance(NPCTypes.STANDARD_NPC, map, gameModel, npcSpecifications.get(0), gatePathTile));
            standard++;
        }
        while(rapid < enemyNumber*proportions.get(1)) {
            npcList.add(npcFactory.getInstance(NPCTypes.RAPID_NPC, map, gameModel, npcSpecifications.get(0), gatePathTile));
            rapid++;
        }
        while(superHealth < enemyNumber*proportions.get(2)) {
            npcList.add(npcFactory.getInstance(NPCTypes.SUPER_HEALTH_NPC, map, gameModel, npcSpecifications.get(0), gatePathTile));
            superHealth++;
        }
        while(explosiveResistant < enemyNumber*proportions.get(3)) {
            npcList.add(npcFactory.getInstance(NPCTypes.EXPLOSIVE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1), gatePathTile));
            explosiveResistant++;
        }
        while(glueResistant < enemyNumber*proportions.get(4)) {
            npcList.add(npcFactory.getInstance(NPCTypes.GLUE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(1), gatePathTile));
            glueResistant++;
        }

        //=========================Incrémentation des compteurs de vague et de cycle====================================
        // TODO: utiliser une méthode ?
        if (waveIterator == 2){
            waveIterator=0;
            cycleIterator++;
        }else{waveIterator++; }

        //=================================================Return=======================================================
        return new Wave(npcList, difficulty, waveIterator, cycleIterator);
    }

    /** Génère la prochaine vague via un appel à la méthode getWave*/
    public Wave getNextWave(Wave lastWave) throws IOException {
        return getWave(lastWave.getDifficulty(), lastWave.getWaveIteration(),lastWave.getCycle());
    }

    /**Après lecture d'un fichier wave(n).properties, renvoie une liste de liste tel que : [constants, easySpecifications , normalSpecifications, hardSpecifications]*/
    public ArrayList<ArrayList<Double>> getWaveSpecifications(File waveSpeFile) throws IOException {
        final Properties waveProperties = new Properties();
        InputStream wavePropertiesFile = new FileInputStream(waveSpeFile.getPath());
        waveProperties.load(wavePropertiesFile);

        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        //Nombre total d'ennemi:
        double totalEnemyNumber = Double.parseDouble(waveProperties.getProperty("totalEnemyNumber"));

        //Porte d'entré:
        double mapGate = Double.parseDouble(waveProperties.getProperty("mapGate"));

        //Difficulté "easy":
        double easyStandardProportion = Double.parseDouble(waveProperties.getProperty("easyStandardProportion"));
        double easyRapidProportion = Double.parseDouble(waveProperties.getProperty("easyRapidProportion"));
        double easySuperHealthProportion = Double.parseDouble(waveProperties.getProperty("easySuperHealthProportion"));
        double easyExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("easyExplosiveResistantProportion"));
        double easyGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("easyGlueResistantProportion"));

        //Difficulté "normal":
        double normalStandardProportion = Double.parseDouble(waveProperties.getProperty("normalStandardProportion"));
        double normalRapidProportion = Double.parseDouble(waveProperties.getProperty("normalRapidProportion"));
        double normalSuperHealthProportion = Double.parseDouble(waveProperties.getProperty("normalSuperHealthProportion"));
        double normalExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("normalExplosiveResistantProportion"));
        double normalGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("normalGlueResistantProportion"));

        //Difficulté "hard":
        double hardStandardProportion = Double.parseDouble(waveProperties.getProperty("hardStandardProportion"));
        double hardRapidProportion = Double.parseDouble(waveProperties.getProperty("hardRapidProportion"));
        double hardSuperHealthProportion = Double.parseDouble(waveProperties.getProperty("hardSuperHealthProportion"));
        double hardExplosiveResistantProportion = Double.parseDouble(waveProperties.getProperty("hardExplosiveResistantProportion"));
        double hardGlueResistantProportion = Double.parseDouble(waveProperties.getProperty("hardGlueResistantProportion"));

        //Créer sous forme de liste les contantes pour chaque niveau de difficuté:
        ArrayList<Double> constants = new ArrayList<>();
        constants.add(totalEnemyNumber);
        constants.add(mapGate);

        //Créer sous forme de liste les spécifications pour la difficulté easy:
        ArrayList<Double> easySpecifications = new ArrayList<>();
        easySpecifications.add(easyStandardProportion);
        easySpecifications.add(easyRapidProportion);
        easySpecifications.add(easySuperHealthProportion);
        easySpecifications.add(easyExplosiveResistantProportion);
        easySpecifications.add(easyGlueResistantProportion);

        //Créer sous forme de liste les spécifications dpour la difficulté normal:
        ArrayList<Double> normalSpecifications = new ArrayList<>();
        normalSpecifications.add(normalStandardProportion);
        normalSpecifications.add(normalRapidProportion);
        normalSpecifications.add(normalSuperHealthProportion);
        normalSpecifications.add(normalExplosiveResistantProportion);
        normalSpecifications.add(normalGlueResistantProportion);

        //Créer sous forme de liste les spécifications pour la difficulté hard:
        ArrayList<Double> hardSpecifications = new ArrayList<>();
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
    public ArrayList<ArrayList<Integer>> getNpcSpecifications(File npcPropFile) throws IOException {
        final Properties npcProperties = new Properties();
        InputStream wavePropertiesFile = new FileInputStream(npcPropFile.getPath());
        npcProperties.load(wavePropertiesFile);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

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
        ArrayList<Integer> standardSpecifications = new ArrayList<>();
        standardSpecifications.add(standardHealth);
        standardSpecifications.add(standardSpeed);
        standardSpecifications.add(standardGoldLoot);
        standardSpecifications.add(standardHealthLoot);
        standardSpecifications.add(standardExtraHealth);
        standardSpecifications.add(standardExtraSpeed);
        standardSpecifications.add(standardExtraGoldLoot);
        standardSpecifications.add(standardExtraHealthLoot);

        //Créer sous forme de liste les spécifications des NPCs spéciaux:
        ArrayList<Integer> specialSpecifications = new ArrayList<>();
        specialSpecifications.add(specialHealth);
        specialSpecifications.add(specialSpeed);
        specialSpecifications.add(specialGoldLoot);
        specialSpecifications.add(specialHealthLoot);
        specialSpecifications.add(specialExtraHealth);
        specialSpecifications.add(specialExtraSpeed);
        specialSpecifications.add(specialExtraGoldLoot);
        specialSpecifications.add(specialExtraHealthLoot);

        //Créer sous forme de liste les incréments liés à la difficulté:
        ArrayList<Integer> difficultyIncrements = new ArrayList<>();
        difficultyIncrements.add(easyIncrement);
        difficultyIncrements.add(normalIncrement);
        difficultyIncrements.add(hardIncrement);

        res.add(standardSpecifications);
        res.add(specialSpecifications);
        res.add(difficultyIncrements);

        return res;
    }

    /**Renvoie une liste, triée dans l'ordre alphabétique, contenant tous les fichiers spécifiant les propriètés d'une carte.*/
    public void getAllMapSpecificationsFiles(File mapFolder){
        for (File fileEntry : Objects.requireNonNull(mapFolder.listFiles())) {
            if (fileEntry.isDirectory()) {
                getAllMapSpecificationsFiles(fileEntry);
            } else {
                allMapSpecificationsFiles.add(fileEntry);
            }
        }
    }

    /*==================================================================================================================
                                                   TESTS
    ==================================================================================================================*/
    public void afficheFichiersSpec(){
        for (File file : allMapSpecificationsFiles){
            System.out.println(file.getName());
        }
    }
}