package aoc;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {



    public static Integer getSolutionPart1(List<String> lines, HashMap<Point, Character> map) {
        HashMap<Point, Integer> sensors = new HashMap<>();
        lines.stream().map(l-> new Scanner(l).useDelimiter("[^0-9]+")).forEach(s-> {
            var x1 = s.nextInt();
            var y1 = s.nextInt();
            var x2 = s.nextInt();
            var y2 = s.nextInt();

            sensors.put(new Point(x1,y1), Math.abs(x1 - x2) + Math.abs(y1 - y2));
        });
        int count = 0;
        var s = "";
        for (int x = -1000000; x <= 1000000; x++) {
            var p = new Point(x,10);
            if (map.containsKey(p) && map.get(p) == 'B') {
                s+= "B";
                continue;
            }
            if (inRange(p, sensors)) {
                count++;
                s+= "#";
                //System.out.println(x + " " + 10 + " with " + map.get(new Point(x,10)));
                //System.out.println(inRange(x,10, sensors) + " c" + count);
            } else {
                System.out.println("not");
                s+= ".";
            }
        }

        System.out.println();
        System.out.println(s.replace("####################################################################################################", "!"));
        return count;
    }

    private static boolean inRange(Point p, HashMap<Point, Integer> sensors) {
        return sensors.keySet().stream().anyMatch(s -> (Math.abs(s.x - p.x) + Math.abs(s.y - p.y)) <= sensors.get(s));
    }

    public static Integer getSolutionPart2(List<String> input) {
        return 0;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("java");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        var input = parseInput("input.txt");
        var map = toMap(input);
        if (part.equals("part2")) {
            System.out.println(getSolutionPart2(input));
        }
        else
            System.out.println(getSolutionPart1(input, map));
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).toList();
    }

    private static void printMap(HashMap<Point, Character> map) {
        for (int y = -2; y <= 22; y++) {
            String s = "";
            for (int x = -2; x <= 25; x++) {
                if (map.containsKey(new Point(x, y))) {
                    s += map.get(new Point(x, y));
                }
                else {
                    s += ".";
                }
            }
            System.out.println(s);
        }
    }

    private static HashMap<Point, Character> toMap(List<String> lines) {

        HashMap<Point, Character> map = new HashMap<>();
        lines.stream().map(l-> new Scanner(l).useDelimiter("[^0-9]+")).forEach(s-> {
            map.put(new Point(s.nextInt(),s.nextInt()), 'S');
            map.put(new Point(s.nextInt(),s.nextInt()), 'B');
        });
        return map;
    }

}