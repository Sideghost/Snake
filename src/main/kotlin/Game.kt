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
        var game = Game(
            Snake(Position(1, GRID_HEIGHT/2), Position(0, GRID_HEIGHT/2), Direction.RIGHT, true),
            emptyList()
        )
        //createGame()
        cv.drawGame(game)
        cv.onKeyPressed { ke :KeyEvent ->
            game = Game(snakeDirection(ke.code, game), game.wall)
        }

        cv.onTimeProgress(BLOCK_SPAWN_TIMER){
            game = Game(game.snake,game.wall + createRandomBrick(game))
        }

        cv.onTimeProgress(250){
            game = move(it.toInt() ,game)
            cv.drawGame(game)
        }

    }
    onFinish {
        println("2 done 1 to go!")
    }
}

fun Canvas.drawGame(game: Game) {
    erase()
    drawSnake(game.snake)
    game.wall.forEach{drawBrick(it)}

}


fun createGame() :Game {
    val snakeInitPos = Snake(Position(1,GRID_HEIGHT/2),Position(0,GRID_HEIGHT/2),Direction.RIGHT,true)
    return Game(snakeInitPos, emptyList())
}


fun createRandomBrick(g: Game) :List<Position> {
    return (ALL_POSITIONS - g.snake.HeadPos - g.snake.TailPos - g.wall).shuffled().take(1).map { Position(it.x, it.y) }
}

