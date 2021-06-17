// Game constants.
const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val BLOCK_SPAWN_TIMER = 5000
const val QUART_OF_A_SEC = 250
const val STATUS_BAR = 40
const val TEXT_BASE = 10
const val FONT_SIZE = 25

/**
 * The three possible game states.
 */
enum class Status { RUN, WIN, LOSE }


/**
 * Class that defines the whole game.
 * @property snake object with movement in the game.
 * @property wall obstacles that stop movement of the [snake].
 */
//data class Game(val snake: Snake, val wall: List<Position>)
data class Game(val snake: Snake, val wall: List<Position>, val apple: Position?, val score: Int, val status: Status)