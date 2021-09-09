package com.yeuristic.mymvi

class MainModel(
    val isLoading: Boolean,
    val userData: UserData,
    val items: List<Item>
)