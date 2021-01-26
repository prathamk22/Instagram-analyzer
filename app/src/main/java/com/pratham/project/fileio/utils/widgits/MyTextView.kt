package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatTextView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.pratham.project.fileio.utils.getScaledPixelFromAbsoluteSize
import com.pratham.project.fileio.R
import com.pratham.project.fileio.utils.SpannableModel

class MyTextView(context: Context, attributeSet: AttributeSet?) : AppCompatTextView(context, attributeSet) {

    private var backColorPalette: ArrayList<Int> = ArrayList()
    private var showGradient: Boolean = false
    private var widthScaling: Boolean = false
    private var cornerRadius: Int? = null
    private var strokeColor: Int? = null
    private var strokeWidth: Int = 2
    private var customWidth: Int? = null
    private var customHeight: Int? = null
    var backgroundShape: GradientDrawable? = null

    init {
        val styled = context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyTextView, 0, 0)

        showGradient = styled.getBoolean(
                R.styleable.MyTextView_showGradient,
                false
        )
        widthScaling = styled.getBoolean(
                R.styleable.MyTextView_widthScaling,
                false
        )

        if (styled.hasValue(R.styleable.MyTextView_cornerRadius)) {
            cornerRadius = styled.getDimensionPixelSize(
                    R.styleable.MyTextView_cornerRadius,
                    resources.getDimensionPixelSize(R.dimen.corner_radius)
            )
        }
        if (styled.hasValue(R.styleable.MyTextView_custom_width)) {
            customWidth = styled.getDimensionPixelSize(
                    R.styleable.MyTextView_custom_width,
                    resources.getDimensionPixelSize(R.dimen.dp0)
            )
        }
        if (styled.hasValue(R.styleable.MyTextView_custom_height)) {
            customHeight = styled.getDimensionPixelSize(
                    R.styleable.MyTextView_custom_height,
                    resources.getDimensionPixelSize(R.dimen.dp0)
            )
        }

        if (styled.hasValue(R.styleable.MyTextView_backColor)) {
            backColorPalette.add(
                    styled.getColor(
                            R.styleable.MyTextView_backColor,
                            ContextCompat.getColor(context, R.color.transparent)
                    )
            )
        }
        if (styled.hasValue(R.styleable.MyTextView_backColorCenter)) {
            backColorPalette.add(
                    styled.getColor(
                            R.styleable.MyTextView_backColorCenter,
                            ContextCompat.getColor(context, R.color.transparent)
                    )
            )
        }
        if (styled.hasValue(R.styleable.MyTextView_backColorEnd)) {
            backColorPalette.add(
                    styled.getColor(
                            R.styleable.MyTextView_backColorEnd,
                            ContextCompat.getColor(context, R.color.transparent)
                    )
            )
        }

        if (styled.hasValue(R.styleable.MyTextView_strokeColor)) {
            strokeColor = styled.getColor(
                    R.styleable.MyTextView_strokeColor,
                    ContextCompat.getColor(context, R.color.transparent)
            )
        }

        strokeWidth = styled.getDimensionPixelSize(
                R.styleable.MyTextView_strokeWidth,
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

    fun setCornerRadius(corner: Float){
        getShapeDrawable().cornerRadius = corner
    }

    fun setTextIfAny(text: String?) {
        if (text != null) {
            setText(text)
        }
    }

    fun setTexts(list: List<SpannableModel>?) {
        list?.let {
            text = ""
            list.forEach { spannableModel ->
                val spannableStringBuilder = SpannableStringBuilder(spannableModel.text)
                spannableModel.textColor?.let {
                    spannableStringBuilder.setSpan(
                            ForegroundColorSpan(ContextCompat.getColor(context, it)),
                            0, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                spannableModel.typeFace?.let {
                    spannableStringBuilder.setSpan(
                            TypefaceSpan(it),
                            0, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                spannableModel.textSize?.let {
                    spannableStringBuilder.setSpan(
                            AbsoluteSizeSpan(context.resources.getDimensionPixelSize(it)),
                            0, spannableStringBuilder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                append(spannableStringBuilder)

            }
        }
    }

    fun setTextColorDrawable(colorDrawable: ColorDrawable) {
        setTextColor(colorDrawable.color)
    }

    fun setTextSpan(spannable: Spannable?) {
        spannable?.let {
            text = spannable
        }
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams) {
        if (widthScaling) {
            if (params.width > 0) {
                params.width = resources.getScaledPixelFromAbsoluteSize(params.width)
            }
            if (params.height > 0) {
                params.height = resources.getScaledPixelFromAbsoluteSize(params.height)
            }
            customHeight?.let {
                params.height = resources.getScaledPixelFromAbsoluteSize(it)
            }
            customWidth?.let {
                params.width = resources.getScaledPixelFromAbsoluteSize(it)
            }
        } else {
            customHeight?.let {
                params.height = it
            }
            customWidth?.let {
                params.width = it
            }

        }
        super.setLayoutParams(params)
    }

    fun setCustomTextColor(color: Int?) {
        color?.let {
            try {
                setTextColor(it)
            } catch (e: Exception) {

            }
        }
    }
    fun setCustomStrokeColor(color: String?) {
        color?.let {
            try {
                strokeColor = Color.parseColor(it)
                getShapeDrawable().setStroke(strokeWidth, strokeColor!!)
            } catch (e: Exception) {
            }
        }
    }

    fun setCustomBackgroundColor(color: String?) {
        color?.let {
            try {
                background = ColorDrawable(Color.parseColor(it))
            } catch (e: Exception) {
            }
        }
    }
    fun setCustomBackgroundColor(color: Int?) {
        color?.let {
            try {
                background = ColorDrawable(it)
            } catch (e: Exception) {
            }
        }
    }

    fun setRadius(radius: FloatArray){
        getShapeDrawable().cornerRadii = radius
    }

    fun setShow(icon: String?) {
        visibility = if (icon?.isNotEmpty() == true) View.VISIBLE else View.GONE
    }

}