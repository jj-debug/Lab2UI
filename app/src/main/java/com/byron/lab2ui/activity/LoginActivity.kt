package com.byron.lab2ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.byron.lab2ui.R
import com.byron.lab2ui.constant.Constant
import com.byron.lab2ui.util.DbUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.Exception

/**
 * 登录界面
 * @author Byron
 * @date 220424
 */
class LoginActivity :  AppCompatActivity() {

    private val TAG: String? = LoginActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        var userNameSp:Int ?= null
        var userPawSp:String ?= null
        var isRemember = false

        var userName:String ?= null
        //注册后使用sp自动填充用户名
        try{
            val sp = getSharedPreferences(Constant.USER_SP, Context.MODE_PRIVATE)
            userNameSp = sp.getInt(Constant.USER_NAME_SP, 0)
            userPawSp = sp.getString(Constant.USER_PAW_SP, "")
            isRemember = sp.getBoolean(Constant.USER_IS_REMEMBER, false)
            login_remember.isChecked = isRemember
            Log.d(TAG, "sp账号：$userNameSp 密码: $userPawSp isRemember: $isRemember")
        }catch (e:Exception){
            e.stackTrace
        }

        if (userNameSp != 0){
            login_account.setText(userNameSp.toString())
            if ( userPawSp!= null && isRemember){
                login_paw.setText(userPawSp)
            }
        }
        //记住密码的监听事件
        login_remember.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(TAG, "选择了记住密码")
            isRemember = isChecked
            val editor = getSharedPreferences(Constant.USER_SP, Context.MODE_PRIVATE).edit()
            editor.putBoolean(Constant.USER_IS_REMEMBER, isRemember)
            editor.apply()
        }

        //注册按钮点击事件
        login_reg_btn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        //登陆按钮点击事件
        login_btn.setOnClickListener {
            userName = login_account.text.toString()
            userPawSp = login_paw.text.toString()
            if (userName.equals("") || userName == null){
                Toast.makeText(this, "请输用户名", Toast.LENGTH_LONG).show()
            }else{
                /**如果默认密码password匹配进入主界面
                 * 否则弹出提示信息
                 */
                val dbPaw = DbUtil.searchUserPawByName(userName)
                Log.d(TAG, "数据库中的密码: $dbPaw")
                if (userPawSp == dbPaw){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val alertDialog:AlertDialog = AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage(R.string.login_error)
                        .setPositiveButton("确定") { dialog, which -> dialog?.dismiss() }
                        .setNegativeButton("退出") { dialog, which -> finish() }
                        .create()
                    alertDialog.show()
                }
            }

        }
    }

}
