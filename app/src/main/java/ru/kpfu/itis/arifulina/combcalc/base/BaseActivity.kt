package ru.kpfu.itis.arifulina.combcalc.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.kpfu.itis.arifulina.combcalc.utils.ActionType

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val fragmentContainerId: Int

    abstract fun goToScreen(
        actionType: ActionType,
        destination: Fragment,
        tag: String? = null,
        isAddToBackStack: Boolean = true,
    )
}
