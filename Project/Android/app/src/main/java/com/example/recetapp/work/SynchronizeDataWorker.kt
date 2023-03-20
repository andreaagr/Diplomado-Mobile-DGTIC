package com.example.recetapp.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.recetapp.model.view.CarouselRecipe
import com.example.recetapp.repository.RecipesRepository
import com.example.recetapp.networking.UIResponseState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SynchronizeDataWorker @AssistedInject constructor (
    @Assisted context: Context,
    @Assisted workerParameters:WorkerParameters,
    private val recipesRepository: RecipesRepository
) : CoroutineWorker(context,workerParameters) {

    companion object {
        const val Progress = "Progress"
    }

    override suspend fun doWork(): Result {
        return try {
            setProgress(workDataOf(Progress to 0))
            generateRandomImages(recipesRepository)
            setProgress(workDataOf(Progress to 100))
            Result.success()
        }catch (error : Throwable){
            Log.d("Work manager", error.message.toString())
            Result.failure()
        }
    }

    private suspend fun generateRandomImages(repository: RecipesRepository){
        // TODO("Revisar que haya internet")
        repository.deleteAllCarouselRecipes()
        val result = repository.getRandomRecipes()
        if (result is UIResponseState.Success<*>) {
            if (result.content is List<*>) {
                result.content.forEach {
                    repository.addNewCarouselRecipe(it as CarouselRecipe)
                }
            }
        }
    }
}