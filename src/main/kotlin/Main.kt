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
        var game = Game(
            Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, INIT_TO_GROW),
            initBlocks(), initApple(), INIT_SCORE, Status.RUN, Leevel(1), Hack(null, null)
        )

        cv.drawGame(game)
        loadSounds("eat.wav", "Win.wav", "Defeat.wav")

        cv.onKeyPressed {
            game = game.copy(snake = snakeDirection(it.code, game))
            game = nextLv(it.code, game)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER) {
            game = if (game.status == Status.RUN)
                game.copy(wall = game.wall + createRandomBrick(game))
            else game
        }

        cv.onTimeProgress(QUART_OF_A_SEC) {
            game = move(it.toInt(), game).isPossible()
            game = game.copy(
                apple = game.createRandomApple(),
                hacking = Hack(game.createRandomPoisonApple(game.hacking), game.createRandomGoldenApple(game.hacking))
            )
            cv.drawGame(game)
        }

    }
    onFinish {
        println("FINALLY DONE!!! MINHA NOSSA...")
    }
}
