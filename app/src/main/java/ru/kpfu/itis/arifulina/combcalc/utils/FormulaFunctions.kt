package ru.kpfu.itis.arifulina.combcalc.utils

import ru.kpfu.itis.arifulina.combcalc.exceptions.FormulaFunctionException
import kotlin.math.max
import kotlin.math.min

object FormulaFunctions {
    fun placementsNoRepetitions(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        if (n == null || k == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (n < k) {
            throw FormulaFunctionException("n can't be less that k")
        }
        return factWithBound(n, n - k)
    }

    fun placementsWithRepetitions(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        if (n == null || k == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        return pow(n, k)
    }

    fun permutationsNoRepetitions(params: Map<String, Long>): Double {
        val n = params["n"] ?: throw NullPointerException("not all the arguments are provided")
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        return fact(n)
    }

    fun permutationsWithRepetitions(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        if (n == null || k == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (n < k) {
            throw FormulaFunctionException("n can't be less that k")
        }
        var sum: Long = 0
        for (i in 1..k) {
            sum += params["n$i"] ?: throw FormulaFunctionException("not all the arguments are provided")
        }
        if (sum != n) {
            throw FormulaFunctionException("n1 + n2 + ... + nk != n")
        }
        var res: Double = fact(n)
        for (i in 1..k) {
            params["n$i"]?.let { res /= fact(it) }
        }
        return res
    }

    fun combinationsNoRepetitions(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        if (n == null || k == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (n < k) {
            throw FormulaFunctionException("n can't be less that k")
        }
        return binomialCoefficient(n, k)
    }

    fun combinationsWithRepetitions(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        if (n == null || k == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 1) {
            throw FormulaFunctionException("n can't be less that 1")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (n + k < 1) {
            throw FormulaFunctionException("n + k can't be less that 1")
        }
        return binomialCoefficient(n + k - 1, k)
    }

    fun urnModelAllMarked(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        val m = params["m"]
        if (n == null || k == null || m == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (m < 0) {
            throw FormulaFunctionException("m can't be less that 0")
        }
        if (n < m) {
            throw FormulaFunctionException("n can't be less that m")
        }
        if (m < k) {
            throw FormulaFunctionException("m can't be less that k")
        }
        if (n < k) {
            throw FormulaFunctionException("n can't be less that k")
        }
        return binomialCoefficient(m, k) / binomialCoefficient(n, k)
    }

    fun urnModelRMarked(params: Map<String, Long>): Double {
        val n = params["n"]
        val k = params["k"]
        val m = params["m"]
        val r = params["r"]
        if (n == null || k == null || m == null || r == null) {
            throw FormulaFunctionException("not all the arguments are provided")
        }
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        if (k < 0) {
            throw FormulaFunctionException("k can't be less that 0")
        }
        if (m < 0) {
            throw FormulaFunctionException("m can't be less that 0")
        }
        if (r < 0) {
            throw FormulaFunctionException("r can't be less that 0")
        }
        if (r > m) {
            throw FormulaFunctionException("m can't be less that r")
        }
        if (r > k) {
            throw FormulaFunctionException("k can't be less that r")
        }
        if (n < m) {
            throw FormulaFunctionException("n can't be less that m")
        }
        if (m < k) {
            throw FormulaFunctionException("m can't be less that k")
        }
        if (n < k) {
            throw FormulaFunctionException("n can't be less that k")
        }
        if (n - m < k - r) {
            return 0.0
        }
        return binomialCoefficient(m, r) * binomialCoefficient(
            n - m,
            k - r
        ) / binomialCoefficient(n, k)
    }

    private fun binomialCoefficient(n: Long, k: Long): Double {
        return factWithBound(n, max(k, n - k)) / fact(min(k, n - k))
    }

    private fun factWithBound(n: Long, k: Long): Double {
        if (n == 0.toLong() || n == 1.toLong() || k == n) {
            return 1.0
        }
        var res: Double = 1.0
        for (i in k + 1..n) {
            res *= i
        }
        return res
    }

    private fun fact(value: Long): Double {
        if (value == 0.toLong() || value == 1.toLong()) {
            return 1.0
        }
        var res: Double = 1.0
        for (i in 2..value) {
            res *= i
        }
        return res
    }

    private fun pow(n: Long, k: Long): Double {
        if (n == 0.toLong() || n == 1.toLong()) {
            return n.toDouble()
        }
        var res: Double = 1.0
        var value = n
        var pow = k
        while (pow > 0) {
            if (pow % 2 == 1.toLong()) {
                res *= value
            }
            value *= value
            pow /= 2
        }
        return res
    }
}
