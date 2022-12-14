package aoc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static Integer getSolutionPart1(List<JsonNode> jsonList) {
        int count = 0;
        for (int i = 0, j = 1; i + 1 <= jsonList.size(); i += 2, j++) {
            if (isOrdered(jsonList.get(i), jsonList.get(i+1)) > 0) count += j;
        }
        return count;
    }

    public static Integer getSolutionPart2(List<JsonNode> jsonList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var n1 = mapper.readValue("[[2]]", JsonNode.class); jsonList.add(n1);
        var n2 = mapper.readValue("[[6]]", JsonNode.class); jsonList.add(n2);
        Comparator<JsonNode> c = (o1, o2) -> isOrdered(o2, o1);
        List<JsonNode> sorted = jsonList.stream().sorted(c).toList();
        return (sorted.indexOf(n1)+1) * (sorted.indexOf(n2)+1);
    }

    public static int isOrdered(JsonNode left, JsonNode right) {
        if (left == null) return 1;
        if (right == null) return -1;
        if (left.isValueNode() && right.isValueNode()) return compare(left.asInt(), right.asInt());
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;
        if (left.isValueNode() && !right.isValueNode()) {
            var d = isOrdered(jsonNodeFactory.arrayNode().add(left.asInt()), right);
            if (d != 0) return d;
        }
        if (!left.isValueNode() && right.isValueNode()) {
            var d = isOrdered(left, jsonNodeFactory.arrayNode().add(right.asInt()));
            if (d != 0) return d;
        }
        if (left.isEmpty()) return right.isEmpty() ? 0 : 1;
        if (right.isEmpty()) return -1;
        for (int i = 0; i < Math.max(left.size(), right.size()); i++) {
            var d = isOrdered(left.get(i), right.get(i));
            if (d != 0) return d;
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("java");
        var jsonList = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2"))
            System.out.println(getSolutionPart2(jsonList));
        else
            System.out.println(getSolutionPart1(jsonList));
    }

    private static List<JsonNode> parseInput(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return Files.lines(Paths.get(filename)).toList().stream()
            .filter(str -> !str.trim().isEmpty()).map(l -> {
            try {
                return mapper.readValue(l, JsonNode.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    private static int compare(int left, int right) {
        return right - left;
    }


}