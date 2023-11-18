package ru.kpfu.itis.arifulina.combcalc

import android.app.Application
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import ru.kpfu.itis.arifulina.combcalc.model.FormulaModel
import ru.kpfu.itis.arifulina.combcalc.ui.fragments.FormulaPageFragment
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaFunctions
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaName
import ru.kpfu.itis.arifulina.combcalc.utils.FormulaType

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }
}
