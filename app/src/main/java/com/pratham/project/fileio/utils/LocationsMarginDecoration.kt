package com.pratham.project.fileio.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LocationsMarginDecoration : RecyclerView.ItemDecoration() {

    val margin = 15

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildLayoutPosition(view)
        if (position == 0) {
            outRect.left = margin
        }
        outRect.right = margin
        outRect.bottom = margin
    }
}
