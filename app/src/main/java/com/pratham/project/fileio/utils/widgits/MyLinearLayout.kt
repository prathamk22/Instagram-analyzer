package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.widget.LinearLayout
import com.pratham.project.fileio.R

class MyLinearLayout(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet) {

    private var backColorPalette: ArrayList<Int> = ArrayList()
    private var showGradient: Boolean = false
    private var cornerRadius: Int? = null
    var backgroundShape: GradientDrawable? = null

    init {
        val styled = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyLinearLayout, 0, 0)

        showGradient = styled.getBoolean(
            R.styleable.MyLinearLayout_showGradient,
            false
        )

        if (styled.hasValue(R.styleable.MyLinearLayout_cornerRadius)) {
            cornerRadius = styled.getDimensionPixelSize(
                R.styleable.MyLinearLayout_cornerRadius,
                resources.getDimensionPixelSize(R.dimen.corner_radius)
            )
        }

        if (styled.hasValue(R.styleable.MyLinearLayout_backColor)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyLinearLayout_backColor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyLinearLayout_backColorCenter)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyLinearLayout_backColorCenter,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyLinearLayout_backColorEnd)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyLinearLayout_backColorEnd,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }

        if (showGradient) {
            getShapeDrawable().colors = backColorPalette.toIntArray()
        }
        if (background != null && background is ColorDrawable) {
            getShapeDrawable().setColor((background as ColorDrawable).color)
        }
        cornerRadius?.let {
            getShapeDrawable().cornerRadius = it.toFloat()
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
        if (background is ColorDrawable) {
            getShapeDrawable().setColor(background.color)
            super.setBackground(getShapeDrawable())
        }
    }

    fun setCustomBackgroundColor(color: Int){
        background = ColorDrawable(color)
    }

    fun setRadius(radius: FloatArray){
        getShapeDrawable().cornerRadii = radius
    }

}
