package network
import model.NewReviewRequest
import model.Game
import model.Review
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body

interface GameApi {

    @GET("api/games")  // This is your endpoint, adjust accordingly.
    suspend fun getGames(): List<Game>

    // If you need to fetch a single game by ID
    @GET("api/Games/{id}")
    suspend fun getGameById(@Path("id") id: Int): Game

    @GET("api/reviews")
    suspend fun getReviews(): List<Review>

    @POST("api/reviews")
    suspend fun postReview(@Body review: NewReviewRequest)
}