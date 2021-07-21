//IMPORT THAT ALLOWS TO HAVE SOUNDS ON THE GAME
import pt.isel.canvas.playSound

/**
 * Function that chooses randomly an initial apple position for any level.
 * @return Position of the initial apple.
 */
fun initApple(blocks:List<Position>) = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - blocks).random()


/**
 * Function that checks if exists a Hacked apple and if its possible to draw it.
 * @receiver Game positions.
 * @param hackedApple Hacked apple.
 * @param level current game level.
 * @return a random position when exists any if not returns nothing.
 */
fun Game.createRandomHackedApple(hackedApple:Position?, level:Int) = when {
    hackedApple == null && ALL_POSITIONS.isNotEmpty() && hacking.level == level -> (ALL_POSITIONS - snake.body - wall - apple).random()
    hackedApple == null && ALL_POSITIONS.isEmpty() && hacking.level == level -> null
    hackedApple != null && hacking.level == level -> hackedApple
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
 * Function that verify if a hacked apple gets eaten and all stats related to that.
 * @receiver Game proprieties.
 * @param sound sound that the game makes if enable when apple gets eaten.
 * @param hackedApple position of the hacked apple.
 * @param level current level of the game.
 * @return a new game, if the apple gets eaten makes a characteristic Sound.
 */
fun Game.hackedAppleGetsEaten(sound:String, hackedApple: Position?, level:Int) =
    if (snake.body[0] == hackedApple) {
        if (hacking.sound) playSound(sound)
        when(level){
            2 -> this.copy(snake = this.snake.copy(toGrow = snake.toGrow + 10), score = score + 2,
                hacking = Hack(hacking.poison, null, hacking.grid, hacking.sound, hacking.level))

            3 -> if (snake.body.size >= 5)
                this.copy(snake = this.snake.copy(body = snake.body.dropLast(3)), score = score - 2,
                    hacking = hacking.copy(poison = null))

            else this.copy(score = score - 2, hacking = hacking.copy(poison = null))

            else -> this
        }
    }
    else this
