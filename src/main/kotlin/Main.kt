import pt.isel.canvas.*


/**
 * Main function responsible for the game implementation.
 */
fun main() {
    onStart {
        val cv = Canvas(CELL_SIDE * GRID_WIDTH, CELL_SIDE * GRID_HEIGHT, BLACK)
        var game = Game(
            Snake(Position(1, GRID_HEIGHT/2), Position(0, GRID_HEIGHT/2), Direction.RIGHT),
            emptyList()
        )

        cv.drawGame(game)
        cv.onKeyPressed { ke : KeyEvent ->
            game = Game(snakeDirection(ke.code, game), game.wall)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER){
            game = Game(game.snake,game.wall + createRandomBrick(game))
        }

        cv.onTimeProgress(QUART_OF_A_SEC){
            game = move(it.toInt() ,game)
            cv.drawGame(game)
        }

    }
    onFinish {
        println("2 done 1 to go!")
    }
}
