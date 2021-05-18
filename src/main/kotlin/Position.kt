import pt.isel.canvas.*

data class Position(val x:Int,val y:Int)

enum class Direction(val dx:Int, val dy:Int) {
    LEFT(-1,0), UP(0,-1), RIGHT(1,0), DOWN(0,1)
}

fun Direction?.dx() = this?.dx ?: 0

fun Direction?.dy() = this?.dy ?: 0

fun directionOf( key:Int ) :Direction? = when (key) {
    LEFT_CODE -> Direction.LEFT
    RIGHT_CODE -> Direction.RIGHT
    UP_CODE -> Direction.UP
    DOWN_CODE -> Direction.DOWN
    else -> null
}

val ALL_POSITIONS :List<Position>  = (0 until GRID_HEIGHT*GRID_WIDTH).map { Position( it%GRID_WIDTH, it/GRID_WIDTH ) }