package com.byron.lab2ui.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bigkoo.pickerview.TimePickerView
import com.byron.lab2ui.R
import com.byron.lab2ui.bean.UserBean
import com.byron.lab2ui.constant.Constant
import com.byron.lab2ui.db.MyDbHelper
import com.byron.lab2ui.util.DbUtil
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 注册界面
 * @author Byron
 * @date 220424
 */
class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val cityList = listOf("广州","深圳","佛山","珠海","中山")
    private val TAG = RegisterActivity::class.simpleName
    private var selectCity:String ?= null
    private var selectSex:String ?= null
    private var userName:String ?= null
    private var userPass:String ?= null
    private var userPassConfirm:String ?= null
    private var userBirth:String ?= null
    private var timePickerView:TimePickerView ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        val cityAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityList)
        reg_city_spinner.adapter = cityAdapter

        choseInfo()

        reg_birth.setOnClickListener(this)
        reg_register.setOnClickListener(this)
        reg_cancel.setOnClickListener(this)
    }

    //选择性别|城市
    private fun choseInfo(){
        //spinner选择触发事件
        reg_city_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                selectCity = cityList[0]
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                selectCity = cityList[position]
            }
        }
        //radioGroup触发事件
        reg_radioGroup.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1.equals(reg_sex_man.id)){
                    selectSex = reg_sex_man.text.toString()

                }else{
                    selectSex = reg_sex_woman.text.toString()
                }
            }
        })
    }

    //获取用户输入的内容
    private fun getInputData(){
        userName = reg_account.text.toString()
        userPass = reg_paw.text.toString()
        userPassConfirm = reg_repaw.text.toString()
        userBirth = reg_birth.text.toString()
}

    //判断用户输入的数据
    private fun judgeInputData(): Boolean{
        //如果为空，或小于6就显示提示信息
        if(userName.equals("") || userName!!.length<6 || userName!!.length>9){
            reg_account.error = "格式错误"
            return false
        }

        if (userPass.equals("")){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show()
            return false
        }

        if (!userPassConfirm.equals(userPass)){
            reg_repaw.error = "两次密码不一致"
            return false
        }

        if (selectSex.equals("") || selectSex == null){
            Toast.makeText(this, "请选择性别", Toast.LENGTH_LONG).show()
            return false
        }

        if (!reg_check_box.isChecked){
            Toast.makeText(this, "请阅读网络安全协议", Toast.LENGTH_LONG).show()
            return false
        }


        UserBean(userName!!.toInt(),
            userPass.toString(), selectSex.toString(), selectCity.toString(), userBirth.toString()
        )

        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
        return true
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.reg_register -> {
                getInputData()
                if (judgeInputData()){
                    //将数据存入数据库
                    saveData2Db()
                    saveUserInfo2Sp()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.reg_cancel ->{
                finish()
            }

            R.id.reg_birth ->{
                initTimePicker()
                timePickerView?.show()
            }
        }
    }

    //初始化日期选择器
    private fun initTimePicker(){
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        startDate.set(1969, 1, 1)
        endDate.set(2099, 12, 31)
        timePickerView = TimePickerView.Builder(this, object: TimePickerView.OnTimeSelectListener{
            override fun onTimeSelect(date: Date?, v: View?) {
                reg_birth.text = getTime(date!!)
            }

        })
            .setType((booleanArrayOf(true, true, true, false, false, false)))
            .setLabel("年", "月", "日", "时", "", "")
            .isCenterLabel(true)
            .setDividerColor(Color.DKGRAY)
            .setContentSize(21)
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .setDecorView(null)
            .build()

    }

    private fun getTime(date:Date):String{
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    private fun saveData2Db(){
        val contentVal = ContentValues().apply {
            //插入数据
            put("name", userName!!.toInt())
            put("paw", userPass)
            put("sex", selectSex)
            put("city", selectCity)
            put("u_birth", userBirth)
        }
        DbUtil.insertData(contentVal)
    }

    private fun saveUserInfo2Sp(){
        Log.d(TAG, "saveInfo paw: $userPass")
        val editor = getSharedPreferences(Constant.USER_SP, Context.MODE_PRIVATE).edit()
        editor.putInt(Constant.USER_NAME_SP, userName!!.toInt())
        editor.putString(Constant.USER_PAW_SP, userPass)
        editor.apply()
    }

}
