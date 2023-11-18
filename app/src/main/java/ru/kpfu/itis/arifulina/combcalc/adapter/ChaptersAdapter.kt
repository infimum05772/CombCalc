package ru.kpfu.itis.arifulina.combcalc.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemChapterBinding
import ru.kpfu.itis.arifulina.combcalc.model.ChapterModel
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.repo.Repository
import ru.kpfu.itis.arifulina.combcalc.ui.holder.ChaptersHolder
import ru.kpfu.itis.arifulina.combcalc.utils.ParamsKey

class ChaptersAdapter(
    private val onItemClick: (FormulaModel) -> Unit,
    private val expandFormulas: (Int, ChapterModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = Repository.list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ChaptersHolder(
            onItemClick,
            expandFormulas,
            ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ChaptersHolder)?.bindItem(list[position])
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Bundle)?.let {
                (holder as? ChaptersHolder)?.changeExpandedStatus(it.getBoolean(ParamsKey.IS_EXPANDED_KEY))
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun updateItem(position: Int, item: ChapterModel) {
        this.list[position] = item
        val diff = Bundle()
        diff.putBoolean(ParamsKey.IS_EXPANDED_KEY, item.isExpanded)
        notifyItemChanged(position, diff)
    }
}
