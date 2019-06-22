package hr.bagy94.base_android.event

open class Event<T>(val content: T) {
    var hasBeenHandled = false
        private set

    fun onEventIfNotHandled(action: (Event<T>) -> Boolean) {
        if (!hasBeenHandled) {
            hasBeenHandled = action(this)
        }
    }
}