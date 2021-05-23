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
        var game = Game(Snake(Position(1, GRID_HEIGHT/2), Position(0, GRID_HEIGHT/2), Direction.RIGHT, false), emptyList())//createGame(game)
        cv.drawGame(game)
        cv.onKeyPressed { ke :KeyEvent ->
            game = Game(snakeDirection(ke.code ,game.snake), game.wall)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER){
            //cv.createRandomBricks(game)
        }

        cv.onTimeProgress(250){
            game = Game(snakeMove(it.toInt() ,game.snake), game.wall)
            cv.drawGame(game)
            cv.createRandomBricks(game)

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
    drawSnake(game.snake)
    game.wall.forEach{
        drawBricks(it)
    }
   // game.wall.forEach {createRandomBricks(game)}
}


//fun createGame(s:Snake) :Game {
//    val snakeInitPos = Snake(Position(1,GRID_HEIGHT/2),Position(0,GRID_HEIGHT/2),s.motion,s.run)
//    return Game(snakeInitPos,createRandomBricks(snakeInitPos))
//}
fun Canvas.createRandomBricks(g:Game) = (ALL_POSITIONS - (g.snake.HeadPos) - (g.snake.TailPos) - g.wall).shuffled().take(1).map{ g.wall + drawBricks(it) }
