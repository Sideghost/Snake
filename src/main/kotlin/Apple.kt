

//IMPORT THAT ALLOWS TO HAVE SOUNDS ON THE GAME
import pt.isel.canvas.playSound

/**
 * Function that chooses randomly an initial apple position.
 * @return Position of the initial apple.
 */
fun initApple() = (ALL_POSITIONS - Position(GRID_WIDTH / 2, GRID_HEIGHT / 2) - initBlocks()).random()

/**
 * Function that checks if exists an apple and if its possible to draw it.
 * @receiver Game positions.
 * @return a random position when exists any if not returns nothing.
 */
fun Game.createRandomApple() = when {
    (apple == null && ALL_POSITIONS.isNotEmpty()) -> (ALL_POSITIONS - snake.body - wall).random()
    (apple == null && ALL_POSITIONS.isEmpty()) -> null
    else -> apple
}

/**
 * Function that verify if an apple gets eaten and all stats related to that.
 * @receiver Game proprieties.
 * @return a new game, if the apple gets eaten makes a characteristic Sound.
 */
fun Game.appleGetsEaten() =
    if (snake.body[0] == apple){
        playSound("eat.wav")
        this.copy(snake = this.snake.copy(toGrow = snake.toGrow + 5), apple = null, score = score + 1)
    }
    else this

