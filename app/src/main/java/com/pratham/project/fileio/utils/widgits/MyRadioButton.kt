package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton
import com.pratham.project.fileio.R

class MyRadioButton(context: Context, attributeSet: AttributeSet): AppCompatRadioButton(context, attributeSet) {

    private var cornerRadius: Int = 0
    init {
        val styled = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyRadioButton, 0, 0)
        cornerRadius = styled.getDimensionPixelSize(
            R.styleable.MyRadioButton_cornerRadius,
            resources.getDimensionPixelSize(R.dimen.corner_radius)
        )

        val backColor = styled.getColor(R.styleable.MyRadioButton_backColor, ContextCompat.getColor(context, R.color.transparent))
        setBackColor(backColor)
    }

    fun setBackColor(color: Int){
        val shape = GradientDrawable()
        shape.cornerRadius = cornerRadius.toFloat()
        if (background == null) {
            shape.setColor(color)
            background = shape
        }
    }
}