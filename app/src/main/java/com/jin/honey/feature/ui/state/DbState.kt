package com.jin.honey.feature.ui.state

sealed class DbState {
    object Success : DbState()
    object Error : DbState()
}
