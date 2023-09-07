package com.example.foodapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.foodapp.R
import com.example.foodapp.adapter.ItemAdapter
import com.example.foodapp.adapter.SlideItemAdapter
import com.example.foodapp.adapter.SlidePhotoAdapter
import com.example.foodapp.interfaces.HomeInterface
import com.example.foodapp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(), HomeInterface {

    private lateinit var homePresenter: HomePresenter
    private lateinit var viewPager2: ViewPager2;
    private var handler = Handler()
    private var runnalbe = Runnable {
        if (viewPager.currentItem == homePresenter.getListFavoritePhoto().size - 1) {
            viewPager.currentItem = 0
        } else {
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        homePresenter = HomePresenter(requireContext(), this)
        homePresenter.getFavoritePhoto(view)
        homePresenter.getItems(view)
        return view
    }

    private fun setSlideImage(view: View) {
        view.viewPager.adapter =
            SlidePhotoAdapter(view.context, homePresenter.getListFavoritePhoto())
        view.circleIndicator.setViewPager(view.viewPager)
        view.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnalbe)
                handler.postDelayed(runnalbe, 2000)
                super.onPageSelected(position)
            }
        })
    }
    private fun setSlideItemImage(view: View){
        view.viewPager2.adapter=
            SlideItemAdapter(view.context,homePresenter.getListItem())
        view.viewPager2.offscreenPageLimit=3
        view.viewPager2.clipToPadding=false
        view.viewPager2.clipChildren=false
        view.viewPager2.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setRcvItem(view: View) {
        view.rcvListFood.layoutManager = GridLayoutManager(view.context, 2)
        view.rcvListFood.adapter =
            ItemAdapter(view.context, homePresenter.getListItem(), homePresenter)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnalbe)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnalbe, 2000)
    }

    override fun setLayoutSlideImage(view: View) {
        setSlideImage(view)
      //  setSlideItemImage(view)
    }

    override fun setLayoutItem(view: View) {
        setRcvItem(view)
     //   setSlideItemImage(view)
    }

}