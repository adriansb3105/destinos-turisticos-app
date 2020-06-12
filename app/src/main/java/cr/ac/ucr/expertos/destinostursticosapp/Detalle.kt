package cr.ac.ucr.expertos.destinostursticosapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_resultados.*

    class Detalle(val destino: DestinoTuristico) : DialogFragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            isCancelable = false
            return inflater.inflate(R.layout.activity_detalle, container, false)
        }

        override fun getTheme(): Int {
            return R.style.DialogTheme
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            fullscreen_dialog_close.setOnClickListener {
                dismiss()
            }

            val nombre = view.findViewById<TextView>(R.id.nombre)
            val lugar = view.findViewById<TextView>(R.id.lugar)
            val descripcion = view.findViewById<TextView>(R.id.descripcion)

            nombre.setText(destino.nombre)
            lugar.setText(destino.lugar)
            descripcion.setText(destino.descripcion)
        }
    }