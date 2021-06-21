//IMPORT THAT ALLOWS TO HAVE SOUNDS ON THE GAME
import pt.isel.canvas.playSound

/**
 * Function that chooses randomly an initial apple position.
 * @return Position of the initial apple.
 */
fun initApple() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocks()).random()

fun initAppleThree() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocksThree()).random()

fun initAppleTwo() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocksTwo()).random()


// nivel 2 maçã dourada // nivel 3 a venenoza

fun Game.createRandomGoldenApple(h: Hack) = when {
    (h.golden == null && ALL_POSITIONS.isNotEmpty() && level.level == 2) -> (ALL_POSITIONS - snake.body - wall - apple).random()
    (h.golden == null && ALL_POSITIONS.isEmpty() && level.level == 2) -> null
    (h.golden != null && ALL_POSITIONS.isNotEmpty() || h.golden != null && ALL_POSITIONS.isEmpty() && level.level == 2) -> h.golden
    else -> null
}

fun Game.createRandomPoisonApple(h: Hack) = when {
    (h.poison == null && ALL_POSITIONS.isNotEmpty() && level.level == 3) -> (ALL_POSITIONS - snake.body - wall - apple).random()
    (h.poison == null && ALL_POSITIONS.isEmpty() && level.level == 3) -> null
    (h.poison != null && ALL_POSITIONS.isNotEmpty() || h.golden != null && ALL_POSITIONS.isEmpty() && level.level == 3) -> h.poison
    else -> null
}


/**
 * Function that checks if exists an apple and if its possible to draw it.
 * @receiver Game positions.
 * @return a random position when exists any if not returns nothing.
 */
fun Game.createRandomApple() = when {
    (apple == null && ALL_POSITIONS.isNotEmpty()) -> (ALL_POSITIONS - snake.body - wall - hacking.golden - hacking.poison).random()
    (apple == null && ALL_POSITIONS.isEmpty()) -> null
    else -> apple
}

/**
 * Function that verify if an apple gets eaten and all stats related to that.
 * @receiver Game proprieties.
 * @return a new game, if the apple gets eaten makes a characteristic Sound.
 */
fun Game.appleGetsEaten() =
    if (snake.body[0] == apple) {
        playSound("eat.wav")
        this.copy(snake = this.snake.copy(toGrow = snake.toGrow + 5), apple = null, score = score + 1)
    } else this


fun Game.poisonAppleGetsEaten() =
    if (snake.body[0] == hacking.poison) {
        playSound("eat.wav")
        this.copy(
            snake = this.snake.copy(body = snake.body.dropLast(3)),
            score = score - 2,
            hacking = Hack(null, hacking.golden)
        )
    } else this


fun Game.goldenAppleGetsEaten() =
    if (snake.body[0] == hacking.golden) {
        playSound("eat.wav")
        this.copy(
            snake = this.snake.copy(toGrow = snake.toGrow + 10),
            score = score + 2,
            hacking = Hack(hacking.poison, null)
        )
    } else this
