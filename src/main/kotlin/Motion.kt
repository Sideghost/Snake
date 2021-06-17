data class Motion(val dx:Int, val dy:Int)

operator fun Position.minus(position:Position) = Motion(x - position.x,y - position.y)


fun sign(value :Int) = when {
    value == 0 -> 0
    value<0 -> -1
    else    -> +1
}


fun Motion.toDirection() :Direction {
    val dxu = sign(dx)
    val dyu = sign(dy)
    return Direction.values().first { it.dx==dxu && it.dy==dyu }
}