package cr.ac.ucr.expertos.destinostursticosapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_resultados.*
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

    }
}