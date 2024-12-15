package com.example.sdev264finalproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(state: GameState, onEvent: (GameEvent) -> Unit)
{
    val coinImageBitmap = ImageBitmap.imageResource(id = R.drawable.coin)
    val playerImageBitmap = ImageBitmap.imageResource(id = R.drawable.player_ball)

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround)
    {
        Card(modifier = Modifier.padding(20.dp).fillMaxWidth())
        {
            Text(modifier = Modifier.padding(15.dp),
                text = "Score: ${state.score}",
                style = MaterialTheme.typography.headlineMedium)
        }
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 2/3f))
        {
            val cellSize = size.width / 20
            drawGameBoard(
                cellSize = cellSize,
                cellColor = Color.DarkGray,
                borderCellColor = Color.Black,
                gridWidth = state.xAxisGridSize,
                gridHeight = state.yAxisGridSize)
            drawCoin(
                coinImage = coinImageBitmap,
                cellSize = cellSize.toInt(),
                coordinate = state.coin
            )
            drawPlayer(
                playerImage = playerImageBitmap,
                cellSize = cellSize,
                coordinate = state.player
            )
        }
        Row(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth())
        {
            Button(modifier = Modifier.weight(1f),
                onClick = {
                    when (state.gameState)
                    {
                        State.IDLE, State.PAUSED -> onEvent(GameEvent.StartGame)
                        State.RUNNING -> onEvent(GameEvent.PauseGame)
                    }
                }
            )
            {
                Text(
                    text = when (state.gameState)
                    {
                        State.IDLE -> "Start"
                        State.RUNNING -> "Pause"
                        State.PAUSED -> "Resume"
                    }
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(modifier = Modifier.weight(1f),
                onClick = {onEvent(GameEvent.ResetGame)},
                enabled = state.gameState == State.PAUSED || state.isGameOver)
            {
                Text(text = if (state.isGameOver) "Reset" else "New Game")
            }
        }
    }
}

private fun DrawScope.drawGameBoard(
    cellSize: Float,
    cellColor: Color,
    borderCellColor: Color,
    gridWidth: Int,
    gridHeight: Int
)
{
    for (i in 0 until gridWidth) // nested loop to draw grid
    {
        for (j in 0 until gridHeight)
        {
            val isBorderCell = i == 0 || j == 0 || i == gridWidth - 1 || j == gridHeight - 1
            drawRect(
                color = if (isBorderCell) borderCellColor
                else if ((i + j) % 2 == 0) cellColor
                else cellColor.copy(alpha = 0.5f),
                topLeft = Offset(x = i * cellSize, y = j * cellSize),
                size = Size(cellSize, cellSize)
            )
        }
    }
}

private fun DrawScope.drawCoin(
    coinImage: ImageBitmap,
    cellSize: Int,
    coordinate: Coordinate
)
{
    drawImage(
        image = coinImage,
        dstOffset = IntOffset(
            x = (coordinate.x * cellSize),
            y = (coordinate.x * cellSize)
        ),
        dstSize = IntSize(cellSize, cellSize)
    )
}
private fun DrawScope.drawPlayer(
    playerImage: ImageBitmap,
    cellSize: Float,
    coordinate: Coordinate
)
{
    val cellSizeInt = cellSize.toInt()
    drawImage(
        image = playerImage,
        dstOffset = IntOffset(
            x = (coordinate.x * cellSizeInt),
            y = (coordinate.x * cellSizeInt)
        ),
        dstSize = IntSize(cellSizeInt, cellSizeInt)
    )
}