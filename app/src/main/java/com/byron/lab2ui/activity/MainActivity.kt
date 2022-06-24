package com.byron.lab2ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.byron.lab2ui.R
import com.byron.lab2ui.adapter.MainFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


//    private val TAB_DATA:IntArray = intArrayOf(R.string.main_bottom_msg, R.string.main_bottom_contact, R.string.main_bottom_find,
//        R.string.main_bottom_mine)
    private val TAB_IMG:IntArray = intArrayOf(R.drawable.main_bottom_msg_map, R.drawable.main_bottom_contact_map,
        R.drawable.main_bottom_find_map, R.drawable.main_bottom_mine_map)

    private val TAB_DATA:Array<String> = arrayOf("微信", "通讯录", "发现", "我")

    var pagerAdapter:PagerAdapter ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.hide()

        initPager()
        setTabs(main_tabLayout, layoutInflater, TAB_DATA, TAB_IMG)

    }

    /**
     * 初始化viewPager和tabLayout
     */
    private fun initPager(){
        pagerAdapter = MainFragmentAdapter(supportFragmentManager)
        main_viewPager.adapter = pagerAdapter

        main_viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(main_tabLayout))

        main_tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0 != null) {
                    main_viewPager.setCurrentItem(p0.position, false)
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }



        })

    }

    private fun setTabs(tabLayout:TabLayout, inflater:LayoutInflater, tabData:Array<String>, tabImgData:IntArray){
        for(i in 0..3){
            var tab:TabLayout.Tab = tabLayout.newTab()
            var view: View ?= inflater.inflate(R.layout.item_main_menu, null)

            //因为是不同的view，所以不能直接用txt_tab.text==来进行赋值
            val tabTv: TextView = view!!.findViewById(R.id.txt_tab)
            val tabImg: ImageView = view!!.findViewById(R.id.img_tab)
            tab.customView = view

            tabTv?.text = tabData[i]
            tabImg?.setImageResource(tabImgData[i])
            tabLayout.addTab(tab)
        }

    }
}

