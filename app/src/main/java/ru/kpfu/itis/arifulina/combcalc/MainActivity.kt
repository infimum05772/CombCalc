package ru.kpfu.itis.arifulina.combcalc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.kpfu.itis.arifulina.combcalc.base.BaseActivity
import ru.kpfu.itis.arifulina.combcalc.databinding.ActivityMainBinding
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.ui.fragments.FormulaPageFragment
import ru.kpfu.itis.arifulina.combcalc.ui.fragments.StartPageFragment
import ru.kpfu.itis.arifulina.combcalc.utils.ActionType
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaName
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaType
import ru.kpfu.itis.arifulina.combcalc.utils.ParamsKey

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override val fragmentContainerId: Int = R.id.main_activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        Class.forName("org.scilab.forge.jlatexmath.TeXFormula")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        initThemeListener()
        initTheme()
        if (supportFragmentManager.findFragmentByTag(StartPageFragment.START_PAGE_FRAGMENT_TAG) == null) {
            goToScreen(
                ActionType.ADD,
                StartPageFragment(),
                StartPageFragment.START_PAGE_FRAGMENT_TAG,
                false
            )
        }
    }

    override fun goToScreen(
        actionType: ActionType,
        destination: Fragment,
        tag: String?,
        isAddToBackStack: Boolean,
    ) {
        supportFragmentManager.beginTransaction().apply {
            when (actionType) {
                ActionType.ADD -> {
                    this.add(fragmentContainerId, destination, tag)
                }

                ActionType.REPLACE -> {
                    this.replace(fragmentContainerId, destination, tag)
                }

                ActionType.REMOVE -> {
                    this.remove(destination)
                }
            }
            if (isAddToBackStack) {
                this.addToBackStack(null)
            }
        }.commit()
    }

    private fun saveTheme() {
        getSharedPreferences(ParamsKey.APP_CONFIG, Context.MODE_PRIVATE).edit().apply {
            putBoolean(ParamsKey.DARK_THEME, binding.switchTheme.isChecked)
            apply()
        }
    }

    private fun initTheme() {
        with(binding) {
            if (getSharedPreferences(ParamsKey.APP_CONFIG, Context.MODE_PRIVATE).getBoolean(
                    ParamsKey.DARK_THEME,
                    false
                )
            ) {
                switchTheme.isChecked = true
                switchTheme.setThumbResource(R.drawable.light_mode_icon)
            } else {
                switchTheme.isChecked = false
                switchTheme.setThumbResource(R.drawable.dark_mode_icon)
            }
        }
    }

    private fun initThemeListener() {
        with(binding) {
            switchTheme.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    switchTheme.setThumbResource(R.drawable.light_mode_icon)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    switchTheme.setThumbResource(R.drawable.dark_mode_icon)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                saveTheme()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null;
    }
}
