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
fun hasCollision(position: Position, wall: List<Position>) = wall.any { it == position }


/**
 * Util function that given a code form the arrow adds it to the Head Position.
 * @param key code of arrow input.
 * @return a new Position.
 */
fun Snake.headToPosition(key: Int) = body[0] + directionOf(key, this)


// Verifies all possible position inside of the arena
val ALL_POSITIONS: List<Position> =
    (0 until GRID_HEIGHT * GRID_WIDTH).map { Position(it % GRID_WIDTH, it / GRID_WIDTH) }


/**
 * Function that takes a random position inside of all unused position.
 * @param game current state of the Game to be affected by possible changes.
 * @return a list of a single Position to be given to a Brick.
 */
fun createRandomBrick(game: Game): List<Position> =
    if (game.apple == null)
        (ALL_POSITIONS - game.snake.body - game.wall).shuffled().take(1).map { Position(it.x, it.y) }
    else
        (ALL_POSITIONS - game.snake.body - game.apple - game.wall).shuffled().take(1).map { Position(it.x, it.y) }

fun createRandomApple(game: Game): Position =
    (ALL_POSITIONS - game.snake.body - game.wall).shuffled().map { Position(it.x, it.y) }.random()

fun initBlocks(): List<Position> {
    val posInit = listOf<Position>(Position(0, 0), Position(1, 0), Position(2, 0),
        Position(17, 0), Position(18, 0), Position(19, 0),
        Position(0, 15), Position(1, 15), Position(2, 15),
        Position(17, 15), Position(18, 15), Position(19, 15),
        Position(0, 1), Position(0, 2), Position(0, 3),
        Position(19, 1), Position(19, 2), Position(19, 3),
        Position(0, 12), Position(0, 13), Position(0, 14),
        Position(19, 12), Position(19, 13), Position(19, 14))

    return posInit
}