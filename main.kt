package chucknorris

fun encode() {
    println("Input string:")
    val input = readln()
    var binary = ""; var unary = "";
    var cBinary = 1;
    val dictBinary = mutableListOf<Pair<Char, Int>>()

    println("Encoded string:")
    for (c in input) {
        binary += Integer.toBinaryString(c.code).padStart(7, '0')
    }
    for (n in 1 until binary.length) {
        if (binary[n] == binary[n - 1]) {
            cBinary++
        } else {
            dictBinary.add(binary[n - 1] to cBinary)
            cBinary = 1
        }
    }
    dictBinary.add(binary.last() to cBinary)
    for ((key, value) in dictBinary) {
        when (key) {
            '0' -> unary += "00 ${"0".repeat(value)}"
            '1' -> unary += "0 ${"0".repeat(value)}"
        }
        unary += " "
    }
    // Nettoyer la chaîne encodée pour retirer l'espace final inutile
    unary = unary.trim()
    println(unary)
}

fun decode() {
    println("Input encoded string:")
    val input = readln()
    val listInput = input.split(" ")
    var binary = ""
    var word = ""
    val dictBinary = mutableListOf<Pair<String, String>>()

    println("Decoded string:")
    if (!listInput.all { it.toString().all { char -> char == '0' } }) {
            println("Encoded string is not valid.")
            return
    }
    for(i in listInput.indices step 2) {
        if (i + 1 < listInput.size) { // Vérifie qu'il y a bien une paire
            dictBinary.add(listInput[i] to listInput[i + 1])
        }
    }
    if((dictBinary.size * 2 != listInput.size) ) {
            println("Encoded string is not valid.")
            return
        }
    for((key, value) in dictBinary) {
        when (key) {
            "0" -> binary += "1".repeat(value.length)
            "00" -> binary += "0".repeat(value.length)
            else -> {
                println("Encoded string is not valid.")
                return
            }
        }
    }
    val chunked = binary.chunked(7)
    for(elem in chunked) {
        word += binaryToChar(elem)
    }
    word = word.trim()
    println(word)
}

fun binaryToChar(binary: String): Char {
    val intValue = Integer.parseInt(binary, 2) // Convertir la chaîne binaire en entier
    return intValue.toChar() // Convertir l'entier en caractère
}

fun main() {
    while(true) {
        println("Please input operation (encode/decode/exit):")
        val operation = readln()

        if(operation == "encode") {
            encode()
        } else if (operation == "decode") {
            decode()
        } else if (operation == "exit") {
            println("Bye!")
            break
        } else {
            println("There is no $operation operation")
        }
    }
}

