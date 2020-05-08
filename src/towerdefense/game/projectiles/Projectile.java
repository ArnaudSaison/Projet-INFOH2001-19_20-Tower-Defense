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
    private Boolean isArrived;

    //Autres:
    protected Map map;
    protected GameModel gameModel;
    protected Thread tProjectile;
    protected NPC target; //cible sur laquelle est vérouiller le projectile.
    protected int frameRate; //féquence de rafraîchissement de l'affichege en Hertz.

    /**
     * Construteur:
     *
     * @param initialPosition Position de la tour depuis laquelle le projectile est tirer.
     */
    public Projectile(Map map, GameModel gameModel, int damage, int velocity, Position initialPosition, NPC target) {
        this.map = map;
        this.gameModel = gameModel;
        this.damage = damage;
        this.velocity = velocity;
        this.position = initialPosition;
        this.target = target;
        frameRate = gameModel.getConfig().getModelFrameRate();
        tProjectile = new Thread(this);
        tProjectile.start();
        isArrived = false;
    }

    /*==================================================================================================================
                                               GESTION DU THREAD
    ==================================================================================================================*/
    public void run() {
        while (gameModel.getRunning() && (!isArrived)) {
            try {
                if (!gameModel.getPaused()) {
                    move();
//                    System.out.println("La position du projectile est" + getPosition().toString());
                    if (position == finalPosition) {
                        doDamage(target);
                        isArrived = true;
//                        System.out.println("Le projectile a atteint sa cible");
                    }
                    Thread.sleep(1000 / frameRate);
                } else {
                    Thread.sleep(1000);
//                    System.out.println("le projectile est en pause");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Comportement du projectile, redéfinie dans chaque sous-classe
     */
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

    /**
     * Permet au projectile d'atteindre sa cible via une trajectoire en ligne droite
     */
    public void move() {
        //Update de la position de la cible pour permettre le suivit de la cible par le projectile:
        //TODO: problème si NPC trop rapide (flèche devient un missile à tête chercheuse)
        finalPosition = target.getPosition();

        //Distance entre la tour et le point d'impact du projectile:
        double trajectoryNorm = position.getDistance(finalPosition);

        //Coordonnées initiales et finales nécessaires au calcul:
        double iniPosX = position.getX();
        double iniPosY = position.getY();
        double finPosX = finalPosition.getX();
        double finPosY = finalPosition.getY();

        //Définition du triangle rectangle généré par le vecteur trajectoire et sa projection orthogonale sur l'axe X:
        double hyp = trajectoryNorm;
        double adj = finPosX - iniPosX;
        double alpha = Math.acos(adj / hyp); // /!\ alpha est en radians.

        //Détermination du signe de l'incrément de déplacement en X et Y (cas par défaut : déplacement en haut à droite):
        int signDepX = 1;
        int signDepY = 1;

        if ((finPosX - iniPosX >= 0) && (finPosY - iniPosY < 0)) {
            signDepY = -1;
        } else if ((finPosX - iniPosX < 0) && (finPosY - iniPosY >= 0)) {
            signDepX = -1;
        } else if ((finPosX - iniPosX < 0) && (finPosY - iniPosY < 0)) {
            signDepX = -1;
            signDepY = -1;
        }

        //Distance parcourue entre l'affichage de deux images à l'écran:
        double distanceDone = velocity / gameModel.getConfig().getModelFrameRate();
        double depX = Math.cos(alpha) * distanceDone;
        double depY = Math.sin(alpha) * distanceDone;

        //Nouvelle position théorique:
        Position newPosition = new Position(map);
        newPosition.setX(iniPosX + depX);
        newPosition.setY(iniPosY + depY);

        //Nouvelle position, on vérifie que la position sinale n'est pas dépassée:
        if (trajectoryNorm - distanceDone <= 0) {
            position = finalPosition;
        } else {
            position = newPosition;
        }
    }

    /*==================================================================================================================
                                               GETTEURS/SETTEURS
    ==================================================================================================================*/
    public int getDamage() {
        return damage;
    }

    public Position getPosition() {
        return position;
    }

    /*==================================================================================================================
                                                 AUTRES
    ==================================================================================================================*/
    @Override
    public String toString() {
        return "Damage : " + damage + "\n" +
                "-velocity : " + velocity + "\n" +
                "-my position : " + position.toString() + "\n" +
                "-my target : " + target.toString() + "\n";
    }
}
