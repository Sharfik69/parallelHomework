import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("START");

        ImgWorker img = new ImgWorker("Method_tester.png", "save_tester.png");

        System.out.println("Введите время работы программы - ");
//        long t = in.nextLong();

        Finder finder = new Finder(10, img.getBrigthnesTable());

        finder.startSearch();

//        Solve solve = new Solve(img.getBrigthnesTable());
//
//        solve.generateSolve();
//
//        img.saveImage(solve);

    }
}
