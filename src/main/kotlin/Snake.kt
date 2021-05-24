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


fun snakeDirection(key: Int, s: Snake): Snake {
    return if (key in listOf(LEFT_CODE, RIGHT_CODE, DOWN_CODE, UP_CODE))
        Snake(Position(s.HeadPos.x, s.HeadPos.y), Position(s.HeadPos.x, s.HeadPos.y), directionOf(key, s), s.run)
    else s
}


fun snakeMove(key: Int, s: Snake): Snake {
    val headToPosition = s.HeadPos + directionOf(key, s)
    val tailPosition = s.HeadPos

    return if (s.run){ Snake(
        when {
            headToPosition.x < 0                -> Position(GRID_WIDTH - 1, headToPosition.y)
            headToPosition.x > GRID_WIDTH - 1   -> Position(0, headToPosition.y)
            headToPosition.y < 0                -> Position(headToPosition.x, GRID_HEIGHT - 1)
            headToPosition.y > GRID_HEIGHT - 1  -> Position(headToPosition.x, 0)
            else -> headToPosition
        }, tailPosition, s.motion, s.run)
    } else s
}