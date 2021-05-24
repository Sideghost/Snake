

/**
 * Class that is used to defines all the relative positions.
 * @param x Coordinate in the x axis.
 * @param y Coordinate in the y axis.
 */
data class Position(val x:Int,val y:Int)


/**
 * Operator function that allows to add a position to a direction.
 * @param direction Direction to be added to a position.
 * @return a new Position.
 */
operator fun Position.plus(direction:Direction ) = Position( x + direction.dx(), y + direction.dy() )


/**
 * Util Function that verifies if a position is already taken.
 * @param position Position to be verified.
 * @param wall List of all position already taken.
 * @return a true or false answer.
 */
fun hasCollision (position: Position, wall: List<Position>) = wall.any{ it == position}


/**
 * Util function that given a code form the arrow adds it to the Head Position.
 * @param key code of arrow input.
 * @return a new Position.
 */
fun Snake.headToPosition (key: Int) = HeadPos + directionOf(key, this)


// Verifies all possible position inside of the arena
val ALL_POSITIONS :List<Position>  = (0 until GRID_HEIGHT*GRID_WIDTH).map{Position(it%GRID_WIDTH, it/GRID_WIDTH)}


/**
 * Function that takes a random position inside of all unused position.
 * @param game current state of the Game to be affected by possible changes.
 * @return a list of a single Position to be given to a Brick.
 */
fun createRandomBrick(game: Game) :List<Position> =
    (ALL_POSITIONS - game.snake.HeadPos - game.snake.TailPos - game.wall).shuffled().take(1).map { Position(it.x, it.y) }
