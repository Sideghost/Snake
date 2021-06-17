import pt.isel.canvas.*


/**
 * Class that defines all the important proprieties of a Snake.
 * @property HeadPos Position(x,y) of the head in the Snake.
 * @property TailPos Position(x,y) of the tail in the Snake.
 * @property direction Direction that the snake faces in the game.
 */
//data class Snake(val HeadPos: Position, val TailPos: Position, val direction: Direction)
data class Snake(val body: List<Position>, val direction: Direction, val run: Boolean = true, val toGrow: Int)

/**
 * Function that moves the Snake.
 * @param key code of arrow input.
 * @param game current state of the Game to be affected by possible changes.
 * @return updated Game pass by snake.
 */
fun move(key: Int, game: Game): Game{
    //if(game.status != Status.RUN) return game
    val cervicalC1 = game.snake.body[0]
    val headToPosition = game.snake.headToPosition(key)
    val toPos = emptyList<Position>() +
            when {
                headToPosition.x < 0 -> Position(GRID_WIDTH - 1, headToPosition.y)
                headToPosition.x > GRID_WIDTH - 1 -> Position(0, headToPosition.y)
                headToPosition.y < 0 -> Position(headToPosition.x, GRID_HEIGHT - 1)
                headToPosition.y > GRID_HEIGHT - 1 -> Position(headToPosition.x, 0)
                else -> headToPosition
            }
//    if (game.snake.toGrow > 0) {
//
//    }
    return if (hasCollision(toPos[0], game.wall) || game.status != Status.RUN) game
    else {
        Game(
            Snake(toPos + cervicalC1 + game.snake.body.drop(1), game.snake.direction, game.snake.run, game.snake.toGrow),
            game.wall, game.apple, game.score, game.status)
    }
}