package com.example.kotlin_zem

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import java.util.*

class MyTabLayout : TabLayout {
    var text = ArrayList(Arrays.asList("진행중", "완료됨", "대기중"))
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)
        val customView = LayoutInflater.from(context).inflate(R.layout.fragment_my, tab.view, false)
        val tabTextView1 = customView.findViewById<TextView>(R.id.tv_icon)
        val tabText1 = if (tab.text == null) null else tab.text.toString()
        if (!TextUtils.isEmpty(tabText1)) {
            tabTextView1.text = tabText1
        }
        val tabTextView = customView.findViewById<TextView>(R.id.tv_title)
        val tabText = if (tab.text == null) null else tab.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(tabText)) {
            tabTextView.setText(text.get(position))
        }
        tab.view.setPadding(0, 0, 0, 0)
        tab.customView = customView

    }
}