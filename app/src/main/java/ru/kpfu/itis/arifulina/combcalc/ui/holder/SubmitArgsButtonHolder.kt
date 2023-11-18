package ru.kpfu.itis.arifulina.combcalc.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemSubmitArgsBtnBinding

class SubmitArgsButtonHolder(
    viewBinding: ItemSubmitArgsBtnBinding,
    private val onButtonClick: () -> Unit,
)  : RecyclerView.ViewHolder (viewBinding.root) {

    init {
        viewBinding.btnConfirmArgs.setOnClickListener {
            onButtonClick.invoke()
        }
    }
}
