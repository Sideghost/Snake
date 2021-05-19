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
    // tail e sempre mais 2 que head
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

fun snakeMove(s: Snake, x:Int): Snake {
    if (s.run) {
        return when (s.motion) {
            Direction.RIGHT -> Snake(Position(s.TailPos.x + 1, s.HeadPos.y), Position(s.TailPos.x, s.TailPos.y), s.motion, s.run)

            Direction.LEFT -> Snake(Position(s.TailPos.x - 1, s.HeadPos.y), Position(s.TailPos.x, s.TailPos.y), s.motion, s.run)

            Direction.UP -> Snake(Position(s.HeadPos.x, s.TailPos.y - 1), Position(s.TailPos.x, s.TailPos.y), s.motion, s.run)

            Direction.DOWN -> Snake(Position(s.HeadPos.x, s.TailPos.y + 1), Position(s.TailPos.x, s.TailPos.y), s.motion, s.run)
        }
    } else return s
}