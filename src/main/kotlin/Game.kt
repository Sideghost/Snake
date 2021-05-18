import pt.isel.canvas.*

const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
const val SPRITE_DIV = 64
data class Game(val snake:Snake, val wall:List<Position>)

fun main (){
    onStart{
        val cv = Canvas(CELL_SIDE*GRID_WIDTH, CELL_SIDE*GRID_HEIGHT, BLACK)
        var game = createGame()
        //cv.drawGrid()
        //cv.drawSnake(game.snake)
        cv.drawGame(game)
    }
    onFinish {
        println("2 done 1 to go!")
    }
}
fun Canvas.drawGrid(){
    (CELL_SIDE..height step CELL_SIDE).forEach{
        drawLine(0,it,width,it, WHITE,1)
    }
    (CELL_SIDE..width step CELL_SIDE).forEach {
        drawLine(it, 0, it, height, WHITE, 1)
    }
}
fun Canvas.drawSprites(s:Snake,){

}
fun Canvas.drawGame(game: Game) {
    erase()
    drawGrid()
    drawSnake(game.snake)
    game.wall.forEach { drawImage("bricks.png",it.x,it.y,CELL_SIDE,CELL_SIDE) }
    //drawActor(game.hero, game.stepAnim,"Hero.png")
    //game.robots.forEach {
    //    drawActor( it , game.stepAnim, "robot.png")
    //}
}

fun createGame() :Game {
    val snakeInitPos = Snake(SnakePos(1,GRID_HEIGHT/2),SnakePos(0,GRID_HEIGHT/2),0,Direction.RIGHT)
    return Game(snakeInitPos,createBricks(snakeInitPos).random())
}
fun createBricks(s:Snake) = (ALL_POSITIONS - s.HeadPos - s.TailPos).shuffled().map { Snake(it,it,0,null)}
