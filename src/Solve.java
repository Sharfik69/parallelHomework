import java.util.ArrayList;
import java.util.Random;

public class Solve {

    private ArrayList <Point> points;
    private int [][] brightnessMatrix;
    private static final int RADIUS = 50, TOWER_PRICE = 5000000;

    class Point {
        private int x, y;
        Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public double getInstance(Point a) {
            return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y));
        }

    }

    /**
     *
     * @param brightnessMatrix матрица яркости
     */
    Solve(int[][] brightnessMatrix) {
        points = new ArrayList<>();
        this.brightnessMatrix = brightnessMatrix;
    }

    /**
     * Генерирует случайное покрытие точек
     */
    public void generateSolve(){
        Random rnd = new Random();
        int n = rnd.nextInt(35) + 10;
        for (int i = 0; i < n; i++) {
            int y = rnd.nextInt(brightnessMatrix.length), x = rnd.nextInt(brightnessMatrix[0].length);
            points.add(new Point(x, y));
            System.out.println(x + " " + y);
        }
    }

    /**
     *
     * @return возвращает прибыль компании, после установки данного набора точек
     */
    public int getEvaluation(){
        int coverage = 0;
        for (Point point : points) {
            int x = point.getX(), y = point.getY();
            for (int i = Math.max(0, x - 50); i < Math.min(brightnessMatrix.length, x + 51); i++) {
                for (int j = Math.max(0, y - 50); j < Math.min(brightnessMatrix[0].length, y + 51); j++) {
                    if (isInside(point, new Point(i, j))){
                        coverage += brightnessMatrix[i][j];
                    }
                }
            }
        }

        return coverage * 10 - TOWER_PRICE * points.size();
    }

    private boolean isInside(Point a, Point b) {
        return RADIUS >= a.getInstance(b);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
