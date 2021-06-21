//IMPORT THAT ALLOWS TO HAVE SOUNDS ON THE GAME
import pt.isel.canvas.playSound

/**
 * Function that chooses randomly an initial apple position for lv.1.
 * @return Position of the initial apple.
 */
fun initApple() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocks()).random()


/**
 * Function that chooses randomly an initial apple position for lv.2.
 * @return Position of the initial apple.
 */
fun initAppleTwo() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocksTwo()).random()


/**
 * Function that chooses randomly an initial apple position for lv.3.
 * @return Position of the initial apple.
 */
fun initAppleThree() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocksThree()).random()


/**
 * Function that checks if exists a golden apple and if its possible to draw it.
 * @receiver Game positions.
 * @return a random position when exists any if not returns nothing.
 */
fun Game.createRandomGoldenApple(h: Hack) = when {
    (h.golden == null && ALL_POSITIONS.isNotEmpty() && h.level == 2) -> (ALL_POSITIONS - snake.body - wall - apple).random()
    (h.golden == null && ALL_POSITIONS.isEmpty() && h.level == 2) -> null
    (h.golden != null && ALL_POSITIONS.isNotEmpty() || h.golden != null && ALL_POSITIONS.isEmpty() && h.level == 2) -> h.golden
    else -> null
}


/**
 * Function that checks if exists a poisonous apple and if its possible to draw it.
 * @receiver Game positions.
 * @return a random position when exists any if not returns nothing.
 */
fun Game.createRandomPoisonApple(h: Hack) = when {
    (h.poison == null && ALL_POSITIONS.isNotEmpty() && h.level == 3) -> (ALL_POSITIONS - snake.body - wall - apple).random()
    (h.poison == null && ALL_POSITIONS.isEmpty() && h.level == 3) -> null
    (h.poison != null && ALL_POSITIONS.isNotEmpty() || h.golden != null && ALL_POSITIONS.isEmpty() && h.level == 3) -> h.poison
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
        if (hacking.sound) playSound("eat.wav")
        this.copy(snake = this.snake.copy(toGrow = snake.toGrow + 5), apple = null, score = score + 1)
    } else this


/**
 * Function that verify if a poisonous apple gets eaten and all stats related to that.
 * @receiver Game proprieties.
 * @return a new game, if the apple gets eaten makes a characteristic Sound.
 */
fun Game.poisonAppleGetsEaten() =
    if (snake.body[0] == hacking.poison) {
        if (hacking.sound) playSound("poison_eat.wav")
        if (snake.body.size >= 5)
            this.copy(
                snake = this.snake.copy(body = snake.body.dropLast(3)),
                score = score - 2,
                hacking = Hack(null, hacking.golden, hacking.grid, hacking.sound, hacking.level)
            )
        else this.copy(
            score = score - 2,
            hacking = Hack(null, hacking.golden, hacking.grid, hacking.sound, hacking.level)
        )

    } else this


/**
 * Function that verify if a golden apple gets eaten and all stats related to that.
 * @receiver Game proprieties.
 * @return a new game, if the apple gets eaten makes a characteristic Sound.
 */
fun Game.goldenAppleGetsEaten() =
    if (snake.body[0] == hacking.golden) {
        if (hacking.sound) playSound("eat.wav")
        this.copy(
            snake = this.snake.copy(toGrow = snake.toGrow + 10),
            score = score + 2,
            hacking = Hack(hacking.poison, null, hacking.grid, hacking.sound, hacking.level)
        )
    } else this
