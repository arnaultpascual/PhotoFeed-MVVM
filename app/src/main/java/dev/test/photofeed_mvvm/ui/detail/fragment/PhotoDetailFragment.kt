package dev.test.photofeed_mvvm.ui.detail.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.databinding.FragmentPhotoDetailBinding
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.ui.detail.viewmodel.PhotoDetailViewModel
import dev.test.photofeed_mvvm.util.state.Status
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PhotoDetailFragment : Fragment() {

    private lateinit var binding : FragmentPhotoDetailBinding

    private val mViewModel by viewModels<PhotoDetailViewModel>()

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


        mViewModel.fetchPhotoFromGivenId()
    }

    private fun setObserver(){
        mViewModel.chosenPhotoWithExif.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { photoWithDetail ->
                    }
                }
                Status.ERROR -> {
                    resource.message?.let {
                        Toasty.error(requireContext(), "network error, retrieve local data",
                            Toast.LENGTH_SHORT, true).show();
                    }
                }
                Status.LOADING -> {

                }
                else -> {}
            }
        })
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


    companion object {
        @JvmStatic
        fun newInstance() = PhotoDetailFragment()
    }
}