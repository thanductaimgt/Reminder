package zalo.taitd.reminder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: MainActivityAdapter
    private lateinit var createRemindDialog: CreateRemindDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(MainActivityViewModel::class.java)
        viewModel.liveReminds.observe(this, Observer { reminds ->
            adapter.reminds = reminds.values.sortedByDescending { it.id }.map { it.copy() }
            adapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
        createRemindDialog = CreateRemindDialog(supportFragmentManager)
        adapter = MainActivityAdapter()
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        addRemindImgView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.addRemindImgView -> createRemindDialog.show()
            R.id.deleteImgView -> {
                val position = recyclerView.getChildLayoutPosition(view.parent as View)
                val remind = adapter.reminds[position]
                deleteRemind(remind.id)
            }
        }
    }

    private fun deleteRemind(remindId: Int) {
//        disable alarms
        viewModel.deleteRemind(this, remindId)
        Toast.makeText(this, getString(R.string.delete_remind_success), Toast.LENGTH_SHORT).show()
    }

    fun createRemind(remind: Remind) {
        viewModel.createRemind(this, remind)
        Toast.makeText(this, getString(R.string.create_remind_success), Toast.LENGTH_SHORT).show()
    }

    fun updateRemindState(remindId: Int, isEnabled: Boolean) {
        viewModel.updateRemindState(this, remindId, isEnabled)
        val toastMessageResId =
            if (isEnabled) {
                R.string.enabled_remind
            } else {
                R.string.disabled_remind
            }
        Toast.makeText(this, getString(toastMessageResId), Toast.LENGTH_SHORT).show()
    }
}
