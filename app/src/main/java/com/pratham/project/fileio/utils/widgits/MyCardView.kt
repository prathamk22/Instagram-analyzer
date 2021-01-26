package com.india.bankinfo.check.balance.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView

class MyCardView(context: Context, attributeSet: AttributeSet): CardView(context, attributeSet) {


    fun setShow(icon: String?) {
        visibility = if (icon?.isNotEmpty() == true) View.VISIBLE else View.GONE
    }
}