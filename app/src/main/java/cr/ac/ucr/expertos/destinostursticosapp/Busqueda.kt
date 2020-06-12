package cr.ac.ucr.expertos.destinostursticosapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_busqueda.*

    class Busqueda : Fragment(R.layout.fragment_busqueda), CallbackListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonShowDialog.setOnClickListener { showDialog() }

        spDestino()
        spPersonas()
        spEdades()
        spIntereses()
    }

        private fun spDestino() {
            val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listOf("Playa", "Ciudad", "Montaña", "Rural")) }
            if (adapter != null) {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spDestino.adapter = adapter

            spDestino.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    // either one will work as well
                    // val item = parent.getItemAtPosition(position) as String
                    val item = adapter?.getItem(position)
                }
            }
        }

        private fun spPersonas() {
            val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listOf("Familia", "Pareja", "Amigos", "Solo")) }
            if (adapter != null) {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spPersonas.adapter = adapter

            spPersonas.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    // either one will work as well
                    // val item = parent.getItemAtPosition(position) as String
                    val item = adapter?.getItem(position)
                }
            }
        }

        private fun spEdades() {
            val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listOf("De 18 a 35 años", "De 36 a 55 años", "Mayores o iguales a 56 años")) }
            if (adapter != null) {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spEdades.adapter = adapter

            spEdades.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    // either one will work as well
                    // val item = parent.getItemAtPosition(position) as String
                    val item = adapter?.getItem(position)
                }
            }
        }

        private fun spIntereses() {
            val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, listOf("Cultura", "Historia", "Relajación", "Actualidad", "Gastronomía", "Entretenimiento")) }
            if (adapter != null) {
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            spIntereses.adapter = adapter

            spIntereses.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    // either one will work as well
                    // val item = parent.getItemAtPosition(position) as String
                    val item = adapter?.getItem(position)
                }
            }
        }

        private fun showDialog() {
            val dialogFragment = Resultados(this, spDestino.selectedItem.toString())
            fragmentManager?.let { dialogFragment.show(it, "signature") }
        }

        override fun onDataReceived(data: String) {
            //textView.text = data
        }

    }