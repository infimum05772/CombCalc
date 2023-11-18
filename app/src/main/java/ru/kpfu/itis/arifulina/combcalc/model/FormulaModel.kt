package ru.kpfu.itis.arifulina.combcalc.model

import ru.kpfu.itis.arifulina.combcalc.utils.FormulaName
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaType
import java.io.Serializable

data class FormulaModel(
    val formulaName: FormulaName,
    val formulaType: FormulaType,
    val formulaFunc: (Map<String, Long>) -> Double,
    val formulaArguments: MutableList<String>,
    val formulaLatex: String,
    val multipleArgumentsAllowed: Boolean
): Serializable
