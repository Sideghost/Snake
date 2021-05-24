import pt.isel.canvas.*

// Game constants
const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val BLOCK_SPAWN_TIMER = 5000


/**
 * Class that defines the whole game.
 * @property snake
 * @property wall
 */
data class Game(val snake: Snake, val wall: List<Position>)


/**
 *
 */
fun Canvas.drawGame(game: Game) {
    erase()
    drawSnake(game.snake)
    game.wall.forEach{drawBrick(it)}

}


fun createRandomBrick(g: Game) :List<Position> =
    (ALL_POSITIONS - g.snake.HeadPos - g.snake.TailPos - g.wall).shuffled().take(1).map { Position(it.x, it.y) }
