package towerdefense.view;

/**
 * Interface qui doit être implémentée par tout élément graphique représentable comme un Node
 * Permet de faire l'intermédiare entre modèle et vue
 * Le but est que le modèle ne doive implémenter aucun package JavaFX
 * Ceci permettrait par exemple de changer complètement de librairie graphique sans pour autant changer le modèle
 *
 * => Concepts MVC
 * */
public interface Printable {
    public void update();
    public void initListeners();
}
