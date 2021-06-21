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
const val Level_WIN = 60
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
 * @property hacking a few additional variables to the game
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
 * @property poison spetial apple present in lv.3.
 * @property golden spetial apple present in lv.2.
 * @property grid game grid.
 * @property sound sounds made by the game.
 * @property level current game level.
 */
data class Hack(
    val poison: Position?,
    val golden: Position?,
    val grid: Boolean,
    val sound: Boolean,
    val level: Int
)


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

}


/**
 * Function responsible for the construction of lv.2.
 */
fun gameTwo(g: Game): Game {
    val snake = Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW)
    val apple = initAppleTwo()
    val status = Status.RUN
    val level = 2
    return Game(
        snake,
        initBlocksTwo(),
        apple,
        g.score,
        status,
        Hack(null, null, g.hacking.grid, g.hacking.sound, level)
    )
}


/**
 * Function responsible for the construction of lv.3.
 */
fun gameThree(g: Game): Game {
    val snake = Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW)
    val apple = initAppleThree()
    val status = Status.RUN
    val level = 3
    return Game(
        snake,
        initBlocksThree(),
        apple,
        g.score,
        status,
        Hack(null, null, g.hacking.grid, g.hacking.sound, level)
    )
}


/**
 * Function responsible for returning the next lv.
 */
fun nextLv(key: Int, game: Game): Game {
    return when (key) {
        'N'.code -> when {
            game.status == Status.WIN && game.hacking.level == 1 -> {
                if (game.hacking.sound) playSound("Win.wav")
                gameTwo(game)
            }
            game.status == Status.WIN && game.hacking.level == 2 -> {
                if (game.hacking.sound) playSound("Win.wav")
                gameThree(game)
            }
            game.status == Status.LOSE -> {
                if (game.hacking.sound) playSound("Defeat.wav")
                game
            }
            game.status == Status.WIN && game.hacking.level == 3 && game.score >= 64 -> {
                if (game.hacking.sound) playSound("Victory.wav")
                game
            }
            else -> game
        }
        else -> game
    }
}


/**
 * Function that enables the existence of a grid.
 */
fun grid(g: Game): Game =
    g.copy(hacking = Hack(g.hacking.poison, g.hacking.golden, true, g.hacking.sound, g.hacking.level))


/**
 * Function that enables the existence of sounds.
 */
fun sound(g: Game): Game =
    g.copy(hacking = Hack(g.hacking.poison, g.hacking.golden, g.hacking.grid, true, g.hacking.level))


/**
 * Function responsible for activating the hacking characteristics of the game.
 */
fun options(key: Int, game: Game): Game {
    return when (key) {
        'G'.code -> when {
            !game.hacking.grid -> grid(game)
            else -> game.copy(
                hacking = Hack(
                    game.hacking.poison,
                    game.hacking.golden,
                    false,
                    game.hacking.sound,
                    game.hacking.level
                )
            )
        }
        'S'.code -> when {
            !game.hacking.sound -> sound(game)
            else -> game.copy(
                hacking = (Hack(
                    game.hacking.poison,
                    game.hacking.golden,
                    game.hacking.grid,
                    false,
                    game.hacking.level
                ))
            )
        }
        'P'.code -> when (game.hacking.level) {
            1 -> gameTwo(game)
            2 -> gameThree(game)
            else -> game
        }
        'N'.code -> nextLv(key, game)
        else -> game
    }
}


