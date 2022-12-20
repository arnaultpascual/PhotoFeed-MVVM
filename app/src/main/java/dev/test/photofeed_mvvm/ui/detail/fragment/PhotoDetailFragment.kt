package dev.test.photofeed_mvvm.ui.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.databinding.FragmentPhotoDetailBinding
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.ui.detail.viewmodel.PhotoDetailViewModel

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

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = PhotoDetailFragment()
    }
}