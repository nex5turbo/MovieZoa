package wonyong.by.moviezoa

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import wonyong.by.moviezoa.SimilarRecyclerAdapter.MyViewHolder
import java.util.*


class SimilarRecyclerAdapter(items: ArrayList<Models.MovieInfo>, displayInfo: DisplayInfo) :
    RecyclerView.Adapter<MyViewHolder>() {
    private val items: ArrayList<Models.MovieInfo>
    var displayInfo: DisplayInfo

    inner class MyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var poster: ImageView

        init {
            title = itemView.findViewById(R.id.similarTitleText)
            poster = itemView.findViewById(R.id.similarPoster)
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }
    interface OnItemClickListener{
        fun OnItemClick(holder: SimilarRecyclerAdapter.MyViewHolder, view:View, data:Models.MovieInfo, position: Int)
    }

    var itemClickListener:OnItemClickListener? = null
    //dp = pixel/(dpi/160)
    //pixel = dp*(dpi/160)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.similar_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data: Models.MovieInfo = items[position]
        holder.title.setText(data.title)
        val widthDp: Int = displayInfo.deviceWidthDp
        val sectionPixel = displayInfo.getPixelfromDp((widthDp - 24) / 2)
        val color = "#FFFFFF"
        holder.title.maxWidth = sectionPixel
        holder.title.setTextColor(Color.parseColor(color))
        Glide.with(holder.itemView).load(Constants.TMDB_POSTER_URL+data.poster_path).centerCrop().override(
            sectionPixel,
            (sectionPixel * 1.5).toInt()
        ).into(holder.poster)
        //        holder.poster.setImageBitmap(Bitmap.createScaledBitmap(data.poster, sectionPixel, (int)(sectionPixel*1.5), false));
    }

    override fun getItemCount(): Int {
        return items.size
    }

    init {
        this.items = items
        this.displayInfo = displayInfo
    }
}
