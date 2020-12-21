public class Main {
    public static void main(String[] args) throws InterruptedException {

        if (args.length != 3) {
            System.out.println("Необходимо в качестве аргументов запуска указать имя файла в папке img, время выполнения работы и количество потоков, которые будут генерировать новые решения");
            System.out.println("Например: image.jpg 60 4");
            System.exit(0);
        }
        String fileName = "";
        long time = 0;
        int threadsCnt = 4;

        try {
            fileName = args[0];
            time = Long.parseLong(args[1]);
            threadsCnt = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Ошибка в введеных аргументах");
            System.exit(0);
        }

        ImgWorker img = new ImgWorker(fileName, "output.png");


        Finder finder = new Finder(time, img.getBrigthnesTable(), threadsCnt);

        Solve ans = finder.startSearch();

        img.saveImage(ans);

    }
}
