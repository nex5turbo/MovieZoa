package wonyong.by.moviezoa

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide

class InformationDialog(context: Context, data: Models.MovieInfo) :
    Dialog(context) {
    var titleText: TextView
    var releaseText: TextView
    var overviewText: TextView
    var genreText: TextView
    var vote_averageText: TextView
    var posterImageView: ImageView
    var similarButton: Button
    var closeButton: Button
    var id: Int

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.information_dialog)
        titleText = findViewById(R.id.dialogTitleText)
        releaseText = findViewById(R.id.dialogReleaseText)
        genreText = findViewById(R.id.dialogGenreText)
        vote_averageText = findViewById(R.id.dialogVoteAverageText)
        overviewText = findViewById(R.id.dialogOverviewText)
        posterImageView = findViewById(R.id.dialogImageView)
        val drawable = context.getDrawable(R.drawable.image_view_rounding) as GradientDrawable?
        posterImageView.background = drawable
        posterImageView.clipToOutline = true
        similarButton = findViewById(R.id.dialogSimilarButton)
        closeButton = findViewById(R.id.dialogCloseButton)
        id = data.id
        Log.d("DIALOG ID", id.toString() + ", " + java.lang.String.valueOf(data.id))
        titleText.text = "제목 : " + data.title
        releaseText.text = "출시일 : " + data.release_date
        var genre = ""
        for (temp in data.genre_ids) {
            genre = genre + " " + Constants.GENRES_MAP.get(temp)
        }
        genreText.text = "장르 :$genre"
        vote_averageText.text = "평점 : 10/" + java.lang.String.valueOf(data.vote_average)
        val overviewTemp: String = data.overview.replace("\n", " ")
        overviewText.text = overviewTemp
        overviewText.movementMethod = ScrollingMovementMethod()
        Glide.with(getContext()).load(Constants.TMDB_POSTER_URL+data.poster_path).override(480, 720).into(posterImageView)
        closeButton.setOnClickListener { dismiss() }

        similarButton.setOnClickListener {
            val i = Intent(getContext(), SimilarActivity::class.java)
            Log.d("INTENT ID", id.toString())
            i.putExtra("id", id)
            context.startActivity(i)
            dismiss()
        }
    }
}
