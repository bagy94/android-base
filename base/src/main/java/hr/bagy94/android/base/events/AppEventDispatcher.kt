package hr.bagy94.android.base.events

import io.reactivex.subjects.PublishSubject


open class AppEventDispatcher {
    val eventsObservable = PublishSubject.create<Event>()
}