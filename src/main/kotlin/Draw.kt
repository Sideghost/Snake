// Import of Canvas and the hex of the colors
import pt.isel.canvas.*

private const val APPLEXFILE = 0
private const val APPLEYFILE = 3

/**
 * Draws the whole game (Snake, Walls, Apple, Status).
 * @receiver where it will be drawn.
 * @param game collection of the snake, walls and apple
 */
fun Canvas.drawGame(game: Game) {
    erase()
    if (game.hacking.grid){
        drawGrid()
    }
    drawSnake(game.snake, "snake.png")
    game.wall.forEach { drawBrick(it, "bricks.png") }
    drawApple(game.apple, "snake.png")
    drawStatus(game)
    drawAppleGolden(game.hacking.golden, "appleGolden.png")
    drawApplePoison(game.hacking.poison, "applePoison.png")
    if (game.hacking.menu){
        drawMenus()
    }
}


private fun Canvas.drawMenus(){
    drawRect(CELL_SIDE * 3,CELL_SIDE * 4,CELL_SIDE * 14,CELL_SIDE * 8, 404040)
    drawText(CELL_SIDE * 4 - 10,170,"G - Activate Grid", WHITE,20)
    drawText(CELL_SIDE * 4 - 10,230,"N - Next Level", WHITE,20)
    drawText(CELL_SIDE * 4 - 10, 290,"S - Activate Sound", WHITE,20)
    drawText(CELL_SIDE * 4 - 10,350,"J - Skip Level", WHITE,20)
    drawImage("logo.png", CELL_SIDE * 11 - 10, 180, 150, 150)
}



/**
 * Draw the background grid.
 * @receiver where it will be drawn.
 */
private fun Canvas.drawGrid() {
    (CELL_SIDE..height step CELL_SIDE).forEach {
        drawLine(0, it, width, it, WHITE, 1) // horizontal
    }
    (CELL_SIDE..width step CELL_SIDE).forEach {
        drawLine(it, 0, it, height, WHITE, 1) // vertical
    }
}


/**
 * Function that draws the Snake in the canvas using a Sprite.
 * @receiver where it will be drawn.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
private fun Canvas.drawSnake(snake: Snake, pngFile: String) {

    // Head
    drawHead(snake, pngFile)

    //Tail
    if (snake.body.size > 1)
        drawTail(snake, pngFile)
    //Body
    if (snake.body.size > 2)
        drawBody(snake, pngFile)

}


/**
 * Function that draws the Head of the Snake in the canvas using a Sprite.
 * @receiver where it will be drawn.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
private fun Canvas.drawHead(snake: Snake, pngFile: String) {
    val bx = snake.body[0].x * CELL_SIDE - snake.direction.dx()
    val by = snake.body[0].y * CELL_SIDE - snake.direction.dy()
    val (hxImg, hyImg) = when (snake.direction) {
        Direction.LEFT -> 3 to 1
        Direction.RIGHT -> 4 to 0
        Direction.DOWN -> 4 to 1
        Direction.UP -> 3 to 0
    }
    drawImage(
        "$pngFile|${hxImg * SPRITE_DIV},${hyImg * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
        bx, by, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that draws the Tail of the Snake in the canvas using a Sprite.
 * @receiver where it will be drawn.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
private fun Canvas.drawTail(snake: Snake, pngFile: String) {
    val position = if (snake.body.size > 1) snake.body[snake.body.size - 2] else snake.body.last()
    val next = (snake.body.last() - position).toDirection()

    val (txImg, tyImg) = when (next) {
        Direction.UP -> 4 to 3
        Direction.DOWN -> 3 to 2
        Direction.LEFT -> 4 to 2
        Direction.RIGHT -> 3 to 3
    }
    drawImage("$pngFile|${txImg * SPRITE_DIV},${tyImg * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
        snake.body.last().x * CELL_SIDE, snake.body.last().y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that draws the Tail of the Snake in the canvas using a Sprite.
 * @receiver where it will be drawn.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
private fun Canvas.drawBody(snake: Snake, pngFile: String) {
    snake.body.forEachIndexed { idx, body ->
        if (idx != 0 && idx != snake.body.size - 1) {

            val next = (body - snake.body[idx - 1]).toDirection()
            val previous = (snake.body[idx + 1] - body).toDirection()

            val (bxImg, byImg) = when {
                next.dx == -1 && previous.dy == -1 || next.dy == +1 && previous.dx == +1 -> 0 to 1 //LOWER_LEFT_CORNER
                next.dx == -1 && previous.dy == +1 || next.dy == -1 && previous.dx == +1 -> 0 to 0 //UPPER_LEFT_CORNER
                next.dy == +1 && previous.dx == -1 || next.dx == +1 && previous.dy == -1 -> 2 to 2 //LOWER_RIGHT_CORNER
                next.dy == -1 && previous.dx == -1 || next.dx == +1 && previous.dy == +1 -> 2 to 0 //UPPER_RIGHT_CORNER
                next.dy == 0 && previous.dy == 0 -> 1 to 0 //Horizontal
                next.dx == 0 && previous.dx == 0 -> 2 to 1 //Vertical
                else -> 0 to 2
            }
            drawImage("$pngFile|${bxImg * SPRITE_DIV},${byImg * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
                body.x * CELL_SIDE, body.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
        }
    }
}


/**
 * Function that draws a brick from the pngFile.
 * @receiver where it will be drawn.
 * @param position position of each individual brick.
 * @param pngFile input file that has the drawing of the snake.
 */
private fun Canvas.drawBrick(position: Position, pngFile: String) {
    drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that draws an apple from the pngFile.
 * @receiver where it will be drawn.
 * @param position to be drawn if possible.
 * @param pngFile input file that has the drawing of the apple.
 */
private fun Canvas.drawApple(position: Position?, pngFile: String) {
    if (position != null)
        drawImage(
            "$pngFile|$APPLEXFILE,${APPLEYFILE * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
            position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE
        )
}


/**
 * Function that draws a poisonous apple from the pngFile.
 * @receiver where it will be drawn.
 * @param position to be drawn if possible.
 * @param pngFile input file that has the drawing of the apple.
 */
private fun Canvas.drawApplePoison(position: Position?, pngFile: String) {
    if (position != null)
        drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that draws a golden apple from the pngFile.
 * @receiver where it will be drawn.
 * @param position to be drawn if possible.
 * @param pngFile input file that has the drawing of the apple.
 */
private fun Canvas.drawAppleGolden(position: Position?, pngFile: String) {
    if (position != null)
        drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


/**
 * Displays game information in status bar.
 * @receiver where it will be drawn.
 * @param game information to be written.
 */
private fun Canvas.drawStatus(game: Game) {
    val validArrowPositions = game.snake.arrowPositions()
    val impPos = game.snake.body + game.wall
    val a = validArrowPositions.all { it in impPos }

    drawRect(0, height - STATUS_BAR, width, STATUS_BAR, 0x333333)
    if (game.snake.body.size < Level_WIN)
        drawText(CELL_SIDE / 2, height - TEXT_BASE, "Size:${(game.snake.body.size)}", WHITE, FONT_SIZE)
    else
        drawText(CELL_SIDE / 2, height - TEXT_BASE, "Size:${(game.snake.body.size)}", GREEN, FONT_SIZE)
    drawText(FIVE_CELLS, height - TEXT_BASE, "Score:${game.score}", WHITE, FONT_SIZE)

    if (game.status == Status.WIN)
        drawText(width - FIVE_CELLS, height - TEXT_BASE,
            "You Win", YELLOW)
    if (game.status == Status.LOSE)
        drawText(width - FIVE_CELLS, height - TEXT_BASE,
            "You Lose", YELLOW)
    if (game.status == Status.PAUSE) when{
        (game.snake.body.size >= Level_WIN && a) -> drawText(width - FIVE_CELLS, height - TEXT_BASE,
            "You Win", YELLOW)
        (game.snake.body.size <= Level_WIN && a) -> drawText(width - FIVE_CELLS, height - TEXT_BASE,
            "You Lose", YELLOW)
        else -> drawText(width - SIX_CELLS, height - TEXT_BASE,
            "Game Paused", YELLOW)
    }
}
