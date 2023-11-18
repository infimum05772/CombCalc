package ru.kpfu.itis.arifulina.combcalc.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemFormulaBinding
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel

class FormulasHolder(
    private val onItemClick: (FormulaModel) -> Unit,
    private val viewBinding: ItemFormulaBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: FormulaModel? = null

    init {
        viewBinding.root.setOnClickListener {
            item?.let { formula -> onItemClick.invoke(formula) }
        }
    }

    fun bindItem(item: FormulaModel) {
        this.item = item
        with(viewBinding) {
            tvFormulaName.text = item.formulaName.name.replace('_', ' ')
            tvFormulaType.text = item.formulaType.name.replace('_', ' ').lowercase()
        }
    }
}
