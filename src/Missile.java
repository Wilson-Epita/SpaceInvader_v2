import java.lang.Math;

public class Missile extends DefaultCriter
{
    // Atributs
    private double  speed;
    private double  theta;
    private int     direction;
    // Constructor
    public Missile(String imgPath, double x, double y, double theta,
        int direction, InvaderGameState gameState)
    {
        /* Constructor of shooter

        # Arguments
            imgPath:    String, path to the img file of shooter.
            x:          double, position on X axis.
            y:          double, position on Y axis.
            theta:      rotation of the missile.
            gameState:  InvaderGameState, instance of game state.
            direction:  int, 1: go down, -1: go up.

        # Returns:
            An instance of Missile.
        */
        super(imgPath, x, y, gameState);

        this.isAlive = false;
        this.speed = 20;
        this.theta = theta;
        this.direction = direction;
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
        // double angle = Math.toRadians(theta);
        x += direction * speed * Math.sin(theta);
        y += direction * speed * Math.cos(theta);
    }

    public void shot(double x, double y, double theta)
    {
        this.x = x;
        this.y = y;
        this.theta = theta;
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
        if(x > maxPos || x < -maxPos || y > Criter.screenSize)
            die();
    }

    public void comportement(Shooter shooter, Enemy[][] enemys)
    {
        if(isAlive)
        {
            move();
            if(direction == -1)
                collideShooter(shooter);
            else
                collideEnemys(enemys);
            checkBorder();
            draw();
        }
    }

    // Static functions
    public static Missile[] initMissiles(int length, String imgPath, double x,
        double y, double theta, int direction, InvaderGameState gameState)
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
            missiles[i] = new Missile(imgPath, x, y, theta, direction,
                gameState);
        return missiles;
    }
}
