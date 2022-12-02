package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"strings"
)

func score(round string) int {
	choices := strings.Split(round, " ")
	switch choices[1] { //My pick
	case "X": // ROCK
		switch choices[0] { // Their pick
		case "A":
			return 1 + 3
		case "C":
			return 1 + 6
		default:
			return 1
		}
	case "Y": // PAPER
		switch choices[0] {
		case "A":
			return 2 + 6
		case "B":
			return 2 + 3
		default:
			return 2
		}
	case "Z": // SCISSOR
		switch choices[0] {
		case "B":
			return 3 + 6
		case "C":
			return 3 + 3
		default:
			return 3
		}
	}
	return 0
}

func evaluate(round string) int {
	choices := strings.Split(round, " ")
	switch choices[1] { //Result
	case "X": // Lose
		switch choices[0] { // Their pick
		case "A":
			return 0 + 3
		case "B":
			return 0 + 1
		case "C":
			return 0 + 2
		}
	case "Y": // Draw
		switch choices[0] {
		case "A":
			return 3 + 1
		case "B":
			return 3 + 2
		case "C":
			return 3 + 3
		}
	case "Z": // Win
		switch choices[0] {
		case "A": // Rock 1
			return 6 + 2
		case "B": // Paper 2
			return 6 + 3
		case "C": // Scissor 3
			return 6 + 1
		}
	}
	return 0
}

func getSolutionPart1(input []string) int {
	current := 0
	for _, value := range input {
		current += score(value)
	}
	return current
}

func getSolutionPart2(input []string) int {
	current := 0
	for _, value := range input {
		current += evaluate(value)
	}
	return current
}

func parseInput(input string) []string {
	return strings.Split(strings.TrimSpace(input), "\n")
}

func main() {
	inputBytes, err := ioutil.ReadFile("input.txt")
	if err != nil {
		panic("couldn't read input")
	}

	input := parseInput(string(inputBytes))
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
