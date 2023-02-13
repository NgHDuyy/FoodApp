package com.example.foodapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.foodapp.R
import com.example.foodapp.interfaces.MainInterface
import com.example.foodapp.model.User
import com.example.foodapp.presenter.CartPresenter
import com.example.foodapp.presenter.MainPresenter
import com.example.foodapp.ui.cart.CartFragment
import com.example.foodapp.ui.history.HistoryFragment
import com.example.foodapp.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_header_nav.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainInterface {

    private val FRAGMENT_HOME = 0
    private val FRAGMENT_CART = 1
    private val FRAGMENT_HISTORY = 2

    private var currentFragment = FRAGMENT_HOME

    private val mainPresenter = MainPresenter(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setTitle(R.string.home)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.nav_drawer_open, R.string.nav_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
        replaceFragment(HomeFragment())
        navigationView.menu.findItem(R.id.nav_home).isChecked = true
        bottom_nav.menu.findItem(R.id.bottom_home).isChecked = true

        bottom_nav.setOnItemSelectedListener{
            when (it.itemId){
                R.id.bottom_home -> {
                    openFragmentHome()
                    navigationView.menu.findItem(R.id.nav_home).isChecked = true
                }
                R.id.bottom_cart -> {
                    openFragmentCart()
                    navigationView.menu.findItem(R.id.nav_cart).isChecked = true
                }
                R.id.bottom_history -> {
                    openFragmentHistory()
                    navigationView.menu.findItem(R.id.nav_history).isChecked = true
                }
            }
            return@setOnItemSelectedListener true
        }
        mainPresenter.getUserInfo()
    }

    override fun setUserInfo(user: User) {
        navigationView.getHeaderView(0).tvUserName.text = user.userName
    }

    override fun logout() {
        mainPresenter.nextLoginActivity()
        finishAffinity()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                openFragmentHome()
                bottom_nav.menu.findItem(R.id.bottom_home).isChecked = true
            }
            R.id.nav_cart -> {
                openFragmentCart()
                bottom_nav.menu.findItem(R.id.bottom_cart).isChecked = true
            }
            R.id.nav_history -> {
                openFragmentHistory()
                bottom_nav.menu.findItem(R.id.bottom_history).isChecked = true
            }
            R.id.nav_logout -> {
                val cartPresenter = CartPresenter(this)
                cartPresenter.deleteAll()
                mainPresenter.logout()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragmentHome(){
        if (currentFragment != FRAGMENT_HOME) {
            toolbar.setTitle(R.string.home)
            replaceFragment(HomeFragment())
            currentFragment = FRAGMENT_HOME
        }
    }

    private fun openFragmentCart(){
        if (currentFragment != FRAGMENT_CART) {
            toolbar.setTitle(R.string.cart)
            replaceFragment(CartFragment())
            currentFragment = FRAGMENT_CART
        }
    }

    private fun openFragmentHistory(){
        if (currentFragment != FRAGMENT_HISTORY) {
            toolbar.setTitle(R.string.history)
            replaceFragment(HistoryFragment())
            currentFragment = FRAGMENT_HISTORY
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.contentFrame, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}