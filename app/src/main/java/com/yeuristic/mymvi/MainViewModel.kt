package com.yeuristic.mymvi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeuristic.mymvi.ui.theme.Mocker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by harliedharma on 09/09/21
 */
class MainViewModel: ViewModel() {
    val model = MutableLiveData<MainModel>(Mocker.mockMainModel(true))

    val intentLiveData = MutableStateFlow<Intent>(Intent.Initial)

    init {
        viewModelScope.launch {
            intentLiveData.collect {
                processIntent(it)
            }
        }
    }

    fun intent(intent: Intent) {
        viewModelScope.launch {
            intentLiveData.emit(intent)
        }
    }

    private suspend fun processIntent(intent: Intent) {
        when(intent) {
            is Intent.FetchData -> fetchData(intent)
            is Intent.FetchUser -> fetchUser(intent)
            else -> Unit
        }
    }

    private suspend fun fetchData(intent: Intent.FetchData) {
        model.value = model.value?.copy(
            isLoading = true
        )

        delay(3000L)

        val length = model.value?.items?.size?: 0
        model.value = model.value?.copy(
            isLoading = false,
            items = model.value?.items?.plus(
                listOf(
                    Item("item${length+1}", "description"),
                    Item("item${length+2}", "description")
                )
            ).orEmpty()
        )
    }

    private suspend fun fetchUser(intent: Intent.FetchUser) {
        model.value = model.value?.copy(
            isLoading = true
        )

        delay(3000L)

        val length = model.value?.items?.size?: 0
        model.value = model.value?.copy(
            isLoading = false,
            userData = UserData(length.toString(), length, model.value?.userData?.photoUrl.orEmpty())
        )
    }


    sealed class Intent {
        object Initial: Intent()
        object FetchData: Intent()
        object FetchUser: Intent()
    }
}