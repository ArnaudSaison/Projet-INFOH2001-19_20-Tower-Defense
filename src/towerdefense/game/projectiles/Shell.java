package towerdefense.game.projectiles;

public class Shell extends Projectile {
    private int damage;
    private Thread tShell;

    public Shell(int damage){
        this.damage = damage;
        tShell = new Thread();
        tShell.start();
    }


    public int getDamage() {
        return damage;
    }

    //Avoir un observer Pattern à peu près propre/


    //todo: dégat de zone. Notifier tous :si vous êtes dans ma zone alors dégats.
    // Obsever = hittable
    // notifyHitZone(int position, int radius) appelé sur tous les observer
}
