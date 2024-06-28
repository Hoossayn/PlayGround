package com.example.core.data.repository

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.example.core.common.injection.IoDispatcher
import com.example.core.model.ThemeConfig
import com.example.core.model.UserData
import com.example.playground.core.data.ThemeConfigProto
import com.example.playground.core.data.UserPreferences
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

interface LocalStorage {

    fun isLoggedIn(): Flow<Boolean>

    fun userData(): Flow<UserData>

    suspend fun saveUserSession(
        accountId: String,
        accessToken: String,
    )

    suspend fun saveGuestSession(guestSessionId: String)

    suspend fun setThemeConfig(config: ThemeConfig)

    suspend fun setUseDynamicColor(useDynamicColor: Boolean)

    suspend fun logout()

}

class UserPreferencesSerializer
@Inject
constructor() : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream,
    ) = t.writeTo(output)
}

@Singleton
class DefaultLocalStorage
@Inject
constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val userPreferences: DataStore<UserPreferences>,
) : LocalStorage {
    companion object {
        const val DATA_STORE_FILE_NAME = "auth_user.pb"
    }

    override fun isLoggedIn(): Flow<Boolean> = userPreferences.data.map {
        it.accessToken.isNullOrBlank().not() || it.guestSessionId.isNullOrBlank().not()
    }.flowOn(dispatcher)

    override fun userData(): Flow<UserData> = userPreferences.data.map {
        UserData(
            accountId = it.accountId,
            isLoggedIn = it.accessToken.isNullOrBlank().not() ||
                    it.guestSessionId.isNullOrBlank().not(),
            themeConfig = when (it.themeConfig) {
                ThemeConfigProto.THEME_CONFIG_FOLLOW_SYSTEM -> ThemeConfig.FOLLOW_SYSTEM
                ThemeConfigProto.THEME_CONFIG_LIGHT -> ThemeConfig.LIGHT
                ThemeConfigProto.THEME_CONFIG_UNSPECIFIED,
                ThemeConfigProto.UNRECOGNIZED,
                ThemeConfigProto.THEME_CONFIG_DARK,
                -> ThemeConfig.DARK
            },
            usDynamicColor = it.useDynamicColor,
            name = null,
            userName = "",
        )
    }.flowOn(dispatcher)

    override suspend fun saveUserSession(
        accountId: String,
        accessToken: String,
    ) {
        withContext(dispatcher) {
            userPreferences.updateData {
                it.toBuilder()
                    .setAccountId(accountId)
                    .setAccessToken(accessToken).build()
            }
        }
    }

    override suspend fun saveGuestSession(guestSessionId: String) {
        withContext(dispatcher) {
            userPreferences.updateData {
                it.toBuilder()
                    .setGuestSessionId(guestSessionId)
                    .build()
            }
        }
    }

    override suspend fun setThemeConfig(config: ThemeConfig) {
        withContext(dispatcher) {
            userPreferences.updateData {
                it.toBuilder()
                    .setThemeConfig(
                        when (config) {
                            ThemeConfig.FOLLOW_SYSTEM ->
                                ThemeConfigProto.THEME_CONFIG_FOLLOW_SYSTEM

                            ThemeConfig.LIGHT -> ThemeConfigProto.THEME_CONFIG_LIGHT
                            ThemeConfig.DARK -> ThemeConfigProto.THEME_CONFIG_DARK
                        },
                    )
                    .build()
            }
        }
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        withContext(dispatcher) {
            userPreferences.updateData {
                it.toBuilder()
                    .setUseDynamicColor(useDynamicColor)
                    .build()
            }
        }
    }

    override suspend fun logout() {
        withContext(dispatcher) {
            userPreferences.updateData {
                it.toBuilder()
                    .clearAccessToken()
                    .clearGuestSessionId()
                    .clearAccountId()
                    .build()
            }
        }
    }
}
