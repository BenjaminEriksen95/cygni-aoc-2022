package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"sort"
	"strconv"
	"strings"
)

func getSolutionPart1(input []int) int {
	best := 0
	current := 0
	for _, value := range input {
		if value == -1 && current >= best {
			best = current
			current = 0
		} else {
			current += value
		}
	}
	return best
}

func getSolutionPart2(input []int) int {
	var scores []int
	var current = 0
	for _, value := range input {
		if value == -1 {
			scores = append(scores, current)
			current = 0
		} else {
			current += value
		}
	}
	sort.Sort(sort.Reverse(sort.IntSlice(scores)))
	return scores[0] + scores[1] + scores[2]
}

func parseInput(input string) ([]int, error) {
	var ints []int

	lines := strings.Split(strings.TrimSpace(input), "\n")

	for _, line := range lines {
		i, err := strconv.Atoi(line)
		if err != nil {
			ints = append(ints, -1)
			// return nil, err
		}

		ints = append(ints, i)
	}
	return ints, nil
}

func main() {
	inputBytes, err := ioutil.ReadFile("input.txt")
	if err != nil {
		panic("couldn't read input")
	}

	input, err := parseInput(string(inputBytes))
	if err != nil {
		panic("couldn't parse input")
	}

	fmt.Println("Go")
	part := os.Getenv("part")

	if part == "part2" {
		fmt.Println(getSolutionPart2(input))
	} else {
		fmt.Println(getSolutionPart1(input))
	}
}
