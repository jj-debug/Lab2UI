package com.byron.lab2ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byron.lab2ui.R
import com.byron.lab2ui.util.FileUtil
import kotlinx.android.synthetic.main.frag_contact.view.*

class ContactFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_contact, container, false)
        view.contact_get_file.setOnClickListener {
            val fileText = FileUtil.loadName(view.context)
            view.contact_tv.text = fileText
        }
        return view
    }
}