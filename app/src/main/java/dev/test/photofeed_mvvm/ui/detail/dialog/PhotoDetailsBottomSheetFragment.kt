package dev.test.photofeed_mvvm.ui.detail.dialog

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.databinding.FragmentPhotoDetailsBottomSheetBinding
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.ui.detail.viewmodel.PhotoDetailViewModel

/**
 * A [BottomSheetDialogFragment] subclass.
 * Use the [PhotoDetailsBottomSheetFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @param photoItem : [PhotoItem]
 */
@AndroidEntryPoint
class PhotoDetailsBottomSheetFragment(val photoItem: PhotoItem) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentPhotoDetailsBottomSheetBinding

    private val viewModel by viewModels<PhotoDetailViewModel>()


    /**
     * @param inflater : [LayoutInflater]
     * @param container : [ViewGroup]?
     * @param savedInstanceState : [Bundle]?
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoDetailsBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupData()
    }

    /**
     * Set all  photo details on the ui according if they're present or not
     */
    private fun setupData(){
        binding.createdAt.text = viewModel.getHumanReadableDate(photoItem.createdAt)

        val imageSizeText = "${photoItem.getResFromSizeInMpx()}Mpx  -  " +
                "${photoItem.width}x${photoItem.height}"
       binding.imageSize.text = imageSizeText

        if (photoItem.exif != null){
            if (!photoItem.exif!!.name.isNullOrEmpty())
                binding.cameraName.text = photoItem.exif!!.name
            else
                binding.cameraName.text = "Pas de modèle référencé"

            if (!photoItem.exif!!.aperture.isNullOrEmpty())
                binding.exifAperture.text = "Ouverture : " + photoItem.exif!!.aperture
            else
                binding.exifAperture.text = "Pas d'ouverture spécifiée'"
        }else{
            binding.cameraName.text = "Pas de modèle référencé"
            binding.exifAperture.text = "Pas de données spécifiques"
        }

        binding.nbView.text = photoItem.nbView.toString()
        binding.nbDownload.text = photoItem.nbDownload.toString()
        binding.nbLike.text = photoItem.nbLikes.toString()

        binding.locationTitleTv.text = viewModel.getLocationDataTitle(photoItem)
    }

    companion object {
        @JvmStatic
        fun newInstance(photoItem: PhotoItem) = PhotoDetailsBottomSheetFragment(photoItem)
    }
}