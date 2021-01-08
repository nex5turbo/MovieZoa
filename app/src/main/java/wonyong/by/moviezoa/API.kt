package wonyong.by.moviezoa


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET(Constants.TMDB_MOVIE_SEARCH_URL)
    fun getMovieSearch(
        @Query("api_key") api_key:String,
        @Query("query") title:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Call<Models.Movie>

    @GET(Constants.TMDB_MOVIE_SIMILAR_SEARCH_URL)
    fun getSimilarSearch(
        @Path("movie_id") id:Int,
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ): Call<Models.Movie>

}