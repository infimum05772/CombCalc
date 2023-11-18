package ru.kpfu.itis.arifulina.combcalc.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemSubmitMultipleArgsBtnBinding

class SubmitMultipleArgsButtonHolder(
    viewBinding: ItemSubmitMultipleArgsBtnBinding,
    private val onMultipleArgsButtonClick: () -> Unit
) : RecyclerView.ViewHolder(viewBinding.root) {
    init {
        viewBinding.btnConfirmArgs.setOnClickListener {
            onMultipleArgsButtonClick.invoke()
        }
    }
}
