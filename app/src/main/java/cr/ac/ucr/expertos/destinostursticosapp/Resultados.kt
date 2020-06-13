package cr.ac.ucr.expertos.destinostursticosapp

import android.app.PendingIntent.getActivity
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_resultados.*
import kotlinx.android.synthetic.main.fragment_busqueda.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


class Resultados(private val callbackListener: CallbackListener, var destino: String, var personas: String, var edad: String, var interes: String) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        return inflater.inflate(R.layout.activity_resultados, container, false)
    }
    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullscreen_dialog_close.setOnClickListener {
            //send back data to PARENT fragment using callback
            //callbackListener.onDataReceived(editText.text.toString())
            // Now dismiss the fragment
            dismiss()
        }

        //var listView = findViewById<ListView>(R.id.listview);

        when (destino) {
            "Playa" -> destino="1"
            "Ciudad" -> destino="2"
            "Montaña" -> destino="3"
            else -> {
                destino="4"
            }
        }

        when (personas) {
            "Familia" -> personas="1"
            "Pareja" -> personas="2"
            "Amigos" -> personas="3"
            else -> {
                personas="4"
            }
        }

        when (edad) {
            "De 18 a 35 años" -> edad="1"
            "De 36 a 55 años" -> edad="2"
            else -> {
                edad="3"
            }
        }

        when (interes) {
            "Cultura" -> interes="1"
            "Historia" -> interes="2"
            "Relajación" -> interes="3"
            "Actualidad" -> interes="4"
            "Gastronomía" -> interes="5"
            else -> {
                interes="6"
            }
        }

        //val url = "http://10.0.2.2:8000/api/atractivos/"+destino+"/b/c/d"
        val url = "https://destinos-turisticos-web.herokuapp.com/api/atractivos/"+destino+"/"+personas+"/"+edad+"/"+interes


        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            var text: String = ""
            val connection = URL(url[0]).openConnection() as HttpURLConnection

            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }

            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonString: String?) {
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<DestinoTuristico>()
        var x = 0

        while (x < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(x)

            list.add(
                DestinoTuristico(
                    jsonObject.getInt("id"),
                    jsonObject.getString("nombre"),
                    jsonObject.getString("imagen"),
                    jsonObject.getString("lugar"),
                    jsonObject.getString("ubicacion"),
                    jsonObject.getString("descripcion"),
                    jsonObject.getString("clase")
                )
            )

            x++
        }

        //val adapter = getActivity()?.getApplicationContext()?.let { ListAdapte(it, list) }
        //presidents_list.adapter = adapter



        val customAdptor = CustomAdptor(this, list)
        listview.adapter=customAdptor

        listview.setOnItemClickListener{ parent, view, position, id ->
            //Toast.makeText(getActivity()?.getApplicationContext(), "You Clicked:"+" "+list.get(position), Toast.LENGTH_SHORT).show()

            val dialogFragment = Detalle(list.get(position))
            fragmentManager?.let { dialogFragment.show(it, "detalle") }
        }
    }
}

class CustomAdptor(private val context: DialogFragment, private val lista: ArrayList<DestinoTuristico>): BaseAdapter() {
    //Array of fruits names
    //var names = arrayOf("Apple", "Strawberry", "Pomegranates", "Oranges", "Watermelon", "Apple", "Strawberry", "Pomegranates", "Oranges", "Watermelon")
    //Array of fruits desc
    //var desc = arrayOf("Malus Domestica", "Fragaria Ananassa ", "Punica Granatum", "Citrus Sinensis", "Citrullus Vulgaris", "Musa Acuminata", "Actinidia Deliciosa", "Solanum Lycopersicum", "Vitis vinifera", "Citrullus Vulgaris")

    //Array of fruits images
    //var images = arrayOf(R.drawable.oranges, R.drawable.watermelon)
    //var url = URL("https://cdn.forbes.com.mx/2019/09/Manuel-Antonio-Shitterstock.jpg")
    //var url = "https://cdn.forbes.com.mx/2019/09/Manuel-Antonio-Shitterstock.jpg"

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val view1 = inflater.inflate(R.layout.row_data,null)
        val fimage = view1.findViewById<ImageView>(R.id.fimageView)
        var fName = view1.findViewById<TextView>(R.id.fName)
        var fDesc = view1.findViewById<TextView>(R.id.fDesc)

        if(!lista.isEmpty()) {
            Picasso.get().load(lista.get(p0).imagen).into(fimage)
            fName.setText(lista.get(p0).nombre)
            fDesc.setText(lista.get(p0).lugar)
            //Log.d(lista.size.toString(), lista.size.toString())
        }

        return view1
    }

    override fun getItem(p0: Int): Any {
        return lista.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return lista.size
    }
}