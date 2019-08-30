package zalo.taitd.reminder

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {
    val reminds = MutableLiveData<List<Remind>>(ArrayList())

    init {
        Single.fromCallable {
            ReminderApplication.database.reminderDao().getAllReminds()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith()
    }

    inner class ReadAllRemindsObserver:SingleObserver<List<Remind>>{
        override fun onSuccess(t: List<Remind>) {
            
        }

        override fun onSubscribe(d: Disposable) {

        }

        override fun onError(e: Throwable) {
            Log.d(TAG, "onError: ${e.message}")
            e.printStackTrace()
        }
    }
}