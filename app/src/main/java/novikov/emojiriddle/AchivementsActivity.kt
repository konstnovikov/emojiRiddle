package novikov.emojiriddle

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import java.io.FileInputStream

class AchivementsActivity : AppCompatActivity() {

    private val achievementNames:Array<String> = arrayOf(
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

    private var unlockedAchievements: BooleanArray =  BooleanArray(achievementNames.size)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achivements)

        readAchievements()
        val achievementTable: TableLayout = findViewById(R.id.achievementTable);

        for (i in unlockedAchievements.indices){
            if (unlockedAchievements[i]){

                val image = ImageView(this)
                image.setImageResource(R.drawable.thumbsup)

                val tableRow = TableRow(this)
                tableRow.gravity = Gravity.CENTER_VERTICAL

                achievementTable.addView(tableRow)

                val numberOfRows = achievementTable.childCount
                val lastRowView = achievementTable.getChildAt(numberOfRows - 1)
                val lastRow: TableRow = lastRowView as TableRow
                lastRow.addView(image)
                val text = TextView(this)
                text.text = achievementNames[i]
                lastRow.addView(text)

            }
        }
    }


    private fun readAchievements() {
        if (fileExist(  "achievements.txt")){


            val file = this.getFileStreamPath("achievements.txt")
            val inputStream = FileInputStream(file)
            val fileLength = file.length().toInt()

            val data = ByteArray(fileLength)
            val output = BooleanArray(fileLength)

            inputStream.read(data)
            for (X in data.indices) {
                if (data[X].toInt() != 0) {
                    output[X] = true
                    continue
                }
                output[X] = false
            }
            unlockedAchievements = output
        }
        else{
            for (i in achievementNames.indices){
                unlockedAchievements[i] = false
            }

        }

    }

    private fun fileExist(fname: String): Boolean {
        val context: Context = this
        val file = context.getFileStreamPath(fname)
        return file.exists()
    }
}
