@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.lang.Exception
import java.util.*
import kotlin.math.floor

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "явгуста", "сентября", "октября", "ноября", "декабря")
    val toDigit: String.() -> Int = {
        try {
            this.toInt()
        } catch (e: Exception) {
            if (months.contains(this)) months.indexOf(this) + 1
            else -1
        }
    }
    val dateDig = IntArray(3)
    val splitStr = str.split(" ")
    splitStr.forEachIndexed { index, value ->
        dateDig[index] = value.toDigit()
    }
    var isDate = false
    run loop@{
        dateDig.forEachIndexed { index, value ->
            when (index) {
                0 -> {
                    isDate = when (value) {
                        in 28..31 -> {
                            if (dateDig[1] != 2) true
                            else return@loop
                        }
                        in 1..27 -> true
                        else -> return@loop
                    }
                }
                1 -> {
                    if (value in 1..12) isDate = true
                    else {
                        isDate = false
                        return@loop
                    }
                }
                2 -> {
                    if (value in 0..Int.MAX_VALUE) isDate = true
                    else {
                        isDate = false
                        return@loop
                    }
                }
                else -> {
                    isDate = false
                    return@loop
                }
            }
        }
    }
    return if (isDate) dateDig.joinToString(separator = ".", transform = {
        String.format("%02d", it)
    }) else ""
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val splitStr = digital.split(".").toTypedArray()
    var isDate = false
    run loop@{
        splitStr.forEachIndexed { index, v ->
            val value = v.toInt()
            when (index) {
                0 -> {
                    isDate = when (value) {
                        in 28..31 -> {
                            if (splitStr[1].toInt() != 2) true
                            else return@loop
                        }
                        in 1..27 -> true
                        else -> return@loop
                    }
                }
                1 -> {
                    if (value in 1..12) {
                        isDate = true
                        splitStr[index] = months[value]
                    } else {
                        isDate = false
                        return@loop
                    }
                }
                2 -> {
                    if (value in 0..Int.MAX_VALUE) isDate = true
                    else {
                        isDate = false
                        return@loop
                    }
                }
                else -> {
                    isDate = false
                    return@loop
                }
            }
        }
    }
    return if (isDate) {
        splitStr.joinToString(separator = ".", transform = {
            String.format("%02d", it)
        })
    } else ""
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val str = phone.replace("""[()\-\s]""".toRegex(), "")
    return when {
        (str.contains("""(?<!^)[+]|[^+\d]""".toRegex())) -> ""
        else -> str
    }
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int = try {
    jumps.replace("""[-%]""".toRegex(), "").split("""[\s]""".toRegex()).filter { it != "" }.map { it.toInt() }.max()
            ?: -1
} catch (e: Exception) {
    -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int =
        if (jumps.contains("""[^+\-%\d\s]""".toRegex())) -1
        else {
            try {
                jumps.split("""(?<!\d)[\s]""".toRegex()).filter { it.contains("""\s[+]""".toRegex()) }.map {
                    it.replace("""[^\d]""".toRegex(), "").toInt()
                }.max() ?: -1
            } catch (e: Exception) {
                -1
            }

        }

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val polkExpress = mutableListOf<String>()
    val temp = mutableListOf<String>()
    if (expression.contains("""(?<![\w\s])[+-]|[+-](?![\w\s])|[-+][\s]*[-+]|[\d][\s][\d]""".toRegex())) {
        throw IllegalArgumentException()
    } else {
        val actionList = expression.split("""[\s]""".toRegex())

        actionList.forEach {
            if (it.toIntOrNull() != null) {
                polkExpress.add(it)
            } else {
                if (temp.isEmpty())
                    temp.add(it)
                else {
                    for (i in temp.size - 1 downTo 0) {
                        polkExpress.add(temp.removeAt(i))
                    }
                    temp.add(it)
                }
            }

        }
    }
    for (i in temp.size - 1 downTo 0) {
        polkExpress.add(temp.removeAt(i))
    }
    polkExpress.forEach {
        if (it.toIntOrNull() != null) {
            temp.add(it)
        } else {
            when (it) {
                "+" -> {
                    val sum = temp.fold(0) { sm, next ->
                        sm + next.toInt()
                    }
                    temp.clear()
                    temp.add(sum.toString())
                }
                "-" -> {
                    var sub = temp[0].toInt()
                    for (i in 1 until temp.size) {
                        sub -= temp[i].toInt()
                    }
                    temp.clear()
                    temp.add(sub.toString())
                }
            }
        }
    }
    return temp[0].toInt()
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val resultMatch = """([\p{L}]+)\s(\1)""".toRegex(option = RegexOption.IGNORE_CASE).find(str, 0)
    return str.indexOf(resultMatch?.value.toString())
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String = if (description.contains("""[^\p{L}\d;.\s]""".toRegex())) {
    ""
} else {
    val products = description.split(";").map { it.trim() }
    val productWithPrice = mutableMapOf<String, Double>().apply {
        products.forEach {
            this.putAll(it.split(" ").zipWithNext { a, b ->
                Pair(a, b.toDouble())
            })
        }
    }
    var maxPrice = Pair("", 0.0)
    productWithPrice.forEach {
        if (it.value > maxPrice.second) maxPrice = it.toPair()
    }
    maxPrice.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var str = roman
    val romanVocabulary = mapOf("M" to 1000, "D" to 500, "C" to 100, "L" to 50, "X" to 10, "V" to 5, "I" to 1)
    val romanVocabulary2 = mapOf("CM" to 900, "CD" to 400, "XC" to 90, "XL" to 40, "IX" to 9, "IV" to 4)
    if (roman.contains("""[^IVXCMLD]""".toRegex())) {
        return -1
    }
    var answer = 0
    romanVocabulary2.forEach { k, v ->
        answer += if (str.contains(k)) {
            str = str.replace(k.toRegex(), "")
            v
        } else 0
    }
    str.forEach { answer += romanVocabulary.getValue(it.toString()) }
    return answer
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var i = 0
    val startPosition: Int = floor(cells.toDouble() / 2).toInt()
    val array: IntArray = intArrayOf(cells).apply { fill(0) }
    if (commands.contains("""[^<>\-\+\[\]]""".toRegex())) throw IllegalArgumentException()
    run {
        val stack = Stack<Char>()
        commands.filter { it == '[' || it == ']' }.forEach {
            if (it == '[') stack.push(it)
            else {
                if (stack.empty()) throw  IllegalArgumentException()
                else stack.pop()
            }
        }
        if (stack.isNotEmpty()) throw  IllegalArgumentException()
    }

    while (i < limit) {


        i++
    }

    return array.toList()

}
