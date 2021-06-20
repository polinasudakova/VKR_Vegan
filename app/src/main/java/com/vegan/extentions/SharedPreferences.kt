package com.vegan.extentions

import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.clear() = edit().clear().apply()

fun SharedPreferences.boolean(key: String, defValue: Boolean) =
    property(
        SharedPreferences::getBoolean,
        SharedPreferences.Editor::putBoolean,
        key,
        defValue
    )

fun SharedPreferences.int(key: String, defValue: Int) =
    property(
        SharedPreferences::getInt,
        SharedPreferences.Editor::putInt,
        key,
        defValue
    )

fun SharedPreferences.float(key: String, defValue: Float) =
    property(
        SharedPreferences::getFloat,
        SharedPreferences.Editor::putFloat,
        key,
        defValue
    )

fun SharedPreferences.long(key: String, defValue: Long) =
    property(
        SharedPreferences::getLong,
        SharedPreferences.Editor::putLong,
        key,
        defValue
    )

fun SharedPreferences.string(key: String, defValue: String) =
    property(
        SharedPreferences::getStringNotNull,
        SharedPreferences.Editor::putString,
        key,
        defValue
    )

private fun SharedPreferences.getStringNotNull(key: String, defValue: String) =
    getString(key, defValue) ?: defValue

fun SharedPreferences.nullableString(key: String, defValue: String? = null) =
    property(
        SharedPreferences::getString,
        SharedPreferences.Editor::putString,
        key,
        defValue
    )

fun SharedPreferences.stringSet(key: String, defValue: Set<String>) =
    property(
        SharedPreferences::getStringSetNotNull,
        SharedPreferences.Editor::putStringSet,
        key,
        defValue
    )

private fun SharedPreferences.getStringSetNotNull(key: String, defValue: Set<String>) =
    getStringSet(key, defValue) ?: defValue

inline fun <reified T> SharedPreferences.json(key: String, defValue: T) =
    property(
        SharedPreferences::getJson,
        SharedPreferences.Editor::putJson,
        key,
        defValue
    )

inline fun <reified T> SharedPreferences.getJson(key: String, defValue: T): T =
    getString(key, null)?.let { jsonString ->
        if (jsonString.isEmpty()) {
            defValue
        } else {
            try {
                Json.decodeFromString<T>(jsonString)
            } catch (e: Exception) {
                defValue
            }
        }
    } ?: defValue

inline fun <reified T> SharedPreferences.Editor.putJson(
    key: String,
    value: T
): SharedPreferences.Editor = putString(key, Json.encodeToString(value))

fun <T> SharedPreferences.property(
    get: SharedPreferences.(String, T) -> T,
    set: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor,
    key: String,
    defValue: T
) = object : ReadWriteProperty<Any?, T> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return get(key, defValue)
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        edit().set(key, value).apply()
    }

}

