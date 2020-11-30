import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("START");

        ImgWorker img = new ImgWorker("Method_tester.png", "save_tester.png");

        System.out.print("Введите время работы программы - ");
        long t = 10;//in.nextLong();

        System.out.println("Введите количество потоков - ");
        int threadsCnt = 4;//in.nextInt();

        /*
        Solve a = new Solve(img.getBrigthnesTable());
        a.generateSolve();
        img.saveImage(a);
        */

        Finder finder = new Finder(t, img.getBrigthnesTable(), threadsCnt);

        Solve ans = finder.startSearch();

        System.out.println(ans.getPointsArraySize());

        img.saveImage(ans);

    }
}
