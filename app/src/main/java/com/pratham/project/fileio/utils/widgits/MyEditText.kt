package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.pratham.project.fileio.R
import com.pratham.project.fileio.utils.getScaledPixelFromAbsoluteSize

open class MyEditText(context: Context, attributeSet: AttributeSet?) : AppCompatEditText(context, attributeSet) {

    private var backColorPalette: ArrayList<Int> = ArrayList()
    private var showGradient: Boolean = false
    private var widthScaling: Boolean = false
    private var cornerRadius: Int? = null
    private var strokeColor: Int? = null
    private var strokeWidth: Int = 2
    var backgroundShape: GradientDrawable? = null

    init {
        val styled = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyEditText, 0, 0)

        showGradient = styled.getBoolean(
            R.styleable.MyEditText_showGradient,
            false
        )
        widthScaling = styled.getBoolean(
            R.styleable.MyEditText_widthScaling,
            false
        )

        if (styled.hasValue(R.styleable.MyEditText_cornerRadius)) {
            cornerRadius = styled.getDimensionPixelSize(
                R.styleable.MyEditText_cornerRadius,
                resources.getDimensionPixelSize(R.dimen.corner_radius)
            )
        }

        if (styled.hasValue(R.styleable.MyEditText_backColor)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyEditText_backColor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyEditText_backColorCenter)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyEditText_backColorCenter,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyEditText_backColorEnd)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyEditText_backColorEnd,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }

        if (styled.hasValue(R.styleable.MyEditText_strokeColor)) {
            strokeColor = styled.getColor(
                R.styleable.MyEditText_strokeColor,
                ContextCompat.getColor(context, R.color.transparent)
            )
        }

        strokeWidth = styled.getDimensionPixelSize(
            R.styleable.MyEditText_strokeWidth,
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

    fun setTextIfAny(text: String?) {
        if (text != null) {
            setText(text)
        }
    }


    fun setTextColorDrawable(colorDrawable: ColorDrawable) {
        setTextColor(colorDrawable.color)
    }


    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        if (widthScaling) {
            if (params.width > 0) {
                params.width = resources.getScaledPixelFromAbsoluteSize(params.width)
            }
            if (params.height > 0) {
                params.height = resources.getScaledPixelFromAbsoluteSize(params.height)
            }
        }
        super.setLayoutParams(params)
    }


}