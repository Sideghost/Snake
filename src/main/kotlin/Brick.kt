

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
