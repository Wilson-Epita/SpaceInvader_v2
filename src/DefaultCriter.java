public abstract class DefaultCriter implements Criter
{
    public  String imgPath;
    public  double x, y;
    public  double dX, dY;
    public  double w, h;
    public  double maxPos;
    public  boolean isAlive;
    public  InvaderGameState gameState;
    public  double  rotation;
    public  int     lives;
    // Costructors

    public DefaultCriter(String imgPath, double x, double y,
        InvaderGameState gameState)
    {
        /* Constructor of DefaultCriter

        # Arguments
            imgPath:    String, path of image.
            x:          double, position on X axis.
            y:          double, position on Y axis.
            gameSate    InvaderGameState, Game instance.

        # Returns
            Abstract class.
        */

        this.imgPath = imgPath;
        Picture picture = new Picture(imgPath);
        this.w = picture.width();
        this.h = picture.height();
        this.x = x;
        this.y = y;
        this.dX = 0;
        this.dY = 0;
        this.lives = 1;
        this.rotation = 0;
        this.maxPos = (Criter.screenSize / 2) - (int)(0.5 * w);
        this.isAlive = true;
        this.gameState = gameState;
    }

    public void draw()
    {
        /* Display the player with StdDraw

        # Arguments:
            this.x:         double, position on x axis.
            this.y:         double, position on y axis.
            this.imgPath:   String, path tom image.

        # Returns:
            Draw the Player.
        */
        StdDraw.picture(x, y, imgPath, rotation);
    }

    // Getters
    public int getX() { return (int)(x); }
    public int getY() { return (int)(y); }
    public int getHeight() { return (int)(h); }
    public int getWidth() { return (int)(w); }
    public boolean isAlive() { return isAlive; }

    // Kill the Object
    public void die()
    {
        isAlive = false;
    }

    // Collison
    public void collide()
    {
        lives--;
        if (lives <= 0)
            die();
    }

    // Call in each frame.
    public void comportement()
    {
        if(isAlive)
            draw();
    }

}
