package novikov.emojiriddle


import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(private val context: Context) {

    private val INTRO = "intro"
    private val RIDDLE_IND = "riddle_ind"
    private val app_prefs: SharedPreferences


    init {
        app_prefs = context.getSharedPreferences(
            "shared",
            Context.MODE_PRIVATE
        )
    }

    fun putRiddleInd(riddleInd: Int) {
        val edit = app_prefs.edit()
        edit.putInt(RIDDLE_IND, riddleInd)
        edit.commit()
    }

    fun getRiddleInd(): Int {
        return app_prefs.getInt(RIDDLE_IND, 0)
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