package hr.bagy94.android.base.viewmodel

import hr.bagy94.android.base.router.SimpleRouter

open class DummyVM (): BaseViewModel<SimpleRouter>(){
    override val screenAdapter: ViewModelScreenAdapter by lazy { ScreenAdapter() }
    override val router: SimpleRouter by lazy { SimpleRouter() }
}