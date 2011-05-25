package mysterychess.model;

import mysterychess.util.Util;

/**
 *
 * @author Tin Bui-Huy
 */
public class ChessTimer {

    private long gameSpentTime;
    private long pieceMoveExpiredTime;
    private boolean gameStarted = false;
    private boolean running = false;

    public void startGame() {
        gameStarted = true;
    }

    public void reset() {
        gameStarted = false;
        running = false;
        gameSpentTime = 0;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void startMovePiece() {
        pieceMoveExpiredTime = System.currentTimeMillis()
                + Util.PIECE_MOVE_EXPIRE_TIME;
        running = true;
    }

    public void stopMovePiece() {
        gameSpentTime = gameSpentTime
                + Util.PIECE_MOVE_EXPIRE_TIME
                - getPieceMoveTimeLeft();
        running = false;
    }

    private String formatTime(long t) {
        long second = t / 1000;
        long minutes = second / 60;
        second = second % 60;
        return minutes + ":" + second;
    }

    public long getGameTimeLeft() {
        return Util.GAME_EXPIRE_TIME
                - gameSpentTime
                - Util.PIECE_MOVE_EXPIRE_TIME
                + getPieceMoveTimeLeft();
    }

    public long getPieceMoveTimeLeft() {
        if (running) {
            return pieceMoveExpiredTime - System.currentTimeMillis();
        }

        return Util.PIECE_MOVE_EXPIRE_TIME;
    }

    public String toString() {
        long g =  getGameTimeLeft();
        if (g < 0) {
            g = 0;
        }
        long p =  getPieceMoveTimeLeft();
        if (p < 0) {
            p = 0;
        }
        return formatTime(g) + "        " + formatTime(p);
    }
}
