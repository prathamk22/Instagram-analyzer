package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pratham.project.fileio.R

class MyImageView(context: Context, attributeSet: AttributeSet?) :
    AppCompatImageView(context, attributeSet) {

    fun setImageUri(url: String?) {
        if (cornerRadius != null) {
            Glide
                .with(this)
                .load(url)
                .override(Target.SIZE_ORIGINAL)
                .transform(FitCenter(), RoundedCorners(cornerRadius!!))
                .into(this)

        } else {
            Glide.with(this).load(url).into(this)
        }
    }

    fun loadIcons(url: String?){
        Glide.with(this).load(url).into(this)
    }

    fun setImageUriCenterInside(url: String?) {
        Glide
            .with(this)
            .load(url)
            .dontTransform()
            .override(this.context.resources.getDimensionPixelSize(R.dimen.dp36), this.context.resources.getDimensionPixelSize(R.dimen.dp36))
//            .placeholder(R.drawable.ic_placeholder)
//            .error(R.drawable.ic_placeholder)
            .into(this)
    }

    fun setCircularImage(url: String?) {
        if (cornerRadius ?: 0 > 0) {
            Glide
                .with(this)
                .load(url)
                .transform(CenterCrop(),RoundedCorners(cornerRadius!!))
                .into(this)

        } else {
            Glide
                .with(this)
                .load(url)
                .transform(CenterCrop(),RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.default_margin)))
                .into(this)

        }
    }
    fun setCircularImage(url: String?, placeholder: Int) {
        if (cornerRadius ?: 0 > 0) {
            Glide
                .with(this)
                .load(url)
                .transform(CenterCrop(),RoundedCorners(cornerRadius!!))
                .placeholder(placeholder)
                .error(placeholder)
                .into(this)

        } else {
            Glide
                .with(this)
                .load(url)
                .transform(CenterCrop(),RoundedCorners(context.resources.getDimensionPixelSize(R.dimen.default_margin)))
                .placeholder(placeholder)
                .error(placeholder)
                .into(this)

        }
    }

    private fun setImageUriWithResourceStrategy(url: String?) {
        Glide.with(this).load(url).into(this)
    }

    private var backColorPalette: ArrayList<Int> = ArrayList()
    private var showGradient: Boolean = false
    private var widthScaling: Boolean = false
    private var cornerRadius: Int? = null
    private var strokeColor: Int? = null
    private var strokeWidth: Int = 2
    var backgroundShape: GradientDrawable? = null

    init {
        val styled =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.MyImageView, 0, 0)

        showGradient = styled.getBoolean(
            R.styleable.MyImageView_showGradient,
            false
        )
        try {
            if (styled.hasValue(R.styleable.MyImageView_imageResId)) {
                val resId =
                    styled.getResourceId(R.styleable.MyImageView_imageResId, R.color.transparent)
                setImageResource(resId)
            }
        } catch (e: Exception) {
        }

        widthScaling = styled.getBoolean(
            R.styleable.MyImageView_widthScaling,
            false
        )

        if (styled.hasValue(R.styleable.MyImageView_cornerRadius)) {
            cornerRadius = styled.getDimensionPixelSize(
                R.styleable.MyImageView_cornerRadius,
                resources.getDimensionPixelSize(R.dimen.corner_radius)
            )
        }

        if (styled.hasValue(R.styleable.MyImageView_backColor)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyImageView_backColor,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyImageView_backColorCenter)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyImageView_backColorCenter,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }
        if (styled.hasValue(R.styleable.MyImageView_backColorEnd)) {
            backColorPalette.add(
                styled.getColor(
                    R.styleable.MyImageView_backColorEnd,
                    ContextCompat.getColor(context, R.color.transparent)
                )
            )
        }

        if (styled.hasValue(R.styleable.MyImageView_strokeColor)) {
            strokeColor = styled.getColor(
                R.styleable.MyImageView_strokeColor,
                ContextCompat.getColor(context, R.color.transparent)
            )
        }

        strokeWidth = styled.getDimensionPixelSize(
            R.styleable.MyImageView_strokeWidth,
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

    fun getShapeDrawable(): GradientDrawable {
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


    fun setImage(drawable: Drawable) {
        setImageDrawable(drawable)
    }

    override fun setImageResource(resId: Int) {
        try {
            super.setImageResource(resId)
        } catch (e: Exception) {
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        try {
            super.setImageDrawable(drawable)
        } catch (e: Exception) {
        }
    }

    fun setCustomBackgroundColor(color: String?) {
        color?.let {
            try {
                background = ColorDrawable(Color.parseColor(color))
            } catch (e: Exception) {
            }
        }
    }

    fun setImageUrl(url: String?) {
        setImageUri(url)
    }


    fun setImageUrl(url: String?, placeholder: Int) {
        val isDrawableFound = try {
            val d = ContextCompat.getDrawable(context, placeholder)
            d != null
        } catch (e: Exception) {
            false
        }
        if (isDrawableFound) {
            Glide.with(context).load(url)
                .apply(
                    RequestOptions()/*.diskCacheStrategy(DiskCacheStrategy.RESOURCE)*/
                        .placeholder(placeholder).error(placeholder)
                ).into(this)

        } else {
            setImageUriWithResourceStrategy(url)
        }
    }

    fun setImageUrlWithCircleTransform(url: String?, placeholder: Int) {
        val isDrawableFound = try {
            val d = ContextCompat.getDrawable(context, placeholder)
            d != null
        } catch (e: Exception) {
            false
        }
        if (isDrawableFound) {
            Glide.with(context).load(url).apply(
                RequestOptions().circleCrop().placeholder(
                    placeholder
                ).error(placeholder)
            ).into(this)

        } else {
            Glide.with(context).load(url).apply(RequestOptions().circleCrop()).into(this)
        }
    }

    fun setImageRes(resID: Int) {
        setImageResource(resID)
    }

    fun setCornerRadius(value: Int){
        cornerRadius = value
        getShapeDrawable().cornerRadius = value.toFloat()
    }

}