package y2017

import com.pascalwelsch.aoc.resourceFileText
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.Test
import y2017.Day7_RecursiveCircus.Node
import y2017.Day7_RecursiveCircus.correctErrorForBalancedTree
import y2017.Day7_RecursiveCircus.findRootNode
import y2017.Day7_RecursiveCircus.parseInfo
import y2017.Day7_RecursiveCircus.part1
import y2017.Day7_RecursiveCircus.part2

class Day7_RecursiveCircusTest {

    @Test
    fun `parse info without childs`() {
        val parsed = parseInfo("ebii (61)")
        val expected = Node("ebii", 61, listOf())
        assertThat(parsed).isEqualTo(expected)
    }

    @Test
    fun `parse info with childs`() {
        assertThat(parseInfo("tknk (41) -> ugml, padx, fwft"))
                .isEqualTo(Node("tknk", 41, listOf("ugml", "padx", "fwft")))
    }

    @Test
    fun `find root`() {
        val root = findRootNode(listOf(
                Node("fwft", 22, listOf("ktlj", "cntj", "xhth")),
                Node("padx", 45, listOf("pbga", "havc", "qoyq")),
                Node("tknk", 41, listOf("ugml", "padx", "fwft")),
                Node("ugml", 68, listOf("gyxo", "ebii", "jptl"))
        ))
        assertThat(root.name).isEqualTo("tknk")
    }

    @Test
    fun `solve sample`() {
        val input = resourceFileText("2017/7-sample.txt").lines().map { parseInfo(it) }
        assertThat(findRootNode(input).name).isEqualTo("tknk")
    }

    @Test
    fun `solve Part 1`() {
        assertThat(part1.test()).isEqualTo("veboyvy")
    }

    @Test
    fun `node without childs requires no changes`() {
        val root = Node("qwer", 22)
        val result = correctErrorForBalancedTree(listOf(root), root)
        assertThat(result).isNull()
    }

    @Test
    fun `child requires a weight addition`() {
        val root = Node("fwft", 22, listOf("aaa", "bbb", "ccc"))

        val tree = listOf(
                root,
                Node("aaa", 45),
                Node("bbb", 45),
                Node("ccc", 52)
        )

        val result = correctErrorForBalancedTree(tree, root)
        assertThat(result).isEqualTo(45)
    }

    @Test
    fun `child requires a weight reduction`() {
        val root = Node("fwft", 22, listOf("aaa", "bbb", "ccc"))

        val tree = listOf(
                root,
                Node("aaa", 45),
                Node("bbb", 45),
                Node("ccc", 12)
        )

        val result = correctErrorForBalancedTree(tree, root)
        assertThat(result).isEqualTo(45)
    }

    @Test
    fun `weight change sample`() {
        val input = resourceFileText("2017/7-sample.txt").lines().map { parseInfo(it) }
        assertThat(correctErrorForBalancedTree(input)).isEqualTo(60)
    }

    @Ignore("executes too long")
    @Test
    fun `solve part 2`() {
        assertThat(part2.test()).isEqualTo("749")
    }
}