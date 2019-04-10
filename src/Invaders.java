public class Invaders
{
    public static void main(String[] args)
    {
        System.out.println("Space Invaders.");

        InvaderGameState game = new InvaderGameState();
        game.initGame();
        game.game();
    }
}
