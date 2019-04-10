public class InvaderGameState
{
    private int score;
    private boolean isOver;
    private boolean enemysTouchWall;
    private Shooter shooter;
    private Enemy[][] enemys;

    // Constructor
    public InvaderGameState()
    {
        /* Constructor of InvaderGameState

        # Returns
            An instance of rowInvad, boolean isFirst, boolean isFirster game Show.
        */
        this.score = 0;
        this.isOver = false;
        this.enemysTouchWall = false;
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(-400, 400);
        StdDraw.setYscale(0, 800);
    }

    public void initGame()
    {
        /* Initialise all game variable who need gameState instance.

        # Returns:
            this.shooter instance.
            this.enemys 2D Array;
        */
        shooter = new Shooter("img/player_s.png", this);
        enemys = Enemy.initEnemys(4, 6, "img/alien.png", this);
        shooter.setEnemys(enemys);
    }

    public void newWave()
    {
        enemys = Enemy.initEnemys(4, 6, "img/alien.png", this);
        shooter.setEnemys(enemys);
    }

    public void game()
    {
        /* Game loop */
        while (!isOver)
        {
            StdDraw.clear();
            StdDraw.picture(0, 400, "img/black_background.jpg");
            shooter.comportement();
            Enemy.enemysComportement(enemys, this, 4, 6);
            StdDraw.show(10);
        }
    }
    public void increaseScore()
    {
        /* Increase the score

        # Instructions

            Should be called when Enemy is killed.

        # Returns
            # Modify score.
        */
        score++;
    }


    public void stopGame()
    {
        /* Stop the game

        # Instructions
            Should be called when Shooter is killed.

        # Returns
            # Turn isOver to false.
        */
        isOver = false;
    }
}