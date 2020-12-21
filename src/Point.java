public class Point {
    private int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static boolean isInside(Point a, Point b) {
        return Solve.RADIUS >= a.getInstance(b);
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