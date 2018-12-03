package com.example.challenge

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.challenge.result.ResultDialog


class ChallengeActivity : AppCompatActivity(), ChallengeView {

    private val presenter: ChallengePresenter by lazy { ChallengePresenter(this) }
    private lateinit var avLoading: LottieAnimationView
    private lateinit var btnDownload: Button

    companion object {
        const val OPEN_FILE = 2809
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnOpenFile = findViewById<Button>(R.id.btn_open_file)
        btnDownload = findViewById<Button>(R.id.btn_download)
        val etTextInput = findViewById<EditText>(R.id.et_text_input)
        val etLinkInput = findViewById<EditText>(R.id.et_link_input)
        val btnSolveChallenge = findViewById<Button>(R.id.btn_solve_challenge)
        avLoading = findViewById(R.id.av_loading)

        btnOpenFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
                    .setType("*/*")
            startActivityForResult(Intent.createChooser(intent, "Select a file"), OPEN_FILE)
        }

        btnDownload.setOnClickListener {
            presenter.fetchString(etLinkInput.text.toString())
        }

        btnSolveChallenge.setOnClickListener {
            if (etTextInput.text.toString().isNotEmpty()) {
                presenter.solveStringInput(etTextInput.text.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_FILE) {
            when (resultCode) {
                RESULT_OK -> {
                    /// Process file
                    val inputStream = contentResolver.openInputStream(data?.data)
                    presenter.solveFileInput(inputStream)
                }
            }
        }
    }

    override fun showProgress(isLoading: Boolean) {
        avLoading.visibility = if (isLoading) VISIBLE else GONE
        btnDownload.isEnabled = !isLoading
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
