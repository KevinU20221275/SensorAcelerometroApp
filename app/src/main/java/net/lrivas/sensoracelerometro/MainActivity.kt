package net.lrivas.sensoracelerometro

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager : SensorManager
    private lateinit var sensorAcelerometro : Sensor
    private lateinit var sensorEventListener : SensorEventListener
    private lateinit var lblValores : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lblValores = findViewById(R.id.lblValores)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAcelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ?: run {
            lblValores.text = "El dispositivo no tiene acelerometro"
            return
        }

        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                lblValores.text = "X: $x\nY: $y\nZ: $z"
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(sensorEventListener, sensorAcelerometro, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}