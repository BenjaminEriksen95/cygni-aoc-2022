package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Game {
    int width = 163+1;
    int height = 163+1;
    int offset = 451;
    int source = 500 - offset;
    ArrayList<int[]> map;

    public static Game from(List<String> input, boolean part2) {
        Game game = new Game();
        game.map = new ArrayList<>();
        if (part2) game.height += 2;
        for (int i = 0; i < game.width; i++) {
            game.map.add(new int[game.height]);
        }
        if (part2) for (int i = 0; i < game.width; i++) game.map.get(i)[game.height -1] = 9;

        game.map.get(500- game.offset)[0] = 2;
        input.forEach(l -> game.addBlocks(l));

        return game;
    }

    private void addBlocks(String s) {
        var points = s.split("->");
        for (int i = 1; i < points.length; i++) {
            var c1 = points[i-1].trim().split(",");
            var c2 = points[i].trim().split(",");
            addBlocks(Integer.parseInt(c1[0])-offset,Integer.parseInt(c1[1]), Integer.parseInt(c2[0])-offset,Integer.parseInt(c2[1]));
        }
    }

    private void addBlocks(int x1,int y1,int x2,int y2) {
        if (x1 > x2 || y1 > y2) {
            addBlocks(x2, y2, x1, y1);
            return;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                map.get(i)[j]=1;
            }
        }
    }
    public boolean nextState() {
        if (map.get(source)[1] == 3) throw new IllegalStateException("No space for sand");
        placeSand(source, 1);
        return true;
    }

    private boolean flowRightBlocked(int x, int d) {
        return this.map.get(x-1)[d+1] != 0;
    }

    private boolean flowLeftBlocked(int x, int d) {
        return this.map.get(x+1)[d+1] != 0;
    }

    private void placeSand(int x, int d) {
        if (this.map.get(x)[d+1] == 9 && this.map.get(x)[d] == 0) {
            map.get(x)[d] = 3;
            return;
        }

        // If can fall
        if (this.map.get(x)[d+1] == 0) {
            placeSand(x, d+1);
        }
        else if (x > 0 && !flowRightBlocked(x, d)) {
            placeSand(x-1,d+1);
        }
        else if (x == 163) {
            if (this.map.get(x)[d+1] == 0) placeSand(x,d+1);
            else this.map.get(x)[d] = 3;
        }
        else if (!flowLeftBlocked(x, d)) {
            placeSand(x+1, d+1);
        }
        // else stay!
        else if (this.map.get(x)[d] == 0) {
            map.get(x)[d] = 3;
        }

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < this.height; i++) {
            s += i + " ";
            for (int j = this.width -1; j >= 0; j--) {
                s += this.map.get(j)[i];
            }
            s += "\n";
        }
        return s;
    }

    public int solve() {
        int i = 0;
        var notFull = true;
        while (notFull) {
            try {
                notFull = this.nextState();
                i++;

            } catch (Exception e ) {
                return i;
            }
        }
        return 0;
    }
}

public class App {

    public static Integer getSolutionPart1(List<String> input) {
        var game = Game.from(input, false);
        return game.solve();
    }

    public static Integer getSolutionPart2(List<String> input) {
        var game = Game.from(input, true);
        return game.solve();
    }


    public static void main(String[] args) throws IOException {
        System.out.println("java");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        var input = parseInput("input.txt");

        if (part.equals("part2")) {
            System.out.println(getSolutionPart2(input));
        }
        else
            System.out.println(getSolutionPart1(input));
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).toList();
    }

}