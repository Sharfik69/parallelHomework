import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImgWorker {
    protected static int[][] brightness;
    private BufferedImage image;
    private String outputName;
    private int status, height, width;

    /**
     * @param inputName  принимает название изображения с картой
     * @param outputName с каким именем сохранить изображение
     */
    ImgWorker(String inputName, String outputName) {
        try {
            this.image = ImageIO.read(new File("../img/" + inputName));
            this.outputName = outputName;
            height = image.getHeight();
            width = image.getWidth();
            if (width > 500 || height > 500) {
                throw new Exception();
            }
            brightness = new int[height][width];

            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    int color = image.getRGB(col, row);
                    int red = (color >>> 16) & 0xFF;
                    int green = (color >>> 8) & 0xFF;
                    int blue = (color) & 0xFF;
                    brightness[row][col] = (int) (red * 0.2126f + green * 0.7152f + blue * 0.0722f);

                }
            }

            status = 1;
        } catch (Exception e) {
            status = 0;
            e.printStackTrace();
        }
    }

    /**
     * @return возвращает статус. 1 - все хорошо, 0 - произошла ошибка в чтении
     */
    public int getStatus() {
        return status;
    }

    /**
     * @return возвращает матрицу яркости, только если удачно был открыт файл, иначе вернет null
     */
    public int[][] getBrigthnesTable() {
        if (getStatus() == 1) {
            return brightness;
        } else {
            return null;
        }
    }

    /**
     * Метод сохранения изображения и вывод ответа
     *
     * @param solve передаем итоговое решение, рисуем круги на изначальной карте, выводим прибыль от установки вышек
     */
    public void saveImage(Solve solve) {
        if (getStatus() == 0) {
            System.out.println("Невозможно сохранить не открытый файл");
            return;
        }

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));
        for (Point point : solve.getPoints()) {
            g.drawOval(point.getY() - 50, point.getX() - 50, 100, 100);
        }


        try {
            File outputFile = new File("../img/" + outputName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Изображение было сохранено");
            System.out.println("Было сгенерировано " + solve.getPointsArraySize() + " вышек");
            System.out.println("Прибыль от самого лучшего решения, которое мы смогли найти: " + solve.getEvaluation() + " у.е.");
        } catch (IOException e) {
            System.out.println("Не удалось сохранить изображение");
        }
    }

}
