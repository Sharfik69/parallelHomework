import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("START");

        ImgWorker img = new ImgWorker("test7.png", "save_tester.png");

        System.out.print("Введите время работы программы - ");
        long t = 60;//in.nextLong();

        System.out.println("Введите количество потоков - ");
        int threadsCnt = 4;//in.nextInt();

        Finder finder = new Finder(t, img.getBrigthnesTable(), threadsCnt);

        Solve ans = finder.startSearch();

        System.out.println(ans.getPointsArraySize());

        img.saveImage(ans);

    }
}
