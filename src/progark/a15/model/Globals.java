package progark.a15.model;

public class Globals {
	// TODO: Vil helst ikke bruke globals (stygt). MADS B: Har du en bedre måte  sende data fra DiffSelectActivity til GameView/GameEngine?
	private static int difficulty;
	private static PlayerType playerType;
	
	// Setters
	public static void setDifficulty(int difficulty) { Globals.difficulty = difficulty; }
	public static void setPlayerType(PlayerType playerType) { Globals.playerType = playerType; }
	
	// Getters
	public static int getDifficulty() { return difficulty; }
	public static PlayerType getPlayerType() { return playerType; }
}
