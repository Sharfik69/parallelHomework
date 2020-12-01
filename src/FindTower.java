import java.util.Random;

public class FindTower extends Thread {
    private boolean [][] used;
    private Random rnd;
    private int bestCoverage, stepCount;
    private Point bestPoint;
    FindTower(boolean [][] used, int stepCount) {
        this.used = used;
        rnd = new Random();
        bestCoverage = 0;
        this.stepCount = stepCount;
    }

    public void run(){
        for (int step = 0; step < stepCount; step++) {
            int x = rnd.nextInt(used.length), y = rnd.nextInt(used[0].length);
            Point potentialPoint = new Point(x, y);
            while (Solve.usedPoint.contains(potentialPoint)) {
                x = rnd.nextInt(used.length);
                y = rnd.nextInt(used[0].length);
                potentialPoint = new Point(x, y);
            }
            Solve.usedPoint.put(potentialPoint, true);
            int coverage = 0;
            for (int i = Math.max(0, x - 50); i < Math.min(ImgWorker.brightness.length, x + 51); i++) {
                for (int j = Math.max(0, y - 50); j < Math.min(ImgWorker.brightness[0].length, y + 51); j++) {
                    if (!used[i][j] && Point.isInside(potentialPoint, new Point(i, j))){
                        coverage += ImgWorker.brightness[i][j];
                    }
                }
            }

            if (coverage * 10 - Solve.TOWER_PRICE > bestCoverage) {
                bestCoverage = coverage * 10 - Solve.TOWER_PRICE;
                bestPoint = potentialPoint;
            }
        }
        Thread.yield();
    }

    public Point getBestPoint() {
        return bestPoint;
    }

    public int getBestCoverage() {
        return bestCoverage;
    }
}
