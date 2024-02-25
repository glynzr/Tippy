package com.example.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_SEEKBAR_VALUE = 15
private const val INITIAL_DESCRIPTION="Good"
class MainActivity : AppCompatActivity() {
    private lateinit var baseValue: EditText
    private lateinit var percentValue: SeekBar
    private lateinit var percent: TextView
    private lateinit var tipValue: TextView
    private lateinit var totalValue: TextView
    private lateinit var description:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseValue = findViewById(R.id.baseValue)
        percentValue = findViewById(R.id.percentValue)
        percent = findViewById(R.id.percent)
        tipValue = findViewById(R.id.tipValue)
        totalValue = findViewById(R.id.totalValue)
        description=findViewById(R.id.description)

        percent.text = "$INITIAL_SEEKBAR_VALUE%"
        percentValue.progress = INITIAL_SEEKBAR_VALUE
        description.text= INITIAL_DESCRIPTION
        percentValue.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "Progress bar $progress")
                computeTipAndTotal()
                updateDescription(progress)
                percent.text = "$progress%"

                // Update UI or perform any action based on seek bar progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // You can implement functionality here if needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // You can implement functionality here if needed
            }
        })

        baseValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // You can implement functionality here if needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // You can implement functionality here if needed
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "$s")
                computeTipAndTotal()
                // Implement logic to calculate tip and total based on the changed base value
            }
        })
    }

    private fun updateDescription(progress: Int) {
        val result=when(progress){
            in 0..9 ->"Poor"
            in 10..14 ->"Acceptable"
            in 15..19->"Good"
            in 20..24 ->"Great"
            else ->"Amazing"
        }
        description.text=result
        val color=ArgbEvaluator().evaluate(
           progress/percentValue.max.toFloat(),
            ContextCompat.getColor(this,R.color.red),
            ContextCompat.getColor(this,R.color.green)
        ) as Int
        description.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        if(baseValue.text.isEmpty()){
            tipValue.text=""
            totalValue.text=""
            return
        }
        val tip= baseValue.text.toString().toDouble()*percentValue.progress/100
        tipValue.text="%.2f".format(tip)
        totalValue.text="%.2f".format(tip+baseValue.text.toString().toDouble())
    }
}
