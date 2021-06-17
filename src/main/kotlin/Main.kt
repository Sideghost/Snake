import pt.isel.canvas.*

const val INIT_SCORE = 0
const val INIT_TOGROW = 0

/**
 * Main function responsible for the game implementation.
 */
fun main() {
    onStart {
        val cv = Canvas(CELL_SIDE * GRID_WIDTH, CELL_SIDE * GRID_HEIGHT + STATUS_BAR, BLACK)
        var game = Game(Snake(listOf(Position(GRID_WIDTH / 2, GRID_HEIGHT / 2)), Direction.RIGHT, toGrow = INIT_TOGROW), initBlocks(), initApple(), INIT_SCORE, Status.RUN)

        cv.drawGame(game)
        cv.onKeyPressed { ke: KeyEvent ->
            game = Game(snakeDirection(ke.code, game), game.wall, game.apple, game.score, game.status)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER) {
            game = Game(game.snake, game.wall + createRandomBrick(game), game.apple, game.score, game.status)
        }

        cv.onTimeProgress(QUART_OF_A_SEC) {
            game = move(it.toInt(), game)
            game = game.appleGetsEaten()
            game = Game(game.snake, game.wall, game.createRandomApple(), game.score, game.status)
            cv.drawGame(game)
            println(game.snake.body)
        }

    }
    onFinish {
        println("WORK IN PROGRESS...")
        //println("FINALLY DONE!!!")
    }
}
