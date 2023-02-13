package com.example.foodapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import com.example.foodapp.interfaces.LoginInterface
import com.example.foodapp.model.User
import com.example.foodapp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() , LoginInterface{


    private lateinit var user: User

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loginPresenter = LoginPresenter(this, this)

        btnSignUp.setOnClickListener{
            user = User(null,edtUserName.text.toString(),edtAccount.text.toString(), edtPassword.text.toString(), false)
            loginPresenter.createUser(user)
            tvCheckSignUp.visibility = View.VISIBLE
        }

    }

    override fun isSuccess() {
        tvCheckSignUp.text = "Tạo tài khoản thành công"
        tvCheckSignUp.setTextColor(resources.getColor(R.color.green))
        finish()
    }

    override fun isFailure() {
        tvCheckSignUp.text = "Tài khoản đã tồn tại!"
        tvCheckSignUp.setTextColor(resources.getColor(R.color.red))
    }
}