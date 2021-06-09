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
 * Function that draws the Snake in the canvas using a Sprite.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
fun Canvas.drawSnake(snake: Snake, pngFile: String) {
    val hx = snake.body[0].x * CELL_SIDE - snake.direction.dx()
    val hy = snake.body[0].y * CELL_SIDE - snake.direction.dy()
    val tx = snake.body.last().x * CELL_SIDE - snake.direction.dx()
    val ty = snake.body.last().y * CELL_SIDE - snake.direction.dy()
    val sxImg = SPRITE_DIV * when (snake.direction) {
        Direction.LEFT, Direction.UP -> 3
        Direction.RIGHT, Direction.DOWN -> 4
    }

    val hyImg = SPRITE_DIV * when (snake.direction) {
        Direction.LEFT, Direction.DOWN -> 1
        Direction.UP, Direction.RIGHT -> 0
    }

    val tyImg = SPRITE_DIV * when (snake.direction) {
        Direction.LEFT, Direction.DOWN -> 3
        Direction.UP, Direction.RIGHT -> 2
    }
    drawImage("$pngFile|$sxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV", hx, hy, CELL_SIDE, CELL_SIDE)
    drawImage("$pngFile|$sxImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV", tx, ty, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that moves the Snake.
 * @param key code of arrow input.
 * @param game current state of the Game to be affected by possible changes.
 * @return updated Game pass by snake.
 */
fun move(key: Int, game: Game): Game {
    val headToPosition = game.snake.headToPosition(key)
    val toPos =
        when {
            headToPosition.x < 0 -> Position(GRID_WIDTH - 1, headToPosition.y)
            headToPosition.x > GRID_WIDTH - 1 -> Position(0, headToPosition.y)
            headToPosition.y < 0 -> Position(headToPosition.x, GRID_HEIGHT - 1)
            headToPosition.y > GRID_HEIGHT - 1 -> Position(headToPosition.x, 0)
            else -> headToPosition
        }
    val teste = emptyList<Position>() + toPos
    return if (hasCollision(toPos, game.wall)) game
    else {
        game.snake.body - game.snake.body[0]
        Game(
            Snake(teste + game.snake.body, game.snake.direction, game.snake.run, game.snake.toGrow),
            game.wall,
            game.apple,
            game.score
        )
    }
}
