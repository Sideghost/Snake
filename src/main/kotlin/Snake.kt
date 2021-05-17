import pt.isel.canvas.*

const val CELL_SIDE = 32
const val GRID_WIDTH = 20
const val GRID_HEIGHT = 16
//data class Game(val snake:Snake, val wall:List<Position>)

fun main (){
    onStart{
        val cv = Canvas(CELL_SIDE*GRID_WIDTH, CELL_SIDE*GRID_HEIGHT, BLACK)

    }
    onFinish {
        println("2 done 1 to go!")
    }
}