/**
 * Class that defines all the important proprieties of a Snake.
 * @property body List of positions(x,y) of the Snake.
 * @property direction Direction that the snake faces in the game.
 * @property toGrow number of pieces that the snake has to grow.
 */
data class Snake(val body: List<Position>, val direction: Direction, val toGrow: Int)

/**
 * Function that moves the Snake.
 * @param key code of arrow input.
 * @param game current state of the Game to be affected by possible changes.
 * @return updated Game pass by snake.
 */
fun move(key: Int, game: Game): Game {
    //para a snake andar : a posicao da cabeca e afetada pela direcao e é acrescentado um elem ao inicio e retirado um ao final.
    //if(game.status != Status.RUN) return game
    //TODO("Still misses")
    val headToPosition = listOf(game.snake.headToPosition(key).normalize())

    val newSnake =
        if (game.snake.body.size < INIT_SIZE || game.snake.toGrow > 0) game.snake.body else game.snake.body.dropLast(1)

    return if (hasCollision(headToPosition[0], game.wall, game.snake.body) || game.status != Status.RUN) game
    else Game(
        Snake(
            headToPosition + newSnake, game.snake.direction,
            if (game.appleGetsEaten().apple == null) game.snake.toGrow - 1 else game.snake.toGrow
        ),
        game.wall, game.apple, game.score, game.status
    )
}