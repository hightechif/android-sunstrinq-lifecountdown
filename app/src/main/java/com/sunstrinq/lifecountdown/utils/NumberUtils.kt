package com.sunstrinq.lifecountdown.utils

import java.text.DecimalFormat

fun Double.format(): String {
    val s = toString()
    val i = s.indexOf('.')
    val dec = s.substring(i + 1).trimEnd('0')
    val decFormat = StringBuilder()
    if (dec.isNotEmpty()) {
        decFormat.append(".")
        repeat(dec.length) {
            decFormat.append('#')
        }
    }

    val formatter = DecimalFormat("#,###${decFormat}")
    val symbols = formatter.decimalFormatSymbols
    symbols.groupingSeparator = ','
    symbols.decimalSeparator = '.'
    formatter.decimalFormatSymbols = symbols
    return formatter.format(this)
}
