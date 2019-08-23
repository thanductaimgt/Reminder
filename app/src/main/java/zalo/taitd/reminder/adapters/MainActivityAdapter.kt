package zalo.taitd.reminder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_remind.view.*
import zalo.taitd.reminder.R
import zalo.taitd.reminder.models.Remind
import zalo.taitd.reminder.utils.BindableViewHolder
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class MainActivityAdapter:RecyclerView.Adapter<MainActivityAdapter.RemindViewHolder>(){
    val reminds = ArrayList<Remind>()
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
                timeTextView.text = SimpleDateFormat("h:mm").format(remind.time)
                dateTextView.text = SimpleDateFormat.getDateInstance().format(remind.time)
            }
        }

    }
}