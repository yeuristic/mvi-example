package com.yeuristic.mymvi

data class MainModel(
    val isLoading: Boolean,
    val userData: UserData,
    val items: List<Item>
)