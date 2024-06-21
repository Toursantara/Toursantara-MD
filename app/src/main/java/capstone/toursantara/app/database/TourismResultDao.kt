package capstone.toursantara.app.database

import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TourismResultDao {

    @Query("SELECT * FROM tourism_result WHERE placeName = :placeName LIMIT 1")
    suspend fun getTourismResultByNameSync(placeName: String): TourismResult?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tourismResult: TourismResult)

    @Update
    suspend fun update(tourismResult: TourismResult)

    @Delete
    suspend fun delete(tourismResult: TourismResult)

    @Query("DELETE FROM tourism_result")
    suspend fun deleteAllBookmarks()

    @Query("SELECT * FROM tourism_result ORDER BY timestamp DESC")
    fun getAllTourismResults(): LiveData<List<TourismResult>>
}
