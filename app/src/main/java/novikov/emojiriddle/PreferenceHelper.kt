package novikov.emojiriddle


import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val INTRO = "intro"
    private val app_prefs: SharedPreferences


    init {
        app_prefs = context.getSharedPreferences(
            "shared",
            Context.MODE_PRIVATE
        )
    }

    fun putIntro(loginorout: String) {
        val edit = app_prefs.edit()
        edit.putString(INTRO, loginorout)
        edit.commit()
    }

    fun getIntro(): String? {
        return app_prefs.getString(INTRO, "")
    }

}