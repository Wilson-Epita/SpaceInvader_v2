import java.awt.event.KeyEvent;

public class Shooter extends DefaultCriter
{
    // Attributs
    private double      speed, acceleration, deceleration;
    private boolean     isMoving;
    private Missile[]   missiles;
    private Enemy[][] enemys;

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

        missiles = Missile.initMissiles(3, "img/projectile.png",
            0, 0, 0, 1, gameState);
        this.speed = 8;
        this.acceleration = 0.5;
        this.deceleration = 0.3;
        this.isMoving = false;
    }

    // Setters.
    public void setEnemys(Enemy[][] enemys) { this.enemys = enemys; }

    // Methods.
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

        if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
        {
            boolean done = false;
            int i = 0;
            while(i < missiles.length && !done)
            {

                if (!(missiles[i].isAlive()))
                {
                    missiles[i].shot(x, y, 0);
                    done = true;
                }
                i++;
            }
        }
    }

    public void comportement()
    {
        /* Enemy behaviour. */
        if (isAlive)
        {
            moveX();
            shot();
            for(int i = 0; i < missiles.length; i++)
                missiles[i].comportement(this, enemys);
            draw();
        }
    }
}
