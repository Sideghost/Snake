
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


//HardCoded
/**
 * Function that draws the initial walls.
 * @return List of the initial walls.
 */
fun initBlocks(): List<Position> = listOf(
        Position(0, 0), Position(1, 0), Position(2, 0),
        Position(17, 0), Position(18, 0), Position(19, 0),
        Position(0, 15), Position(1, 15), Position(2, 15),
        Position(17, 15), Position(18, 15), Position(19, 15),
        Position(0, 1), Position(0, 2), Position(0, 3),
        Position(19, 1), Position(19, 2), Position(19, 3),
        Position(0, 12), Position(0, 13), Position(0, 14),
        Position(19, 12), Position(19, 13), Position(19, 14)
    )

