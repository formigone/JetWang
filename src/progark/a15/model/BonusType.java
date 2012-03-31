package progark.a15.model;

/*
 * Enum containing all bonus types available.
 * Used by CollectableSprite for bonus values and PlayerSprite (for utilizing the bonus)
 * Format is: BONUS_NAME (magnitude_easy, magnitude_medium, magnitude_hard), for the three difficulty values.
 * Magnitude is a concrete number which relates to different things depending on the type of bonus.
 */
public enum BonusType {
	FUEL_ADD(10, 7, 5),
	FUEL_FILL(100, 75, 50),
	BONUSPOINT_SMALL(100, 200, 300),
	BONUSPOINT_BIG(500, 700, 1000);
	
	private final int mag_e,mag_m,mag_h;
	private BonusType(int mag_e, int mag_m, int mag_h) {
		this.mag_e=mag_e;
		this.mag_m=mag_m;
		this.mag_h=mag_h;
	}
	public int getEasy() { return mag_e; }
	public int getMedium() { return mag_m; }
	public int getHard() { return mag_h; }
	
}
