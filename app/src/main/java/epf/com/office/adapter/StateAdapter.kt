package epf.com.office.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import epf.com.office.R
import epf.com.office.model.StateCode
import kotlinx.android.synthetic.main.item_state.view.*

class StateAdapter() : RecyclerView.Adapter<StateAdapter.ItemViewHolder>() {

    var onItemClick: ((stateCode: StateCode) -> Unit)? = null
    private var stateList = listOf<StateCode>()

    fun setData(stateList:List<StateCode>){
        this.stateList = stateList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_state, parent, false))
    }

    override fun getItemCount(): Int {
        return stateList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setData(stateList.get(position))
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val stateTextView = v.state_tv
        val selectedImageView= v.selected_iv

        init {
            v.setOnClickListener {
                onItemClick?.invoke(stateList[adapterPosition])
            }
        }

        fun setData(stateCode: StateCode){
            stateTextView.text=stateCode.value
            if(stateCode.isSelected){
                selectedImageView.visibility = VISIBLE
            }else{
                selectedImageView.visibility = GONE
            }
        }

    }

}