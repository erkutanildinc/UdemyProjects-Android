package com.example.artbooktesting.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.artbooktesting.api.RetrofitApi
import com.example.artbooktesting.roomdb.ArtDB
import com.example.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context : Context) {
        Room.databaseBuilder(context,ArtDB::class.java,"ArtBookDatabase").build()
    }

    @Singleton
    @Provides
    fun injectDao(database : ArtDB) {
        database.artDao()
    }

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build().create(RetrofitApi::class.java)
    }
}