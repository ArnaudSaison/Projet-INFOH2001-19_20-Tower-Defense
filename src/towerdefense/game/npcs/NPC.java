package towerdefense.game.npcs;

import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Movable;
import towerdefense.game.Placeable;
import towerdefense.game.map.*;
import towerdefense.game.model.GameModel;
import towerdefense.game.projectiles.Arrow;
import towerdefense.game.projectiles.Glue;
import towerdefense.game.projectiles.Shell;
import towerdefense.view.Printable;
import towerdefense.view.npc.NPCView;

import java.util.ArrayList;

public abstract class NPC implements Drawable, Movable, Placeable, Runnable, Hittable {
    /*==================================================================================================================
                                                     ATTRIBUTS
    ==================================================================================================================*/
    protected GameModel gameModel;
    protected Map map;
    private Thread tNPC;

    //Attributs de déplacement:
    private Position position;
    private Position nextPosition;//Prochaine position à atteindre par le NPC:
    private Path path;
    private final ArrayList<Position> positions;
    protected boolean isArrived;
    protected HeadedDir isHeaded;
    protected boolean onMap;

    //Attributs de spécification:
    protected int health;
    private int maxHealth;
    protected double speed;
    protected int goldLoot;
    protected int healthLoot;

    //Attributs de représentation:
    private double width = 1.0/4.0;
    private double height = 1.0/4.0;
    private int frameRate;

    // JavaFX
    private NPCView npcView;

    /*==================================================================================================================
                                                   CONSTRUCTEUR
    ==================================================================================================================*/
    public NPC (Map map, GameModel gameModel, int health, double speed, int goldLoot, int scoreLoot, GatePathTile gatePathTile){
        path = gatePathTile.getRandomPath();
        positions = path.getRandomPositions();
        position = nextPosition = positions.get(0);
        onMap = false; //TODO: set sur false dans le gameModel
        isArrived = false;
        this.gameModel = gameModel;
        this.tNPC = new Thread(this);
        this.map = map;
        frameRate = gameModel.getConfig().getFrameRate();

        this.maxHealth = health;
        this.health = health;

        this.speed = speed;
        this.goldLoot = goldLoot;
        this.healthLoot = scoreLoot;
        isHeaded = HeadedDir.DOWN;
    }

    /*==================================================================================================================
                                                        GESTION DES ATTAQUES
    ==================================================================================================================*/
    /**
     * Methode surchargée qui prend en argument un objet type Projectile
     * */
    @Override
    public void hit(Shell shell){
        injure(shell);
    }

    @Override
    public void hit(Glue glue){
        stick(glue);
    }

    @Override
    public void hit(Arrow arrow){
        pierce(arrow);
    }

    /*==================================================================================================================
                                                        GESTION DES DEGATS
    ==================================================================================================================*/
    //En fonction du type de projectile, applique un effet sur le NPC:

    /**Si non résistant, ralenti le NPC*/
    public abstract void stick(Glue glue);

    /**Si non résistant, blesse le NPC*/
    public abstract void injure(Shell shell);

    /**Blesse le NPC*/
    public abstract void pierce(Arrow arrow);

    /**Retire de la vie au NPC*/
    public void decreaseHealth(int damage){
        if (health - damage <= 0) {
            gameModel.killNPC(this);
        } else {
            health -= damage;
        }
    }

    /*==================================================================================================================
                                                        GESTION DU THREAD
    ==================================================================================================================*/
    public void initialize(){
        if(onMap){
            tNPC.start();
//            System.out.println("NPC : je suis initialisé.");
        }
    }

    @Override
    public void run(){
        while(gameModel.getRunning() && (!isArrived)){
            try{
                if (!gameModel.getPaused()){
                    move();
//                    System.out.println("La position du NPC est" + getPosition().toString());
                    if (position == nextPosition){
                        setNextPosition();
//                        System.out.println("Le NPC a atteint une position intermédiaire");
                        if(isArrived){
                            gameModel.killNPC(this);
                        }
                    }
                    Thread.sleep(1000/frameRate);
                }
                else{
                    Thread.sleep(1000);
//                    System.out.println("le NPC est en pause");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/
    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
        npcView = new NPCView(this, map, "generic");
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
        npcView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
        return npcView;
    }

    /*==================================================================================================================
                                                        GESTION DU DEPLACEMENT
    ==================================================================================================================*/

    /**
     * Supprime les positions déjà visitée de la liste positions.
     * @return position à atteindre sur la prochaine case.
     */
    public void setNextPosition(){
        if (!positions.isEmpty()){
            nextPosition = positions.get(0);
        }else{
            isArrived = true;
        }
        positions.remove(nextPosition);
//        System.out.println("=============================LA PROCHAINE POSITION EST=================================="+ nextPosition);
    }

    /**
     * Permet au NPC de se déplacer en ligne droite, d'une case à l'autre.
     */
    public void move() {
        //Distance entre le NPC et la prochaine position à atteindre:
        double trajectoryNorm = position.getDistance(nextPosition);

        //Coordonnées initiales et finales nécessaires au calcul:
        double iniPosX = position.getX();
        double iniPosY = position.getY();
        double finPosX = nextPosition.getX();
        double finPosY = nextPosition.getY();

        //Définition du triangle rectangle généré par le vecteur trajectoire et sa projection orthogonale sur l'axe X:
        double hyp = trajectoryNorm;
        double adj = finPosX - iniPosX;
        double alpha = Math.acos(adj/hyp); // /!\ alpha est en radians.

        //Détermination du signe de l'incrément de déplacement en X et Y (cas par défaut : déplacement en haut à droite):
        int signDepX=1;
        int signDepY=1;

        if((finPosX-iniPosX >= 0) && (finPosY-iniPosY < 0)){
            signDepY = -1;
        }else if((finPosX-iniPosX < 0) && (finPosY-iniPosY >= 0)){
            signDepX = -1;
        }else if((finPosX-iniPosX < 0) && (finPosY-iniPosY < 0)){
            signDepX = -1;
            signDepY = -1;
        }

        //Distance parcourue entre l'affichage de deux images à l'écran:
        double distanceDone = speed / 100*gameModel.getConfig().getModelFrameRate(); //TODO: régler la vitesse de déplacement
        double depX = signDepX*(Math.cos(alpha)*distanceDone);
        double depY = signDepY*(Math.sin(alpha)*distanceDone);

        //Nouvelle position théorique:
        Position newPosition = new Position(map);
        newPosition.setX(iniPosX + depX);
        newPosition.setY(iniPosY + depY);

        //Nouvelle position, on vérifie que la position finale n'est pas dépassée:
        if (trajectoryNorm-distanceDone <= 0){
            position = nextPosition;
        }else{
            position = newPosition;
        }
    }

    /**
     * Donne la direction dans laquelle le NPC se dirige
     * @return direction du NPC
     */
    public HeadedDir getHeadedDir() {
        return isHeaded;
    }

    /*==================================================================================================================
                                                    GETTEURS/SETTEURS
    ==================================================================================================================*/
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getGoldLoot() {
        return goldLoot;
    }

    public int getHealthLoot() {
        return healthLoot;
    }

    public boolean getIsArrived() {
        return isArrived;
    }

    public void setIsArrived(boolean isArrived) {
        this.isArrived = isArrived;
    }

    public void setOnMap(boolean onMap){this.onMap = onMap;}

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public Path getPath(){
     return  path;
    }

    public Position getNextPosition(){
        return  nextPosition;
    }
    /*==================================================================================================================
                                                        AUTRES
    ==================================================================================================================*/
    @Override
    public String toString(){
        return "- position: " + position + "\n" +
                "- health: " + health + "\n"+
                "- goldLoot: " + goldLoot + "\n"+
                "- speed: " + speed + ".";
    }
}