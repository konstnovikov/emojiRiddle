package novikov.emojiriddle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.io.File
import kotlin.system.exitProcess
import android.R.id.edit
import android.content.SharedPreferences
import android.preference.PreferenceManager



class MainActivity : AppCompatActivity() {
    private var preferenceHelper: PreferenceHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val t = Thread(Runnable {
            //  Initialize SharedPreferences
            val getPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext())


            //  Create a new boolean and preference and set it to true
            preferenceHelper = PreferenceHelper(this)
            //preferenceHelper!!.putIntro("")
            val isFirstStart = preferenceHelper!!.getIntro().equals("");
            //val isFirstStart = true;
            //val isFirstStart = getPrefs.getBoolean("firstStart", true)

            //  If the activity has never started before...
            if (isFirstStart) {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)

            }
        })

        // Start the thread
        t.start()


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
