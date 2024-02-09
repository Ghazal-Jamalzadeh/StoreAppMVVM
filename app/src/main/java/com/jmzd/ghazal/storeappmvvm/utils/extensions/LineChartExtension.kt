package com.jmzd.ghazal.storeappmvvm.utils.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.jmzd.ghazal.storeappmvvm.R
import com.jmzd.ghazal.storeappmvvm.utils.gui.CustomToolTip

fun LineChart.setupMyChart(formatter: IndexAxisValueFormatter, entry: ArrayList<Entry>, count: Int, list: MutableList<String>) {
    with(this) {
        //General
        legend.isEnabled = false
        setTouchEnabled(true)
        isDragEnabled = false
        setScaleEnabled(false)
        setPinchZoom(false)
        animateX(1000)
        description.isEnabled = false
        axisRight.isEnabled = false
        extraRightOffset = context.resources.getDimension(`in`.nouri.dynamicsizeslib.R.dimen._8mdp)
        extraRightOffset = context.resources.getDimension(`in`.nouri.dynamicsizeslib.R.dimen._8mdp)
        //Left
        axisLeft.apply {
            setDrawLabels(false)
            setDrawGridLines(true)
            gridColor = ContextCompat.getColor(context, R.color.lavender)
            setDrawAxisLine(false)
            axisMinimum = 0f
            axisMaximum = 50000000f
        }
        //X-Axis
        xAxis.apply {
            labelCount = count - 1
            axisMinimum = 0f
            axisMaximum = (count - 1).toFloat()
            isGranularityEnabled = true
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = formatter
            typeface = setTypefaceNormal(context)
            textSize = 9.5f
            setDrawGridLines(false)
            setDrawAxisLine(true)
            axisLineColor = ContextCompat.getColor(context, R.color.lavender)
        }
        //Tooltip
        val toolTip = CustomToolTip(context, R.layout.custom_chart_tooltip, list)
        marker = toolTip
        //Init chart
        data = lineChartDataSet(entry, context)
        invalidate()
    }
}

private fun lineChartDataSet(list: ArrayList<Entry>, context: Context): LineData {
    val lineDataSet = LineDataSet(list, "Data").apply {
        lineWidth = 2f
        valueTextSize = 15f
        mode = LineDataSet.Mode.LINEAR
        color = ContextCompat.getColor(context, R.color.royalBlue)
        setDrawValues(false)
        valueTextColor = ContextCompat.getColor(context, R.color.eerieBlack)
        setDrawCircleHole(true)
        circleHoleRadius = 4f
        circleRadius = 5f
        setCircleColor(ContextCompat.getColor(context, R.color.royalBlue))
        fillAlpha = 100
        setDrawHighlightIndicators(false)
    }
    val dataSet = ArrayList<ILineDataSet>()
    dataSet.add(lineDataSet)
    return LineData(dataSet)
}