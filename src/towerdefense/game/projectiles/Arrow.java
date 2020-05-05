package towerdefense.game.projectiles;

public class Arrow extends Projectile {
    private int damage;

    public Arrow(int damage){
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    //dissocier chaque flêche
}
