package zalo.taitd.reminder.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import kotlinx.android.synthetic.main.activity_main.*
import zalo.taitd.reminder.R
import zalo.taitd.reminder.adapters.MainActivityAdapter
import zalo.taitd.reminder.fragments.CreateRemindDialog
import zalo.taitd.reminder.models.Remind
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var reminds:List<Remind>
    private lateinit var adapter: MainActivityAdapter
    private lateinit var createRemindDialog: CreateRemindDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView(){
        createRemindDialog = CreateRemindDialog(supportFragmentManager)
        adapter = MainActivityAdapter()
        adapter.reminds.add(Remind(1, Date(), true))
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        addRemindImgView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.addRemindImgView->createRemindDialog.show()
        }
    }

    fun createRemind(){
// Create a Constraints object that defines when the task should run
        val constraints = Constraints.Builder()
            .
            .setRequiresCharging(true)
            .build()

// ...then create a OneTimeWorkRequest that uses those constraints
        val compressionWork = OneTimeWorkRequestBuilder<CompressWorker>()
            .setConstraints(constraints)
            .build()
    }
}
