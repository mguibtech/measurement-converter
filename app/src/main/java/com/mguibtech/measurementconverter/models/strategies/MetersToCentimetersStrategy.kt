package com.mguibtech.measurementconverter.models.strategies

class MetersToCentimetersStrategy : CalculationStrategies {
    override fun calculate(value: Double): Double = value * 100


    override fun getResultLabel(isPlural: Boolean): String =
        if (isPlural) "centimetros" else "centimetros"
}