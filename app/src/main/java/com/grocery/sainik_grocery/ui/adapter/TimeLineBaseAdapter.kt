package com.grocery.sainik_grocery.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.TimeLineModel
import com.grocery.sainik_grocery.R
import com.lriccardo.timelineview.TimelineAdapter
import com.lriccardo.timelineview.TimelineView

class TimeLineBaseAdapter(val context: Context, val items: List<TimeLineModel>, val mPosition: Int) :
    RecyclerView.Adapter<TimeLineBaseAdapter.BaseViewHolder>(),
    TimelineAdapter {

    val ITEM_TYPE_ONE = 0
    val ITEM_TYPE_TWO = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = if (viewType == ITEM_TYPE_ONE) LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_base_list_item, parent, false)
        else LayoutInflater.from(parent.context)
            .inflate(R.layout.timeline_base_list_item1, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.title.text = items[position].ordername
        holder.subTitle.text = items[position].orderDetails
        holder.tvDate.text = items[position].orderDate
    }

    override fun getItemViewType(position: Int): Int {
        if (position < mPosition)
            return ITEM_TYPE_ONE
        else
            return ITEM_TYPE_TWO
    }

    override fun getItemCount(): Int = items.size

    override fun getIndicatorDrawableRes(position: Int): Int? {
        if (position < mPosition)
            return R.drawable.ic_check_circle_green
        else return R.drawable.ic_check_circle_grey
    }


    override fun getIndicatorStyle(position: Int): TimelineView.IndicatorStyle? {
        if (position <= mPosition)
            return TimelineView.IndicatorStyle.Checked
        else return TimelineView.IndicatorStyle.Empty
    }

    /*override fun getTimelineViewType(position: Int): TimelineView.ViewType? {
        return if (position == 0) {
            TimelineView.ViewType.FIRST
        } else if (position< 2) {
            TimelineView.ViewType.SPACER
        }else if (position == itemCount-1) {
            TimelineView.ViewType.LAST
        } else {
            TimelineView.ViewType.MIDDLE
        }
    }*/

    override fun getLineStyle(position: Int): TimelineView.LineStyle? {

        return if (position < mPosition) TimelineView.LineStyle.Normal
        else TimelineView.LineStyle.Dashed
        //return super.getLineStyle(position)
    }


    override fun getLineColor(position: Int): Int? {
        return if (position < mPosition)
            Color.parseColor("#45bc1b")
        else
            Color.parseColor("#CCCCCC")
    }

    override fun getLinePadding(position: Int): Float? {
        if (position > mPosition)
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10f,
                Resources.getSystem().displayMetrics
            )
        return super.getLinePadding(position)
    }

    inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val subTitle = itemView.findViewById<TextView>(R.id.subTitle)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
    }
}
