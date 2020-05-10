package towerdefense.game.projectiles;

import towerdefense.game.Drawable;
import towerdefense.game.Hittable;
import towerdefense.game.Movable;
import towerdefense.game.Placeable;
import towerdefense.game.map.Map;
import towerdefense.game.map.Position;
import towerdefense.game.model.GameModel;
import towerdefense.view.Printable;

public abstract class Projectile implements Runnable, Drawable, Movable, Placeable {
    protected final Object syncKeyDrawing = new Object();
    private final Object syncKeyMove = new Object();

    protected Printable view;

    //Spécification:
    protected int damage;
    protected double velocity;

    //Positon:
    protected Position finalPosition;
    protected Position position;
    private Boolean isArrived;
    protected Position direction;
    private double angle;

    //Autres:
    protected Map map;
    protected GameModel gameModel;
    private Thread tProjectile;
    protected Hittable target; //cible sur laquelle est vérouiller le projectile.
    protected int frameRate; //féquence de rafraîchissement de l'affichege en Hertz.

    /**
     * Construteur:
     *
     * @param initialPosition Position de la tour depuis laquelle le projectile est tirer.
     */
    public Projectile(Map map, GameModel gameModel, int damage, double velocity, Position initialPosition, Hittable target) {
        this.map = map;
        this.gameModel = gameModel;
        this.damage = damage;
        this.velocity = velocity;
        this.position = new Position(initialPosition.getX(), initialPosition.getY(), map);
        this.target = target;
        frameRate = gameModel.getConfig().getModelFrameRate();
        tProjectile = new Thread(this);
        isArrived = false;
        direction = new Position(0, 0, map);
    }

    /*==================================================================================================================
                                               GESTION DU THREAD
    ==================================================================================================================*/
    @Override
    public void initialize() {
        tProjectile.start();
    }

    @Override
    public void run() {
        double sleepTime = 1.0 / gameModel.getConfig().getModelFrameRate() * 1000;

        try {
            // si le jeu est en cours et que le NPC ni arrivé au bout du chemin, ni mort
            while (gameModel.getRunning() && !isArrived && target.getAlive()) {
                if (!gameModel.getPaused()) { // si le jeu n'est pas en pause
                    move(); // déplacement

//                    System.out.println("La position du projectile est" + getPosition().toString());
                    if (position == finalPosition) {
                        doDamage(target); // blasser la cible
                        isArrived = true; //
//                        System.out.println("Le projectile a atteint sa cible");
                    }
                }
                Thread.sleep((long) sleepTime);
            }
        } catch (InterruptedException exception) { // gestion des possibles erreurs lors de l'exécution du thread
            exception.printStackTrace();
        }

        gameModel.killElement(this); // suppression de cet élément du modèle et de la vue
    }

    /**
     * Comportement du projectile, redéfinie dans chaque sous-classe
     */
    public abstract void doDamage(Hittable target);

    /*==================================================================================================================
                                               GESTION DE LA REPRESENTATION
    ==================================================================================================================*/

    /**
     * Initilisation de la vue
     * Création d'un objet de la vue qui pourra ensuite être récupéré
     */
    @Override
    public abstract void initDrawing();

    /**
     * Mise à jour de la représentation graphique
     * Ne peut être appelée que par la vue
     */
    @Override
    public void updateDrawing() {
        synchronized (syncKeyDrawing) {
            view.update();
        }
    }

    /**
     * Récupérer la représentation graphique de l'ojet
     * Ne peut être appelée que par la vue
     *
     * @return représentation graphique de l'ojet
     */
    @Override
    public Printable getDrawing() {
        return view;
    }

    /*==================================================================================================================
                                               GESTION DU MOUVEMENT
    ==================================================================================================================*/

    /**
     * Permet au projectile d'atteindre sa cible via une trajectoire en ligne droite
     */
    @Override
    public void move() {
//        //Update de la position de la cible pour permettre le suivit de la cible par le projectile:
//        //TODO: problème si NPC trop rapide (flèche devient un missile à tête chercheuse)
//        finalPosition = target.getPosition();
//
//        //Distance entre la tour et le point d'impact du projectile:
//        double trajectoryNorm = position.getDistance(finalPosition);
//
//        //Coordonnées initiales et finales nécessaires au calcul:
//        double iniPosX = position.getX();
//        double iniPosY = position.getY();
//        double finPosX = finalPosition.getX();
//        double finPosY = finalPosition.getY();
//
//        //Définition du triangle rectangle généré par le vecteur trajectoire et sa projection orthogonale sur l'axe X:
//        double hyp = trajectoryNorm;
//        double adj = finPosX - iniPosX;
//        double alpha = Math.acos(adj / hyp); // /!\ alpha est en radians.
//
//        //Détermination du signe de l'incrément de déplacement en X et Y (cas par défaut : déplacement en haut à droite):
//        int signDepX = 1;
//        int signDepY = 1;
//
//        if ((finPosX - iniPosX >= 0) && (finPosY - iniPosY < 0)) {
//            signDepY = -1;
//        } else if ((finPosX - iniPosX < 0) && (finPosY - iniPosY >= 0)) {
//            signDepX = -1;
//        } else if ((finPosX - iniPosX < 0) && (finPosY - iniPosY < 0)) {
//            signDepX = -1;
//            signDepY = -1;
//        }
//
//        //Distance parcourue entre l'affichage de deux images à l'écran:
//        double distanceDone = velocity / gameModel.getConfig().getModelFrameRate();
//        double depX = Math.cos(alpha) * distanceDone;
//        double depY = Math.sin(alpha) * distanceDone;
//
//        //Nouvelle position théorique:
//        Position newPosition = new Position(map);
//        newPosition.setX(iniPosX + depX);
//        newPosition.setY(iniPosY + depY);
//
//        //Nouvelle position, on vérifie que la position sinale n'est pas dépassée:
//        if (trajectoryNorm - distanceDone <= 0) {
//            position = finalPosition;
//        } else {
//            position = newPosition;
//        }

        double maxDistance = velocity / gameModel.getConfig().getModelFrameRate();

        finalPosition = target.getPosition();
        direction = finalPosition.getSubstracted(position);
        double distance = direction.getNorm();
        angle = direction.getAngle();

        synchronized (syncKeyMove) {
            if (distance > maxDistance) {
                position.add(direction.getNormalized().getMultiplied(maxDistance));
            } else {
                position = finalPosition;
            }
        }

    }

    public Position getDirection() {
        return direction;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
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
