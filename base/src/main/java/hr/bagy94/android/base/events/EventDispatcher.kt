package hr.bagy94.android.base.events

import io.reactivex.subjects.PublishSubject


open class EventDispatcher {
    val eventsObservable = PublishSubject.create<Event>()
}