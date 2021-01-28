package com.pratham.project.fileio.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pratham.project.fileio.R
import com.pratham.project.fileio.data.remote.models.Item

fun SharedPreferences.save(key: String, value: Any) {
    val edit = edit()
    when (value) {
        is Int -> edit.putInt(key, value)
        is Float -> edit.putFloat(key, value)
        is String -> edit.putString(key, value)
        is Long -> edit.putLong(key, value)
        is Boolean -> edit.putBoolean(key, value)
    }
    edit.apply()
}

fun ImageView.loadImage(url: String?) {
    url ?: return
    Glide
        .with(context)
        .load(url)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}

fun <T : Collection<*>> T?.letEmpty(f: (it: T) -> Unit) {
    if (this != null && this.isNotEmpty()) f(this)
}

fun String?.letEmpty(f: (it: String) -> Unit) {
    if (this != null && this.isNotEmpty()) f(this)
}

fun Resources.getScaledPixelFromRes(dimenRes: Int): Int {
    return (getDimensionPixelSize(dimenRes) * configuration.smallestScreenWidthDp / 360f).toInt()
}

fun Resources.getScaledPixelFromAbsoluteSize(px: Int): Int {
    return (px * configuration.smallestScreenWidthDp / 360f).toInt()
}

fun Resources.getPxFromDP(dp: Float): Int {
    val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            displayMetrics
    )
    return px.toInt()
}

fun getColorFromString(colorHex: String): Int{
    return try{
        Color.parseColor(colorHex)
    }catch (e: Exception){
        Color.TRANSPARENT
    }
}

fun List<Item>?.getLikesSpannableList(): MutableList<SpannableModel> {
    val list = mutableListOf<SpannableModel>()
    val likesCount = this?.sumBy { it.likeCount ?: 0 } ?: 0
    list.add(
            SpannableModel(
                    "Likes\n",
                    textColor = R.color.white,
                    typeFace = "normal",
                    textSize = R.dimen.sp20
            )
    )
    list.add(
            SpannableModel(
                    "$likesCount",
                    textColor = R.color.white,
                    typeFace = "bold",
                    textSize = R.dimen.sp40
            )
    )
    return list
}

fun List<Item>?.getCommentsSpannableList(): MutableList<SpannableModel> {
    val list = mutableListOf<SpannableModel>()
    val commentsCount = this?.sumBy { it.commentCount ?: 0 } ?: 0
    list.add(
            SpannableModel(
                    "Comment\n",
                    textColor = R.color.white,
                    typeFace = "normal",
                    textSize = R.dimen.sp20
            )
    )
    list.add(
            SpannableModel(
                    "$commentsCount",
                    textColor = R.color.white,
                    typeFace = "bold",
                    textSize = R.dimen.sp40
            )
    )
    return list
}

fun List<Item>?.getPostsSpannableList(): MutableList<SpannableModel> {
    val list = mutableListOf<SpannableModel>()
    list.add(
            SpannableModel(
                    "Posts\n",
                    textColor = R.color.white,
                    typeFace = "normal",
                    textSize = R.dimen.sp20
            )
    )
    list.add(
            SpannableModel(
                    "${this?.size ?: 0}",
                    textColor = R.color.white,
                    typeFace = "bold",
                    textSize = R.dimen.sp40
            )
    )
    return list
}

fun Context.color(colorId: Int): Int{
    return try{
        ContextCompat.getColor(this, colorId)
    }catch (e: Exception){
        ContextCompat.getColor(this, R.color.transparent)
    }
}

fun Activity?.hideSoftKeyboard() {
    if(this == null) return
    if (currentFocus != null) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}