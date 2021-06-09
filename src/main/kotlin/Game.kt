import pt.isel.canvas.*


// Game constants.
const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val BLOCK_SPAWN_TIMER = 5000
const val QUART_OF_A_SEC = 250


/**
 * Class that defines the whole game.
 * @property snake object with movement in the game.
 * @property wall obstacles that stop movement of the [snake].
 */
//data class Game(val snake: Snake, val wall: List<Position>)
data class Game(val snake:Snake, val wall:List<Position>, val apple:Position?, val score:Int )

/**
 * Draws the whole game.
 * @param game collection of [wall] && [snake].
 */
fun Canvas.drawGame(game: Game) {
    erase()
    drawSnake(game.snake, "snake.png")
    game.wall.forEach { drawBrick(it, "bricks.png") }
    drawApple(game.apple,"snake.png")
}


/**
 * Function that draws a brick from the pngFile.
 * @param position position of each individual brick.
 * @param pngFile input file that has the drawing of the snake.
 */
fun Canvas.drawBrick(position: Position, pngFile: String) {
    drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}

fun Canvas.drawApple(position: Position?, pngFile: String) {
    val xFile = 0
    val yFile = 3
    drawImage("$pngFile|$xFile,$yFile,$SPRITE_DIV,$SPRITE_DIV",position?.x * CELL_SIDE,position?.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}
