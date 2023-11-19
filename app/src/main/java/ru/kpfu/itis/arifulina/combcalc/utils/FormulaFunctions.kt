package ru.kpfu.itis.arifulina.combcalc.utils

import ru.kpfu.itis.arifulina.combcalc.exceptions.FormulaFunctionException
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

object FormulaFunctions {
    private val mc : MathContext = MathContext.DECIMAL128
    fun placementsNoRepetitions(params: Map<String, Long>): BigDecimal {
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

    fun placementsWithRepetitions(params: Map<String, Long>): BigDecimal {
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

    fun permutationsNoRepetitions(params: Map<String, Long>): BigDecimal {
        val n = params["n"] ?: throw FormulaFunctionException("not all the arguments are provided")
        if (n < 0) {
            throw FormulaFunctionException("n can't be less that 0")
        }
        return fact(n)
    }

    fun permutationsWithRepetitions(params: Map<String, Long>): BigDecimal {
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
        var res: BigDecimal = fact(n)
        for (i in 1..k) {
            params["n$i"]?.let { res.divide(fact(it), mc) }
        }
        return res
    }

    fun combinationsNoRepetitions(params: Map<String, Long>): BigDecimal {
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

    fun combinationsWithRepetitions(params: Map<String, Long>): BigDecimal {
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

    fun urnModelAllMarked(params: Map<String, Long>): BigDecimal {
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
        return binomialCoefficient(m, k).divide(binomialCoefficient(n, k), mc)
    }

    fun urnModelRMarked(params: Map<String, Long>): BigDecimal {
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
            return BigDecimal.ZERO
        }
        return binomialCoefficient(m, r) * binomialCoefficient(
            n - m,
            k - r
        ).divide(binomialCoefficient(n, k), mc)
    }

    private fun binomialCoefficient(n: Long, k: Long): BigDecimal {
        return factWithBound(n, max(k, n - k)).divide(fact(min(k, n - k)), mc)
    }

    private fun factWithBound(n: Long, k: Long): BigDecimal {
        if (n == 0.toLong() || n == 1.toLong() || k == n) {
            return BigDecimal.ONE
        }
        var res = BigDecimal.ONE
        for (i in k + 1..n) {
            res *= i.toBigDecimal()
        }
        return res
    }

    private fun fact(value: Long): BigDecimal {
        if (value == 0.toLong() || value == 1.toLong()) {
            return BigDecimal.ONE
        }
        var res = BigDecimal.ONE
        for (i in 2..value) {
            res *= i.toBigDecimal()
        }
        return res
    }

    private fun pow(n: Long, k: Long): BigDecimal {
        if (n == 0.toLong() || n == 1.toLong()) {
            return n.toBigDecimal()
        }
        var res = BigDecimal.ONE
        for (i in 1 .. k){
            res *= n.toBigDecimal()
        }
        return res
    }
}
