//IMPORT FROM THE CANVAS LIB
import pt.isel.canvas.*

const val INIT_SCORE = 0
const val INIT_TO_GROW = 0

/**
 * Main function responsible for the game implementation.
 */
fun main() {
    onStart {
        val cv = Canvas(CELL_SIDE * GRID_WIDTH, CELL_SIDE * GRID_HEIGHT + STATUS_BAR, BLACK)
        var game = Game(Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW),
            initBlocks(), initApple(initBlocks()), INIT_SCORE, Status.RUN, Hack())

        cv.drawGame(game)
        loadSounds("eat.wav", "Win.wav", "Defeat.wav", "poison_eat.wav", "Victory.wav")

        cv.onKeyPressed {
            game = if (!game.hacking.menu) game.copy(snake = snakeDirection(it.code, game))
            else game
            game = options(it.code, game)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER) {
            game = if (game.status == Status.RUN)
                game.copy(wall = game.wall + createRandomBrick(game))
            else game
        }

        cv.onTimeProgress(QUART_OF_A_SEC) {
            game = if(game.status == Status.RUN) move(it.toInt(), game).isPossible()
            else game
            game = game.copy(apple = game.createRandomApple(), hacking = game.hacking.copy(
                poison =game.createRandomHackedApple(game.hacking.poison,LEVEL_THREE),
                golden =game.createRandomHackedApple(game.hacking.golden,LEVEL_TWO)))
            cv.drawGame(game)
        }
    }
    onFinish {
        println("FINALLY DONE!!! MINHA NOSSA...")
    }
}
