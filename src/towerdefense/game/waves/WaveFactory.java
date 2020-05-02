package towerdefense.game.waves;

import towerdefense.game.map.Map;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.game.npcs.NPCFactory;

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

    private ArrayList<String> standardNPCTypes;
    private ArrayList<String> specialNPCTypes;

    //Paramètres de configuration lus dans le fichier wave.properties:
    private int standardHealth;
    private int standardSpeed;
    private int standardGoldLoot;
    private int standardExtraHealth;
    private int standardExtraSpeed;
    private int standardExtraGoldLoot;

    private int specialHealth;
    private int specialSpeed;
    private int specialGoldLoot;
    private int specialExtraHealth;
    private int specialExtraSpeed;
    private int specialExtraGoldLoot;

    //Attributs nécessaires à la récursivité de la méthode getWave:
    private String waveFilePath;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public WaveFactory(Map map, GameModel gameModel, String waveFilePath) {
        this.map = map;
        this.gameModel = gameModel;

        //Ajout des différents types de NPC:
        standardNPCTypes = new ArrayList<String>();
        standardNPCTypes.add("standardnpc");
        standardNPCTypes.add("superhealthnpc");
        standardNPCTypes.add("rapidnpc");
        specialNPCTypes = new ArrayList<String>();
        specialNPCTypes.add("explosiveresistantnpc");
        specialNPCTypes.add("glueresistantnpc");
        //TODO: /!\ HARDCODING => utiliser le fichier properties pour ça : multiple values pour même propriété = compliqué à gérer.
        this.waveFilePath = waveFilePath;
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
        standardExtraHealth = Integer.parseInt(waveProperties.getProperty("standardExtraHealth"));
        standardExtraSpeed = Integer.parseInt(waveProperties.getProperty("standardExtraSpeed"));
        standardExtraGoldLoot = Integer.parseInt(waveProperties.getProperty("standardExtraGoldLoot"));

        specialHealth = Integer.parseInt(waveProperties.getProperty("specialHealth"));
        specialSpeed = Integer.parseInt(waveProperties.getProperty("specialSpeed"));
        specialGoldLoot = Integer.parseInt(waveProperties.getProperty("specialGoldLoot"));
        specialExtraHealth = Integer.parseInt(waveProperties.getProperty("specialExtraHealth"));
        specialExtraSpeed = Integer.parseInt(waveProperties.getProperty("specialExtraSpeed"));
        specialExtraGoldLoot = Integer.parseInt(waveProperties.getProperty("specialExtraGoldLoot"));

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
                while(waveNPCs.size() < enemyNumber) {
                    if(standards< (int)(easyStandardProportion*enemyNumber)){ //Respecte le pourcentage de NPC standard.
                        waveNPCs.add(generateRandomStandardNPC());
                        standards++;
                    }else{
                        waveNPCs.add(generateRandomSpecialNPC());
                    }
                }
                break;
            case "normal":
                enemyNumber = initialEnemyNumber + waveIterator*normalNPCIncrement;
                while(waveNPCs.size() < enemyNumber) {
                    if(standards< (int)(normalStandardProportion*enemyNumber)){
                        waveNPCs.add(generateRandomStandardNPC());
                        standards++;
                    }else{
                        waveNPCs.add(generateRandomSpecialNPC());
                    }
                }
                break;
            case "hard":
                enemyNumber = initialEnemyNumber + waveIterator*hardNPCIncrement;
                while(waveNPCs.size() < enemyNumber) {
                    if(standards < (int)(hardStandardProportion*enemyNumber)){
                        waveNPCs.add(generateRandomStandardNPC());
                        standards++;
                    }else{
                        waveNPCs.add(generateRandomSpecialNPC());
                    }
                }
                break;
        }
        waveIterator++;

        //=================================================Return=======================================================
        return new Wave(waveNPCs, difficulty, waveIterator);
    }

    /**Choisit de manière alétoire un type de NPC standard*/
    public String getRandomStandardNPCType() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(standardNPCTypes.size());
        return standardNPCTypes.get(randomIndex);
    }

    /**Choisit de manière alétoire un type de NPC spécial*/
    public String getRandomSpecialNPCType() {
        Random randomGenerator = new Random();
        int randomIndex = randomGenerator.nextInt(specialNPCTypes.size());
        return specialNPCTypes.get(randomIndex);
    }

    /** Renvoie sous forme de liste les spécifications des NPCs standards, dans l'ordre: [int health, int speed, int goldLoot]*/
    public ArrayList<Integer> getStandardSpecifications(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(standardHealth);
        res.add(standardSpeed);
        res.add(standardGoldLoot);
        res.add(standardExtraHealth);
        res.add(standardExtraSpeed);
        res.add(standardExtraGoldLoot);

        return res;
    }

    /** Renvoie sous forme de liste les spécifications des NPCs standards, dans l'ordre: [int health, int speed, int goldLoot]*/
    public ArrayList<Integer> getSpecialSpecifications(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(specialHealth);
        res.add(specialSpeed);
        res.add(specialGoldLoot);
        res.add(specialExtraHealth);
        res.add(specialExtraSpeed);
        res.add(specialExtraGoldLoot);
        return res;
    }

    /** Instancie une NPCFactory et génère un NPC standard de manière aléatoire via un appel à la méthode getRandomStandardNPC*/
    public NPC generateRandomStandardNPC() {
        NPCFactory npcFactory = new NPCFactory();
        return npcFactory.getInstance(getRandomStandardNPCType(), map, gameModel, getStandardSpecifications());
    }

    /** Instancie une NPCFactory et génère un NPC spécial de manière aléatoire via un appel à la méthode getRandomStandardNPC*/
    public NPC generateRandomSpecialNPC() {
        NPCFactory npcFactory = new NPCFactory();
        return npcFactory.getInstance(getRandomSpecialNPCType(), map, gameModel, getSpecialSpecifications());
    }

    /** Génère la prochaine vague via un appel à la méthode getWave*/
    public Wave getNextWave(Wave lastWave) throws IOException {
        return getWave(lastWave.getDifficulty(), lastWave.getOccurrence());
    }
}