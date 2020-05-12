package epf.com.office.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import epf.com.office.R
import epf.com.office.model.EpfOffice
import epf.com.office.model.StateCode
import kotlinx.android.synthetic.main.item_branch.view.*

class BranchAdapter(private var epfOfficeList: List<EpfOffice>) :
    RecyclerView.Adapter<BranchAdapter.ItemViewHolder>() {


    var onItemClick: ((epf: EpfOffice) -> Unit)? = null

    fun setData(epfOfficeList: List<EpfOffice>){
        this.epfOfficeList=epfOfficeList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_branch, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return epfOfficeList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setData(epfOfficeList.get(position))
    }

    inner class ItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val branchTextView = v.branch_tv
        val addressTextView = v.address_tv
        val distanceTexView = v.distance_tv
        val detailImageButton = v.detail_ib

        init {
            detailImageButton.setOnClickListener {
                onItemClick?.invoke(epfOfficeList[adapterPosition])
            }

        }

        fun setData(epfOffice: EpfOffice) {
            branchTextView.text = "EPF ${epfOffice.nam} Office"
            addressTextView.text = epfOffice.ads
            if(epfOffice.dist <1){
                distanceTexView.text = "${epfOffice.dist*100} M"
            }else{
                distanceTexView.text = "${epfOffice.dist.toInt()} KM"
            }

        }


    }
}