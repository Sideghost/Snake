import pt.isel.canvas.*


data class Snake(val HeadPos: Position, val TailPos: Position, val motion: Direction, val run: Boolean)


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


fun snakeDirection(key: Int, g: Game): Snake {
    val headToPosition = g.snake.HeadPos + directionOf(key, g.snake)
    val tailPosition = g.snake.TailPos
    return if (key in listOf(LEFT_CODE, RIGHT_CODE, DOWN_CODE, UP_CODE) && headToPosition != tailPosition)
        Snake(
            Position(g.snake.HeadPos.x, g.snake.HeadPos.y),
            Position(g.snake.HeadPos.x, g.snake.HeadPos.y),
            directionOf(key, g.snake),
            g.snake.run
        )
    else g.snake
}


fun move(key: Int, g: Game): Game {
    val headToPosition = g.snake.HeadPos + directionOf(key, g.snake)
    val tailToPosition = g.snake.HeadPos
    if (g.snake.run) {
        val topos =
            when {
                headToPosition.x < 0 -> Position(GRID_WIDTH - 1, headToPosition.y)
                headToPosition.x > GRID_WIDTH - 1 -> Position(0, headToPosition.y)
                headToPosition.y < 0 -> Position(headToPosition.x, GRID_HEIGHT - 1)
                headToPosition.y > GRID_HEIGHT - 1 -> Position(headToPosition.x, 0)
                else -> headToPosition
            }
        if (g.wall.any { it == topos }) {
            println("colide"); return g}
            else return Game(Snake(topos,tailToPosition,g.snake.motion,g.snake.run),g.wall)
    } else return g
}