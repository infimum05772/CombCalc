package ru.kpfu.itis.arifulina.combcalc.model

import androidx.annotation.DrawableRes

data class ChapterModel(
    val name: String,
    @DrawableRes val image: Int,
    var isExpanded: Boolean,
    val formulas_list: MutableList<FormulaModel>
)
