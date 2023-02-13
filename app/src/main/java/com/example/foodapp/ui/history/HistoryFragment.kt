package com.example.foodapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.BillAdapter
import com.example.foodapp.interfaces.HistoryInterface
import com.example.foodapp.model.Bill
import com.example.foodapp.presenter.HistoryPresenter
import kotlinx.android.synthetic.main.fragment_history.view.*

class HistoryFragment : Fragment(), HistoryInterface {

    private lateinit var view: View
    private lateinit var historyPresenter: HistoryPresenter
    private var listBill: ArrayList<Bill> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view  = inflater.inflate(R.layout.fragment_history, container, false)

        historyPresenter = HistoryPresenter(requireContext(), this)
        listBill = historyPresenter.getListBill()

        return view
    }

    private fun setLayout(view: View) {
        view.rcv_bill.layoutManager = LinearLayoutManager(requireContext())
        view.rcv_bill.adapter = BillAdapter(requireContext(),listBill)
    }

    override fun setLayout() {
        setLayout(requireView())
    }
}