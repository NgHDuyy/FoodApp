package com.example.foodapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.interfaces.LoginInterface
import com.example.foodapp.model.User
import com.example.foodapp.presenter.LoginPresenter
import com.example.foodapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginInterface {

    private lateinit var user: User

    private lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this, this)

        btnLogin.setOnClickListener {
            user = User(null,null,edtAccount.text.toString(), edtPassword.text.toString(), null)
            loginPresenter.checkValidUser(user)
            tvCheckLogin.visibility = View.VISIBLE
        }

        tvSignUp.setOnClickListener {
            loginPresenter.nextSignUpActivity(this)
        }
    }

    override fun isSuccess() {
        tvCheckLogin.text = "Đăng nhập thành công!"
        tvCheckLogin.setTextColor(resources.getColor(R.color.green))
        loginPresenter.nextHomeActivity(user, this)
        finish()
    }

    override fun isFailure() {
        tvCheckLogin.text = "Tài khoản hoặc mật khẩu không đúng"
        tvCheckLogin.setTextColor(resources.getColor(R.color.red))
    }
}