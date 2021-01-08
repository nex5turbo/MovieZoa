package wonyong.by.moviezoa

import com.google.gson.annotations.SerializedName

object Models {
    data class Movie(
        @SerializedName("page") var page:Int,
        @SerializedName("results") var movieList:ArrayList<MovieInfo>,
        @SerializedName("total_pages") var total_pages:Int,
        @SerializedName("total_results") var total_results:Int
    )
    data class MovieInfo(
        @SerializedName("id") var id:Int,
        @SerializedName("original_language") var original_language:String,
        @SerializedName("original_title") var original_title:String,
        @SerializedName("overview") var overview:String,
        @SerializedName("release_date") var release_date:String,
        @SerializedName("title") var title:String,
        @SerializedName("poster_path") var poster_path:String,
        @SerializedName("vote_average") var vote_average:Float,
        @SerializedName("vote_count") var vote_count:Int,
        @SerializedName("genre_ids") var genre_ids:Array<Int>,
        @SerializedName("popularity") var popularity:Float
    )
}