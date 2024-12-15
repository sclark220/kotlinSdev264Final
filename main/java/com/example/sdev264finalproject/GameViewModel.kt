package com.example.sdev264finalproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel : ViewModel()
{
    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    fun onEvent(event: GameEvent)
    {
        when(event)
        {
            GameEvent.StartGame -> {_state.update{it.copy(gameState = State.RUNNING)}
                viewModelScope.launch {
                    while (state.value.gameState == State.RUNNING) {
                        delay(100)
                        _state.value = updateGame(state.value)
                    }
                }
            }
            GameEvent.PauseGame ->{_state.update{it.copy(gameState = State.PAUSED)}}
            GameEvent.ResetGame -> {_state.value = GameState()}
        }
    }

    private fun updateGame(currentGame: GameState): GameState
    {
        currentGame.player.x += 1

        if(currentGame.isGameOver)
        {
            return currentGame
        }
        val xAxisGridSize = currentGame.xAxisGridSize
        val yAxisGridSize = currentGame.yAxisGridSize

        //Check if the player goes out of bounds
        if (!isWithinBounds(currentGame.player, xAxisGridSize, yAxisGridSize)
        ) {
            return currentGame.copy(isGameOver = true)
        }

        //Check if the coin is collected
        val newCoin = if (currentGame.player == currentGame.coin)
        {
            GameState.randomCoinCoordinate()
        }
        else
        {
            currentGame.coin
        }

        return currentGame.copy(coin = newCoin)
    }

    private fun isWithinBounds(
        coordinate: Coordinate,
        xAxisGridSize: Int,
        yAxisGridSize: Int
    ): Boolean
    {
        return coordinate.x in 1 until xAxisGridSize - 1 && coordinate.y in 1 until yAxisGridSize - 1
    }
}