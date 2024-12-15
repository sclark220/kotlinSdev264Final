package com.example.sdev264finalproject

sealed class GameEvent {
    data object StartGame : GameEvent()
    data object PauseGame : GameEvent()
    data object ResetGame : GameEvent()
    //data class UpdateDirection(val offset: Offset, val canvasWidth: Int) : GameEvent()
}