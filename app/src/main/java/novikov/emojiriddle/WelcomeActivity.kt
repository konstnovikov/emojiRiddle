package novikov.emojiriddle

import android.app.Fragment
import android.content.Intent
//import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.github.paolorotolo.appintro.AppIntro

class WelcomeActivity : AppIntro() {

    private var preferenceHelper: PreferenceHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(this)
        if (preferenceHelper!!.getIntro().equals("no")) {
            this.finish()
        }

        addSlide(IntroFragment1() as androidx.fragment.app.Fragment)  //extend AppIntro and comment setContentView
        addSlide(IntroFragment2() as androidx.fragment.app.Fragment)
        supportActionBar?.hide()
    }

    override fun onSkipPressed(currentFragment: androidx.fragment.app.Fragment?) {
        super.onSkipPressed(currentFragment as androidx.fragment.app.Fragment)

        preferenceHelper!!.putIntro("no")
        finish()
    }

    override fun onDonePressed(currentFragment: androidx.fragment.app.Fragment?) {
        super.onDonePressed(currentFragment as androidx.fragment.app.Fragment)

        preferenceHelper!!.putIntro("no")

        finish()
    }

    //override
    fun onSlideChanged(oldFragment: Fragment?, newFragment: androidx.fragment.app.Fragment?) {
        super.onSlideChanged(oldFragment as androidx.fragment.app.Fragment, newFragment as androidx.fragment.app.Fragment)
        // Do something when the slide changes.
    }

}