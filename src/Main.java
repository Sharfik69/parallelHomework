public class Main {
    public static void main(String[] args) {
        System.out.println("START");

        ImgWorker img = new ImgWorker("Method_tester.png", "save_tester.png");

        Solve solve = new Solve(img.getBrigthnesTable());

        solve.generateSolve();

        img.saveImage(solve);

    }
}
