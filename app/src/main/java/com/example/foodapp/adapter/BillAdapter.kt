package com.example.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.R
import com.example.foodapp.model.Bill
import com.example.foodapp.presenter.CartPresenter
import kotlinx.android.synthetic.main.item_bill.view.*

class BillAdapter(private val context: Context, private val listBill: ArrayList<Bill>) :
    RecyclerView.Adapter<BillAdapter.BillViewHolder>() {
    inner class BillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(bill: Bill) {
            itemView.userName.text = bill.name
            itemView.phone.text = bill.phone
            itemView.address.text = bill.address
            itemView.note.text = bill.note
            itemView.time.text = bill.time
            itemView.rcv_bill.layoutManager = LinearLayoutManager(context)
            itemView.rcv_bill.adapter =
                ItemBillAdapter(context, bill.listItemBill, CartPresenter(context), false)
            itemView.sum.text = bill.sum.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder {
        return BillViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false)
        )
    }

    override fun getItemCount(): Int = listBill.size

    private var flag = true

    override fun onBindViewHolder(holder: BillViewHolder, position: Int) {
        holder.bindData(listBill[position])
        holder.itemView.scroll_rcv.setOnClickListener {
            if (flag){
                holder.itemView.rcv_bill.visibility = View.VISIBLE
                holder.itemView.scroll_rcv.setImageResource(R.drawable.ic_drop_up)
                flag = false
            }
            else{
                holder.itemView.rcv_bill.visibility = View.GONE
                holder.itemView.scroll_rcv.setImageResource(R.drawable.ic_drop_down)
                flag = true
            }
        }
    }
}