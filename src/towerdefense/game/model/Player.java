package towerdefense.game.model;

/**
 * La classe joueur sert à gérer tous les éléments qui appartiennent au joueur :
 * sa vie, l'or qu'il possède, son score (qui augmente automatiquement)
 */
public class Player implements Runnable {
    private final Object syncKey1 = new Object();

    private GameModel gameModel;
    private int health;
    private int maxHealth;
    private int gold;
    private int score;
    private Thread tPlayer;

    public Player(GameModel gameModel, int initialGold, int initialHealth) {
        this.gameModel = gameModel;
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        this.gold = initialGold;
        this.score = 0;

        tPlayer = new Thread(this);
    }

    @Override
    public void run() {
        long timer = 0;
        double sleepTime = 1.0 / gameModel.getConfig().getModelFrameRate() * 1000;
        int scoreTime = (int) Math.round(1.0 / sleepTime * 1000); // Le score est défini comme le nombre de secondes survécues

        try {
            while (gameModel.getRunning()) { // si le jeu est en cours
                if (!gameModel.getPaused()) { // si le jeu n'est pas en pause
                    if (timer == 0) { // si le timer est terminé
                        increaseScore(); // augmentation du score
                        timer = scoreTime; // temps avant le prochain score
                    } else {
                        timer--; // évolution du timer
                    }
                }

                Thread.sleep((long) sleepTime);
            }
        } catch (InterruptedException exception) { // gestion des possibles erreurs lors de l'exécution du thread
            exception.printStackTrace();
        }
    }

    /**
     * Démarrage du Thread
     */
    public void initialize() {
        tPlayer.start();
    }

    //******Gestion du score*******

    /**
     * Augmentation du score
     */
    public void increaseScore() {
        score++; // par défaut, incrémente de 1 le score
    }

    //******Gestion de l'or*******

    /**
     * Augmentation de l'or que possède le joueur
     * @param goldIncrement or gangé
     */
    public void increaseGold(int goldIncrement) {
        gold += goldIncrement;
    }

    /**
     * Diminution de l'or que possède le joueur
     * @param goldDecrement or pris
     */
    public void decreaseGold(int goldDecrement) {
        gold -= goldDecrement;
    }

    //******Gestion de la vie*******

    /**
     * Régénération de la vie
     * @param healthIncrement ajout de vie
     */
    public void increaseHealth(int healthIncrement) {
        health += healthIncrement;
    }

    /**
     * Diminution de la vie
     * @param healthDecrement points de vie en moins
     */
    public void decreaseHealth(int healthDecrement) {
        synchronized (syncKey1) {
            if (health - healthDecrement <= 0) {
                health = 0;
                gameModel.setGameOver(); // le jeu est terminé, on l'en notify
            } else {
                health -= healthDecrement; // on peut diminuer la vie du joueur
            }
        }
    }

    //********Getteurs*********
    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return ("Player : \n"
                + "-score: " + score + "\n"
                + "-gold: " + gold + "\n"
                + "-health: " + health + "\n");
    }
}
