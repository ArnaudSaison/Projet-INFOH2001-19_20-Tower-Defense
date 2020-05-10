package towerdefense.game.waves;

import towerdefense.game.map.GatePathTile;
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
    private int waveIterator;
    private int cycleIterator;

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

        //Compteurs:
        waveIterator = 0;
        cycleIterator = 0;
    }

    /*==================================================================================================================
                                                   METHODES
    ==================================================================================================================*/

    /**
     * Retourne un objet Wave contenant une liste de NPC dont la proportion et les caractéristiques de chaque type est
     * défini ici sur base de la lecture des fichiers properties correspondant
     */
    public Wave getWave(String difficulty) throws IOException {
        //====================================Variables locales=========================================================
        //Récupérations des fichiers properties:
        File npcProperties = allMapSpecificationsFiles.get(2);
        File waveProperties = allMapSpecificationsFiles.get(3 + waveIterator);

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
        GatePathTile gatePathTile = map.getGates().get(waveSpecifications.get(0).get(1).intValue());

        //===============================================Gestion des cas================================================
        switch (difficulty.toLowerCase()) {
            case "easy":
                increment = npcSpecifications.get(5).get(0);
                proportions = waveSpecifications.get(1);
                break;
            case "normal":
                increment = npcSpecifications.get(5).get(1);
                proportions = waveSpecifications.get(2);
                break;
            case "hard":
                increment = npcSpecifications.get(5).get(2);
                proportions = waveSpecifications.get(3);
                break;
        }

        //Remplissage de la liste de NPC:
        int initialEnemyNumber = waveSpecifications.get(0).get(0).intValue();
        enemyNumber = initialEnemyNumber + cycleIterator * increment;

        assert proportions != null;
        while (standard < enemyNumber * proportions.get(0)) {
            npcList.add(npcFactory.getInstance(NPCTypes.STANDARD_NPC, map, gameModel, npcSpecifications.get(0), gatePathTile));
            standard++;
        }
        while (rapid < enemyNumber * proportions.get(1)) {
            npcList.add(npcFactory.getInstance(NPCTypes.RAPID_NPC, map, gameModel, npcSpecifications.get(1), gatePathTile));
            rapid++;
        }
        while (superHealth < enemyNumber * proportions.get(2)) {
            npcList.add(npcFactory.getInstance(NPCTypes.SUPER_HEALTH_NPC, map, gameModel, npcSpecifications.get(2), gatePathTile));
            superHealth++;
        }
        while (explosiveResistant < enemyNumber * proportions.get(3)) {
            npcList.add(npcFactory.getInstance(NPCTypes.EXPLOSIVE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(3), gatePathTile));
            explosiveResistant++;
        }
        while (glueResistant < enemyNumber * proportions.get(4)) {
            npcList.add(npcFactory.getInstance(NPCTypes.GLUE_RESISTANT_NPC, map, gameModel, npcSpecifications.get(4), gatePathTile));
            glueResistant++;
        }

        //=========================Incrémentation des compteurs de vague et de cycle====================================
//        System.out.println("*************************VAGUE :" +(waveIterator+1) +"***************************************");
        if (waveIterator == 2) {
            waveIterator = 0;
            cycleIterator++;
//            System.out.println("=============Dernière vague avant nouveau cycle======================");
        } else {
            waveIterator++;
        }

        //=================================================Return=======================================================
        return new Wave(npcList, difficulty);
    }

    /**
     * Génère la prochaine vague via un appel à la méthode getWave
     */
    public Wave getNextWave(Wave lastWave) throws IOException {
        return getWave(lastWave.getDifficulty());
    }

    /**
     * Après lecture d'un fichier wave(n).properties, renvoie une liste de liste tel que :
     * [constants, easySpecifications , normalSpecifications, hardSpecifications]
     */
    public ArrayList<ArrayList<Double>> getWaveSpecifications(File waveSpeFile) throws IOException {
        final Properties waveProperties = new Properties();
        InputStream wavePropertiesFile = new FileInputStream(waveSpeFile.getPath());
        waveProperties.load(wavePropertiesFile);

        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        //Lecture des constantes:
        String[] constant = waveProperties.getProperty("constants").split(", ");

        //Lectures des proportions:
        String[] easy = waveProperties.getProperty("easy").split(", ");
        String[] normal = waveProperties.getProperty("normal").split(", ");
        String[] hard = waveProperties.getProperty("hard").split(", ");

        //Conversion des listes de string en liste de double:
        ArrayList<Double> constants = convertToDoubleList(constant);
        ArrayList<Double> easySpecifications = convertToDoubleList(easy);
        ArrayList<Double> normalSpecifications = convertToDoubleList(normal);
        ArrayList<Double> hardSpecifications = convertToDoubleList(hard);

        //Ajout à la liste renvoyée:
        res.add(constants);
        res.add(easySpecifications);
        res.add(normalSpecifications);
        res.add(hardSpecifications);

        return res;
    }

    /**
     * Après lecture du fichier npc.properties, renvoie une liste de liste tel que :
     * [standardSpecifications , specialSpecifications, difficultyIncrements]
     */
    public ArrayList<ArrayList<Integer>> getNpcSpecifications(File npcPropFile) throws IOException {
        final Properties npcProperties = new Properties();
        InputStream wavePropertiesFile = new FileInputStream(npcPropFile.getPath());
        npcProperties.load(wavePropertiesFile);

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        //Spécification des attributs de base des NPCs:
        String[] standard = npcProperties.getProperty("standardSpe").split(", ");
        String[] rapid = npcProperties.getProperty("rapidSpe").split(", ");
        String[] superHealth = npcProperties.getProperty("superHealthSpe").split(", ");
        String[] explosiveResistant = npcProperties.getProperty("explosiveResistantSpe").split(", ");
        String[] glueResistant = npcProperties.getProperty("glueResistantSpe").split(", ");

        //Increment de d'ennemi au prochain cycle de vague, selon la difficulté:
        String[] difficultiesIncrement = npcProperties.getProperty("difficultiesIncrement").split(", ");

        //Conversion des listes de String en liste d'entier:
        ArrayList<Integer> standardSpe = convertToIntegerList(standard);
        ArrayList<Integer> rapidSpe = convertToIntegerList(rapid);
        ArrayList<Integer> superHealthSpe = convertToIntegerList(superHealth);
        ArrayList<Integer> explosiveResistantSpe = convertToIntegerList(explosiveResistant);
        ArrayList<Integer> glueResistantSpe = convertToIntegerList(glueResistant);
        ArrayList<Integer> difficultyIncrements = convertToIntegerList(difficultiesIncrement);

        //Ajout à la liste renvoyée:
        res.add(standardSpe);
        res.add(rapidSpe);
        res.add(superHealthSpe);
        res.add(explosiveResistantSpe);
        res.add(glueResistantSpe);
        res.add(difficultyIncrements);

        return res;
    }

    /**
     * Renvoie une liste, triée dans l'ordre alphabétique, contenant tous les fichiers spécifiant les propriètés
     * d'une carte.
     */
    public void getAllMapSpecificationsFiles(File mapFolder) {
        for (File fileEntry : Objects.requireNonNull(mapFolder.listFiles())) {
            if (fileEntry.isDirectory()) {
                getAllMapSpecificationsFiles(fileEntry);
            } else {
                allMapSpecificationsFiles.add(fileEntry);
            }
        }
    }

    /**
     * Permet de convertir une liste de string en une ArrayList d'entier.
     *
     * @param list de string à convertir en liste d'entier.
     */
    private ArrayList<Integer> convertToIntegerList(String[] list) {
        ArrayList<Integer> res = new ArrayList<>();
        for (String elem : list) {
            res.add(Integer.parseInt(elem));
        }
        return res;
    }

    /**
     * Permet de convertir une liste de string en une ArrayList de double.
     *
     * @param list de string à convertir en liste de double.
     */
    private ArrayList<Double> convertToDoubleList(String[] list) {
        ArrayList<Double> res = new ArrayList<>();
        for (String elem : list) {
            res.add(Double.parseDouble(elem));
        }
        return res;
    }

    /*==================================================================================================================
                                                   TESTS
    ==================================================================================================================*/
    public void afficheFichiersSpec() {
        for (File file : allMapSpecificationsFiles) {
//            System.out.println(file.getName());
        }
    }
}