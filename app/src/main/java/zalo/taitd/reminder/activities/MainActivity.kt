package zalo.taitd.reminder.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import zalo.taitd.reminder.R
import zalo.taitd.reminder.adapters.MainActivityAdapter

class MainActivity : AppCompatActivity() {
private lateinit var adapter: MainActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView(){
        adapter = MainActivityAdapter()
        recyclerView.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}
