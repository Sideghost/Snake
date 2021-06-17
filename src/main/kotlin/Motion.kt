data class Motion(val dx:Int, val dy:Int)

operator fun Position.minus(position:Position) = Motion(x - position.x,y - position.y)

