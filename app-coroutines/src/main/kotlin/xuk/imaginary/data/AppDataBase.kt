package xuk.imaginary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import xuk.imaginary.data.db.User
import xuk.imaginary.data.db.UserDao

/**
 * 数据库文件
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
  // 得到UserDao
  abstract fun userDao(): UserDao

  companion object {
    @Volatile
    private var instance: AppDataBase? = null

    fun getInstance(context: Context): AppDataBase {
      return instance ?: synchronized(this) {
        instance ?: buildDataBase(context)
            .also {
              instance = it
            }
      }
    }

    private fun buildDataBase(context: Context): AppDataBase {
      return Room
          .databaseBuilder(context, AppDataBase::class.java, "xukDemo-database")
          .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
              super.onCreate(db)

//              // 读取鞋的集合
//              val request = OneTimeWorkRequestBuilder<ShoeWorker>().build()
//              WorkManager.getInstance().enqueue(request)
            }
          })
          .build()
    }
  }
}