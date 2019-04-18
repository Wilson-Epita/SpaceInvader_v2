import java.awt.event.KeyEvent;
import java.lang.Math;

public class Shooter extends DefaultCriter
{
    // Attributs
    private double      speed, acceleration, deceleration;
    private double      rotation;
    private boolean     isMoving;
    private boolean     spaceRealeased;
    private Missile[]   missiles;
    private Enemy[][]   enemys;

    // Constructor
    public Shooter(String imgPath, InvaderGameState gameState)
    {
        /* Constructor of shooter

        # Arguments
            imgPath:    String, path to the img file of shooter.
            gameState:  InvaderGameState, instance of game state.

        # Returns
            An instance of Shooter.
        */
        super(imgPath, 0, 50, gameState);
        missiles = Missile.initMissiles(3, Criter.missilePath,
            0, 0, 20, gameState);
        this.speed = 8;
        this.acceleration = 0.5;
        this.deceleration = 0.3;
        this.isMoving = false;
        this.spaceRealeased = true;
        this.lives = 3;
    }

    // Setters.
    public void setEnemys(Enemy[][] enemys) { this.enemys = enemys; }

    // Methods.

    public void rotate()
    {

        if (StdDraw.isKeyPressed(KeyEvent.VK_Q) ||
            StdDraw.isKeyPressed(KeyEvent.VK_UP))
        {
            rotation--;
            if (rotation < -90)
                rotation = -90;
        }

        if (StdDraw.isKeyPressed(KeyEvent.VK_E) ||
              StdDraw.isKeyPressed(KeyEvent.VK_DOWN))
        {
            rotation++;
            if (rotation > 90)
                rotation = 90;
        }
    }
    public void moveX()
    {
      /* Move object on X axis

      # Arguments
          this.dX:            double, actual speed of the object.
          this.acceleration:  double, speed we should increase/decrease dX.
          this.speed:      double, maximum value of speed.
          this.maxPos:        int, maximum X position on the screen.

       # Returns
          Should modify value of x and dX.
       */

        isMoving = false;

        // Move Right.
        if (StdDraw.isKeyPressed(KeyEvent.VK_D) ||
              StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            dX += acceleration;
            if (dX > speed)
                dX = speed;
            isMoving = true;
        }
        // Move Left.
        if (StdDraw.isKeyPressed(KeyEvent.VK_A) ||
              StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
        {
            dX -= acceleration;
            if (dX < -speed)
                dX = -speed;
            isMoving = true;
        }
        int direction = (dX < 0) ? -1 : 1;
        // Slow Down.
        if(!isMoving)
        {
            if(dX <= deceleration && dX >= -deceleration)
                dX = 0;
            else
                dX -= deceleration * direction;
        }
        // Stop movevement
        if (StdDraw.isKeyPressed(KeyEvent.VK_S))
            dX = 0;
        // Apply Movement.
        x += dX;
        // Detect Wall Collision.
        if(x > maxPos || x < -maxPos)
        {
            x = maxPos * direction;
            dX = -(dX - 0.1); // Bouncing.
        }
    }

    // Overide Function :

    // Draw
    public void draw()
    {
        /* Display the player with StdDraw with rotation

        # Arguments:
            this.x:         double, position on x axis.
            this.y:         double, position on y axis.
            this.imgPath:   String, path tom image.
            this.rotation   double, rotation of player

        # Returns:
            Draw the Player.
        */
        StdDraw.picture(x, y, imgPath, rotation);
    }


    public void die()
    {
        /* Kill the player */
        isAlive = false;
        System.out.println("Game Over, you are dead.");
    }

    private void shot()
    {
        /* shot

        # Arguments:
            this.missiles   Missile[], array of missiles.
            this.x          double, position X.
            this.y          double, position Y.
            this.theta      double, rotation.

        Behaviour : Shot if there is unused munition
        */

        // Check if there is no Missile
        boolean noMissileAlive = true;
        int     i = 0;
        while(i < missiles.length && noMissileAlive)
            noMissileAlive = !missiles[i++].isAlive();

        // Shot if he can.
        if ((noMissileAlive || spaceRealeased) &&
            StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
        {
            spaceRealeased = false;
            i = 0;
            while(i < missiles.length && missiles[i].isAlive())
                i++;
            if ( i < missiles.length)
                missiles[i].shot(x, y, rotation);
        }
        spaceRealeased |= (!spaceRealeased &&
            !StdDraw.isKeyPressed(KeyEvent.VK_SPACE));
    }

    public void comportement()
    {
        /* Enemy behaviour.

        # Arguments
            this.isAlive:   boolean, true if is Alive.
            missiles:       Missile[], array of missle.
            enemys:         Enemy[], array of enemy.

        # Behaviour :
            Move if key pressed + momentum.
            Shot if key pressed + allowed.
            Comportement for each missiles.
            Draw shooter.
        */
        if (isAlive)
        {
            moveX();
            rotate();
            shot();
            for(int i = 0; i < missiles.length; i++)
                missiles[i].comportement(enemys);
            draw();
        }
    }
}
