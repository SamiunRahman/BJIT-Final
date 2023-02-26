import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.samiun.mycricket.R
import com.samiun.mycricket.model.fixture.FixtureEntity
import com.samiun.mycricket.ui.HomeFragment
import com.samiun.mycricket.ui.MainActivity
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
        val fragment = HomeFragment()
        return fragment.getFixtureByid(fixtureId)

    }

    private fun createNotification(context: Context, fixture: FixtureEntity): Notification {
        // Implement your notification creation logic here
        // Create a notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "FixtureNotificationChannel"
            val channelName = "Fixture Notification Channel"
            val channelDescription = "Notification channel for fixture reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Create the intent for the notification tap action
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            fixture.id!!,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, "FixtureNotificationChannel")
            .setContentTitle("Upcoming Fixture")
            .setContentText("Match is starting soon!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        return notificationBuilder.build()
    }

    fun scheduleFixtureAlarm(context: Context, fixture: FixtureEntity) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Calculate the alarm time
        val startingAtMillis = fixture.starting_at?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .parse(it)
                ?.time
        }
        val alarmTime = startingAtMillis?.minus((15 * 60 * 1000)) // 15 minutes before starting_at

        // Create the intent for the alarm receiver
        val intent = Intent(context, FixtureAlarmReceiver::class.java)
        intent.putExtra("fixtureId", fixture.id)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            fixture.id!!,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Schedule the alarm
        if (alarmTime != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
        }
    }

}
