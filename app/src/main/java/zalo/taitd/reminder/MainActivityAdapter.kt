package zalo.taitd.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_remind.view.*

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
                dateTimeTextView.text = String.format("%s - %s", Utils.getTimeFormat(remind.time), Utils.getDateFormat(remind.time))
                remainTimeTextView.text = Utils.getDateTimeDiffFormat(context!!, remind.time)
                descTextView.text = remind.description
                itemSwitch.isChecked = remind.isEnabled

                itemSwitch.setOnCheckedChangeListener { _, isChecked ->
                    (context as MainActivity).updateRemindState(remind.id, isChecked)
                }

                deleteImgView.setOnClickListener(context as View.OnClickListener)
            }
        }
    }
}