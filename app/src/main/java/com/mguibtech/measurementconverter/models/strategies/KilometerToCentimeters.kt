package com.mguibtech.measurementconverter.models.strategies

class KilometerToCentimeters : CalculationStrategies {
    override fun calculate(value: Double): Double = value * 100_000

    override fun getResultLabel(isPlural: Boolean): String =
        if (isPlural) "centimetros" else "centimetro"
}