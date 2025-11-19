package es.jvbabi.tui.table.components

sealed class BorderStyle {
    @Suppress("unused")
    data object Borderless : BorderStyle()

    internal interface WithBorders {
        val topLeft: Char
        val topRight: Char
        val bottomLeft: Char
        val bottomRight: Char
        val horizontal: Char
        val vertical: Char
        val topJoint: Char
        val middleJoint: Char
        val leftJoint: Char
        val rightJoint: Char
        val bottomJoint: Char
    }

    @Suppress("unused")
    data object Default : BorderStyle(), WithBorders {
        override val topLeft = '┌'
        override val topRight = '┐'
        override val bottomLeft = '└'
        override val bottomRight = '┘'
        override val horizontal = '─'
        override val vertical = '│'
        override val topJoint = '┬'
        override val middleJoint = '┼'
        override val leftJoint = '├'
        override val rightJoint = '┤'
        override val bottomJoint = '┴'
    }

    @Suppress("unused")
    data object Double : BorderStyle(), WithBorders {
        override val topLeft = '╔'
        override val topRight = '╗'
        override val bottomLeft = '╚'
        override val bottomRight = '╝'
        override val horizontal = '═'
        override val vertical = '║'
        override val topJoint = '╦'
        override val middleJoint = '╬'
        override val leftJoint = '╠'
        override val rightJoint = '╣'
        override val bottomJoint = '╩'
    }

    @Suppress("unused")
    data class Custom(
        override val topLeft: Char,
        override val topRight: Char,
        override val bottomLeft: Char,
        override val bottomRight: Char,
        override val horizontal: Char,
        override val vertical: Char,
        override val topJoint: Char,
        override val middleJoint: Char,
        override val leftJoint: Char,
        override val rightJoint: Char,
        override val bottomJoint: Char
    ): BorderStyle(), WithBorders
}