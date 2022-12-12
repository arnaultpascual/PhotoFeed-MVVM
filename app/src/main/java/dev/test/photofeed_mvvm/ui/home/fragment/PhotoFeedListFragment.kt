package dev.test.photofeed_mvvm.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.R
import dev.test.photofeed_mvvm.databinding.FragmentPhotoFeedListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFeedListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PhotoFeedListFragment : Fragment() {

    private lateinit var binding : FragmentPhotoFeedListBinding

    companion object {
        fun newInstance() = PhotoFeedListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * @param inflater : [LayoutInflater]
     * @param container : [ViewGroup]?
     * @param savedInstanceState : [Bundle]?
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoFeedListBinding.inflate(layoutInflater)
        return binding.root
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}