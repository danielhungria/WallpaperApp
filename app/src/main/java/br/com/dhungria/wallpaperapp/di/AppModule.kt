package br.com.dhungria.wallpaperapp.di

import android.content.Context
import androidx.room.Room
import br.com.dhungria.wallpaperapp.data.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDataBase::class.java, "app.database")
        .build()

    @Singleton
    @Provides
    fun providesWallpaperDao(
        db:AppDataBase
    ) = db.getWallpaperDao()

}