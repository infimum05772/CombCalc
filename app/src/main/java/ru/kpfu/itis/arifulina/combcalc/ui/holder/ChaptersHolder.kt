package ru.kpfu.itis.arifulina.combcalc.ui.holder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListHorizontalDecorator
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListVerticalDecorator
import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.adapter.FormulasAdapter
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemChapterBinding
import ru.kpfu.itis.arifulina.combcalc.model.ChapterModel
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.utils.getValueInPx

class ChaptersHolder(
    private val onItemClick: (FormulaModel) -> Unit,
    private val expandFormulas: (Int, ChapterModel) -> Unit,
    private val viewBinding: ItemChapterBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: ChapterModel? = null

    init {
        with(viewBinding) {
            root.setOnClickListener {
                item?.let { chapter ->
                    chapter.isExpanded = !chapter.isExpanded
                    ivExpandMore.animate().rotationBy(360F).setDuration(500).start();
                    expandFormulas(adapterPosition, chapter)
                }
            }
        }
    }

    fun bindItem(item: ChapterModel) {
        this.item = item
        with(viewBinding) {
            tvCardTitle.text = item.name
            ivImage.setImageResource(item.image)
            changeExpandedStatus(item.isExpanded)

            with(rvFormulas) {
                if (itemDecorationCount < 2){
                    addItemDecoration(ListVerticalDecorator(4.getValueInPx(resources.displayMetrics)))
                    addItemDecoration(ListHorizontalDecorator(8.getValueInPx(resources.displayMetrics)))
                }
                adapter = FormulasAdapter(onItemClick, item.formulas_list)
            }
        }
    }

    fun changeExpandedStatus(isExpanded: Boolean) {
        val expandIcon = if (isExpanded) R.drawable.expand_less else R.drawable.expand_more
        with(viewBinding) {
            ivExpandMore.setImageResource(expandIcon)
            rvFormulas.isVisible = isExpanded
        }
    }

}
