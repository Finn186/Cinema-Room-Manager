package cinema

fun createBoard(rows: Int, seatsRow: Int): MutableList<MutableList<Char>> {
    val board = mutableListOf<MutableList<Char>>()
    repeat(rows) {
        val tempList = MutableList(seatsRow) { 'S' }
        board.add(tempList)
    }
    return board
}

fun printBoard(board: MutableList<MutableList<Char>>, seatsRow: Int) {
    println("Cinema:")
    print("  ")
    for (i in 1..seatsRow) {
        print("$i ")
    }
    println()
    for (i in 1..board.size) {
        println("$i ${ board[i - 1].joinToString(" ") }")
    }
}

fun main() {

    print("Enter the number of rows: ")
    val rows = readLine()!!.toInt()
    print("Enter the number of seats in each row: ")
    val seatsRow = readLine()!!.toInt()
    val board = createBoard(rows, seatsRow)
    var income = 0
    var ticketsSold = 0
    while (true) {
        println("""
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent())
        when (readLine()!!.toInt()) {
            0 -> break
            1 -> printBoard(board, seatsRow)
            2 -> {
                income += getSeat(board, rows, seatsRow)
                ticketsSold++
            }
            3 -> statistics(ticketsSold, seatsRow * rows, income, seatsRow, rows)
        }
    }
}

fun statistics(ticketsSold: Int, seats: Int, income: Int, seatsRow: Int, rows: Int) {
    val totalIncome = if (seats <= 60) {
        seats * 10
    } else {
        rows / 2 * seatsRow * 10 + (rows - rows / 2) * seatsRow * 8
    }
    var percentage = "%.2f".format(ticketsSold.toDouble() / seats * 100)
    println("""
        Number of purchased tickets: $ticketsSold
        Percentage: $percentage%
        Current income $$income
        Total income: $$totalIncome
    """.trimIndent())
}

fun getSeat(board: MutableList<MutableList<Char>>, rows: Int, seatsRow: Int): Int {
    var rowNumber: Int
    while (true) {
        print("Enter a row number: ")
        rowNumber = readLine()!!.toInt()
        print("Enter a seat number in that row: ")
        val seatNumber = readLine()!!.toInt()
        try {
            editBoard(board, rowNumber, seatNumber)
            break
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
        } catch (e: Exception) {
            continue
        }
    }
    return printPrice(rowNumber, rows, seatsRow)
}

fun printPrice(rowNumber: Int, rows: Int, seatsRow: Int): Int {
    val totalIncome = if (seatsRow * rows <= 60) {
        10
    } else {
        when {
            rowNumber <= rows / 2 -> 10
            else -> 8
        }
    }
    println("Ticket price: $$totalIncome")
    return totalIncome
}

fun editBoard(board: MutableList<MutableList<Char>>, rowNumber: Int, seatNumber: Int) {
    if (board[rowNumber - 1][seatNumber - 1] == 'B') {
        println("That ticket has already been purchased!")
        throw Exception("Already booked")
    }
    board[rowNumber - 1][seatNumber - 1] = 'B'
}
