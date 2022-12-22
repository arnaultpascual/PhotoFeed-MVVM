package dev.test.photofeed_mvvm.ui.detail.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.databinding.FragmentPhotoDetailBinding
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.ui.detail.dialog.PhotoDetailsBottomSheetFragment
import dev.test.photofeed_mvvm.ui.detail.viewmodel.PhotoDetailViewModel
import dev.test.photofeed_mvvm.util.state.Status
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private lateinit var binding : FragmentPhotoDetailBinding

    lateinit var bottomSheetDialog : PhotoDetailsBottomSheetFragment

    private val mViewModel by viewModels<PhotoDetailViewModel>()

    /**
     * @param savedInstanceState : [Bundle]?
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate the shared element animation for photo
        val animation = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)

        sharedElementEnterTransition = animation
    }


    /**
     * @param inflater : [LayoutInflater]
     * @param container : [ViewGroup]?
     * @param savedInstanceState : [Bundle]?
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(layoutInflater)

        /** get [PhotoItem] from the args of the previous fragment */
        mViewModel.chosenPhotoFromList = requireArguments().get("clickedPhoto") as PhotoItem
        setupPhotoDataForTransition()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setObserver()
        mViewModel.fetchPhotoFromGivenId()
    }


    private fun setObserver(){
        mViewModel.chosenPhotoWithExif.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { photoWithDetail ->
                        createDetailsFragment(photoWithDetail)
                    }
                }
                Status.ERROR -> {
                    resource.message?.let {
                        Toasty.error(requireContext(), "network error, retrieve local data",
                            Toast.LENGTH_SHORT, true).show();
                        createDetailsFragment(mViewModel.chosenPhotoFromList)
                    }
                }
                Status.LOADING -> {

                }
                else -> {}
            }
        })
    }

    private fun setOnClickListeners(){
        binding.infoBtnLt.setOnClickListener {
            bottomSheetDialog.show(childFragmentManager, "DetailsBottomSheet")
        }
        binding.shareBtnLt.setOnClickListener {
            sharePicture()
        }
    }

    /**
     * @param photoWithDetail : [PhotoItem]
     */
    private fun createDetailsFragment(photoWithDetail : PhotoItem){
        bottomSheetDialog = PhotoDetailsBottomSheetFragment(photoWithDetail)
    }

    /**
     * Setup the data img before request for transition
     * put the low quality img in thumbnail so we have
     * time to load the big one and switch when it's ready
     */
    private fun setupPhotoDataForTransition(){
        Glide.with(requireContext())
            .load(mViewModel.chosenPhotoFromList.urlFull)
            .thumbnail(
                Glide.with(requireContext())
                .asDrawable()
                .load(mViewModel.chosenPhotoFromList.url))
            .into(binding.photoDetailImg)
    }

    /**
     * Get the image that we draw in the [ImageView]
     * Store it temporary
     * Launch the shared activity
     */
    private fun sharePicture(){
        val intent = Intent(Intent.ACTION_SEND).setType("image/*")
        val bitmap = binding.photoDetailImg.drawable.toBitmap()
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "tempimage", null)
        val uri = Uri.parse(path)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDetailFragment()
    }
}