package cr.ac.ucr.expertos.destinostursticosapp

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_resultados.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class Resultados(private val callbackListener: CallbackListener) : DialogFragment() {
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




        val url = "https://mysafeinfo.com/api/data?list=presidents&format=json"

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
        val list = ArrayList<President>()
        var x = 0

        while (x < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(x)

            list.add(President(
                jsonObject.getInt("ID"),
                jsonObject.getString("FullName"),
                jsonObject.getString("Party"),
                jsonObject.getString("Terms")
            )
            )

            x++
        }

        //val adapter = getActivity()?.getApplicationContext()?.let { ListAdapte(it, list) }
        //presidents_list.adapter = adapter



        val customAdptor = CustomAdptor(this, list)
        listview.adapter=customAdptor

        listview.setOnItemClickListener{ parent, view, position, id ->
            Toast.makeText(getActivity()?.getApplicationContext(), "You Clicked:"+" "+list.get(position), Toast.LENGTH_SHORT).show()
        }
    }
}

class CustomAdptor(private val context: DialogFragment, private val lista: ArrayList<President>): BaseAdapter() {
    //Array of fruits names
    //var names = arrayOf("Apple", "Strawberry", "Pomegranates", "Oranges", "Watermelon", "Apple", "Strawberry", "Pomegranates", "Oranges", "Watermelon")
    //Array of fruits desc
    //var desc = arrayOf("Malus Domestica", "Fragaria Ananassa ", "Punica Granatum", "Citrus Sinensis", "Citrullus Vulgaris", "Musa Acuminata", "Actinidia Deliciosa", "Solanum Lycopersicum", "Vitis vinifera", "Citrullus Vulgaris")

    //Array of fruits images
    var image = intArrayOf(R.drawable.apple, R.drawable.strawberry, R.drawable.pomegranates, R.drawable.oranges, R.drawable.watermelon)


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.layoutInflater
        val view1 = inflater.inflate(R.layout.row_data,null)
        val fimage = view1.findViewById<ImageView>(R.id.fimageView)
        var fName = view1.findViewById<TextView>(R.id.fName)
        var fDesc = view1.findViewById<TextView>(R.id.fDesc)
        fimage.setImageResource(image[p0])


        fName.setText(lista.get(p0).name)
        fDesc.setText(lista.get(p0).politic)



        //Log.d(p0.toString(), lista.get(p0).name)
        return view1
    }

    override fun getItem(p0: Int): Any {
        return image[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return image.size
    }
}