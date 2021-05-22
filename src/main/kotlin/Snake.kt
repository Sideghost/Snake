import pt.isel.canvas.*

data class Snake(val HeadPos: Position, val TailPos: Position, val motion: Direction, val run: Boolean)


fun Canvas.drawSnake(s: Snake) {
    val hx = s.HeadPos.x * CELL_SIDE - s.motion.dx()
    val hy = s.HeadPos.y * CELL_SIDE - s.motion.dy()
    val tx = s.TailPos.x * CELL_SIDE - s.motion.dx()
    val ty = s.TailPos.y * CELL_SIDE - s.motion.dy()
    val hxImg = SPRITE_DIV * when (s.motion) {
        Direction.LEFT, Direction.UP -> 3
        Direction.RIGHT, Direction.DOWN -> 4
    }

    val txImg = SPRITE_DIV * when (s.motion) {
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
    drawImage("snake.png|$hxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV", hx, hy, CELL_SIDE, CELL_SIDE)
    drawImage("snake.png|$txImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV", tx, ty, CELL_SIDE, CELL_SIDE)
}

fun snakeDirection(key:Int ,s: Snake): Snake {
    val dir = directionOf(key, s)
        return when (key){
        LEFT_CODE -> Snake(Position(s.HeadPos.x , s.HeadPos.y),Position(s.HeadPos.x, s.HeadPos.y),dir,s.run)
        RIGHT_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y),Position(s.HeadPos.x, s.HeadPos.y),dir,s.run)
        DOWN_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y ),Position(s.HeadPos.x, s.HeadPos.y),dir,s.run)
        UP_CODE -> Snake(Position(s.HeadPos.x, s.HeadPos.y),Position(s.HeadPos.x, s.HeadPos.y),dir,s.run)
        else -> s
    }
}

//a função de move faz com que a cobrinha se mova imediatamente assim que se muda a direção,
// logo para ela nao saltar um quadrado temos de compensar no modo como ela se comporta na de cima
// nao perguntes o pq de nao se alterar nada em cima que eu n quero pensar agora nisto ja me esta a dar demasiada dor
// de cabeça como ja esta n quero mais!!
// NOTA ADICIONAL: PENSO QUE TEM MESMO DE SER ASSIM!!
fun snakeMove(key:Int ,s: Snake): Snake {
    val dir = directionOf(key, s)
    val snakePosition = s.HeadPos + dir
    val tailPosition = s.HeadPos
    return if (snakePosition.itsValid(GRID_WIDTH,GRID_HEIGHT) && s.run) Snake(snakePosition, tailPosition, s.motion, s.run)
    else s
}