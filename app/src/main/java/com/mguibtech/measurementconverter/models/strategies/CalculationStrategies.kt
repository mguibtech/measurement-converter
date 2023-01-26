package com.mguibtech.measurementconverter.models.strategies

interface CalculationStrategies {
    fun calculate(value : Double): Double

    fun getResultLabel(isPlural : Boolean) : String

}