package novikov.emojiriddle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class RulesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        supportActionBar?.title = "Правила игры"
    }
}
