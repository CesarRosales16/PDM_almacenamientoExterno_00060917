package com.naldana.ejemplo11

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.os.Environment.getExternalStorageDirectory
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO (1) Obtener acceso a Shared Preference
        // TODO (2) Obtener Shared Preference desde la actividad
        // TODO (3) Especificar el nombre del archivo de preferencia (si se esta usando mas de uno)
        // TODO (3.1) Cuando se necesita solamente un archivo de preferencia en el app llamar a getPreferences con un paramentro
        // TODO (4) Asignar que la preferencias se leeran o escribiran en modo privado (Para mantender privado las configuración)
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)


        bt_save.setOnClickListener {
            // TODO (5) Para escribir valores en shared preference
            with(sharedPref.edit()) {
                putString(
                    getString(R.string.save_email_key),
                    et_option.text.toString()
                ) // TODO (6) Se guardan en formato clave valor
                commit() // TODO (7) Confirma que se guarden todos los elementos añadidos

            }

            tv_data.text = et_option.text.toString() // Solamente para mostrar el valor de inmediato
        }


        // TODO (8) Para leer basta con ejecutar el metodo getXXXX y definir el valor por defecto si no existe
        val email = sharedPref.getString(getString(R.string.save_email_key), "")

        tv_data.text = email


        bt_write_internal.setOnClickListener {
            // TODO (9) openFileOutput crea un archivo y un InputStream para escribir en el
            val filename = "email.txt"
            val fileContent = "email: $email"

            // TODO (10) Al usar use obtenemos el fileInput que devuelve FileOutputStream
            // TODO (11) use cierra el FileOutputStream y maneja la exception a nivel de bloque

            openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContent.toByteArray())
            }

        }


        bt_read_internal.setOnClickListener {
            // TODO (12) Abrir un archivo existente
            val filename = "email.txt"
            openFileInput(filename).use {
                val text = it.bufferedReader().readText() // TODO (13) Se lee todo el contenido
                tv_data.text = text
            }
        }

        bt_write_external.setOnClickListener {
            val extfilename = "externalFile.txt"
            val contenido = et_option.text.toString()
            //val fileContent = email
            try {
                val tarjeta = getExternalStorageDirectory()
                //Toast.makeText(this,tarjeta.absolutePath,Toast.LENGTH_LONG).show();
                val file = File(tarjeta.absolutePath, extfilename)
                val fileOutPutStream = FileOutputStream(file)
                fileOutPutStream.write(contenido.toByteArray())
                fileOutPutStream.flush()
                fileOutPutStream.close()
                Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();
            } catch (e: IOException) {
                Toast.makeText(
                    this, "No se pudo grabar",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        bt_read_external.setOnClickListener {
            val extfilename = "externalFile.txt"
            val tarjeta = getExternalStorageDirectory()
            val file = File(tarjeta.absolutePath, extfilename)
            try {
                val fIn = FileInputStream(file)
                val archivo = InputStreamReader(fIn)
                val br = BufferedReader(archivo)
                var linea = br.readLine()
                var todo = ""
                while (linea != null) {
                    todo = "$todo$linea "
                    linea = br.readLine()
                }
                br.close()
                archivo.close()
                tv_data.text = todo
                Toast.makeText(
                    this, todo,
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: IOException) {
                Toast.makeText(
                    this, "No se pudo leer",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


}
