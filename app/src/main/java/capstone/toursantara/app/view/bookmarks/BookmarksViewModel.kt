package capstone.toursantara.app.view.bookmarks

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import capstone.toursantara.app.database.AppDatabase
import capstone.toursantara.app.database.TourismResult
import kotlinx.coroutines.launch

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {

    private val tourismResultDao = AppDatabase.getDatabase(application).tourismResultDao()
    val bookmarks: LiveData<List<TourismResult>> = tourismResultDao.getAllTourismResults()

    fun saveBookmark(tourismResult: TourismResult) {
        viewModelScope.launch {
            tourismResult.timestamp = System.currentTimeMillis() // Set timestamp saat ini
            tourismResultDao.insert(tourismResult)
        }
    }

    fun removeBookmark(tourismResult: TourismResult) {
        viewModelScope.launch {
            Log.d("BookmarksViewModel", "Deleting: ${tourismResult.placeName}")
            tourismResultDao.delete(tourismResult)
        }
    }

    fun isTourismResultSaved(placeName: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val tourismResult = tourismResultDao.getTourismResultByNameSync(placeName)
            result.postValue(tourismResult != null)
        }
        return result
    }

    suspend fun getTourismResultByNameSync(placeName: String): TourismResult? {
        return tourismResultDao.getTourismResultByNameSync(placeName)
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            tourismResultDao.deleteAllBookmarks()
        }
    }
}

