package br.com.jose.hydrationreminder.data.settings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import br.com.jose.hydrationreminder.Settings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer: Serializer<Settings> {
    override val defaultValue: Settings
        get() = Settings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Settings {
      try {
          return Settings.parseFrom(input)
      } catch (e : InvalidProtocolBufferException) {
          throw CorruptionException("Cannot read proto.", e)
      }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) =
        t.writeTo(output)

    val Context.settingsDataStore: DataStore<Settings> by dataStore(
        "settings.pb",
        SettingsSerializer
    )
}