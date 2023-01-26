package com.mguibtech.measurementconverter.models

import com.mguibtech.measurementconverter.models.strategies.CalculationStrategies

object Calculator {
    private var calculationStrategy : CalculationStrategies? = null

    fun setCalculationStrategy(calculationStrategy: CalculationStrategies) {
        this.calculationStrategy = calculationStrategy
    }

    fun calculate(value : Double): Double{
        if(calculationStrategy == null)
            throw IllegalArgumentException("Calculation Strategy is not set")

        return calculationStrategy!!.calculate((value))
    }
}