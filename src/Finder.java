import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Finder {
    private long endTime;
    private int threadsCnt;
    static protected List<Solve> oldGeneration;
    static protected List<Solve> newGeneration;
    private int [][] brightnessMatrix;

    Finder (long timeInSec, int [][] brightnessMatrix, int threadsCnt){
        this.endTime = System.currentTimeMillis() + timeInSec * 1000;
        oldGeneration = null;
        this.brightnessMatrix = brightnessMatrix;
        this.threadsCnt = threadsCnt;
    }

    boolean first = false;
    public Solve startSearch() throws InterruptedException {
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

             newGeneration = Collections.synchronizedList(new ArrayList<>());

             geneticSolve [] threads = new geneticSolve[threadsCnt];

             for (int i = 0; i < threadsCnt; i++) {
                 threads[i] = new geneticSolve(100 / threadsCnt + (i == threadsCnt - 1 ? 100 % threadsCnt : 0));
                 threads[i].start();
             }
             for (int i = 0; i < threadsCnt; i++) {
                 threads[i].join();
             }

            oldGeneration = newGeneration;
        }
        int evaluation = Integer.MIN_VALUE;
        Solve ans = null;
        for (Solve solve : newGeneration) {
            int evLoc = solve.getEvaluation();
            if (evLoc > evaluation) {
                ans = solve;
                evaluation = evLoc;
            }
        }
        System.out.println("Мы нашли итоговое решение");

        return ans;
    }

}


class geneticSolve extends Thread {
    //Тут будут браться два решения и скрещиваться только после того как нам скажет препод что нужно делать
    int cnt;
    Random rnd = new Random();
    geneticSolve(int cnt) {
        this.cnt = cnt;
    }

    public void run(){
        for (int i = 0; i < cnt; i++) {
            ArrayList<Integer> ids = new ArrayList<>();
            int k;
            while (ids.size() < 4) {
                k = rnd.nextInt(Finder.oldGeneration.size());
                if (!ids.contains(k)) {
                    ids.add(k);
                }

            }
            Solve mother = Solve.getBest(Finder.oldGeneration.get(ids.get(0)), Finder.oldGeneration.get(ids.get(2)));
            Solve father = Solve.getBest(Finder.oldGeneration.get(ids.get(1)), Finder.oldGeneration.get(ids.get(3)));

            Solve newSolve = mother.mergeSolve(father);
            Finder.newGeneration.add(newSolve);
        }
    }
}