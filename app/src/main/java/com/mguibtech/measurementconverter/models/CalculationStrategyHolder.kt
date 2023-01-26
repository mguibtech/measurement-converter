package com.mguibtech.measurementconverter.models

import com.mguibtech.measurementconverter.models.strategies.CalculationStrategies

data class CalculationStrategyHolder (
    val name: String,
    val calculationStrategy: CalculationStrategies
    )