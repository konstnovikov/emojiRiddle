package novikov.emojiriddle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OutOfRiddlesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_of_riddles)
        supportActionBar?.title = ""
    }
}
