package com.goda.recipesapp.features.detail.di

import com.goda.recipesapp.data.db.FoodDatabase
import com.goda.recipesapp.features.detail.data.remote.DetailApi
import com.goda.recipesapp.features.detail.data.repositoryImp.DetailRepositoryImp
import com.goda.recipesapp.features.detail.domain.DetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailModule {
    @Singleton
    @Provides
    fun providesDataRepository(
        api: DetailApi,
        db:FoodDatabase
    ): DetailRepository =
        DetailRepositoryImp(
            db,api
        )


    @Provides
    fun providesService( retrofit: Retrofit): DetailApi =
        retrofit.create(DetailApi::class.java)

}