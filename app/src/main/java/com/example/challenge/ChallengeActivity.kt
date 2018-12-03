package com.example.challenge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.result.ResultDialog

class ChallengeActivity : AppCompatActivity(), ChallengeView {

    private val presenter: ChallengePresenter by lazy { ChallengePresenter(this) }

    companion object {
        val OPEN_FILE = 2809
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnOpenFile = findViewById<Button>(R.id.btn_open_file)
        val btnDownload = findViewById<Button>(R.id.btn_download)
        val etTextInput = findViewById<EditText>(R.id.et_text_input)
        val etLinkInput = findViewById<EditText>(R.id.et_link_input)
        val btnSolveChallenge = findViewById<Button>(R.id.btn_solve_challenge)

        btnOpenFile.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), OPEN_FILE)
        }

        btnDownload.setOnClickListener {
            presenter.fetchString(etLinkInput.text.toString())
        }

        btnSolveChallenge.setOnClickListener {
            presenter.solveStringInput(etTextInput.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_FILE) {
            when (resultCode) {
                1 -> {
                    /// Process file
                }
                else -> {
                    /// Do nothing
                }

            }
        }
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showResult(list: Array<String>) {
        val fm = supportFragmentManager
        val resultDialog = ResultDialog.newInstance(list)
        resultDialog.show(fm, "")
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, LENGTH_SHORT).show()
    }
}
