package ru.kpfu.itis.arifulina.combcalc.ui.fragments

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.drawable.Animatable
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnRepeat
import androidx.fragment.app.Fragment
import ru.kpfu.itis.arifulina.combcalc.R
import ru.kpfu.itis.arifulina.combcalc.base.BaseActivity
import ru.kpfu.itis.arifulina.combcalc.databinding.FragmentStartPageBinding
import ru.kpfu.itis.arifulina.combcalc.utils.ActionType

class StartPageFragment : Fragment(R.layout.fragment_start_page) {

    private var _binding: FragmentStartPageBinding? = null
    private val binding: FragmentStartPageBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            val anim = ivAnim.drawable
            (anim as? AnimatedVectorDrawable)?.registerAnimationCallback(object: Animatable2.AnimationCallback(){
                override fun onAnimationEnd(drawable: Drawable?) {
                    anim.start()
                }
            })
            (anim as? Animatable)?.start()
            btnStart.setOnClickListener {
                (requireActivity() as? BaseActivity)?.goToScreen(
                    ActionType.REPLACE,
                    MenuPageFragment(),
                    MenuPageFragment.MENU_PAGE_FRAGMENT_TAG,
                    true
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val START_PAGE_FRAGMENT_TAG = "START_PAGE_FRAGMENT_TAG"
    }
}
