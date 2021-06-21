/**
 * Function that takes a random position inside of all unused position.
 * @param game current state of the Game to be affected by possible changes.
 * @return a Position to be given to a Brick.
 */
fun createRandomBrick(game: Game): Position = when (game.level.level) {
    1 -> if (game.apple == null)
        (ALL_POSITIONS - game.snake.body - game.wall).random()
    else
        (ALL_POSITIONS - game.snake.body - game.apple - game.wall).random()

    2 -> if (game.apple != null && game.hacking.golden != null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.apple - game.hacking.golden).random()
    else if (game.apple != null && game.hacking.golden == null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.apple).random()
    else if (game.apple == null && game.hacking.golden != null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.hacking.golden).random()
    else
        (ALL_POSITIONS - game.snake.body - game.wall).random()

    3 -> if (game.apple != null && game.hacking.poison != null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.apple - game.hacking.poison).random()
    else if (game.apple != null && game.hacking.poison == null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.apple).random()
    else if (game.apple == null && game.hacking.poison != null)
        (ALL_POSITIONS - game.snake.body - game.wall - game.hacking.poison).random()
    else
        (ALL_POSITIONS - game.snake.body - game.wall).random()

    else -> game.wall[0]
}


/**
 * Function that draws the initial walls.
 * @return List of the initial walls.
 */
fun initBlocks(): List<Position> {
    val topNDownRow = (0 until GRID_WIDTH).filter { it !in 3..16 }
    val sideRow = (1 until GRID_HEIGHT - 1).filter { it !in 4..11 }
    return topNDownRow.map { Position(it, 0) } + topNDownRow.map {
        Position(it, GRID_HEIGHT - 1)
    } + sideRow.map { Position(0, it) } + sideRow.map { Position(GRID_WIDTH - 1, it) }
}

//fun initBlocksThree(): List<Position> = listOf(
//    Position(0, 0), Position(1, 0), Position(2, 0),
//    Position(8, 0), Position(9, 0), Position(10, 0), Position(11, 0),
//    Position(17, 0), Position(18, 0), Position(19, 0),
//    Position(0, 15), Position(1, 15), Position(2, 15),
//    Position(8, 15), Position(9, 15), Position(10, 15), Position(11, 15),
//    Position(17, 15), Position(18, 15), Position(19, 15),
//    Position(0, 1), Position(0, 2),
//    Position(0, 7), Position(0, 8),
//    Position(0, 13), Position(0, 14),
//    Position(19, 1), Position(19, 2),
//    Position(19, 7), Position(19, 8),
//    Position(19, 13), Position(19, 14),
//    Position(3, 3), Position(4, 3), Position(3, 4), Position(4, 4),
//    Position(15, 3), Position(16, 3), Position(15, 4), Position(16, 4),
//    Position(9, 4), Position(10, 4), Position(9, 5), Position(10, 5),
//    Position(6, 7), Position(7, 7), Position(6, 8), Position(7, 8),
//    Position(12, 7), Position(13, 7), Position(12, 8), Position(13, 8),
//    Position(3, 11), Position(4, 11), Position(3, 12), Position(4, 12),
//    Position(9, 10), Position(10, 10), Position(9, 11), Position(10, 11),
//    Position(15, 11), Position(16, 11), Position(15, 12), Position(16, 12)
//)
//
//
//fun initBlocksTwo(): List<Position> = listOf(
//    Position(0, 0), Position(1, 0),
//    Position(9, 0), Position(10, 0),
//    Position(18, 0), Position(19, 0),
//    Position(0, 15), Position(1, 15),
//    Position(9, 15), Position(10, 15),
//    Position(18, 15), Position(19, 15),
//    Position(0, 1), Position(0, 7), Position(0, 14),
//    Position(19, 1), Position(19, 7), Position(19, 14),
//    Position(14, 2), Position(15, 2), Position(16, 2), Position(16, 3), Position(16, 4),
//    Position(5, 14), Position(4, 12), Position(5, 12), Position(6, 12), Position(5, 10),
//    Position(5, 4), Position(6, 4), Position(4, 5), Position(5, 5), Position(6, 5), Position(7, 5),
//    Position(4, 6), Position(5, 6), Position(6, 6), Position(7, 6), Position(5, 7), Position(6, 7),
//    Position(13, 9), Position(14, 9), Position(12, 10), Position(13, 10), Position(14, 10), Position(15, 10),
//    Position(12, 11), Position(13, 11), Position(14, 11), Position(15, 11), Position(13, 12), Position(14, 12)
//)
/**
 * Function that draws the initial walls in level 2.
 * @return List of the initial walls.
 */
fun initBlocksTwo(): List<Position> {
    val topNDownRow = (0 until GRID_WIDTH).filter { it !in 2..8 && it !in 11..17}
    val sideRow = (1 until GRID_HEIGHT -1).filter { it !in 2..13 }
    val line2 = (0 until GRID_WIDTH).filter { it in 14..16 }
    val collum16 = (0 until GRID_HEIGHT - 1).filter { it in 2..4 }
    val collum3 = (0 until GRID_HEIGHT - 1).filter { it in 11..13 }
    val line13 = (0 until GRID_WIDTH).filter { it in 3..5 }
    val line4N5 = (0 until GRID_WIDTH).filter { it in 4..7 }
    val line10N11 = (0 until GRID_WIDTH).filter { it in 12..15 }
    val line3N6 = (0 until GRID_WIDTH).filter { it in 5..6 }
    val line9N12 = (0 until GRID_WIDTH).filter { it in 13..14 }
    return topNDownRow.map { Position(it, 0) } + topNDownRow.map { Position(it, GRID_HEIGHT - 1)
    } + sideRow.map { Position(0, it) } + sideRow.map { Position(GRID_WIDTH - 1, it)
    } + line2.map { Position(it, 2) } + collum16.map { Position(16, it) } + collum3.map { Position(3, it)
    } + line13.map { Position(it, 13) } + line4N5.map { Position(it, 4) } + line4N5.map { Position(it, 5)
    } + line10N11.map { Position(it, 10) } + line10N11.map { Position(it, 11) } + line3N6.map { Position(it, 3)
    } + line3N6.map { Position(it, 6) } + line9N12.map { Position(it, 9) } + line9N12.map { Position(it, 12) }
}


/**
 * Function that draws the initial walls in level 3.
 * @return List of the initial walls.
 */
fun initBlocksThree() :List<Position> {
    val topNDownRow = (0 until GRID_WIDTH).filter { it !in 3..7 && it !in 12..16}
    val sideRow = (0 until GRID_HEIGHT - 1).filter { it !in 3..6 && it !in 9..12}
    val line3N4N11N12 = (3 until GRID_WIDTH - 3).filter { it !in 5..14 }
    val line7N8 = (8 until GRID_WIDTH - 9).filter { it !in 8..11 }
    val collum9N10 = (4 until GRID_HEIGHT - 4).filter { it !in 6..9 }
    return topNDownRow.map { Position(it,0) } + topNDownRow.map { Position(it,GRID_HEIGHT-1)
    } + sideRow.map { Position(0,it) } + sideRow.map { Position(GRID_WIDTH-1,it)
    } + line3N4N11N12.map { Position(it,3) } + line3N4N11N12.map { Position(it,4)
    } + line3N4N11N12.map { Position(it,11) } + line3N4N11N12.map { Position(it,12) } + line7N8.map { Position(it,7)
    } + line7N8.map { Position(it,8) } + collum9N10.map { Position(9,it) } + collum9N10.map { Position(10,it) }
}