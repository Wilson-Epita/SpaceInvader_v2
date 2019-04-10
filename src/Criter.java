public interface Criter
{
    /* Specify minimum API for in-game objects */

    int screenSize = 800;

    /* Display the Object with StdDraw */
    void draw();

    // Return position on X axis.
    public int getX();

    // Return position on Y axis.
    public int getY();

    // Return Width.
    public int getWidth();

    // Return Height.
    public int getHeight();

    public boolean isAlive();

    // Kill the object.
    public void die();

    // Collide.
    public void collide();


    // Shot.
    //public void shot();

    /* All Object behaviour */
    public void comportement();

}
