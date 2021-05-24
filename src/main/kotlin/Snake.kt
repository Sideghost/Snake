import pt.isel.canvas.*


data class Snake(val HeadPos: Position, val TailPos: Position, val motion: Direction)


fun Canvas.drawSnake(s: Snake) {
    val hx = s.HeadPos.x * CELL_SIDE - s.motion.dx()
    val hy = s.HeadPos.y * CELL_SIDE - s.motion.dy()
    val tx = s.TailPos.x * CELL_SIDE - s.motion.dx()
    val ty = s.TailPos.y * CELL_SIDE - s.motion.dy()
    val sxImg = SPRITE_DIV * when (s.motion) {
        Direction.LEFT, Direction.UP -> 3
        Direction.RIGHT, Direction.DOWN -> 4
    }

    val hyImg = SPRITE_DIV * when (s.motion) {
        Direction.LEFT, Direction.DOWN -> 1
        Direction.UP, Direction.RIGHT -> 0
    }

    val tyImg = SPRITE_DIV * when (s.motion) {
        Direction.LEFT, Direction.DOWN -> 3
        Direction.UP, Direction.RIGHT -> 2
    }
    drawImage("snake.png|$sxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV", hx, hy, CELL_SIDE, CELL_SIDE)
    drawImage("snake.png|$sxImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV", tx, ty, CELL_SIDE, CELL_SIDE)
}


fun Snake.headToPosition (key: Int) = HeadPos + directionOf(key, this)


fun hasCollision (pos: Position, wall: List<Position>) = wall.any{ it == pos}


fun snakeDirection(key: Int, g: Game): Snake {
    val headToPosition = g.snake.headToPosition(key)

    return if (g.wall.any { it == headToPosition } || headToPosition == g.snake.TailPos) g.snake
    else Snake(g.snake.HeadPos, g.snake.TailPos, directionOf(key, g.snake))
}


fun move(key: Int, g: Game): Game {
    val headToPosition = g.snake.headToPosition(key)
    val toPos =
        when {
            headToPosition.x < 0                -> Position(GRID_WIDTH - 1, headToPosition.y)
            headToPosition.x > GRID_WIDTH - 1   -> Position(0, headToPosition.y)
            headToPosition.y < 0                -> Position(headToPosition.x, GRID_HEIGHT - 1)
            headToPosition.y > GRID_HEIGHT - 1  -> Position(headToPosition.x, 0)
            else -> headToPosition
        }
    return if (hasCollision(toPos, g.wall)) g
    else Game(Snake(toPos, g.snake.HeadPos, g.snake.motion), g.wall)
}
