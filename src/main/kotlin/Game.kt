import pt.isel.canvas.playSound

// Game constants.
const val CELL_SIDE = 32
const val FIVE_CELLS = CELL_SIDE * 5
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val BLOCK_SPAWN_TIMER = 5000
const val QUART_OF_A_SEC = 250
const val STATUS_BAR = 40
const val TEXT_BASE = 10
const val FONT_SIZE = 25

//const val LEVEL_ONE_WIN = 60
const val INIT_SIZE = 5


/**
 * The three possible game states.
 */
enum class Status { RUN, WIN, LOSE }


/**
 * Class that defines the whole game.
 * @property snake object with movement in the game.
 * @property wall obstacles that stop movement of the [snake].
 * @property apple fruit that makes [snake] grow bigger.
 * @property score numbers of apples eaten.
 * @property status current state of the game.
 */
data class Game(
    val snake: Snake,
    val wall: List<Position>,
    val apple: Position?,
    val score: Int,
    val status: Status,
    val level: Leevel,
    val hacking: Hack
)

data class Hack(/*val grid:Boolean = false, val Sound:Boolean = false , val help:Boolean = false*/
    val poison: Position?,
    val golden: Position?)

data class Leevel(val level: Int)


fun Game.isPossible(): Game {
    val rightPos = (snake.body[0] + Direction.RIGHT).normalize()
    val upPos = (snake.body[0] + Direction.UP).normalize()
    val downPos = (snake.body[0] + Direction.DOWN).normalize()
    val leftPos = (snake.body[0] + Direction.LEFT).normalize()
    //all unavailable Position
    val impPos = snake.body + wall
    val newStatus = if (rightPos in impPos && upPos in impPos && downPos in impPos && leftPos in impPos) when {
        snake.body.size >= 60 -> Status.WIN
        else -> Status.LOSE
    }
    else Status.RUN
    return this.copy(status = newStatus)

}


fun gameTwo(g: Game): Game {
    val snake = Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW)
    val apple = initAppleTwo()
    val status = Status.RUN
    val level = Leevel(2)
    return Game(snake, initBlocksTwo(), apple, g.score, status, level, Hack(null,null))
}


fun gameThree(g: Game): Game {
    val snake = Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW)
    val apple = initAppleThree()
    val status = Status.RUN
    val level = Leevel(3)
    return Game(snake, initBlocksThree(), apple, g.score, status, level, Hack(null,null))
}



fun nextLv(key: Int, game: Game): Game {
    return when (key) {
        'N'.code -> if (game.status == Status.WIN && game.level.level == 1) {
            playSound("Win.wav")
            gameTwo(game) }
        else if (game.status == Status.WIN && game.level.level == 2) {
            playSound("Win.wav")
            gameThree(game) }
        else if (game.status == Status.LOSE){
            playSound("Defeat.wav")
            game }
        else if (game.status == Status.WIN && game.level.level == 3 && game.score >= 64){
            playSound("Victory.wav")
            game }
        else game

        'P'.code -> when (game.level.level) {
            1 -> {
                gameTwo(game)
            }
            2 -> {
                gameThree(game)
            }
            else -> game
        }

        else -> game
    }
}

