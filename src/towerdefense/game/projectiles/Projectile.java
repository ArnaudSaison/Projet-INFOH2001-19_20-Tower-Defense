package towerdefense.game.projectiles;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;
import towerdefense.view.towers.TowerView;

public abstract class Projectile implements Runnable, Drawable, Movable {
    //Spécification:
    protected int damage;
    protected double velocity;

    //Positon:
    protected Position finalPosition;
    protected Position position;

    //Autres:
    protected Map map;
    protected GameModel gameModel;
    protected Thread tProjectile;
    protected Boolean running;
    protected NPC target; //cible sur laquelle est vérouiller le projectile.

    /**Construteur:
     * @param initialPosition Position de la tour depuis laquelle le projectile est tirer.
     */
    public Projectile(Map map, Position initialPosition, GameModel gameModel, int damage){
        this.damage = damage;
        this.map = map;
        this.gameModel = gameModel;

        position = initialPosition;
        running = false;
        tProjectile = new Thread(this);
    }

    /*==================================================================================================================
                                               GESTION DU THREAD
    ==================================================================================================================*/
    public void run(){
        while(running){
            try{
                if (!gameModel.getPaused()){
                    int frameRate = 24;//TODO : récupérer la frameRate du mainApplication ?
                    move();
                    System.out.println("La position du projectile est" + getPos().toString());
                    if (position == finalPosition){
                        doDamage(target);
                        System.out.println("Le projectile a atteint sa cible");
                    }
                    Thread.sleep(1000/frameRate);
                }
                else{
                    Thread.sleep(1000);
                    System.out.println("le projectile est en pause");
                }
            }catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

    /**Lance le thread et passe une référence de la cible.
     * @param target NPC sur lequel le projectile est vérouillé.
     */
    public void initialize(NPC target){
        this.target = target;
        this.finalPosition = target.getPos();
        running = true;
        tProjectile.start();
    }

    /**Comportement du projectile, redéfinie dans chaque sous-classe*/
    public abstract void doDamage(NPC target);

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
//        towerView = new TowerView(this, map, graphicsName);
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
//        towerView.update();
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    public Printable getDrawing() {
        return null;
    }

    /*==================================================================================================================
                                               GESTION DU MOUVEMENT
    ==================================================================================================================*/
    /** Permet au projectile d'atteindre sa cible via une trajectoire en ligne droite*/
    public void move(){
        //Distance entre la tour et le point d'impact du projectile:
        double trajectoryNorm = position.getDistance(finalPosition);

        //Coordonnées initiales et finales nécessaires au calcul:
        double iniPosX = position.getX();
        double iniPosY = position.getY();
        double finPosX = finalPosition.getX();

        //Définition du triangle rectangle généré par le vecteur trajectoire et ses projections sur les axes:
        double hyp = trajectoryNorm;
        double adj = finPosX - iniPosX;
        double alpha = Math.acos(adj/hyp); //  /!\ alpha est en radians. TODO: cdt sur hyp != 0 ?

        //Distance parcouru entre l'affichage de deux images à l'écran:
        double distanceDone = velocity / gameModel.getConfig().getModelFrameRate(); //TODO: cdt sur numberFPS != 0 ?
        double depX = Math.cos(alpha)*distanceDone;
        double depY = Math.sin(alpha)*distanceDone;

        //Nouvelle position théorique:
        Position newPosition = new Position(map);
        newPosition.setX(iniPosX + depX);
        newPosition.setY(iniPosY + depY);

        //Nouvelle position:
        if (trajectoryNorm-distanceDone <= 0){
            position = finalPosition;
        }else{
            position = newPosition;
        }
    }

    /*==================================================================================================================
                                               GETTEURS/SETTEURS
    ==================================================================================================================*/
    public int getDamage() {
        return damage;
    }

    public Position getPos(){return position;}
}
