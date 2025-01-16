package com.highbelief.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

object JsonHandler {
    private val gson = Gson()

    // JSON 파일을 읽어서 객체로 변환
    fun <T> readFromFile(file: File, type: Type): T? {
        return try {
            if (!file.exists()) {
                file.createNewFile()
                return null
            }
            val json = file.readText()
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Logger.error("JSON 파일 읽기 실패: ${e.message}")
            null
        }
    }

    // 객체를 JSON 형식으로 변환 후 파일에 저장
    fun <T> writeToFile(file: File, data: T) {
        try {
            val json = gson.toJson(data)
            file.writeText(json)
        } catch (e: Exception) {
            Logger.error("JSON 파일 쓰기 실패: ${e.message}")
        }
    }

    // JSON 데이터를 특정 타입으로 변환
    fun <T> fromJson(json: String, type: Type): T? {
        return try {
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Logger.error("JSON 파싱 실패: ${e.message}")
            null
        }
    }

    // 객체를 JSON 형식으로 변환
    fun <T> toJson(data: T): String {
        return try {
            gson.toJson(data)
        } catch (e: Exception) {
            Logger.error("JSON 변환 실패: ${e.message}")
            ""
        }
    }
}
