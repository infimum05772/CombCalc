package ru.kpfu.itis.arifulina.combcalc.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListHorizontalDecorator
import ru.kpfu.itis.arifulina.combcalc.adapter.decorations.ListVerticalDecorator
import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.adapter.ChaptersAdapter
import ru.kpfu.itis.arifulina.combcalc.base.BaseActivity
import ru.kpfu.itis.arifulina.combcalc.databinding.FragmentMenuPageBinding
import ru.kpfu.itis.arifulina.combcalc.model.ChapterModel
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.utils.ActionType
import ru.kpfu.itis.arifulina.combcalc.utils.getValueInPx

class MenuPageFragment : Fragment(R.layout.fragment_menu_page) {

    private var _binding: FragmentMenuPageBinding? = null
    private val binding: FragmentMenuPageBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            with(rvChapters) {
                addItemDecoration(ListVerticalDecorator(4.getValueInPx(resources.displayMetrics)))
                addItemDecoration(ListHorizontalDecorator(8.getValueInPx(resources.displayMetrics)))
                adapter = ChaptersAdapter(::onFormulaClick, ::expandFormulas)
            }
        }
    }

    private fun expandFormulas(position: Int, chapterItem: ChapterModel) {
        (binding.rvChapters.adapter as? ChaptersAdapter)?.updateItem(position, chapterItem)
    }

    private fun onFormulaClick(item: FormulaModel) {
        (requireActivity() as? BaseActivity)?.goToScreen(
            ActionType.REPLACE,
            FormulaPageFragment.newInstance(item),
            FormulaPageFragment.FORMULA_PAGE_FRAGMENT_TAG,
            true
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val MENU_PAGE_FRAGMENT_TAG = "MENU_PAGE_FRAGMENT_TAG"
    }
}
