package com.example.foodapp.presenter

import android.content.Context
import android.content.Intent
import com.example.foodapp.ui.MainActivity
import com.example.foodapp.MySharePreference
import com.example.foodapp.interfaces.LoginInterface
import com.example.foodapp.model.User
import com.example.foodapp.ui.login.SignUpActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginPresenter(val loginInterface: LoginInterface, val context: Context) {

    private var users: ArrayList<User> = arrayListOf()
    private val ref = FirebaseDatabase.getInstance().getReference("users")
    private val sharePre = MySharePreference()

    private var IS_LOGGED: String = "IS_LOGGED"
    private var USER_LOGGED: String = "USER_LOGGED"

    init {
        getListUsers()
    }

    private fun getListUsers() {
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                if (snapshot.exists()) {
                    for (userSnap in snapshot.children) {
                        users.add(userSnap.getValue(User::class.java)!!)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun checkValidUser(user: User) {
        var flag = 0
        for (i in 0 until users.size) {
            if (user.account.equals(users[i].account)) {
                if (user.password.equals(users[i].password)) {
                    flag = 1
                    break
                }
            }
        }
        if (flag == 0) {
            loginInterface.isFailure()
        } else {
            loginInterface.isSuccess()
        }
    }

    fun nextHomeActivity(user: User, context: Context) {
        sharePre.getInstance(context)!!.putBooleanLoginValue(IS_LOGGED, true)
        for (i in 0 until users.size){
            if (user.account.equals(users[i].account)){
                user.id = users[i].id
                user.userName = users[i].userName
                user.isAdmin = users[i].isAdmin
            }
        }
        sharePre.getInstance(context)!!.putUserLogged(USER_LOGGED, user)
        context.startActivity(Intent(context, MainActivity::class.java))
    }

    fun nextSignUpActivity(context: Context) {
        context.startActivity(Intent(context, SignUpActivity::class.java))
    }

    fun createUser(user: User) {
        var flag = 0
        for (i in 0 until users.size) {
            if (user.account.equals(users[i].account)) {
                flag = 1
                break
            }
        }
        if (flag == 1) {
            loginInterface.isFailure()
        } else {
            user.id = users[users.size -1].id!! + 1
            ref.child(user.id.toString()).setValue(user)
            loginInterface.isSuccess()
        }
    }
}