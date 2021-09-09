package com.yeuristic.mymvi.ui.theme

import com.yeuristic.mymvi.Item
import com.yeuristic.mymvi.MainModel
import com.yeuristic.mymvi.UserData

object Mocker {
    fun mockMainModel(loading: Boolean = false): MainModel = MainModel(
        isLoading = loading,
        userData = UserData("John Doe", 31, "https://picsum.photos/200"),
        items = (1..100).map {
            Item(
                "Title $it",
                "Lorem ipsum dolor sit amet $it"
            )
        }
    )
}