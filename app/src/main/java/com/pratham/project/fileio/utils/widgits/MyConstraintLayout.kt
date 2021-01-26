package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.pratham.project.fileio.utils.KeyboardListener
import com.pratham.project.fileio.R


class MyConstraintLayout(context: Context, attributeSet: AttributeSet?) : ConstraintLayout(context, attributeSet) {

    private var listenKeyboard: Boolean = false

    private var backColorPalette: ArrayList<Int> = ArrayList()
    private var showGradient: Boolean = false
    private var widthScaling: Boolean = false
    private var cornerRadius: Int? = null
    private var strokeColor: Int? = null
    private var strokeWidth: Int = 2
    var backgroundShape: GradientDrawable? = null
    var heightBottom: Int = 0
    private var keyboardShown: Boolean = false
    private var keyboardListener: KeyboardListener? = null

    init {
        val styled = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyConstraintLayout, 0, 0)

        showGradient = styled.getBoolean(
            R.styleable.MyConstraintLayout_showGradient,
            false
        )
        listenKeyboard = styled.getBoolean(
            R.styleable.MyRelativeLayout_listenKeyboard,
            false
        )
//        if (listenKeyboard) {
//            keyboardListener = context as KeyboardListener
//        }
        widthScaling = styled.getBoolean(
            R.styleable.MyConstraintLayout_widthScaling,
            false
        )

        if (styled.hasValue(R.styleable.MyConstraintLayout_cornerRadius)) {
            cornerRadius = styled.getDimensionPixelSize(
                R.styleable.MyConstraintLayout_cornerRadius,
                resources.getDimensionPixelSize(R.dimen.corner_radius)
            )
        }

        if (styled.hasValue(R.styleable.MyConstraintLayout_backColor)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyConstraintLayout_backColor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyConstraintLayout_backColorCenter)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyConstraintLayout_backColorCenter,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyConstraintLayout_backColorEnd)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyConstraintLayout_backColorEnd,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }

        if (styled.hasValue(R.styleable.MyConstraintLayout_strokeColor)) {
            strokeColor = styled.getColor(
                R.styleable.MyConstraintLayout_strokeColor,
                ContextCompat.getColor(context, R.color.transparent)
            )
        }

        strokeWidth = styled.getDimensionPixelSize(
            R.styleable.MyConstraintLayout_strokeWidth,
            resources.getDimensionPixelSize(R.dimen.stroke_width_regular)
        )

        if (showGradient) {
            getShapeDrawable().colors = backColorPalette.toIntArray()
        }
        if (background != null && background is ColorDrawable) {
            getShapeDrawable().setColor((background as ColorDrawable).color)
        }
        cornerRadius?.let {
            getShapeDrawable().cornerRadius = it.toFloat()
        }
        strokeColor?.let {
            getShapeDrawable().setStroke(strokeWidth, it)
        }
        backgroundShape?.let {
            background = it
        }
    }

    private fun getShapeDrawable(): GradientDrawable {
        if (backgroundShape == null) {
            backgroundShape = GradientDrawable()
        }
        return backgroundShape!!
    }

    override fun setBackground(background: Drawable?) {
        if (background != null && background is ColorDrawable) {
            getShapeDrawable().setColor(background.color)
        }
        super.setBackground(getShapeDrawable())
    }

    fun setKeyboardListener(keyboardListener: KeyboardListener){
        this.keyboardListener = keyboardListener
    }
    fun isKeyboardShown(): Boolean = keyboardShown

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (heightBottom == 0) {
            heightBottom = b
        } else if (listenKeyboard) {
            if (heightBottom > b) {
                heightBottom = b
                keyboardShown = true
                keyboardListener?.onKeyboardOpen()
            } else if (heightBottom < b) {
                heightBottom = b
                keyboardShown = false
                keyboardListener?.onKeyboardClosed()
            }
        }

        super.onLayout(changed, l, t, r, b)
    }

    fun removeKeyBoard() {
        if (context == null) {
            return
        }
        keyboardShown = false
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun setCustomBackgroundColor(color: Int){
        background = ColorDrawable(color)
    }

}