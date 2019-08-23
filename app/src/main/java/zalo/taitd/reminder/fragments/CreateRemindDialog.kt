package zalo.taitd.reminder.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_create_remind.view.*
import zalo.taitd.reminder.R
import zalo.taitd.reminder.activities.MainActivity
import zalo.taitd.reminder.utils.TAG
import java.text.SimpleDateFormat
import java.util.*


class CreateRemindDialog(private val fm: FragmentManager) : DialogFragment(), View.OnClickListener {
    private val timeFormater = SimpleDateFormat.getTimeInstance()
    private val dateFormater = SimpleDateFormat.getDateInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_create_remind, container, false)
        initView(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private fun initView(view: View) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.apply {
            cancelButton.isEnabled = true
            cancelButton.setOnClickListener(this@CreateRemindDialog)
            createRemindButton.setOnClickListener(this@CreateRemindDialog)
            timeTextView.setOnClickListener(this@CreateRemindDialog)
            dateTextView.setOnClickListener(this@CreateRemindDialog)
        }
    }
    fun show() {
        show(fm, TAG)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cancelButton -> clearFocusAndDismiss()
            R.id.createRemindButton -> createRemindAndDismiss()
            R.id.timeTextView -> showTimePickerDialog()
            R.id.dateTextView -> showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            view!!.dateTextView.text = String.format("$dayOfMonth/$monthOfYear/$year")
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            view!!.timeTextView.text = String.format("$hourOfDay:$minute")
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()
    }

    private fun clearFocusAndDismiss() {
        view!!.descEditText.clearFocus()
        dismiss()
    }

    private fun createRemindAndDismiss() {
        (activity as MainActivity).createRemind()
        clearFocusAndDismiss()
    }
}