package towerdefense.game.projectiles;

import towerdefense.game.Drawable;
import towerdefense.game.Movable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.game.npcs.NPC;
import towerdefense.view.Printable;

public abstract class Projectile implements Runnable, Drawable, Movable {
    protected int damage;
    protected int velocity;
    protected Position finalPosition;

    protected Position position;
    protected Map map;
    protected GameModel gameModel;
    protected Thread tProjectile;
    protected Boolean running;
    protected NPC target;

    public Projectile(Map map,Position initialPosition, GameModel gameModel, int damage){
        this.damage = damage;
        position = initialPosition;

        this.map = map;
        this.gameModel = gameModel;
        tProjectile = new Thread();
        running = false;
    }

    /*==================================================================================================================
                                               GESTION DU THREAD
    ==================================================================================================================*/
    @Override
    public void run(){
        while(running){
            while (!gameModel.getPaused()){
                try{
                    int numberFPS = 24;
                    move(numberFPS);
                    if (position == finalPosition){
                        doDamage(target);
                    }
                    tProjectile.sleep(1000/numberFPS);
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
    @Override
    public Printable getDrawing(){return null;}

    @Override
    public void removeDrawing(){}

    @Override
    public void updateDrawing(){}

    /*==================================================================================================================
                                               GESTION DU MOUVEMENT
    ==================================================================================================================*/
    @Override
    /** Permet au projectile d'atteindre sa cible via une trajectoire en ligne droite*/
    public void move(int numberFPS){
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
        double distanceDone = velocity/numberFPS; //TODO: cdt sur numberFPS != 0 ?
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
