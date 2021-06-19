import pt.isel.canvas.*


const val OUT_OF_BOUNDS = 10

/**
 * Function that draws the Snake in the canvas using a Sprite.
 * @receiver where it will be drawn.
 * @param snake object with movement in the game.
 * @param pngFile input file that has the drawing of the snake.
 */
fun Canvas.drawSnake(snake: Snake, pngFile: String) {

    val bx = snake.body.map { it.x * CELL_SIDE - snake.direction.dx() }
    val by = snake.body.map { it.y * CELL_SIDE - snake.direction.dy() }
    val hxImg = SPRITE_DIV * when (snake.direction) {
        Direction.LEFT, Direction.UP -> 3
        Direction.RIGHT, Direction.DOWN -> 4
    }

    val hyImg = SPRITE_DIV * when (snake.direction) {
        Direction.UP, Direction.RIGHT -> 0
        Direction.LEFT, Direction.DOWN -> 1
    }


    val txImg = SPRITE_DIV * when (snake.direction){
        Direction.LEFT, Direction.UP -> 3
        Direction.RIGHT, Direction.DOWN -> 4
    }

    val tyImg = SPRITE_DIV * when (snake.direction) {
        Direction.UP, Direction.RIGHT -> 2
        Direction.LEFT, Direction.DOWN -> 3
    }



    drawImage("$pngFile|$hxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV", bx[0], by[0], CELL_SIDE, CELL_SIDE)// head
    if (snake.body.size == 2)
        drawImage("$pngFile|$hxImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV", bx.last(), by.last(), CELL_SIDE, CELL_SIDE)// tail
    //TODO("Implement the rest of  the body ; ERROR IN INDECES")

    if (snake.body.size > 2)
        for (i in 1 until snake.body.size - 1) {
            val next = (snake.body[i] - snake.body[i - 1]).toDirection()
            val previous = (snake.body[i + 1] - snake.body[i]).toDirection()

            val (xImg, yImg) = when {
                //LOWER_LEFT_CORNER
                next == Direction.LEFT && previous == Direction.UP ||
                        next == Direction.DOWN && previous == Direction.RIGHT -> 0 to 1

                //UPPER_LEFT_CORNER
                next == Direction.LEFT && previous == Direction.DOWN ||
                        next == Direction.UP && previous == Direction.RIGHT -> 0 to 0

                //LOWER_RIGHT_CORNER
                next == Direction.DOWN && previous == Direction.LEFT ||
                        next == Direction.RIGHT && previous == Direction.UP -> 2 to 2

                //UPPER_RIGHT_CORNER
                next == Direction.UP && previous == Direction.LEFT ||
                        next == Direction.RIGHT && previous == Direction.DOWN -> 2 to 0

                //Horizontal
                next.dy == 0 && previous.dy == 0 -> 1 to 0

                //Vertical
                next.dx == 0 && previous.dx == 0 -> Pair(2, 1)



                else -> 0 to 2
            }

            drawImage("$pngFile|${xImg * SPRITE_DIV},${yImg * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV", snake.body[i].x * CELL_SIDE, snake.body[i].y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
        }
    }



/**
 * Draws the whole game.
 * @receiver where it will be drawn.
 * @param game collection of TODO "what to write?"
 */
fun Canvas.drawGame(game: Game) {
    erase()
    drawGrid()
    drawSnake(game.snake, "snake.png")
    game.wall.forEach { drawBrick(it, "bricks.png") }
    drawApple(game.apple, "snake.png")
    drawStatus(game)
}


/**
 * Displays game information in status bar.
 * @receiver where it will be drawn.
 * @param game information to be written.
 */
fun Canvas.drawStatus(game: Game) {
    drawRect(0, height - STATUS_BAR, width, STATUS_BAR, 0x333333)
    if (game.snake.body.size < 60)
        drawText(CELL_SIDE / 2, height - TEXT_BASE, "Size:${(game.snake.body.size)}", WHITE, FONT_SIZE)
    else
        drawText(CELL_SIDE / 2, height - TEXT_BASE, "Size:${(game.snake.body.size)}", GREEN, FONT_SIZE)
    drawText(FIVE_CELLS, height - TEXT_BASE, "Score:${game.score}", WHITE, FONT_SIZE)

    if (game.status != Status.RUN)
        drawText(
            width - FIVE_CELLS,
            height - TEXT_BASE,
            "You ${if (game.status == Status.WIN) "WIN" else "LOSE"}",
            YELLOW
        )
}


/**
 * Draw the background grid.
 * @receiver where it will be drawn.
 */
fun Canvas.drawGrid() {
    (CELL_SIDE..height step CELL_SIDE).forEach {
        drawLine(0, it, width, it, WHITE, 1) // horizontal
    }
    (CELL_SIDE..width step CELL_SIDE).forEach {
        drawLine(it, 0, it, height, WHITE, 1) // vertical
    }
}


/**
 * Function that draws a brick from the pngFile.
 * @receiver where it will be drawn.
 * @param position position of each individual brick.
 * @param pngFile input file that has the drawing of the snake.
 */
fun Canvas.drawBrick(position: Position, pngFile: String) {
    drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


/**
 * Function that draws an apple from the pngFile.
 * @receiver where it will be drawn.
 * @param position to be drawn if possible.
 * @param pngFile input file that has the drawing of the apple.
 */
fun Canvas.drawApple(position: Position?, pngFile: String) {
    val xFile = 0
    val yFile = 3
    if (position != null)
        drawImage(
            "$pngFile|$xFile,${yFile * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
            position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE
        )
}
