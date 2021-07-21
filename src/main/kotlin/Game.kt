// Import that allows to make sounds during the game.
import pt.isel.canvas.playSound


// Game Grid Features.
const val CELL_SIDE = 32    // Pixel dimension of each grid square.
const val GRID_WIDTH = 20   // Number of cells horizontally.
const val GRID_HEIGHT = 16  // Number of cells vertically.
const val SPRITE_DIV = 64   // Sprite Division on the Sprite file "snake.png".


//Status Bar Features
const val STATUS_BAR = 40               // Status bar height.
const val FIVE_CELLS = CELL_SIDE * 5    //Score text origin on Status and status lose or lose origin .
const val TEXT_BASE = 10                // Origin Point of the text on
const val FONT_SIZE = 25                // Font size on Status Bar


//Spawn Rate and Snake Velocity.
const val BLOCK_SPAWN_TIMER = 5000  // Spawn Block Rate.
const val QUART_OF_A_SEC = 250      // Velocity of the snake on each movement.


// Hacking Features
const val Level_WIN = 60    // Level score to Win.
const val INIT_SIZE = 5     // Initial max size of the snake.
private const val LEVEL_ONE = 1     // Level 1.
const val LEVEL_TWO = 2     // Level 2.
const val LEVEL_THREE = 3   // level 3.
private const val EASTER_EGG = 64   // Easter egg.

/**
 * The three possible game states.
 */
enum class Status { RUN, WIN, LOSE }

//TODO("perguntar se posso por publico no git por causa do canvas")

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
    val hacking: Hack
)


/**
 * Class that defines a few variables to the game.
 * @property poison special apple present in lv.3.
 * @property golden special apple present in lv.2.
 * @property grid game grid.
 * @property sound sounds made by the game.
 * @property level current game level.
 */
data class Hack(
    val poison: Position? = null,
    val golden: Position? = null,
    val grid: Boolean = false,
    val sound: Boolean = false,
    val level: Int = 1 /*IntRange = IntRange(1,3)*/
)
/*val level = IntRange('1','3') = LEVEL_ONE*/
/* val help:Boolean = false*/


/**
 * Function responsible for recognizing whether or not the game is over.
 * @receiver Game to check.
 * @return Game checked.
 */
fun Game.isPossible(): Game {
    val rightPos = (snake.body[0] + Direction.RIGHT).normalize()
    val upPos = (snake.body[0] + Direction.UP).normalize()
    val downPos = (snake.body[0] + Direction.DOWN).normalize()
    val leftPos = (snake.body[0] + Direction.LEFT).normalize()
    //all unavailable Position
    val impPos = snake.body + wall
    val newStatus = if (rightPos in impPos && upPos in impPos && downPos in impPos && leftPos in impPos) when {
        snake.body.size >= Level_WIN -> Status.WIN
        else -> Status.LOSE
    }
    else Status.RUN
    return this.copy(status = newStatus)
//TODO (make this better)
}


/**
 * Function responsible for the construction of any level.
 * @param game game to be altered.
 * @return a new game level.
 */
private fun gameLevel(game: Game): Game {
    val initSnake = Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW)
    return game.copy(snake = initSnake,status= Status.RUN)
}


/**
 * Function responsible for returning the next lv.
 */
private fun Game.nextLv(key: Int) = if (key == 'N'.code)
    when {
        status == Status.WIN && hacking.level == LEVEL_ONE -> {
            if (hacking.sound) playSound("Win.wav")
            gameLevel(copy(apple= initApple(initBlocksTwo()),wall =initBlocksTwo(), hacking= hacking.copy(level= LEVEL_TWO)))
        }

        status == Status.WIN && hacking.level == LEVEL_TWO -> {
            if (hacking.sound) playSound("Win.wav")
            gameLevel(copy(apple= initApple(initBlocksThree()),wall= initBlocksThree(),hacking = hacking.copy(level= LEVEL_THREE)))
        }

        status == Status.LOSE -> {
            if (hacking.sound) playSound("Defeat.wav")
            this
        }

        status == Status.WIN && hacking.level == LEVEL_THREE && score >= EASTER_EGG -> {
            if (hacking.sound) playSound("Victory.wav")
            this
        }

        else -> this
    }
else this


/**
 * Function that enables the existence of a grid.
 */
private fun grid(game: Game): Game = game.copy(hacking = game.hacking.copy(grid = true))


/**
 * Function that enables the existence of sounds.
 */
private fun sound(game: Game): Game = game.copy(hacking = game.hacking.copy(sound = true))


/**
 * Function responsible for activating the hacking characteristics of the game.
 */
fun options(key: Int, game: Game) = when (key) {
        'G'.code -> if (!game.hacking.grid) grid(game)
        else game.copy(hacking = game.hacking.copy(grid = false))

        'S'.code -> if (!game.hacking.sound) sound(game)
        else game.copy(hacking = game.hacking.copy(sound = false))

        'P'.code -> when (game.hacking.level) {
            1 -> gameLevel(game.copy(apple = initApple(initBlocksTwo()),wall= initBlocksTwo(), hacking = game.hacking.copy(level= LEVEL_TWO)))

            2 -> gameLevel(game.copy(apple = initApple(initBlocksThree()), wall= initBlocksThree(),hacking = game.hacking.copy( level=LEVEL_THREE )))

            else -> game
        }
        'N'.code -> game.nextLv(key)
        else -> game
    }

