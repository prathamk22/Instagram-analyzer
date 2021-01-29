package com.pratham.project.fileio.utils.widgits

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class CustomGraphView(context: Context, arrts: AttributeSet) : LineChart(context, arrts) {

    init {
        setDrawGridBackground(false)
        setTouchEnabled(false)
        axisRight.gridColor = Color.TRANSPARENT
        axisRight.setDrawGridLines(false)
        axisLeft.gridColor = Color.TRANSPARENT
        axisLeft.setDrawGridLines(false)
        axisRight.setDrawAxisLine(false)
        axisRight.setDrawLabels(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawLabels(false)
    }

    fun setGraphData(graphDataModel: GraphDataModel?){
        if (graphDataModel == null || graphDataModel.likesEntryList.isEmpty()){
            return
        }
        description = graphDataModel.description

        //setupLikes
        val lineDataSet = LineDataSet(graphDataModel.likesEntryList, "Likes")
        with(lineDataSet){
            setDrawCircles(false)
            lineWidth = 3f
            color = Color.RED
            setDrawValues(false)
            setDrawFilled(true)
            fillColor = Color.WHITE
            label = "Likes"
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.3f
        }

        //setupComments
        val commentsDataSet = LineDataSet(graphDataModel.commentsEntryList, "Comments")
        with(commentsDataSet){
            setDrawCircles(false)
            lineWidth = 3f
            color = Color.parseColor("#e1306c")
            setDrawValues(false)
            setDrawFilled(true)
            fillColor = Color.WHITE
            label = "Comments"
            mode = LineDataSet.Mode.CUBIC_BEZIER
            cubicIntensity = 0.3f
        }

        axisLeft.axisMaximum = lineDataSet.yMax + 50f
        axisLeft.axisMinimum = 0f
        val lineDataList = listOf<ILineDataSet>(lineDataSet, commentsDataSet)

        val lineData = LineData(lineDataList)
        lineData.setValueTextSize(15f)
        lineData.setValueTextColor(Color.BLACK)
        data = lineData

        animateX(500, Easing.EaseInCubic)
        invalidate()
    }

    data class GraphDataModel(
        val description: Description,
        val likesEntryList: List<Entry>,
        val commentsEntryList: List<Entry>
    )

}