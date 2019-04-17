import java.util.Random;

public class Enemy extends DefaultCriter
{
    // Attributs
    private double      speedX, speedY;
    private double      downDistance;
    private int         direction;
    private boolean     touchWall;
    private Missile[]   missiles;
    private Shooter     shooter;

    // Constructor
    public Enemy(String imgPath, double x, double y, InvaderGameState gameState,
        Shooter shooter)
    {
        /* Constructor of shooter

        # Arguments
            x:          int, position on X axis.
            y:          int, position on Y axis.
            imgPath:    String, path to the img file of shooter.
            gameState:  InvaderGameState, instance of game state.

        # Returns
            An instance of Shooter.
        */
        super (imgPath, x, y, gameState);
        this.shooter = shooter;
        missiles = Missile.initMissiles(3, Criter.enemyMissilePath,
            0, 0, -3, gameState);
        this.downDistance = 0;
        this.touchWall = false;
        this.speedX = 2;
        this.speedY = -3;
        this.direction = 1; // 1: right, -1: left
    }

    // Implements Interface
    private void moveX()
    {
        /* Move object on X axis

        # Arguments
            this.speedX:     double, speed of the Enemy.
            this.maxPos:    int, maximum X position on the screen.
            this.Y:    double, actual speed on Y axis.

        # Returns
            Should modify value of x and dX.
            Modify this.touchWall to True if Wall Collision, false otherwise.
        */

        if (dY != 0)
            dX = 0;
        else
        {
            dX          = speedX * direction;
            x           += speedX * direction;
            touchWall   = (x > maxPos || x < -maxPos);
        }
    }

    private void moveY()
    {
        /* Move object on Y axis

        # Arguments
        this.speedY:        double, speed of the Enemy.
        this.downDistance:  double, what distance the enemy should
            go down before going back in the other direction.
        this.Y:             double, actual speed on Y axis.

        # Returns
        Should modify value of x and dX.
        Modify this.touchWall to True if Wall Collision, false otherwise.
        */

        if (downDistance > 0)
        {
            dY = speedY;
            y += dY;
            downDistance += dY;
        }
        else
            dY = 0;
    }

    private void goDown()
    {
        /* Set down destination for enemys.

        # Instructions
            Should be called when at least one enemy touch a wall.
        */

        direction = - direction;
        downDistance = h + 10;
        touchWall = false;
        x += speedX * direction;
    }

    public void random_shot(double probaility)
    {
        /* Shot randomy depending of probaility.

        # Argument :
            probaility: float, probability that the enemy have to shot.

        # Behaviour :
            if Shot, add the missile in the missile liste.

        */
        boolean shot = (Math.random() <= probaility);

        if (shot)
        {
            int i = 0;
            while(i < missiles.length && missiles[i].isAlive())
                i++;
            if (i < missiles.length)
                missiles[i].shot(x, y, 0);
        }

    }

    public void die()
    {
        /* Kill the enemy And Increase score. */
        isAlive = false;
        gameState.increaseScore();
    }

    public void comportement()
    {
        /* Enemy behaviour.*/
        if (isAlive)
        {
            moveX();
            moveY();
            draw();
        }
        for(int i = 0; i < missiles.length; i++)
            missiles[i].comportement(shooter);
    }

    // Static functions
    public static Enemy[][] initEnemys(int row, int col,
        String imgPath, InvaderGameState gameState, Shooter shooter)
    {
        /* Create list of enemys.

        # Arguments
            row:        int, number of rows.
            col:        int; number of collumns.
            imgPath     String, path to image.
            gameState:  InvaderGameState, instance of game.
        */

        int		x;
        int     y;
        Enemy[][] res = new Enemy[row][col];
    	y = 730;

        for (int i = 0; i < row; i++)
        {
            x = -365;
            for (int j = 0; j < col; j++)
            {
                // New Enemy.
                res[i][j] = new Enemy(imgPath, x, y,  gameState, shooter);
                // Decal by width + 20 pixels.
                x += (res[0][0].w + 20);
            }
            y -= (res[0][0].h + 10);
        }

        return (res);
    }

    private static boolean noEnemyBelow(Enemy[][] enemys, int i, int j)
    {
        boolean res = true;
        i++;

        while (res && i < enemys.length)
        {
            res = !enemys[i][j].isAlive();
            i++;
        }
        return (res);
    }

    public static void enemysComportement(Enemy[][] enemys, InvaderGameState game,
        int row, int col)
    {
        /* Behaviour of all enemys

        # enemys:   Enmey 2D Array, list of enemys.
        # game:     InvaderGame, game Instance.
        # row:      int, number of rows.
        # col:      int, number of collumns.

        */
        boolean anyAlive        = false; // True if one enemy is alive at least.
        boolean changeDirection = false; // True if one enmy touch wall at least.

        for (int i = 0; i < row; i++)
        {
            for(int j = 0; j < col; j++)
            {
                enemys[i][j].comportement();

                if (enemys[i][j].isAlive)
                {
                    if (noEnemyBelow(enemys, i, j))
                        enemys[i][j].random_shot(0.003);
                    anyAlive = true;
                    changeDirection |= (enemys[i][j].touchWall);
                }
            }
        }

        // New wave if the past one die.
        if (!anyAlive)
            game.newWave();


        // Go down if any alien touch wall.
        if (changeDirection)
        {
            for (int i = 0; i < row; i++)
            {
                for(int j = 0; j < col; j++)
                    enemys[i][j].goDown();
            }
        }
    }

}
