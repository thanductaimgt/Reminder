package zalo.taitd.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_remind.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class MainActivityAdapter:RecyclerView.Adapter<MainActivityAdapter.RemindViewHolder>(){
    var reminds:List<Remind> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_remind, parent, false)
        return RemindViewHolder(view)
    }

    override fun getItemCount(): Int {
         return reminds.size
    }

    override fun onBindViewHolder(holder: RemindViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RemindViewHolder(itemView: View): BindableViewHolder(itemView){
        override fun bind(position: Int) {
            val remind = reminds[position]
            itemView.apply {
                timeTextView.text = SimpleDateFormat("hh:mm").format(remind.time)
                dateTextView.text = SimpleDateFormat("dd/MM/yyyy").format(remind.time)
                deleteImgView.setOnClickListener(context as View.OnClickListener)
            }
        }
    }
}