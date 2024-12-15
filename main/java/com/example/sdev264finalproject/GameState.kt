package com.example.sdev264finalproject

import kotlin.random.Random

data class GameState(
    val xAxisGridSize: Int = 20,
    val yAxisGridSize: Int = 30,
    val direction: Direction = Direction.Right,
    var player: Coordinate = Coordinate(x = 9, y = 15), // centers player on board
    val coin: Coordinate = randomCoinCoordinate(),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val gameState: State = State.IDLE
) {
    companion object {
        fun randomCoinCoordinate(): Coordinate {
            return Coordinate(
                x= Random.nextInt(from = 1, until = 19),
                y= Random.nextInt(from = 1, until = 29)
            )
        }
    }
}

enum class State
{
    IDLE,
    RUNNING,
    PAUSED
}

enum class Direction
{
    UP,
    Down,
    Left,
    Right
}

data class Coordinate(
    var x: Int,
    val y: Int
)