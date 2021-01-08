package wonyong.by.moviezoa

import java.util.*

object Constants {
    const val TMDB_KEY="c0e0fe5d77f2d08e7948ca71987e4caf"
    const val TMDB_MOVIE_SEARCH_URL = "search/movie"
    const val TMDB_MOVIE_SIMILAR_SEARCH_URL = "movie/{movie_id}/similar"
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_POSTER_URL = "http://image.tmdb.org/t/p/w185"
    const val NO_POSTER_URL = "https://folo.co.kr/img/gm_noimage.png"
    const val TMDB_PEOPLE_URL = "/search/person"
    val GENRES_MAP: HashMap<Int, String> = object : HashMap<Int, String>() {
        init {
            put(28, "액션")
            put(12, "어드벤쳐")
            put(16, "애니메이션")
            put(35, "코메디")
            put(80, "범죄")
            put(99, "다큐")
            put(18, "드라마")
            put(10751, "가족")
            put(14, "판타지")
            put(36, "역사")
            put(27, "호러")
            put(10402, "음악")
            put(9648, "미스테리")
            put(10749, "로맨스")
            put(878, "공상과학")
            put(10770, "TV영화")
            put(53, "스릴러")
            put(10752, "전쟁")
            put(37, "서부")
        }
    }
}