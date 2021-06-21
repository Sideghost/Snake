

/**
 * Function that takes a random position inside of all unused position.
 * @param game current state of the Game to be affected by possible changes.
 * @return a Position to be given to a Brick.
 */
fun createRandomBrick(game: Game): Position =
    if (game.apple == null)
        (ALL_POSITIONS - game.snake.body - game.wall).random()
    else
        (ALL_POSITIONS - game.snake.body - game.apple - game.wall).random()


/**
 * Function that draws the initial walls.
 * @return List of the initial walls.
 */
fun initBlocks(): List<Position> {
    val topNDownRow = (0 until GRID_WIDTH).filter { it !in 3..16 }
    val sideRow = (1 until GRID_HEIGHT - 1).filter { it !in 4..11 }
    return topNDownRow.map { Position(it, 0) } + topNDownRow.map { Position(it, GRID_HEIGHT - 1)
    } + sideRow.map { Position(0, it) } + sideRow.map { Position(GRID_WIDTH - 1, it) }
}

fun initBlockstwo(): List<Position> {
    val posInit2 = listOf<Position>(
        Position(0,0), Position(1,0), Position(2,0),
        Position(8,0), Position(9,0), Position(10,0), Position(11,0),
        Position(17,0), Position(18,0), Position(19,0),
        Position(0,15), Position(1,15), Position(2,15),
        Position(8,15), Position(9,15), Position(10,15), Position(11,15),
        Position(0,1), Position(0,2),
        Position(0,7), Position(0,8),
        Position(0,13), Position(0,14),
        Position(19,1), Position(19,2),
        Position(19,7), Position(19,8),
        Position(19,13), Position(19,14),
        Position(3,3), Position(4, 3), Position(3,4), Position(4,4),
        Position(15,3), Position(16,3), Position(15,4), Position(16,4),
        Position(9,4), Position(10,4), Position(9,5), Position(10,5),
        Position(6,7), Position(7,7), Position(6,8), Position(7,8),
        Position(12,7), Position(13,7), Position(12,8), Position(13,8),
        Position(3,11), Position(4,11), Position(3,12), Position(3,12),
        Position(9,10), Position(10,10), Position(9,11), Position(10,11),
        Position(15,11), Position(16,11), Position(15,12), Position(16,12)
    )
    return posInit2
}
