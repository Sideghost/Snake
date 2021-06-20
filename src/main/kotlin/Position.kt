
/**
 * Class that is used to defines all the relative positions.
 * @param x Coordinate in the x axis.
 * @param y Coordinate in the y axis.
 */
data class Position(val x: Int, val y: Int)


/**
 * Operator function that allows to add a position to a direction.
 * @param direction Direction to be added to a position.
 * @return a new Position.
 */
operator fun Position.plus(direction: Direction) = Position(x + direction.dx(), y + direction.dy())


/**
 * Util Function that verifies if a position is already taken.
 * @param position Position to be verified.
 * @param wall List of all position already taken.
 * @return a true or false answer.
 */
fun hasCollision(position: Position, wall: List<Position>, snake: List<Position>) =
    wall.any { it == position } || snake.any { it == position }


/**
 * Util function that given a code form the arrow adds it to the Head Position.
 * @param key code of arrow input.
 * @return a new Position.
 */
fun Snake.headToPosition(key: Int) = body[0] + directionOf(key, this)


/**
 * Function that turns an illegal position into a legal one.
 * @receiver Position to be normalized.
 * @return Normalized position.
 */
fun Position.normalize() = when {
        x < 0 -> Position(GRID_WIDTH - 1, y)
        x > GRID_WIDTH - 1 -> Position(0, y)
        y < 0 -> Position(x, GRID_HEIGHT - 1)
        y > GRID_HEIGHT - 1 -> Position(x, 0)
        else -> this
    }


// Verifies all possible position inside of the arena
val ALL_POSITIONS: List<Position> =
    (0 until GRID_HEIGHT * GRID_WIDTH).map { Position(it % GRID_WIDTH, it / GRID_WIDTH) }


