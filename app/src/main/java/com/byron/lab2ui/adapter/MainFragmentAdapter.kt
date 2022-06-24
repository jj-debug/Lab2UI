package com.byron.lab2ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.byron.lab2ui.fragment.ContactFragment
import com.byron.lab2ui.fragment.FindFragment
import com.byron.lab2ui.fragment.MineFragment
import com.byron.lab2ui.fragment.MsgFragment

/**
 * 主页底部导航栏viewPager的适配器
 * @author Byron
 * @date 220509
 */
class MainFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        var fragment:Fragment ?= null
        when(position){
            0 -> {
                fragment = MsgFragment()
            }
            1 ->{
                fragment = ContactFragment()
            }
            2 ->{
                fragment = FindFragment()
            }
            3 ->{
                fragment = MineFragment()
            }
        }
        return fragment
    }

    override fun getCount(): Int {

        return 4
    }
}