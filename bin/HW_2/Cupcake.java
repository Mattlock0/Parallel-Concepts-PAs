public class Cupcake {
    private boolean uneaten;

    public synchronized void eat() {
        uneaten = false;
    } 

    public synchronized void replace() {
        uneaten = true;
    }

    public Cupcake(boolean start) {
        uneaten = start;
    }

    public boolean isEaten() {
        return !uneaten;
    } // flipped for clarity's sake
}