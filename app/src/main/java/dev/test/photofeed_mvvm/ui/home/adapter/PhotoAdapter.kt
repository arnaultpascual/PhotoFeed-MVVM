package dev.test.photofeed_mvvm.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dev.test.photofeed_mvvm.databinding.PhotoGridItemBinding
import dev.test.photofeed_mvvm.databinding.PhotoListItemBinding
import dev.test.photofeed_mvvm.model.local.PhotoItem

class PhotoAdapter : RecyclerView.Adapter<PhotoGridAndListViewHolder>() {

    /**
     * The [PhotoItem] list used in this adapter
     */
    private var mPhotoList = mutableListOf<PhotoItem>()

    var displayInGridStyle = true

    /**
     * @param photoList : [ArrayList]<[PhotoItem]>
     */
    fun setPhotoList(photoList : ArrayList<PhotoItem>){
        this.mPhotoList = photoList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAndListViewHolder {
        if (displayInGridStyle){
            return PhotoGridViewHolder(
                PhotoGridItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }else{
            return PhotoListViewHolder(
                PhotoListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    /**
     * @param holder : [PhotoGridAndListViewHolder]
     * @param position : [Int]
     *
     * choose the right binding method
     */
    override fun onBindViewHolder(holder: PhotoGridAndListViewHolder, position: Int) {
        if (displayInGridStyle)
            bindGridListItem(holder, position)
        else
            bindListItem(holder, position)
    }

    /**
     * @param holder : [PhotoGridAndListViewHolder]
     * @param position : [Int]
     *
     * Bin the data in grid style
     */
    private fun bindListItem(holder: PhotoGridAndListViewHolder, position: Int){
        val  photoGridViewHolder = holder as PhotoListViewHolder
        val currentPhotoItem = mPhotoList[position]

        Glide.with(holder.itemView.context)
            .load(currentPhotoItem.url)
            .into(holder.photoImg)

        holder.artistName.text = currentPhotoItem.authorName
        holder.description.text = currentPhotoItem.description
    }

    /**
     * @param holder : [PhotoGridAndListViewHolder]
     * @param position : [Int]
     *
     * Bin the data in list style
     */
    private fun bindGridListItem(holder: PhotoGridAndListViewHolder, position: Int){
        val  photoGridViewHolder = holder as PhotoGridViewHolder
        val currentPhotoItem = mPhotoList[position]

        Glide.with(holder.itemView.context)
            .load(currentPhotoItem.url)
            .transition(DrawableTransitionOptions.withCrossFade(1500))
            .into(holder.photoImg)

        //set square image
        val layoutParamsImg    = holder.photoImg.layoutParams;
        layoutParamsImg.height = holder.itemView.measuredWidth
        layoutParamsImg.width  = holder.itemView.measuredWidth
        holder.photoImg.layoutParams = layoutParamsImg

        holder.artistName.text = currentPhotoItem.authorName
    }

    override fun getItemCount(): Int {
        return mPhotoList.size
    }

}

open class PhotoGridAndListViewHolder(view: View) : RecyclerView.ViewHolder(view)

/**
 * @param binding : [PhotoGridItemBinding]
 */
class PhotoGridViewHolder(private val binding: PhotoGridItemBinding)
    : PhotoGridAndListViewHolder(binding.root) {

    val photoImg   = binding.photoImg
    val artistName = binding.artistName
}

/**
 * @param binding : [PhotoGridItemBinding]
 */
class PhotoListViewHolder(private val binding: PhotoListItemBinding)
    : PhotoGridAndListViewHolder(binding.root) {

    val photoImg   = binding.photoImg
    val artistName = binding.photoArtistName
    val description = binding.photoDescription
}