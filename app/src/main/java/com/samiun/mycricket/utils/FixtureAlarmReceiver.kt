import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.samiun.mycricket.model.fixture.FixtureEntity
import java.text.SimpleDateFormat

class FixtureAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val fixtureId = intent?.getIntExtra("fixtureId", -1)
        if (fixtureId != null && fixtureId != -1) {
            // Retrieve the fixture from the database
            val fixture = getFixtureById(fixtureId)

            // Create a notification
            val notification = createNotification(context!!, fixture)

            // Send the notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(fixtureId, notification)
        }
    }

    private fun getFixtureById(fixtureId: Int): FixtureEntity {
        // Implement your database retrieval logic here

    }

    private fun createNotification(context: Context, fixture: FixtureEntity): Notification? {
        // Implement your notification creation logic here
        return  null
    }

    fun scheduleFixtureAlarm(context: Context, fixture: FixtureEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Calculate the alarm time
        val startingAtMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .parse(fixture.starting_at)
            .time
        val alarmTime = startingAtMillis - (15 * 60 * 1000) // 15 minutes before starting_at

        // Create the intent for the alarm receiver
        val intent = Intent(context, FixtureAlarmReceiver::class.java)
        intent.putExtra("fixtureId", fixture.id)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixture.id!!,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Schedule the alarm
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }

}
