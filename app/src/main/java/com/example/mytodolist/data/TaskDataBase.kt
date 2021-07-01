package com.example.mytodolist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mytodolist.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao


    class Callback @Inject constructor(
        private val dataBase: Provider<TaskDataBase>,
        @ApplicationScope private val applicationScope: CoroutineScope
        ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operations
            val dao = dataBase.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Помыть посуду","Помыть без средства"))
                dao.insert(Task("Побегать","В лесу", completed = true))
                dao.insert(Task("Сходить в магазин","купить: макароны,сыр,ветчину"))
                dao.insert(Task("Учёба","почитать статьи про Dagger 2", important = true))
                dao.insert(Task("Доделать проект","доделать красиыве переходы", important = true))
            }

        }

    }

}

