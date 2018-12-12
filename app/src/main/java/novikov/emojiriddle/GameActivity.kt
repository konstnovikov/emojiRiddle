package novikov.emojiriddle

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.Toast
import android.content.Context.MODE_PRIVATE
import novikov.emojiriddle.R.id.editText
import android.content.Context.MODE_PRIVATE
import novikov.emojiriddle.R.id.textView
import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files.exists




class GameActivity : AppCompatActivity() {

    private val riddles:Array<Array<String>> = arrayOf(
        arrayOf("one", "stadium", "not", "warrior"),
        arrayOf("book", "book", "book", "mother", "study"),
        arrayOf("strength", "stop", "intelligence"),
        arrayOf("first", "pancake", "thread"),
        arrayOf("fire", "right", "fire"),
        arrayOf("first", "guy","village"),
        arrayOf("poop", "right", "king"),
        arrayOf("intelligence", "mountain", "not", "walk", "intelligence", "mountain", "around"),
        arrayOf("cheese", "sunflower", "slide"),
        arrayOf("hare", "hare", "run", "oops"),
        arrayOf("shirt", "baby"),
        arrayOf("swan", "water"),
        arrayOf("silence", "first"),
        arrayOf("clock", "money"),
        arrayOf("not", "worker", "not", "tableware"),
        arrayOf("guy", "guy", "guy", "guy", "guy", "guy", "guy", "turtle", "not", "timer"),
        arrayOf("bear", "ear", "animalsteps"),
        arrayOf("thumbsup", "spoon", "soup"),
        arrayOf("gift", "horse", "not", "monocle", "tooth"),
        arrayOf("fight", "sandglassend", "not", "fist"),
        arrayOf("night", "right", "sunrise", "intelligence"),
        arrayOf("apple", "not", "rocket", "falling", "tree"),
        arrayOf("bomb", "not", "build"),
        arrayOf("not", "fire", "not", "steam")
    )
    val answer = arrayOf(
        "Один в поле не воин.",
        "Повторенье - мать ученья.",
        "Сила есть, ума не надо.",
        "Первый блин комом.",
        "Из огня да в полымя.",
        "Первый парень на деревне.",
        "Из грязи в князи.",
        "Умный в гору не пойдёт, умный гору обойдёт.",
        "Как сыр в масле кататься.",
        "За двумя зайцами погонишься - ни одного не поймаешь.",
        "В рубашке родился.",
        "Как с гуся вода.",
        "Молчание - золото.",
        "Время - деньги.",
        "Кто не работает, тот не ест.",
        "Семеро одного не ждут.",
        "Медведь на ухо наступил.",
        "Дорога ложка к обеду.",
        "Дарёному коню в зубы не смотрят.",
        "После драки кулаками не машут.",
        "Утро вечера мудренее.",
        "Яблоко от яблони недалеко падает.",
        "Ломать - не строить.",
        "Нет дыма без огня."
    )



    private val indArray = riddles.indices.shuffled()

    private val achievementNames:Array<String> = ArrayOf(
        "Новичок: Отгадана первая поговорка",
        "Отгадано 5 поговорок",
        "Отгадано 10 поговорок",
        "Отгаданы все поговорки",
        "Отгадано 5 поговорок подряд",
        "Отгадано 10 поговорок подряд",
        "5 попыток на одну поговорку",
        "10 попыток на одну поговорку",
        "Пропущено 5 поговорок",
        "Пропущено 10 поговорок"
    )
    private val unlockedAchievements: BooleanArray =  BooleanArray(achievementNames.size)
    val maxWidth = 100

    var currentRiddleInd = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        drawMainScreen(indArray[currentRiddleInd])
        supportActionBar?.hide()

        val answerView = findViewById<AutoCompleteTextView>(R.id.editText)

        val inputStream = this.resources.openRawResource(R.raw.dictionary)
        val adageList = mutableListOf<String>()
        inputStream.bufferedReader(charset("Windows-1251")).useLines { lines -> lines.forEach { adageList.add(it)} }
        adageList.forEach{println(">  $it")}
        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, adageList
        )
        answerView.setAdapter(adapter)

    }

    fun giveAnswerButtonReaction(view: View){
        hideKeyboardFrom(this, view)
        val editText: EditText = findViewById(R.id.editText)
        val ans:String = editText.text.toString()

        var answerReactionString: String
        var rightAnswerGiven: Boolean
        if (checkAnswerString(ans, answer[indArray[currentRiddleInd]])) {
            answerReactionString = "Ура! Правильно!"
            rightAnswerGiven = true
        }
        else {
            answerReactionString = "Даже не близко!"
            rightAnswerGiven = false
        }
        val popupMessage = Toast.makeText(this, answerReactionString, Toast.LENGTH_SHORT)
        val group = popupMessage.getView() as ViewGroup
        val messageTextView = group.getChildAt(0) as TextView
        messageTextView.textSize = 25f
        if (rightAnswerGiven) {
            rightAnswer(view)
//            popupMessage.show()
//
//            skipRiddleButtonReaction(view)
        }
        else
            showError()

    }
    fun skipRiddleButtonReaction(view: View){
        currentRiddleInd += 1
        if (currentRiddleInd >= riddles.size) {
            finish()
            outOfRiddlesRespond(view)
        }
        else
            drawMainScreen(indArray[currentRiddleInd])
    }

    private fun drawMainScreen(numberOfRiddle: Int){
        val currentEmojiList = riddles[numberOfRiddle];
        var sumWidth = 1000000
        val emojiTable:TableLayout = findViewById(R.id.wholeTable);

        if (emojiTable.childCount > 0)
            emojiTable.removeAllViews()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x



        for (i in currentEmojiList.indices) {
            val image = ImageView(this)
            image.setImageResource(getImageId(this, currentEmojiList[i].toString()))
            image.adjustViewBounds = true
            image.maxWidth = maxWidth

            if (sumWidth + maxWidth > width) {
                val tableRow = TableRow(this)
                emojiTable.addView(tableRow)
                sumWidth = 0
            }
            sumWidth += maxWidth


            val numberOfRows = emojiTable.childCount
            val lastRowView = emojiTable.getChildAt(numberOfRows - 1)
            val lastRow: TableRow = lastRowView as TableRow
            lastRow.addView(image)
        }
        val answerTextField:EditText = findViewById(R.id.editText)
        answerTextField.text.clear()

        val editText:EditText = findViewById(R.id.editText)
        editText.setBackgroundColor(Color.WHITE)

        val ansButton:Button = findViewById(R.id.ansButton)
        ansButton.isClickable = true

        val nextButton:Button = findViewById(R.id.skipButton)
        nextButton.text = "ПРОПУСТИТЬ"
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources.getIdentifier("drawable/$imageName", null, context.packageName)
    }
    private fun checkAnswerString(ans: String, rightAns: String): Boolean {
        var ansLoc = ans.toLowerCase()
        val re = Regex("[^А-Яа-я0-9]")
        ansLoc = re.replace(ansLoc, "")

        var rightAnsLoc = re.replace(rightAns, "")
        rightAnsLoc = rightAnsLoc.toLowerCase()
        if (ansLoc == rightAnsLoc)
            return true
        return false
    }



    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun outOfRiddlesRespond(view: View) {
        val intent = Intent(this, OutOfRiddlesActivity::class.java)
        startActivity(intent)
    }

    fun eraseText(view: View) {
        val editText:EditText = findViewById(R.id.editText)
        editText.text.clear()
    }

    private fun rightAnswer(view:View){
        val editText:EditText = findViewById(R.id.editText)
        editText.setBackgroundColor(Color.GREEN)

        val nextButton:Button = findViewById(R.id.skipButton)
        nextButton.text = "СЛЕДУЮЩАЯ ПОГОВОРКА"

        val ansButton:Button = findViewById(R.id.ansButton)
        ansButton.isClickable = false

        val handler = Handler()
        handler.postDelayed({ skipRiddleButtonReaction(view) }, 1000)
    }

    private fun showError() {
        val editText:EditText = findViewById(R.id.editText)

//        val set: AnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.temporaryredbackground)
//            .apply {
//                setTarget(editText)
//                start()
//            }
        Log.i("info","button clicked")
        val set: AnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.temporaryredbackground)
            .apply {
                setTarget(editText)
                start()
            } as AnimatorSet

    }

    private fun saveAchivements() {
        try {
            // Open Stream to write file.
            val context:Context = this;
            val out = context.openFileOutput("achievements.txt", MODE_PRIVATE)
            out.write(unlockedAchievements)
            out.
            out.close()
            Toast.makeText(this, "File saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error:" + e.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun readAchievements() {
        if (fileExist("achievements.txt")){

            try {
                // Open stream to read file.
                val context: Context = this
                val inputStream = context.openFileInput("achievements.txt")

                val br = BufferedReader(InputStreamReader(inputStream))

                val sb = StringBuilder()
                var s: String? = null
                while ((s = br.readLine()) != null) {
                    sb.append(s).append("\n")
                }
                this.textView.setText(sb.toString())

            } catch (e: Exception) {
                Toast.makeText(this, "Error:" + e.message, Toast.LENGTH_SHORT).show()
            }
        }
        else{
            for (i in achievementNames.indices){
                unlockedAchievements[i] = false
            }

        }

    }

    fun fileExist(fname: String): Boolean {
        val context:Context = this;
        val file = context().getFileStreamPath(fname)
        return file.exists()
    }
}
