package zalo.taitd.reminder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
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

    fun createRemind(remind:Remind){
        Utils.createAlarm(this, remind)
    }
}
