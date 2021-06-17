import pt.isel.canvas.Canvas
import pt.isel.canvas.WHITE

/**
 * Function that draws the Snake in the canvas using a Sprite.
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

    val tyImg = SPRITE_DIV * when (snake.direction) {
        Direction.UP, Direction.RIGHT -> 2
        Direction.LEFT, Direction.DOWN -> 3
    }


    drawImage("$pngFile|$hxImg,$hyImg,$SPRITE_DIV,$SPRITE_DIV", bx[0], by[0], CELL_SIDE, CELL_SIDE)// head
    if (snake.body.size > 1)
        drawImage("$pngFile|$hxImg,$tyImg,$SPRITE_DIV,$SPRITE_DIV", bx.last(), by.last(), CELL_SIDE, CELL_SIDE)// tail


//    if(snake.body.size > 2 )
//    (snake.body.subList(1,snake.body.size-2)).forEach {
//
//        val bxImag = SPRITE_DIV * when ((snake.body[it.x] - snake.body[it.x-1]).toDirection()) {
//            Direction.LEFT, Direction.RIGHT -> 1
//            Direction.DOWN, Direction.UP -> 2
//        }
//
//        val byImg = SPRITE_DIV * when ((snake.body[it.y] - snake.body[it.y-1]).toDirection()) {
//            Direction.UP, Direction.DOWN -> 1
//            Direction.RIGHT, Direction.LEFT -> 0
//        }
//
//        val xImg = SPRITE_DIV * when ((snake.body[it.x] - snake.body[it.x-1]).toDirection()) {
//            Direction.LEFT, Direction.DOWN -> 2
//            Direction.RIGHT, Direction.UP -> 0
//        }
//
//        val yImg = SPRITE_DIV * when ((snake.body[it.y] - snake.body[it.y-1]).toDirection()) {
//            Direction.UP -> 1
//            Direction.DOWN, Direction.RIGHT -> 0
//            Direction.LEFT -> 2
//        }
//
//        drawImage("$pngFile|$xImg,$yImg,$SPRITE_DIV,$SPRITE_DIV", it.x * CELL_SIDE, it.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
//    }
//    drawImage("$pngFile|$bxImag,$byImg,$SPRITE_DIV,$SPRITE_DIV", bx, by,CELL_SIDE,CELL_SIDE)//body


}


/**
 * Draws the whole game.
 * @param game collection of [wall] && [snake].
 */
fun Canvas.drawGame(game: Game) {
    erase()
//    drawGrid()
    drawSnake(game.snake, "snake.png")
    game.wall.forEach { drawBrick(it, "bricks.png") }
    drawApple(game.apple, "snake.png")
    drawStatus(game)
}


/**
 * Displays game information in status bar.
 */
fun Canvas.drawStatus(g: Game) {
    drawRect(0, height - STATUS_BAR, width, STATUS_BAR, 0x333333)
    drawText(TEXT_BASE, height - TEXT_BASE, "Size:${g.snake.body.size}    Score:${g.score}", WHITE, FONT_SIZE)
    if (g.status != Status.RUN)
        drawText(
            width - 5 * CELL_SIDE,
            height - TEXT_BASE,
            "You ${if (g.status == Status.WIN) "win" else "lose"}",
            WHITE
        )
}


/**
 * Draw the background grid.
 * @receiver where it will be drawn
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
 * @param position position of each individual brick.
 * @param pngFile input file that has the drawing of the snake.
 */
fun Canvas.drawBrick(position: Position, pngFile: String) {
    drawImage(pngFile, position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE)
}


fun Canvas.drawApple(position: Position?, pngFile: String) {
    val xFile = 0
    val yFile = 3
    if (position != null)
        drawImage(
            "$pngFile|$xFile,${yFile * SPRITE_DIV},$SPRITE_DIV,$SPRITE_DIV",
            position.x * CELL_SIDE, position.y * CELL_SIDE, CELL_SIDE, CELL_SIDE
        )
}
