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
    //if(game.status != Status.RUN) return game
    //TODO("Still misses the lose feature")
    val headToPosition = listOf(game.snake.headToPosition(key).normalize())
    //val lose = headToPosition.lose(game)
    val newSnake =
        if (game.snake.body.size < INIT_SIZE || game.snake.toGrow > 0) game.snake.body else game.snake.body.dropLast(1)

    return if (hasCollision(headToPosition[0], game.wall, game.snake.body) || game.status != Status.RUN) game
    else Game(Snake(headToPosition + newSnake, game.snake.direction,
            if (game.snake.toGrow > 0) game.snake.toGrow - 1 else game.snake.toGrow),
        game.wall, game.apple, game.score, game.status).appleGetsEaten()
}

fun List<Position>.lose(game: Game): Game{
    return if(this[0] in game.snake.body + game.wall) game.copy(status = Status.LOSE)
        else game

}