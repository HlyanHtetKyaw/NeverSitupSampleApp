import android.util.Log

object BonusCodeTests {

    fun fibonacci(input: Int) {
        var temp1 = 0
        var temp2 = 1
        Log.d("fibonacci", "The number is defined as: $input")
        for (i in 1..input) {
            Log.d("fibonacci", temp1.toString())
            val sum = temp1 + temp2
            temp1 = temp2
            temp2 = sum
        }
    }

    fun specificNumbers() {
        val numbers = generateSequence(2 to generateSequence(3) { it + 2 }) {
            val currSeq = it.second.iterator()
            val nextNumber = currSeq.next()
            nextNumber to currSeq.asSequence().filter { it % nextNumber != 0 }
        }.map { it.first }
        Log.d("specificNumbers", numbers.take(10).toList().toString())
        println(numbers.take(10).toList())
    }

    fun arrayFilter(first: Array<Int>, second: Array<Int>) {
        var ans = arrayOf<Int>()
        for (i in first) {
            for (j in second) {
                if (i == j) {
                    ans += i
                }
            }
        }
        for (k in ans) {
            Log.d("arrayFilter", "arrayFilter: $k")
        }
    }

}