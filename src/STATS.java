public class STATS {

    private static int numFood=5;
    private static int numEnemies=2;
    private static int life=3;
    private static int level=1;
    private static int lowSpeed=4;
    private static int rangeSpeed=8;
    private static int numDeaths=0;

    public static int getNumFood() {
        return numFood;
    }

    public static void setNumFood(int numFood) {
        STATS.numFood = numFood;
    }

    public static int getNumEnemies() {
        return numEnemies;
    }

    public static void setNumEnemies(int numEnemies) {
        STATS.numEnemies = numEnemies;
    }

    public static int getLife() {
        return life;
    }

    public static void setLife(int life) {
        STATS.life = life;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        STATS.level = level;
    }

    public static int getLowSpeed() {
        return lowSpeed;
    }

    public static void setLowSpeed(int lowSpeed) {
        STATS.lowSpeed = lowSpeed;
    }

    public static int getRangeSpeed() {
        return rangeSpeed;
    }

    public static void setRangeSpeed(int rangeSpeed) {
        STATS.rangeSpeed = rangeSpeed;
    }

    public static void updateLevel() {
        switch (level) {
            case 1:
                setNumFood(5);
                setNumEnemies(2);
                setLowSpeed(2);
                setRangeSpeed(2);
                break;
            case 2:
                setNumFood(8);
                setNumEnemies(3);
                setLowSpeed(3);
                setRangeSpeed(5);
                break;
            case 3:
                setNumFood(10);
                setNumEnemies(3);
                setLowSpeed(5);
                setRangeSpeed(8);
                break;
            case 4:
                setNumFood(15);
                setNumEnemies(3);
                setLowSpeed(8);
                setRangeSpeed(10);
        }
    }

    public static int getNumDeaths() {
        return numDeaths;
    }

    public static void setNumDeaths(int numDeaths) {
        STATS.numDeaths = numDeaths;
    }
}
