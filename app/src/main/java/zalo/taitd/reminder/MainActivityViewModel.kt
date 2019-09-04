package zalo.taitd.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MainActivityViewModel : ViewModel() {
    val disposables = CompositeDisposable()
    val liveReminds = MutableLiveData<HashMap<Int, Remind>>(HashMap())

    init {
        Single.fromCallable {
            ReminderApplication.database.reminderDao().getAllReminds()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(GetAllRemindsObserver{reminds->
                liveReminds.value = liveReminds.value!!.apply {
                    reminds.forEach {
                        put(it.id, it)
                    }
                }
            })
    }

    fun deleteRemind(context: Context, remindId: Int) {
        Completable.fromCallable {
            ReminderApplication.database.reminderDao().deleteRemind(remindId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(DeleteRemindObserver(remindId))

        deleteAlarms(context, liveReminds.value!![remindId]!!)
    }

    private fun deleteAlarms(context: Context, remind: Remind) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        Constants.remindTimes.forEachIndexed { index, _ ->
            alarmManager.cancel(
                PendingIntent.getService(
                    context,
                    remind.id + index,
                    Intent(context, NotificationService::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }
    }

    fun updateRemindState(context: Context, remindId: Int, isEnabled: Boolean) {
        val remind = liveReminds.value!![remindId]!!.copy().apply { this.isEnabled = isEnabled }

        Completable.fromCallable {
            ReminderApplication.database.reminderDao().updateRemind(remind)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(UpdateRemindObserver(remind))

        if (isEnabled) {
            Utils.createAlarms(context, remind)
        } else {
            deleteAlarms(context, remind)
        }
    }

    fun createRemind(context: Context, remind: Remind) {
        Completable.fromCallable {
            ReminderApplication.database.reminderDao().insertRemind(remind)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(InsertRemindObserver(remind))

        Utils.createAlarms(context, remind)
    }

    private inner class GetAllRemindsObserver(private val callback:(reminds:List<Remind>)->Unit) : SingleObserver<List<Remind>> {
        override fun onSuccess(reminds: List<Remind>) {
            callback.invoke(reminds)
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: ${e.message}")
            e.printStackTrace()
        }
    }

    private inner class InsertRemindObserver(private val remind: Remind) : CompletableObserver {
        override fun onComplete() {
            Log.d(TAG, "onComplete: $remind")
            liveReminds.value = liveReminds.value!!.apply {
                put(remind.id, remind)
            }
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
            Log.d(TAG, "onSubscribe: $remind")
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: $remind")
            e.printStackTrace()
        }
    }


    private inner class DeleteRemindObserver(private val remindId: Int) : CompletableObserver {
        override fun onComplete() {
            Log.d(TAG, "onComplete: $remindId")
            liveReminds.value = liveReminds.value!!.apply {
                remove(remindId)
            }
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
            Log.d(TAG, "onSubscribe: $remindId")
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: $remindId")
            e.printStackTrace()
        }
    }

    private inner class UpdateRemindObserver(private val remind: Remind) : CompletableObserver {
        override fun onComplete() {
            Log.d(TAG, "onComplete: $remind")
            liveReminds.value = liveReminds.value!!.apply {
                put(remind.id, remind)
            }
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
            Log.d(TAG, "onSubscribe: $remind")
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: $remind")
            e.printStackTrace()
        }
    }

    override fun onCleared() {
        disposables.dispose()
    }
}