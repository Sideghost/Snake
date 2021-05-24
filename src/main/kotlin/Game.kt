import pt.isel.canvas.*

const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
const val BLOCK_SPAWN_TIMER = 5000


data class Game(val snake: Snake, val wall: List<Position>)


fun main() {
    onStart {
        val cv = Canvas(CELL_SIDE * GRID_WIDTH, CELL_SIDE * GRID_HEIGHT, BLACK)
        var game = createGame()
        //Game(Snake(Position(1, GRID_HEIGHT/2), Position(0, GRID_HEIGHT/2), Direction.RIGHT, false), emptyList())
        cv.drawGame(game)
        cv.onKeyPressed { ke :KeyEvent ->
            game = Game(snakeDirection(ke.code ,game.snake), game.wall)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER){
            game = Game(game.snake,cv.createRandomBrick(game))
        }

        cv.onTimeProgress(250){
            game = Game(snakeMove(it.toInt() ,game.snake), game.wall)
            cv.drawGame(game)
            //cv.createRandomBricks(game)

        }

    }
    onFinish {
        println("2 done 1 to go!")
    }
}


fun Canvas.drawGrid() {
    (CELL_SIDE..height step CELL_SIDE).forEach {
        drawLine(0, it, width, it, WHITE, 1)
    }
    (CELL_SIDE..width step CELL_SIDE).forEach {
        drawLine(it, 0, it, height, WHITE, 1)
    }
}


fun Canvas.drawGame(game: Game) {
    erase()
    //drawGrid()
    drawSnake(game.snake)
    createRandomBrick(game)
    //game.wall.forEach { drawImage("bricks.png", it.x, it.y, CELL_SIDE, CELL_SIDE) }

}


fun createGame() :Game {
    val snakeInitPos = Snake(Position(1,GRID_HEIGHT/2),Position(0,GRID_HEIGHT/2),Direction.RIGHT,true)
    return Game(snakeInitPos, emptyList())
}


fun Canvas.createRandomBrick(g:Game) {
    val newbrick = (ALL_POSITIONS - g.snake.HeadPos - g.snake.TailPos).shuffled().take(1).map { drawBrick(it)/*Snake(it,it,0,null)*/}
    return g.wall + newbrick
}

