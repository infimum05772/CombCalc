package ru.kpfu.itis.arifulina.combcalc.ui.fragments

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListHorizontalDecorator
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListVerticalDecorator
import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.adapter.ArgumentInputAdapter
import ru.kpfu.itis.arifulina.combcalc.databinding.FragmentFormulaPageBinding
import ru.kpfu.itis.arifulina.combcalc.exceptions.FormulaFunctionException
import ru.kpfu.itis.arifulina.combcalc.model.ArgumentModel
import ru.kpfu.itis.arifulina.combcalc.model.ArgumentsRVModel
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.model.SubmitArgsButton
import ru.kpfu.itis.arifulina.combcalc.model.SubmitMultipleArgsButton
import ru.kpfu.itis.arifulina.combcalc.ui.holder.ArgumentInputHolder
import ru.kpfu.itis.arifulina.combcalc.utils.ParamsKey
import ru.kpfu.itis.arifulina.combcalc.utils.getValueInPx
import ru.noties.jlatexmath.JLatexMathDrawable
import java.math.BigDecimal
import java.text.DecimalFormat

class FormulaPageFragment : Fragment(R.layout.fragment_formula_page) {
    private var _binding: FragmentFormulaPageBinding? = null
    private val binding: FragmentFormulaPageBinding
        get() = _binding!!

    private var formula: FormulaModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormulaPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initViews() {
        formula = arguments?.getSerializable(ParamsKey.FORMULA_MODEL_KEY, FormulaModel::class.java)
        formula?.let { formula ->
            val formulaLatex = JLatexMathDrawable.builder(formula.formulaLatex)
                .textSize(70F)
                .color(resources.getColor(R.color.text))
                .build()
            with(binding) {
                mvFormula.setLatexDrawable(formulaLatex)
                tvFormulaName.text = formula.formulaName.name.replace('_', ' ')
                tvFormulaType.text = formula.formulaType.name.replace('_', ' ').lowercase()
                with(rvArguments) {
                    addItemDecoration(ListVerticalDecorator(4.getValueInPx(resources.displayMetrics)))
                    addItemDecoration(ListHorizontalDecorator(8.getValueInPx(resources.displayMetrics)))
                    adapter = ArgumentInputAdapter(
                        ::onButtonClick,
                        ::onMultipleArgumentsButtonClick,
                        ::onInputChanged,
                        generateArgumentsModelList(),
                        ParamsKey.ARGUMENTS_ADAPTER_TAG
                    )
                }
            }
        }
    }

    private fun onButtonClick() {
        with(binding) {
            val args = saveArgumentsValues(rvArguments)
            formula?.let {
                if (!it.multipleArgumentsAllowed) {
                    setResult(args)
                } else {
                    with(rvMultipleArgs) {
                        if (itemDecorationCount < 2) {
                            addItemDecoration(ListVerticalDecorator(4.getValueInPx(resources.displayMetrics)))
                            addItemDecoration(ListHorizontalDecorator(8.getValueInPx(resources.displayMetrics)))
                        }
                        adapter = ArgumentInputAdapter(
                            ::onButtonClick,
                            ::onMultipleArgumentsButtonClick,
                            ::onInputChanged,
                            generateMultipleArgumentsModelList(args["k"]),
                            ParamsKey.MULTIPLE_ARGUMENTS_ADAPTER_TAG
                        )
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun saveArgumentsValues(rv: RecyclerView): MutableMap<String, Long> {
        val map = mutableMapOf<String, Long>()
        (rv.adapter as? ArgumentInputAdapter)?.list?.let { list ->
            for (i in 0 until list.size) {
                if (list[i] is ArgumentModel) {
                    (list[i] as ArgumentModel).value?.let {
                        map.put(
                            (list[i] as ArgumentModel).name,
                            it
                        )
                    }
                    (rv.findViewHolderForLayoutPosition(i) as? ArgumentInputHolder)?.saveArg()
                        ?.let {
                            map.plusAssign(it)
                            println(it.first + " " + it.second)
                        }
                }
            }
        }
        return map
    }

    private fun onMultipleArgumentsButtonClick() {
        with(binding) {
            setResult(saveArgumentsValues(rvMultipleArgs).plus(saveArgumentsValues(rvArguments)))
        }
    }

    private fun setResult(args: Map<String, Long>) {
        with(binding) {
            formula?.let {
                lifecycleScope.launch {
                    pbCalculating.isVisible = true
                    tvError.visibility = View.GONE
                    tvError.text = ""
                    tvResult.visibility = View.GONE
                    tvResult.text = ""
                    runCatching {
                        withContext(Dispatchers.IO) {
                            async {
                                it.formulaFunc.invoke(args)
                            }
                        }
                    }.onSuccess { result ->
                        pbCalculating.isVisible = false
                        tvResult.visibility = View.VISIBLE
                        tvResult.text = result.await().toPlainString()
                    }.onFailure { e ->
                        pbCalculating.isVisible = false
                        tvError.visibility = View.VISIBLE
                        tvError.text = e.message
                    }
                }
            }
        }
    }

    private fun onInputChanged(position: Int, arg: ArgumentModel, tag: String) {
        with(binding) {
            when (tag) {
                ParamsKey.ARGUMENTS_ADAPTER_TAG -> {
                    rvArguments.post {
                        (rvArguments.adapter as? ArgumentInputAdapter)?.updateItem(
                            position,
                            arg
                        )
                    }
                }

                ParamsKey.MULTIPLE_ARGUMENTS_ADAPTER_TAG -> {
                    rvMultipleArgs.post {
                        (rvMultipleArgs.adapter as? ArgumentInputAdapter)?.updateItem(
                            position,
                            arg
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun generateArgumentsModelList(): MutableList<ArgumentsRVModel> {
        val list = mutableListOf<ArgumentsRVModel>()
        formula?.let {
            for (elem in it.formulaArguments) {
                list.add(ArgumentModel(elem, null))
            }
            list.add(SubmitArgsButton)
        }
        return list
    }

    private fun generateMultipleArgumentsModelList(k: Long?): MutableList<ArgumentsRVModel> {
        val list = mutableListOf<ArgumentsRVModel>()
        k?.let {
            for (i in 1..k) {
                list.add(ArgumentModel("n$i", null))
            }
            list.add(SubmitMultipleArgsButton)
        }
        return list
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val FORMULA_PAGE_FRAGMENT_TAG = "FORMULA_PAGE_FRAGMENT_TAG"
        fun newInstance(formulaModel: FormulaModel) = FormulaPageFragment().apply {
            arguments = bundleOf(ParamsKey.FORMULA_MODEL_KEY to formulaModel)
        }
    }
}
