public class GameStates {

    private static boolean pause=false;
    private static boolean menu=true;
    private static boolean death=false;
    private static boolean play=false;
    private static boolean newLevel=false;
    private static boolean win=false;

    public static boolean isPause() {
        return pause;
    }

    public static void setPause(boolean pause) {
        GameStates.pause = pause;
    }

    public static boolean isMenu() {
        return menu;
    }

    public static void setMenu(boolean menu) {
        GameStates.menu = menu;
    }

    public static boolean isDeath() {
        return death;
    }

    public static void setDeath(boolean death) {
        GameStates.death = death;
    }

    public static boolean isPlay() {
        return play;
    }

    public static void setPlay(boolean play) {
        GameStates.play = play;
    }

    public static boolean isNewLevel() {
        return newLevel;
    }

    public static void setNewLevel(boolean newLevel) {
        GameStates.newLevel = newLevel;
    }

    public static boolean isWin() {
        return win;
    }

    public static void setWin(boolean win) {
        GameStates.win = win;
    }
}
