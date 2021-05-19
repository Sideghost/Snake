import pt.isel.canvas.*

data class Snake(val HeadPos: Position, val TailPos: Position, val motion: Direction, val run: Boolean)

operator fun Position.plus( dir:Direction ) = Position( x + dir.dx(), y + dir.dy() )

fun Position.itsValid(xSize:Int, ySize:Int) = x in 0 until xSize && y in 0 until ySize

fun Canvas.drawSnake(s:Snake) {
    val hx = s.HeadPos.x * CELL_SIDE -  s.motion.dx()
    val hy = s.HeadPos.y * CELL_SIDE -  s.motion.dy()
    val tx = s.TailPos.x * CELL_SIDE -  s.motion.dx()
    val ty = s.TailPos.y * CELL_SIDE -  s.motion.dy()
    val sxImg = SPRITE_DIV * when(s.motion) {
        Direction.LEFT ,Direction.UP-> 3
        Direction.RIGHT, Direction.DOWN-> 4
    }

    // tail e sempre mais 2 que head
    val hyImg = SPRITE_DIV * when(s.motion) {
        Direction.LEFT, Direction.DOWN -> 1
        Direction.UP, Direction.RIGHT -> 0
    }
    val tyImg = SPRITE_DIV * when(s.motion) {
        Direction.LEFT, Direction.DOWN -> 3
        Direction.UP, Direction.RIGHT -> 2
    }
    drawImage("snake.png|$sxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV",hx,hy,CELL_SIDE,CELL_SIDE)
    drawImage("snake.png|$sxImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV",tx,ty,CELL_SIDE,CELL_SIDE)
}

