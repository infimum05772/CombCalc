package ru.kpfu.itis.arifulina.combcalc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemFormulaBinding
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.ui.holder.FormulasHolder

class FormulasAdapter(
    private val onItemClick: (FormulaModel) -> Unit,
    private val list: MutableList<FormulaModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = FormulasHolder(
        onItemClick,
        ItemFormulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FormulasHolder)?.bindItem(list[position])
    }
}
