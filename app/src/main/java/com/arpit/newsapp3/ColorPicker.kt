package com.arpit.newsapp3

object ColorPicker {
    val colors = arrayOf("#D62424", "#7B1FA2" , "#1565C0" , "#6A1B9A" , "#2E7D32" , "#D84315" , "#FFEA00", "#F50057" , "#00E5FF" , "#00C853")

    var colorIndex = 1
    fun getColor(): String{
        return colors[colorIndex++ % colors.size]
    }
    }