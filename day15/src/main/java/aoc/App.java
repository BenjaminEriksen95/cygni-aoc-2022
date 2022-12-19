package aoc;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {

    public static Integer getSolutionPart1(List<String> lines, HashMap<Point, Character> map) {
        HashMap<Point, Integer> sensors = deriveSensorRanges(lines);
        int count = 0;
        for (int x = -3000000; x <= 10000000; x++) {
            var p = new Point(x, 2000000);
            if (map.containsKey(p) && map.get(p) == 'B') continue;
            if (inRange(p, sensors))  count++;
        } return count;
    }

    private static HashMap<Point, Integer> deriveSensorRanges(List<String> lines) {
        var sensors = new HashMap<Point, Integer>();
        lines.stream().map(l-> new Scanner(l).useDelimiter("[^-0-9]+")).forEach(s-> {
            int x1 = s.nextInt(), y1 = s.nextInt(), x2 = s.nextInt(), y2 = s.nextInt();
            sensors.put(new Point(x1,y1), Math.abs(x1 - x2) + Math.abs(y1 - y2));
        }); return sensors;
    }

    private static int distance(Point current, HashMap<Point, Integer> sensorRanges) {
        var sensorInRange = sensorRanges.keySet().stream().filter(s -> (Math.abs(s.x - current.x) + Math.abs(s.y - current.y)) <= sensorRanges.get(s)).findAny();
        if (sensorInRange.isPresent()) {
            var sensor = sensorInRange.get();
            return sensorRanges.get(sensor) - (Math.abs(sensor.x - current.x) + Math.abs(sensor.y - current.y) );
        } else return -1;
    }

    private static boolean inRange(Point p, HashMap<Point, Integer> sensors) {
        return sensors.keySet().stream().anyMatch(s -> (Math.abs(s.x - p.x) + Math.abs(s.y - p.y)) <= sensors.get(s));
    }

    public static Long getSolutionPart2(List<String> lines, HashMap<Point, Character> map) {
        HashMap<Point, Integer> sensors = deriveSensorRanges(lines);
        int from = 0, to = 4000000;
        for (int y = from; y < to; y++) {
            for (int x = from; x < to; x++) {
                var c = new Point(x,y);
                long d = distance(c, sensors);
                if (d == -1) {
                    return x*4000000L + y;
                } else {
                    if (d > 0) x+= d;
                }
            }
        } return -1L;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("java");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        var input = parseInput("input.txt");
        var map = toMap(input);
        if (part.equals("part2")) {
            System.out.println(getSolutionPart2(input, map));
        }
        else
            System.out.println(getSolutionPart1(input, map));
    }

    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).toList();
    }
    private static HashMap<Point, Character> toMap(List<String> lines) {
        HashMap<Point, Character> map = new HashMap<>();
        lines.stream().map(l-> new Scanner(l).useDelimiter("[^0-9]+")).forEach(s-> {
            map.put(new Point(s.nextInt(),s.nextInt()), 'S');
            map.put(new Point(s.nextInt(),s.nextInt()), 'B');
        }); return map;
    }
}