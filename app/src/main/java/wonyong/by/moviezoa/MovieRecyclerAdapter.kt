package wonyong.by.moviezoa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieRecyclerAdapter(var items:ArrayList<Models.MovieInfo>): RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var movieTitle: TextView =itemView.findViewById(R.id.recyclerMovieName)
        var movieDate: TextView=itemView.findViewById(R.id.recyclerMovieOpendate)
        var thumbnail: ImageView =itemView.findViewById(R.id.recyclerMovieThumbnail)
        var movieGenre:TextView=itemView.findViewById(R.id.recyclerMovieCountry)

        init{
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var genre = ""
        for(temp in items.get(position).genre_ids){
            genre = genre+" "+Constants.GENRES_MAP.get(temp)
        }
        holder.movieGenre.text = genre
        holder.movieTitle.text = items.get(position).title
        holder.movieDate.text = items.get(position).release_date
        if(items.get(position).poster_path != null) {
            Glide.with(holder.itemView)
                .load(Constants.TMDB_POSTER_URL + items.get(position).poster_path)
                .into(holder.thumbnail)
        }else{
            Glide.with(holder.itemView)
                .load(Constants.NO_POSTER_URL)
                .into(holder.thumbnail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_recycler_item, parent, false)
        return MyViewHolder(v)
    }

    interface OnItemClickListener{
        fun OnItemClick(holder:MyViewHolder, view:View, data:Models.MovieInfo, position: Int)
    }

    var itemClickListener:OnItemClickListener? = null

}