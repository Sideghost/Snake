fun initApple(): Position =
    (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocks()).shuffled().map { Position(it.x, it.y) }
        .random()


fun Game.createRandomApple(): Position? {
    return when {
        (apple == null && ALL_POSITIONS.isNotEmpty()) -> (ALL_POSITIONS - snake.body - wall).shuffled()
            .map { Position(it.x, it.y) }.random()
        (apple == null && ALL_POSITIONS.isEmpty()) -> null
        else -> apple
    }
}

fun Game.appleGetsEaten() =
    if (snake.body[0] == apple)
        Game(Snake(snake.body,snake.direction,snake.run,snake.toGrow + 5),wall,null, score + 1,status)
    else this

