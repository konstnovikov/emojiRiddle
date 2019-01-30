package novikov.emojiriddle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    fun playButtonRespond(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
    fun achievementsButtonRespond(view: View) {
        val intent = Intent(this, AchivementsActivity::class.java)
        startActivity(intent)
    }
    fun rulesButtonRespond(view: View) {
        val intent = Intent(this, RulesActivity::class.java)
        startActivity(intent)
    }
    fun aboutButtonRespond(view: View) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
    fun exitButtonRespond(view: View){
        exitProcess(0)
    }
}
