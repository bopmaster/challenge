package com.example.challenge.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.example.challenge.R

class ResultDialog() : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle(getString(R.string.results))
        val rootView = inflater.inflate(R.layout.result_dialog, container, false)
        val resultList = arguments?.getStringArray(RESULT_ARRAY)
        val listView = rootView.findViewById<ListView>(R.id.lv_result)
        listView!!.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, resultList)
        isCancelable = true
        return rootView
    }

    companion object {
        /**
         * Create a new instance of CustomDialogFragment, providing "resultList" as an argument.
         */
        const val RESULT_ARRAY = "RESULT_ARRAY"

        fun newInstance(result: Array<String>): ResultDialog {
            val resultFragment = ResultDialog()
            val args = Bundle()
            args.putStringArray(RESULT_ARRAY, result)
            resultFragment.arguments = args
            return resultFragment
        }
    }


}
