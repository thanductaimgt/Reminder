package zalo.taitd.reminder

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MainActivityViewModel : ViewModel() {
    val disposables = ArrayList<Disposable>()
    val liveReminds = MutableLiveData<HashMap<Int, Remind>>(HashMap())

    init {
        Single.fromCallable {
            ReminderApplication.database.reminderDao().getAllReminds()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(ReadAllRemindsObserver())
    }

    fun insertRemind(remind: Remind){
        Completable.fromCallable {
            ReminderApplication.database.reminderDao().insertRemind(remind)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(InsertRemindObserver(remind))
    }

    fun deleteRemind(remindId: Int){
        Completable.fromCallable {
            ReminderApplication.database.reminderDao().deleteRemind(remindId)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(DeleteRemindObserver(remindId))
    }

    inner class ReadAllRemindsObserver:SingleObserver<List<Remind>>{
        override fun onSuccess(reminds: List<Remind>) {
            liveReminds.value = liveReminds.value!!.apply {
                reminds.forEach {
                    put(it.id, it)
                }
            }
        }

        override fun onSubscribe(d: Disposable) {
            disposables.add(d)
        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: ${e.message}")
            e.printStackTrace()
        }
    }

    inner class InsertRemindObserver(private val remind: Remind): CompletableObserver {
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


    inner class DeleteRemindObserver(private val remindId: Int): CompletableObserver {
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

    fun disposeAllDisposables(){
        disposables.forEach { it.dispose() }
    }
}