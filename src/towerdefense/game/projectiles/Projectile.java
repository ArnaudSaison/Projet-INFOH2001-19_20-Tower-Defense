package towerdefense.game.projectiles;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;
import towerdefense.view.npc.NPCView;

public abstract class Projectile implements Runnable, Drawable, Movable {
    protected int damage;
    protected int velocity;
    protected Position finalPosition;

    protected Position position;
    protected Map map;
    protected GameModel gameModel;
    private int FPS;
    protected Thread tProjectile;
    protected Boolean running;
    protected NPC target;

    public Projectile(Map map, Position initialPosition, GameModel gameModel, int damage){
        this.damage = damage;
        position = initialPosition;

        this.map = map;
        this.gameModel = gameModel;
        tProjectile = new Thread();
        running = false;
        
        FPS = gameModel.getConfig().getModelFrameRate();
    }

    /*==================================================================================================================
                                               GESTION DU THREAD
    ==================================================================================================================*/
    @Override
    public void run(){
        while(running){
            while (!gameModel.getPaused()){
                try{
                    int FPS = 24;
                    move();
                    if (position == finalPosition){
                        doDamage(target);
                    }
                    tProjectile.sleep(1000/FPS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            while (gameModel.getPaused()){
                try{
                    tProjectile.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void initialize(NPC target){
        this.target = target;
        this.finalPosition = target.getPos();
        running = true;
        tProjectile.start();
    }

    public abstract void doDamage(NPC target);

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/
    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    public void initDrawing() {
    }

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    public void updateDrawing() {
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
    /**
     * Permet au projectile d'atteindre sa cible via une trajectoire en ligne droite
     * */
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
        double distanceDone = velocity / FPS; //TODO: cdt sur FPS != 0 ?
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
