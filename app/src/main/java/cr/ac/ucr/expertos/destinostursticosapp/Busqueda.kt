package cr.ac.ucr.expertos.destinostursticosapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_busqueda.*

    class Busqueda : Fragment(R.layout.fragment_busqueda), CallbackListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonShowDialog.setOnClickListener { showDialog() }
    }

        private fun showDialog() {
            val dialogFragment = Resultados(this)
            fragmentManager?.let { dialogFragment.show(it, "signature") }
        }

        override fun onDataReceived(data: String) {
            //textView.text = data
        }

    }