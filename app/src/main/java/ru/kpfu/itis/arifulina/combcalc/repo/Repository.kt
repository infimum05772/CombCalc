package ru.kpfu.itis.arifulina.combcalc.repo

import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.model.ChapterModel
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.combinationsNoRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.combinationsWithRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.permutationsNoRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.permutationsWithRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.placementsNoRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.placementsWithRepetitions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.urnModelAllMarked
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions.urnModelRMarked
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaName
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaType

object Repository {
    val list: MutableList<ChapterModel> = mutableListOf(
        ChapterModel(
            "Combinatorics", R.drawable.combinatorics, false, mutableListOf(
                FormulaModel(
                    FormulaName.PLACEMENTS,
                    FormulaType.NO_REPETITIONS,
                    ::placementsNoRepetitions,
                    mutableListOf("n", "k"),
                    "\$\$ A^k_n = \\frac{n!}{(n-k)!} \$\$",
                    false
                ),
                FormulaModel(
                    FormulaName.PLACEMENTS,
                    FormulaType.WITH_REPETITIONS,
                    ::placementsWithRepetitions,
                    mutableListOf("n", "k"),
                    "\$\$ \\bar A^k_n = n^k \$\$",
                    false
                ),
                FormulaModel(
                    FormulaName.PERMUTATIONS,
                    FormulaType.NO_REPETITIONS,
                    ::permutationsNoRepetitions,
                    mutableListOf("n"),
                    "\$\$ P_n = n! \$\$",
                    false
                ),
                FormulaModel(
                    FormulaName.PERMUTATIONS,
                    FormulaType.WITH_REPETITIONS,
                    ::permutationsWithRepetitions,
                    mutableListOf("n", "k"),
                    "\$\$ P_n(n_1,n_2,...,n_k) = \\frac{n!}{n_1!n_2!...n_k!}, \\\\ (\\text{if } n_1 + n_2 + ... + n_k = n) \$\$",
                    true
                ),
                FormulaModel(
                    FormulaName.COMBINATIONS,
                    FormulaType.NO_REPETITIONS,
                    ::combinationsNoRepetitions,
                    mutableListOf("n", "k"),
                    "\$\$ C^k_n = \\frac{n!}{k!(n-k)!} \$\$",
                    false
                ),
                FormulaModel(
                    FormulaName.COMBINATIONS,
                    FormulaType.WITH_REPETITIONS,
                    ::combinationsWithRepetitions,
                    mutableListOf("n", "k"),
                    "\$\$ \\bar C^k_n = C^k_{n+k-1} = \\frac{(n+k-1)!}{k!(n-1)!} \$\$",
                    false
                )
            )
        ),
        ChapterModel(
            "Urn model", R.drawable.urn_model, false, mutableListOf(
                FormulaModel(
                    FormulaName.URN_MODEL,
                    FormulaType.ALL_MARKED,
                    ::urnModelAllMarked,
                    mutableListOf("n", "k", "m"),
                    "\$\$ P(A) = \\frac{C^k_m}{C^k_n} \$\$",
                    false
                ),
                FormulaModel(
                    FormulaName.URN_MODEL,
                    FormulaType.R_MARKED,
                    ::urnModelRMarked,
                    mutableListOf("n", "k", "m", "r"),
                    "\$\$ P(A) = \\frac{C^r_mC^{k-r}_{n-m}}{C^k_m} \$\$",
                    false
                )
            )
        )
    )
}
