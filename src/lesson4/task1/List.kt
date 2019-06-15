@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.floor
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = if (v.isEmpty()) 0.0 else sqrt(v.fold(0.0) { previousElement, element -> previousElement + sqr(element) })

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0 else list.sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val avg = list.average()
    list.forEachIndexed { index, d -> list[index] = d - avg }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double = a.foldIndexed(0.0) { index, previousElement, element -> previousElement + element * b[index] }

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double = p.foldIndexed(0.0) { index, previousElement, element -> previousElement + element * pow(x, index.toDouble()) }

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    val copyList = list.toList()
    list.forEachIndexed { index, d ->
        if (index > 0) {
            val sum = copyList.filterIndexed { deepIndex, deepD -> deepIndex < index }.sum()
            list[index] += sum
        }
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val result = mutableListOf<Int>()
    var numb = n
    for (i in 2..floor(sqrt(n.toDouble())).toInt()) {
        while (numb % i == 0) {
            result += i
            numb /= i
        }
    }
    if (numb > 1) result += numb
    return result.sorted().toList()
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val result = mutableListOf<Int>()
    var numb = n
    for (i in 2..floor(sqrt(n.toDouble())).toInt()) {
        while (numb % i == 0) {
            result += i
            numb /= i
        }
    }
    if (numb > 1) result += numb
    return result.sorted().joinToString(separator = "*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var n = n
    val result = mutableListOf<Int>()
    while (n / base != 0) {
        result += n % base
        n /= base
    }
    result += n % base
    return result.toList().reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var n = n
    val result = mutableListOf<Int>()
    while (n / base != 0) {
        result += n % base
        n /= base
    }
    result += n % base
    return result.toList().reversed().joinToString(separator = "", transform = {
        if (it < 10) it.toString()
        else {
            (it + 87).toChar().toString()
        }
    })
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var result = 0
    digits.reversed().forEachIndexed { index, it ->
        result += it * pow(base.toDouble(), index.toDouble()).toInt()
    }
    return result
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var result = 0
    str.reversed().forEachIndexed { index, it ->
        result += if (it.isDigit()) it.toString().toInt() * pow(base.toDouble(), index.toDouble()).toInt()
        else (it - 87).toInt() * pow(base.toDouble(), index.toDouble()).toInt()

    }
    return result
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var result = ""
    var digPlace = 1
    var n = n
    var numb = 0
    do {
        when (digPlace) {
            1 -> {
                numb = n % 10
                when (numb) {
                    in 1..3 -> {
                        for (i in 1..numb) result = "I$result"
                    }
                    4 -> result = "IV$result"
                    5 -> result = "V$result"
                    in 6..8 -> {
                        for (i in 1..numb - 5) result = "I$result"
                        result = "V$result"

                    }
                    9 -> result = "IX$result"
                    else -> result += ""
                }
            }
            10 -> {
                numb = n % 10
                when (numb) {
                    in 1..3 -> {
                        for (i in 1..numb) result = "X$result"
                    }
                    4 -> result = "XL$result"
                    5 -> result = "L$result"
                    in 6..8 -> {
                        for (i in 1..numb - 5) result = "X$result"
                        result = "L$result"
                    }
                    9 -> result = "XC$result"
                    else -> result += ""
                }
            }
            100 -> {
                numb = n % 10
                when (numb) {
                    in 1..3 -> {
                        for (i in 1..numb) result = "C$result"
                    }
                    4 -> result = "CD$result"
                    5 -> result = "D$result"
                    in 6..8 -> {
                        for (i in 1..numb - 5) result = "C$result"
                        result = "D$result"
                    }
                    9 -> result = "CM$result"
                    else -> result += ""
                }
            }
            1000 -> {
                numb = n % 10
                for (i in 1..numb) result = "M$result"
            }
        }
        n /= 10
        digPlace *= 10
    } while (n != 0)
    return result
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val ones = listOf(" один", " два", " три", " четыре", " пять", " шесть", " семь", " восемь", " девять")
    val ones2 = listOf(" одна", " две", " три", " четыре", " пять", " шесть", " семь", " восемь", " девять")
    val tens = listOf(" десять", " одинадцать", " двенадцать", " тринадцать", " четырнадцать", " пятнадцать", " шестнадцать", " семнадцать", "восемнадцать", " девятнадцать")
    val tens2 = listOf(" двадцать", " тридцать", " сорок", " пятьдесят", " шестьдесят", " семьдесят", " восемьдесят", " девяносто")
    val hundreds = listOf(" сто", " двести", " триста", " четыреста", " пятьсот", " шестьсот", " семьсот", " восемьсот", " девятьсот")
    var result = ""
    var digPlace = 0
    var n = n
    var numb: Int
    fun local1(number: Int, tempRes: String) = when (number) {
        1 -> " ${ones[number - 1]}$tempRes"
        in 2..4 -> "${ones[number - 1]}$tempRes"
        in 5..9 -> "${ones[number - 1]}$tempRes"
        else -> ""
    }
    do {
        when (digPlace) {
            0 -> {
                numb = n % 1000
                when (numb) {
                    in 1..9 -> {
                        result = local1(numb, result)
                    }
                    in 10..19 -> result = "${tens[numb - 10]}$result"
                    in 20..99 -> {
                        var numb2 = numb % 10
                        result = local1(numb2, result)
                        numb2 %= 10
                        numb = n % 10
                        when (numb) {
                            in 2..9 -> result = "${tens2[numb - 2]}$result"
                            else -> result += ""
                        }
                    }
                    in 100..999 -> {
                        var numb2 = numb % 100
                        when (numb2) {
                            in 1..9 -> {
                                result = local1(numb2, result)
                            }
                            in 10..19 -> result = " $result${tens[numb2 - 10]}$result"
                            in 20..99 -> {
                                var numb3 = numb2 % 10
                                result = local1(numb3, result)
                                numb3 = numb2 / 10
                                when (numb3) {
                                    in 2..9 -> result = "${tens2[numb3 - 2]}$result"
                                    else -> result += ""
                                }
                            }
                            else -> result += ""
                        }
                        numb /= 100
                        when (numb) {
                            in 1..9 -> result = hundreds[numb - 1] + result
                            else -> result += ""
                        }
                    }
                    else -> result += ""
                }
            }
            1 -> {
                numb = n % 1000
                when (numb) {
                    in 1..9 -> {
                        when (numb) {
                            1 -> result = "${ones2[numb - 1]} тысяча$result"
                            in 2..4 -> result = "${ones2[numb - 1]} тысячи$result"
                            in 5..9 -> result = "${ones2[numb - 1]} тысяч$result"
                        }
                    }
                    in 10..19 -> result = "${tens[numb - 10]} тысяч$result"
                    in 20..99 -> {
                        var numb2 = numb % 10
                        when (numb2) {
                            1 -> result = " ${ones2[numb2 - 1]} тысяча$result"
                            in 2..4 -> result = "${ones2[numb2 - 1]} тысячи$result"
                            in 5..9 -> result = "${ones2[numb2 - 1]} тысяч$result"
                        }
                        numb2 %= 10
                        numb = n % 10
                        when (numb) {
                            in 2..9 -> result = "${tens2[numb - 2]}$result"
                            else -> result += ""
                        }
                    }
                    in 100..999 -> {
                        var tempRes = ""
                        numb /= 100
                        when (numb) {
                            in 1..9 -> tempRes = hundreds[numb - 1]
                            else -> result += ""
                        }
                        var numb2 = n % 100
                        when (numb2) {
                            in 1..9 -> {
                                when (numb2) {
                                    1 -> result = "$tempRes${ones2[numb2 - 1]} тысяча$result"
                                    in 2..4 -> result = "$tempRes${ones2[numb2 - 1]} тысячи$result"
                                    in 5..9 -> result = " $tempRes${ones2[numb2 - 1]} тысяч$result"
                                }
                            }
                            in 10..19 -> result = " $tempRes${tens[numb2 - 10]} тысяч$result"
                            in 20..99 -> {
                                var numb3 = numb2 % 10
                                when (numb3) {
                                    1 -> result = " $tempRes${ones2[numb2 - 1]} тысяча$result"
                                    in 2..4 -> result = " $tempRes${ones2[numb3 - 1]} тысячи$result"
                                    in 5..9 -> result = " $tempRes${ones2[numb3 - 1]} тысяч$result"
                                }
                                numb3 %= 10
                                when (numb) {
                                    in 2..9 -> result = " $tempRes${tens2[numb3 - 2]}$result"
                                    else -> result += ""
                                }
                            }
                            else -> result = "$tempRes тысяч$result"
                        }
                    }
                    else -> result += ""
                }
                digPlace /= 100
                n /= 100
            }
        }
        n /= 1000
        digPlace++
    } while (n != 0)
    result = result.trim()
    return result
}