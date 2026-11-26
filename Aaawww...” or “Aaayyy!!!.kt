
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import java.util.Random

private class FastScanner {
    private val br = BufferedReader(InputStreamReader(System.`in`))
    private var st = StringTokenizer("")

    fun next(): String {
        while (!st.hasMoreTokens()) {
            st = StringTokenizer(br.readLine())
        }
        return st.nextToken()
    }

    fun nextInt(): Int = next().toInt()
}

private class Node(
    val team: Int,
    var pending: Int,
    val priority: Int
) {
    var left: Node? = null
    var right: Node? = null
    var parent: Node? = null

    var size: Int = 1
    var pendingSum: Int = pending
}

private class ImplicitTreap {
    private val random = Random(1234567L)

    var root: Node? = null
        private set

    private fun size(node: Node?): Int {
        return node?.size ?: 0
    }

    private fun sum(node: Node?): Int {
        return node?.pendingSum ?: 0
    }

    private fun pull(node: Node) {
        node.size = 1 + size(node.left) + size(node.right)
        node.pendingSum =
            node.pending + sum(node.left) + sum(node.right)

        node.left?.parent = node
        node.right?.parent = node
    }

    private fun merge(a: Node?, b: Node?): Node? {
        if (a == null) {
            b?.parent = null
            return b
        }

        if (b == null) {
            a.parent = null
            return a
        }

        if (a.priority < b.priority) {
            a.right = merge(a.right, b)
            pull(a)
            a.parent = null
            return a
        } else {
            b.left = merge(a, b.left)
            pull(b)
            b.parent = null
            return b
        }
    }

    private fun split(
        node: Node?,
        k: Int
    ): Pair<Node?, Node?> {
        if (node == null) {
            return Pair(null, null)
        }

        val leftSize = size(node.left)

        if (k <= leftSize) {
            val (a, b) = split(node.left, k)

            node.left = b
            pull(node)
            node.parent = null

            a?.parent = null

            return Pair(a, node)
        } else {
            val (a, b) = split(
                node.right,
                k - leftSize - 1
            )

            node.right = a
            pull(node)
            node.parent = null

            b?.parent = null

            return Pair(node, b)
        }
    }

    fun addTeam(team: Int, pending: Int): Node {
        val node = Node(team, pending, random.nextInt())
        root = merge(root, node)
        return node
    }

    // Find the rightmost team with pending submissions.
    fun lowestRankedPending(): Node {
        var current = root!!

        while (true) {
            if (sum(current.right) > 0) {
                current = current.right!!
            } else if (current.pending > 0) {
                return current
            } else {
                current = current.left!!
            }
        }
    }

    // Update pending count and all subtree sums.
    fun removePending(node: Node) {
        node.pending--

        var current: Node? = node

        while (current != null) {
            pull(current)
            current = current.parent
        }
    }

    // Return the current zero-based rank of a team.
    fun rankOf(node: Node): Int {
        var rank = size(node.left)
        var current = node

        while (current.parent != null) {
            val p = current.parent!!

            if (current === p.right) {
                rank += size(p.left) + 1
            }

            current = p
        }

        return rank
    }

    // Move a team upward by 'passed' positions.
    fun moveUp(node: Node, passed: Int) {
        if (passed <= 0) return

        val oldRank = rankOf(node)
        val newRank = maxOf(0, oldRank - passed)

        // Remove the team from its current position.
        val (left, rest) = split(root, oldRank)
        val (middle, right) = split(rest, 1)

        root = merge(left, right)

        // Detach the node.
        node.left = null
        node.right = null
        node.parent = null
        node.size = 1
        node.pendingSum = node.pending

        // Insert at the new position.
        val (before, after) = split(root, newRank)

        root = merge(merge(before, node), after)
    }
}

fun main() {
    val fs = FastScanner()

    val n = fs.nextInt()
    val m = fs.nextInt()
    val favoriteRank = fs.nextInt()

    val submissions = Array(n) { fs.next() }

    val pendingPositions = Array(n) { IntArray(m) }
    val pendingCount = IntArray(n)

    var totalPending = 0

    for (team in 0 until n) {
        for (problem in 0 until m) {
            if (submissions[team][problem] == 'P') {
                pendingPositions[team][pendingCount[team]++] =
                    problem
                totalPending++
            }
        }
    }

    val treap = ImplicitTreap()
    val nodes = arrayOfNulls<Node>(n)

    // Initial scoreboard order is 1, 2, ..., n.
    for (team in 0 until n) {
        nodes[team] = treap.addTeam(
            team,
            pendingCount[team]
        )
    }

    val favoriteNode = nodes[favoriteRank - 1]!!

    val pointer = IntArray(n)

    repeat(totalPending) {
        // Select the lowest-ranked team with pending submissions.
        val node = treap.lowestRankedPending()
        val team = node.team

        // Consume its leftmost pending submission.
        val problem =
            pendingPositions[team][pointer[team]++]

        // Read the audience chant.
        fs.next() // Ooohhh...
        val resultChant = fs.next()

        // A rejected submission does not change the ranking.
        val accepted = resultChant.startsWith("Aaay")

        treap.removePending(node)

        if (accepted) {
            // Baseline chant has 3 y's.
            // Every extra y means one team was passed.
            val yCount = resultChant.count { it == 'y' }
            val passed = maxOf(0, yCount - 3)

            treap.moveUp(node, passed)
        }
    }

    println(treap.rankOf(favoriteNode) + 1)
}
