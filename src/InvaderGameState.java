import java.awt.event.KeyEvent;
public class InvaderGameState
{
    private int score;
    private int level;
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

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(-400, 400);
        StdDraw.setYscale(0, 800);
        initGame();
    }

    public void initGame()
    {
        /* Initialise all game variable who need gameState instance.

        # Returns:
            this.shooter instance.
            this.enemys 2D Array;
        */
        this.level = 1;
        this.score = 0;
        this.isOver = false;
        this.enemysTouchWall = false;
        shooter = new Shooter(Criter.shooterPath, this);
        enemys = Enemy.initEnemys(4, 6, 1, Criter.enemyPath1, this, shooter);
        shooter.setEnemys(enemys);
    }

    // Getters & Setters :
    public int getLevel() { return level; }
    public void over() { isOver = true; }

    public void newWave()
    {
        level++;
        int enemy_lives;
        if (level < 3)
            enemy_lives = level;
        else
            enemy_lives = 3;
        enemys = Enemy.initEnemys(4, 6, enemy_lives, Criter.enemyPath1,
            this, shooter);
        shooter.setEnemys(enemys);
    }

    public void game()
    {
        /* Game loop */
        while (shooter.isAlive() && !isOver)
        {
            StdDraw.clear();
            StdDraw.picture(0, 400, Criter.backgroundPath);
            displayScoreLevel();
            shooter.comportement();
            Enemy.enemysComportement(enemys, this, 4, 6);
            StdDraw.show(10);
            if (StdDraw.isKeyPressed(KeyEvent.VK_N))
                newWave();
        }

        initGame();
        game();
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

    public void displayScoreLevel()
    {
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.textLeft(-400, 770, "Score: " + score);
        StdDraw.textRight(400, 770, "Level: " + level);
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
