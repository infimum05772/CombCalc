package ru.kpfu.itis.arifulina.combcalc.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemArgumentInputBinding
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemSubmitArgsBtnBinding
import ru.kpfu.itis.arifulina.combcalc.databinding.ItemSubmitMultipleArgsBtnBinding
import ru.kpfu.itis.arifulina.combcalc.model.ArgumentModel
import ru.kpfu.itis.arifulina.combcalc.model.ArgumentsRVModel
import ru.kpfu.itis.arifulina.combcalc.model.SubmitArgsButton
import ru.kpfu.itis.arifulina.combcalc.model.SubmitMultipleArgsButton
import ru.kpfu.itis.arifulina.combcalc.ui.holder.ArgumentInputHolder
import ru.kpfu.itis.arifulina.combcalc.ui.holder.SubmitArgsButtonHolder
import ru.kpfu.itis.arifulina.combcalc.ui.holder.SubmitMultipleArgsButtonHolder
import ru.kpfu.itis.arifulina.combcalc.utils.ParamsKey

class ArgumentInputAdapter(
    private val onButtonClicked: () -> Unit,
    private val onMultipleArgsButtonClick: () -> Unit,
    private val onInputChanged: (Int, ArgumentModel, String) -> Unit,
    val list: MutableList<ArgumentsRVModel>,
    private val tag: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        R.layout.item_submit_args_btn -> SubmitArgsButtonHolder(
            ItemSubmitArgsBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onButtonClicked
        )

        R.layout.item_submit_multiple_args_btn -> SubmitMultipleArgsButtonHolder(
            ItemSubmitMultipleArgsBtnBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onMultipleArgsButtonClick
        )

        R.layout.item_argument_input -> ArgumentInputHolder (
            ItemArgumentInputBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onInputChanged,
            tag
        )

        else -> throw RuntimeException("Unknown item in ArgumentInputAdapter")
    }

    override fun getItemCount(): Int  = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SubmitArgsButtonHolder -> {}
            is SubmitMultipleArgsButtonHolder -> {}
            is ArgumentInputHolder -> (list[position] as? ArgumentModel)?.let { holder.bindItem(it) }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Bundle)?.let {
                (holder as? ArgumentInputHolder)?.setValue(it.getLong(ParamsKey.ARG_VALUE_KEY))
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is SubmitArgsButton -> R.layout.item_submit_args_btn
            is SubmitMultipleArgsButton -> R.layout.item_submit_multiple_args_btn
            is ArgumentModel -> R.layout.item_argument_input
            else -> 0
        }
    }

    fun updateItem(position: Int, item: ArgumentModel) {
        this.list[position] = item
        val diff = Bundle()
        item.value?.let { diff.putLong(ParamsKey.ARG_VALUE_KEY, it) }
        notifyItemChanged(position, diff)
    }
}
