package com.indu.ai

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoryManager(context: Context) :
    SQLiteOpenHelper(context, "indu_memory.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE messages (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                role TEXT NOT NULL,
                message TEXT NOT NULL,
                created_at INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Future database upgrades
    }

    fun saveMessage(role: String, message: String) {
        val values = ContentValues().apply {
            put("role", role)
            put("message", message)
            put("created_at", System.currentTimeMillis())
        }

        writableDatabase.insert("messages", null, values)
    }

    fun getRecentMessages(limit: Int = 50): List<String> {
        val messages = mutableListOf<String>()

        val cursor = readableDatabase.rawQuery(
            "SELECT role, message FROM messages ORDER BY id DESC LIMIT ?",
            arrayOf(limit.coerceIn(1, 500).toString())
        )

        cursor.use {
            while (it.moveToNext()) {
                val role = it.getString(0)
                val message = it.getString(1)
                messages.add("$role: $message")
            }
        }

        return messages.reversed()
    }

    fun clearMemory() {
        writableDatabase.delete("messages", null, null)
    }
    }
