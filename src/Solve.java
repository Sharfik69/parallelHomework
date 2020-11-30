import java.util.*;

public class Solve {

    private ArrayList <Point> points;
    private int [][] brightnessMatrix;
    private int [][] prefixMatrix;
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

    Solve (List <Point> points) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
        this.brightnessMatrix = ImgWorker.brightness;
    }

    /**
     * Генерирует случайное покрытие точек
     */
    public void generateSolve(){
        Random rnd = new Random();
        int n = rnd.nextInt(35) + 10;
        for (int i = 0; i < n; i++) {
            int x = rnd.nextInt(brightnessMatrix.length), y = rnd.nextInt(brightnessMatrix[0].length);
            points.add(new Point(x, y));
        }
    }

    public void generateSmartSolve() {
        prefixMatrix = new int[brightnessMatrix.length + 1][brightnessMatrix[0].length + 1];
        //TODO: Сделать умное рандомное решение через матрицу сумм
        for (int i = 0; i < brightnessMatrix.length; i++) {
            for (int j = 0 ; j < brightnessMatrix[0].length; j++) {

            }
        }
    }

    /**
     *
     * @return возвращает прибыль компании, после установки данного набора точек
     */
    public int getEvaluation(){
        int coverage = 0;

        boolean [][] used = new boolean[brightnessMatrix.length][brightnessMatrix[0].length];

        for (Point point : points) {
            int x = point.x, y = point.y;
            for (int i = Math.max(0, x - 50); i < Math.min(brightnessMatrix.length, x + 51); i++) {
                for (int j = Math.max(0, y - 50); j < Math.min(brightnessMatrix[0].length, y + 51); j++) {
                    if (!used[i][j] && isInside(point, new Point(i, j))){
                        coverage += brightnessMatrix[i][j];
                        used[i][j] = true;
                    }
                }
            }
        }

        return coverage * 10 - TOWER_PRICE * points.size();
    }

    private boolean isInside(Point a, Point b) {
        return RADIUS >= a.getInstance(b);
    }

    public List<Point> getPoints() {
        return points;
    }
    public int getPointsArraySize(){
        return points.size();
    }

    public static Solve getBest(Solve a, Solve b){
        if (a.getEvaluation() > b.getEvaluation()) {
            return a;
        }
        else {
            return b;
        }
    }

    public Solve mergeSolve(Solve b) {
        Solve newSolve = simpleMerge(b);
        return simpleMutation(newSolve);
    }

    private Solve simpleMerge(Solve b) {
        Random rnd = new Random();
        int n = rnd.nextBoolean() ? getPointsArraySize() : b.getPointsArraySize();
        ArrayList <Point> mergedList = new ArrayList<>(getPoints());
        mergedList.addAll(b.getPoints());

        Collections.shuffle(mergedList);

        HashSet<Point> hashSet = new HashSet<>(mergedList);

        mergedList = new ArrayList<>(hashSet);

        return new Solve(mergedList.subList(0, n));

    }

    private Solve simpleMutation(Solve newSolve) {
        return deleteTower(newSolve);
//        if (newSolve.points.size() > 4) return deleteTower(newSolve);
//        return newSolve;
    }

    private Solve deleteTower(Solve a) {
        ArrayList <Point> shallWeDeleteThis = new ArrayList<>();

        int height = brightnessMatrix.length, width = brightnessMatrix[0].length;
        boolean [][] used = new boolean[height][width];

        for (Point point : a.points) {
            int coverage = 0;
            int x = point.x, y = point.y;
            for (int i = Math.max(0, x - 50); i < Math.min(brightnessMatrix.length, x + 51); i++) {
                for (int j = Math.max(0, y - 50); j < Math.min(brightnessMatrix[0].length, y + 51); j++) {
                    if (!used[i][j] && isInside(point, new Point(i, j))){
                        coverage += brightnessMatrix[i][j];
                        used[i][j] = true;
                    }
                }
            }
            if (coverage * 10 - TOWER_PRICE < 0) {
                shallWeDeleteThis.add(point);
            }
        }



        for (Point point : shallWeDeleteThis) {
            a.points.remove(point);
        }
        return a;
    }

    @Override
    public String toString() {
        StringBuilder stringResponse = new StringBuilder("String {\n");
        for (Point point : points) {
            stringResponse.append(point.x).append(" ").append(point.y).append("\n");
        }
        stringResponse.append("}");
        return stringResponse.toString();
    }

}
