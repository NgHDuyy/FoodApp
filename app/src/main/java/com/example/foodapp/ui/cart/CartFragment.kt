package com.example.foodapp.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.ItemBillAdapter
import com.example.foodapp.model.ItemBill
import com.example.foodapp.presenter.CartPresenter
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {

    private lateinit var cartPresenter: CartPresenter
    private var listItemBills: ArrayList<ItemBill> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        cartPresenter = CartPresenter(requireContext())

        setLayout(view)
        view.pay.setOnClickListener {
            cartPresenter.confirm()
        }
        return view
    }

    private fun setLayout(view: View) {
        listItemBills = cartPresenter.getListItemBill()
        if (listItemBills.isEmpty()) {
            view.blank.visibility = View.VISIBLE
            view.pay.visibility = View.GONE
            view.layout_sum.visibility = View.GONE
        } else {
            view.blank.visibility = View.GONE
            view.pay.visibility = View.VISIBLE
            view.layout_sum.visibility = View.VISIBLE
        }
        view.rcvItemBill.layoutManager = LinearLayoutManager(requireContext())
        view.rcvItemBill.adapter = ItemBillAdapter(requireContext(), listItemBills, cartPresenter, true)
        view.sum.text = cartPresenter.getSum().toString()
    }

    override fun onResume() {
        super.onResume()
        setLayout(requireView())
    }
}