package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.pratham.project.fileio.utils.GenericAdapter

class MyRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private lateinit var genericAdapter: GenericAdapter<*, *>

    fun setGenericAdapter(adapter: GenericAdapter<*, *>){
        this.adapter = adapter
        genericAdapter = adapter
    }

}