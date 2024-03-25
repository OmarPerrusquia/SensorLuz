package com.example.sensorluz

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener{

    private lateinit var gestorSensor: SensorManager
    private lateinit var sensorLuz: Sensor
    private lateinit var  fondoPantalla: RelativeLayout
    private lateinit var textoLuz: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Inicializar el gesto de sensor el cual va a acceder a los sensores del dispositivo
        // este va a obtener el sistema de servicios y lo cinvierte el servicio obtenido a un objeto
        gestorSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager


        sensorLuz = gestorSensor.getDefaultSensor(Sensor.TYPE_LIGHT)!!

        // Inicializar el relative Layout

        fondoPantalla = findViewById(R.id.fondo)
        textoLuz = findViewById(R.id.texto)


    }


    // Metodo cuando la actividad pasa a primer plano

    override fun onResume() {
        super.onResume()

        // Para esuchar los cambios del sensor de luz
        gestorSensor.registerListener(this,sensorLuz,SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        // Anular el registro del evento Listerner cuando el sensor este en pausa

        gestorSensor.unregisterListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {


            if(event.sensor.type == Sensor.TYPE_LIGHT){
                val intensidadLuz = event.values[0]

                textoLuz.text = "Intensidad de luz: $intensidadLuz luz "

                cambiarColor(intensidadLuz)
            }// fin del if
        } // fin del if evente
    } // fin de la funcion

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    // Cambiar color
    private fun cambiarColor(intensidadLuz:Float){

        val color = calcularColor(intensidadLuz)
        fondoPantalla.setBackgroundColor(color)
    }

    private fun calcularColor(intensidadLuz: Float): Int {

        val rangoAzul = 1000f


        if (intensidadLuz >= rangoAzul) {
            return Color.rgb(77, 210, 255)

        } else if (intensidadLuz <= 800f && intensidadLuz> 600f ){
            return Color.rgb(153, 102, 255)
        }else if (intensidadLuz <= 600 && intensidadLuz> 400f ){
            return  Color.rgb(209, 26, 255)
        }else if (intensidadLuz <= 400 && intensidadLuz> 200f){
            return Color.rgb(255, 0, 102)
        }else if (intensidadLuz <= 200f){
            return Color.rgb(255, 80, 80)
        }

        return Color.BLACK

    }// fin de la funcion


} // no tocar