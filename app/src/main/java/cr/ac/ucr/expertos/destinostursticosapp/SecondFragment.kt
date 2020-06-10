package cr.ac.ucr.expertos.destinostursticosapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_second.*

    class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button?.setOnClickListener {
            val dialog: DialogFragment = FullscreenDialog.newInstance()
            dialog as FullscreenDialog
                dialog.setCallback(FullscreenDialog.Callback() {
                fun onActionClick(name: String?) {
                    Toast.makeText(getActivity(), name,Toast.LENGTH_SHORT).show();
                }
            })
            getActivity()?.getSupportFragmentManager()?.let { it1 -> dialog.show(it1, "tag") }
        }
    }
}