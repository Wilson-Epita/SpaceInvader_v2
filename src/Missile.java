import java.lang.Math;

public class Missile extends DefaultCriter
{
    // Atributs
    private double  speed;
    // Constructor
    public Missile(String imgPath, double x, double y,
        double speed, InvaderGameState gameState)
    {
        /* Constructor of shooter

        # Arguments
            imgPath:    String, path to the img file of shooter.
            x:          double, position on X axis.
            y:          double, position on Y axis.
            theta:      rotation of the missile.
            gameState:  InvaderGameState, instance of game state.

        # Returns:
            An instance of Missile.
        */
        super(imgPath, x, y, gameState);

        this.isAlive = false;
        this.speed = speed;
    }

    private void move()
    {
        /* Movement of the missile.

        # Arguments
            this.x:     double, position in X axis.
            this.y:     double, position in Y axis.
            this.theta  doube, rotation.

        # Returns
            Modify this.x and this.y.
        */
        double theta = Math.toRadians(rotation);
        x += speed * -Math.sin(theta);
        y += speed * Math.cos(theta);
    }

    public void shot(double x, double y, double rotation)
    {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        isAlive = true;
    }

    private void collideShooter(Shooter player)
    {
        /* Detect collision with Player

        # Arguments
            player:     Shooter, the player.
            this.x:     double, position on X axis.
            this.y:     double; psition on Y axis.

        # Behaviour:
            Call collide on both Object function if they overlap.
        */
        double playerX = player.getX();
        double playerY = player.getY();
        double playerW = 0.5 * player.getWidth();
        double playerH = 0.5 * player.getHeight();

        if (x < playerX + playerW   &&
            x > playerX - playerW   &&
            y < playerY + playerH   &&
            y > playerY - playerH)
        {
            player.collide();
            this.collide();
        }
    }


    private void collideEnemys(Enemy[][] enemys)
    {
        /* Detect collision with enemys.

        # Arguments
            enemys:     Enemys 2D array, the player.
            this.x:     double, position on X axis.
            this.y:     double; psition on Y axis.

        # Behaviour:
            Call collide on both Object function if they overlap.
        */
        for(int i = 0; i < enemys.length; i++)
        {
            for(int j = 0; j < enemys[0].length; j++)
            {
                Enemy enemy = enemys[i][j];
                if(enemy.isAlive())
                {
                    double enemyX = enemy.getX();
                    double enemyY = enemy.getY();
                    double enemyW = 0.5 * enemy.getWidth();
                    double enemyH = 0.5 * enemy.getHeight();

                    if (x < enemyX + enemyW   &&
                        x > enemyX - enemyW   &&
                        y < enemyY + enemyH   &&
                        y > enemyY - enemyH)
                    {
                        enemy.collide();
                        this.collide();
                    }
                }
            }
        }
    }

    private void checkBorder()
    {
        /* Check if missil is out of the screen

        # Arguments
            this.x:      double, position X.
            this.y:      double, position Y.
            this.maxPos: double, Position of Wall.
            Criter.screenSize:  int, size of screen.
        */
        if(x > maxPos || x < -maxPos || y > Criter.screenSize || y < 0)
            die();
    }

    public void comportement(Shooter shooter)
    {
        if(isAlive)
        {
            move();
            collideShooter(shooter);
            checkBorder();
            draw();
        }
    }

    public void comportement(Enemy[][] enemys)
    {
        if(isAlive)
        {
            move();
            collideEnemys(enemys);
            checkBorder();
            draw();
        }
    }


    // Static functions
    public static Missile[] initMissiles(int length, String imgPath, double x,
        double y, double speed, InvaderGameState gameState)
    {
        /* Initialise a new missile  array

        # Argument
            length:         int, number of missle.
            imgPath:        String, path to image.
            x;              double, position X.
            y:              double, position Y.
            theta:          double, rotation.
            directiton:     int, direction 1: un, -1: down.
            gameState       InvaderGameState, game instance.
        */

        Missile[] missiles = new Missile[length];
        for(int i = 0; i < length; i++)
            missiles[i] = new Missile(imgPath, x, y, speed,
                gameState);
        return missiles;
    }
}
