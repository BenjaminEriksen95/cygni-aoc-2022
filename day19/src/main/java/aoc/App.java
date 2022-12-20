package aoc;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


class State {

    State prev = null;
    int minute = 0;
    int[] prices;
    int[] resources = {0, 0, 0, 0};
    int[] machines = {1, 0, 0, 0};
    public State() {}
    public State(int p1, int p2, int p3, int p4, int p5, int p6) {
        prices = new int[]{p1, p2, p3, p4, p5, p6};
    }
    public List<State> expand() {
        List<State> possibleStates = new ArrayList<>();
        for (int machine : possiblePurchases()) {
            possibleStates.add(this.next(machine));
        }
        if (possibleStates.size() <= 1) possibleStates.add(this.next(-1));
        return possibleStates;
    }

    private List<Integer> possiblePurchases() {
        List<Integer> options = new ArrayList<>();
        if (resources[0] >= prices[0] && resources[0] < 10 && minute < 12) {
            if (machines[2] <= 4) options.add(0);
        }
        if (resources[0] >= prices[1] && minute < 16) {
            if (machines[3] <= 3) options.add(1);
        }
        if (resources[0] >= prices[2] && resources[1] >= prices[3]) {
            options.add(2);
        }
        if (resources[0] >= prices[4] && resources[2] >= prices[5]) {
            options.add(3);
        } return options;
    }

    public State next(int machine) {
        State next = new State();
        next.prev = this;
        next.prices = this.prices;
        next.minute = this.minute + 1;

        for (int i = 0; i < next.resources.length; i++) {
            next.machines[i] = this.machines[i];
            next.resources[i] = this.resources[i] + this.machines[i];
        }
        if (machine != -1) next.build(machine);
        return next;
    }

    public void build(int machine) {
        this.machines[machine]++;
        switch (machine) {
            case 0 -> this.resources[0] -= this.prices[0];
            case 1 -> this.resources[0] -= this.prices[1];
            case 2 -> {
                this.resources[0] -= this.prices[2];
                this.resources[1] -= this.prices[3];
            }
            case 3 -> {
                this.resources[0] -= this.prices[4];
                this.resources[2] -= this.prices[5];
            }
        }
    }
}

public class App {
    public static Integer getSolutionPart1(List<String> lines) {
        List<State> initialStates = new ArrayList<>();

        lines.stream().map(l-> new Scanner(l).useDelimiter("[^0-9]+")).forEach(s-> {
            int bn = s.nextInt(), c1 = s.nextInt(), c2 = s.nextInt(), c3 = s.nextInt(), c4 = s.nextInt(), c5 = s.nextInt(), c6 = s.nextInt();
            initialStates.add(new State(c1, c2, c3, c4, c5, c6));

        });

        ArrayList<State> bestStates = new ArrayList<>();
        for (State s0: initialStates) {
            List<State> frontier = new ArrayList<>();
            frontier.add(s0);
            for (int i = 1; i <= 24; i++) {
                var si = new ArrayList<State>();
                for (State s : frontier) {
                    si.addAll(s.expand());
                }
                frontier = si;
            }
            var l = frontier.stream().max(Comparator.comparingInt(s -> s.resources[3]));
            bestStates.add(l.get());
        }
        int result = 0;
        for (int i = 0; i < bestStates.size(); i++) {
            result += (i+1) * bestStates.get(i).resources[3];
        }
        return result;
    }

    public static Integer getSolutionPart2(List<String> lines) {
        return 0;
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