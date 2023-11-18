package ru.kpfu.itis.arifulina.combcalc.ui.holder

import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemArgumentInputBinding
import ru.kpfu.itis.arifulina.combcalc.model.ArgumentModel

class ArgumentInputHolder(
    private val viewBinding: ItemArgumentInputBinding,
    private val onInputChanged: (Int, ArgumentModel, String) -> Unit,
    private val tag: String
) : RecyclerView.ViewHolder(viewBinding.root) {

    private var item: ArgumentModel? = null

    init {
        viewBinding.etArgumentInput.addTextChangedListener {
            val text = viewBinding.etArgumentInput.text.toString()
            if (text.isNotEmpty() && text.toLong() != item?.value) {
                saveArg()
            }
        }
    }

    fun bindItem(item: ArgumentModel) {
        this.item = item
        with(viewBinding) {
            tvArgumentName.text = "${item.name} = "
            item.value?.let {
                etArgumentInput.setText(item.value.toString())
            }
        }
    }

    fun setValue(value: Long) {
        viewBinding.etArgumentInput.setText(value.toString())
        viewBinding.etArgumentInput.setSelection(value.toString().length)
    }

    fun saveArg() : Pair<String, Long>? {
        var res: Pair<String, Long>? = null
        with(viewBinding) {
            item?.let { arg ->
                if (!etArgumentInput.text.isNullOrEmpty()) {
                    etArgumentInput.text.toString().toLong().also {
                        arg.value = it
                        res = arg.name to it
                    }
                    onInputChanged.invoke(adapterPosition, arg, tag)
                }
            }
        }
        return res
    }
}
