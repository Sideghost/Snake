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
    val dir = directionOf(key, s)
    return when (key) {
        LEFT_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y), Position(s.HeadPos.x, s.HeadPos.y), dir, s.run)
        RIGHT_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y), Position(s.HeadPos.x, s.HeadPos.y), dir, s.run)
        DOWN_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y), Position(s.HeadPos.x, s.HeadPos.y), dir, s.run)
        UP_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y), Position(s.HeadPos.x, s.HeadPos.y), dir, s.run)
        else -> s
    }
}


fun snakeMove(key: Int, s: Snake): Snake {
    val dir = directionOf(key, s)
    val headToPosition = s.HeadPos + dir
    val tailPosition = s.HeadPos

    return if (s.run) {
        when {
            headToPosition.x < 0 -> Snake(Position(GRID_WIDTH - 1, headToPosition.y), tailPosition, s.motion, s.run)
            headToPosition.x > GRID_WIDTH - 1 -> Snake(Position(0, headToPosition.y), tailPosition, s.motion, s.run)
            headToPosition.y < 0 -> Snake(Position(headToPosition.x, GRID_HEIGHT - 1), tailPosition, s.motion, s.run)
            headToPosition.y > GRID_HEIGHT - 1 -> Snake(Position(headToPosition.x, 0), tailPosition, s.motion, s.run)
            else -> Snake(headToPosition, tailPosition, s.motion, s.run)
        }
    }
    else s
}