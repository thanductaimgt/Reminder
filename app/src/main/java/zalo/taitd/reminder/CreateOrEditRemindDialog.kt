package zalo.taitd.reminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.dialog_create_remind.*
import kotlinx.android.synthetic.main.dialog_create_remind.view.*
import java.util.*


class CreateOrEditRemindDialog(private val fm: FragmentManager) : DialogFragment(),
    View.OnClickListener {
    private var remind: Remind? = null
    private lateinit var titleText: String
    private lateinit var positiveButtonText: String
    private lateinit var positiveButtonClickListener: (remind: Remind) -> Unit
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_create_remind, container, false)

        calendar = Calendar.getInstance().apply {
            if (remind != null) {
                time = remind!!.time
            } else {
                set(
                    get(Calendar.YEAR),
                    get(Calendar.MONTH),
                    get(Calendar.DAY_OF_MONTH),
                    Constants.DEFAULT_HOUR,
                    0,
                    0
                )
            }
        }

        initView(view)

        return view
    }

    override fun onPause() {
        remind?.description = view!!.descEditText.text.toString()
        super.onPause()
    }

    override fun onResume() {
        remind?.let { view!!.descEditText.setText(remind!!.description) }
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun initView(view: View) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        view.apply {
            titleTextView.text = titleText
            positiveButton.text = positiveButtonText
            descEditText.setText(remind?.description ?: "")

            cancelButton.setOnClickListener(this@CreateOrEditRemindDialog)
            positiveButton.setOnClickListener(this@CreateOrEditRemindDialog)
            timeTextView.setOnClickListener(this@CreateOrEditRemindDialog)
            dateTextView.setOnClickListener(this@CreateOrEditRemindDialog)

            timeTextView.text = Utils.getTimeFormat(calendar.time)

            dateTextView.text = Utils.getDateFormat(calendar.time)
        }
    }

    fun show(
        titleText: String,
        positiveButtonText: String,
        positiveButtonClickListener: (remind: Remind) -> Unit,
        remind: Remind? = null
    ) {
        this.remind = remind
        this.titleText = titleText
        this.positiveButtonText = positiveButtonText
        this.positiveButtonClickListener = positiveButtonClickListener
        show(fm, TAG)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cancelButton -> clearFocusAndDismiss()
            R.id.positiveButton -> callbackAndDismiss()
            R.id.timeTextView -> showTimePickerDialog()
            R.id.dateTextView -> showDatePickerDialog()
        }
    }

    private fun showTimePickerDialog() {
        TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, hourOfDay)
                    set(Calendar.MINUTE, minute)
                }
                view!!.timeTextView.text = Utils.getTimeFormat(calendar.time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.apply {
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.YEAR, year)
                }
                view!!.dateTextView.text = Utils.getDateFormat(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun clearFocusAndDismiss() {
        view!!.descEditText.clearFocus()
        view!!.descEditText.setText("")
        dismiss()
    }

    private fun callbackAndDismiss() {
        positiveButtonClickListener.invoke(
            Remind(
                id = if (remind != null) remind!!.id else Utils.generateNewId((activity as MainActivity).viewModel.liveReminds.value!!.values.toList()),
                time = calendar.time,
                description = descEditText.text.toString()
            )
        )

        clearFocusAndDismiss()
    }
}