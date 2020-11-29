import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Finder {
    long endTime;
    List<Solve> oldGeneration;
    private int [][] brightnessMatrix;

    Finder (long timeInSec, int [][] brightnessMatrix){
        this.endTime = System.currentTimeMillis() + timeInSec * 1000;
        oldGeneration = null;
        this.brightnessMatrix = brightnessMatrix;
    }

    boolean first = false;
    public void startSearch() {
        while(System.currentTimeMillis() < endTime) {
            if (!first) {
                oldGeneration = Collections.synchronizedList(new ArrayList<>());
                System.out.println("Я генерирую решения");
                for (int i = 0; i < 100; i++) {
                    Solve anotherSolve = new Solve(brightnessMatrix);
                    anotherSolve.generateSolve();
                    oldGeneration.add(anotherSolve);
                }
                System.out.println("Я сделляль");
                first = true;
            }

            List<Solve> newGeneration = Collections.synchronizedList(new ArrayList<>());
            //Вызов штуки, которая генерирует новые решения


            oldGeneration = newGeneration;

        }
        System.out.println("Я кончиль");
    }

}


class superSex extends Thread {
    //Тут будут браться два решения и скрещиваться только после того как нам скажет препод что нужно делать
}