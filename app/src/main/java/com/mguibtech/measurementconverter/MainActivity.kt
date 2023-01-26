package com.mguibtech.measurementconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.mguibtech.measurementconverter.models.CalculationStrategyHolder
import com.mguibtech.measurementconverter.models.Calculator
import com.mguibtech.measurementconverter.models.strategies.KilometerToCentimeters
import com.mguibtech.measurementconverter.models.strategies.KilometerToMeterStrategy
import com.mguibtech.measurementconverter.models.strategies.MeterToKilometerStrategy
import com.mguibtech.measurementconverter.models.strategies.MetersToCentimetersStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var edtValue: EditText
    private lateinit var spConversions : Spinner

    val supportedCaculationStrategies = arrayListOf(
        CalculationStrategyHolder("Quilômetro para centimetros", KilometerToCentimeters()),
        CalculationStrategyHolder("Quilômetro para metros", KilometerToMeterStrategy()),
        CalculationStrategyHolder("Metors para centimetros", MetersToCentimetersStrategy()),
        CalculationStrategyHolder("Metors para kilômetros", MeterToKilometerStrategy())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var value = 0.0
        var position = 0

        savedInstanceState?.let {
            value = it.getDouble("VALUE")
            position = it.getInt("POSITION")
        }

        initUI()
        setUi(value, position)
        setActions()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistableBundle: PersistableBundle) {
        super.onSaveInstanceState(outState)
        try {
            outState.putDouble("VALUE", edtValue.text.toString().toDouble())
        }catch (e:NumberFormatException){

        }
        outState.putInt("POSITION", spConversions.selectedItemPosition)
    }

    private fun initUI() {
        spConversions = findViewById(R.id.spConversions)
        edtValue = findViewById(R.id.edtValue)

    }

    private fun setActions() {
        val btnConvert : Button = findViewById(R.id.btnConvert)
        val edtValue : EditText = findViewById(R.id.edtValue)
        btnConvert.setOnClickListener {

            try {
                val value = edtValue.text.toString().toDouble()
                val calculationStrategy = supportedCaculationStrategies[
                        spConversions.selectedItemPosition
                ].calculationStrategy

                Calculator.setCalculationStrategy(
                    calculationStrategy
                )
                val result = Calculator.calculate(value)
                val label = calculationStrategy.getResultLabel(result != 1.toDouble())

               showResult(result, label)

            }catch (e: java.lang.NumberFormatException){
                edtValue.error = "Valor INVÁLIDO"
                edtValue.requestFocus()
            }

        }

        val btnClean : Button = findViewById(R.id.btnClean)
        btnClean.setOnClickListener {
            edtValue.setText("")
            edtValue.error = null

            spConversions.setSelection(0)
        }
    }

    private fun showResult(result: Double, label: String) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("RESULT", result)
        intent.putExtra("LABEL", label)

        startActivity(intent)
    }

    private fun setUi( value: Double, position : Int) {
        edtValue.setText(value.toString())

        val spAdapter = ArrayAdapter(this, R.layout.res_spinner_item, getSpinnerData())
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spConversions.adapter = spAdapter
        spConversions.setSelection(position)
    }

    fun getSpinnerData(): MutableList<String> {
        val spinnerData = mutableListOf<String>()
        supportedCaculationStrategies.forEach{
            spinnerData.add(it.name)
        }
        return spinnerData
    }
}