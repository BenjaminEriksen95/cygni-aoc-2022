package aoc

import spock.lang.Specification

class AppTest extends Specification {
    def "Sorted"() {
    }

    def "IsOrdered"() {
        when:
        var output = App.isOrdered(s1,s2)

        then:
        expected == output

        where:
        s1              | s2             || expected
        "[9]"           | "[[8,7,6]]"    || false //
        "[7]"           | "[8]"          || true
        "[2,3,4]"       | "[4]"          || true
        "[1,1,3,1,1]"   | "[1,1,5,1,1]"  || true
        "[[1],[2,3,4]]" | "[[1],4]"      || true
        "[[4,4],4,4]"   | "[[4,4],4,4,4]"|| true
        "[7,7,7,7]"     | "[7,7,7]"      || false
        "[]"            | "[3]"          || false
        "[[4]]"         | "[[]]"         || true
        "[[[]]]"        | "[[]]"         || true
        "[1,[2,[3,[4,[5,6,7]]]],8,9]" | "[1,[2,[3,[4,[5,6,0]]]],8,9]" || false
    }

    def "RecursiveSplit"() {

        when:
        var output = App.recursiveSplit(input)

        then:
        expected == output

        where:
        input  || expected
        "[]"   || ""
        "[1]"  || "1"
        "[[1]]"  || "1"
        "[[[1]]]"  || "1"





    }
}
