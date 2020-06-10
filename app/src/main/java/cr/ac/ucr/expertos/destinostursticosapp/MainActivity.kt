package cr.ac.ucr.expertos.destinostursticosapp


import android.os.AsyncTask
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()

        setCurrentFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miMessages -> setCurrentFragment(secondFragment)
            }
            true
        }

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

        val adapter = ListAdapte(this, list)
        presidents_list.adapter = adapter
    }

    private fun setCurrentFragment(fragment : Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, fragment)
                commit()
            }
}
