import pt.isel.canvas.*

const val INIT_SIZE = 5
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
    //para a snake andar : a posicao da cabeca e afetada pela direcao ; e acrescentada uma vertebra ao 2 elemtento da lista
    // a cauda sai da lista ; a cauda e redesenhada na ultima posicao da lista
    //if(game.status != Status.RUN) return game
    val headToPosition = game.snake.headToPosition(key).normalize()

    val newSnake = if (game.snake.body.size >= INIT_SIZE ) game.snake.body.dropLast(1) else game.snake.body
//    if (game.snake.toGrow > 0) {
//
//    }
    return if (hasCollision(headToPosition, game.wall, game.snake.body) || game.status != Status.RUN) game
    else {
        Game(
            Snake((emptyList<Position>() + headToPosition) + newSnake, game.snake.direction, game.snake.run, game.snake.toGrow),
            game.wall, game.apple, game.score, game.status)
    }
}