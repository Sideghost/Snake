import pt.isel.canvas.*


data class Position(val x:Int,val y:Int)


enum class Direction(val dx:Int, val dy:Int) {
    LEFT(-1,0),
    UP(0,-1),
    RIGHT(+1,0),
    DOWN(0,+1)
}


operator fun Position.plus( dir:Direction ) = Position( x + dir.dx(), y + dir.dy() )


fun Position.itsValid(xSize:Int, ySize:Int) = x in 0 until xSize && y in 0 until ySize


fun Direction.dx() = this.dx


fun Direction.dy() = this.dy


fun directionOf( key:Int, snake: Snake ) :Direction = when (key) {
    LEFT_CODE   -> Direction.LEFT
    RIGHT_CODE  -> Direction.RIGHT
    UP_CODE     -> Direction.UP
    DOWN_CODE   -> Direction.DOWN
    else        -> snake.motion
}


fun Canvas.drawBrick(p :Position) {
    drawImage("bricks.png",p.x * CELL_SIDE,p.y * CELL_SIDE,CELL_SIDE,CELL_SIDE)
}


val ALL_POSITIONS :List<Position>  = (0 until GRID_HEIGHT*GRID_WIDTH).map{Position(it%GRID_WIDTH, it/GRID_WIDTH)}