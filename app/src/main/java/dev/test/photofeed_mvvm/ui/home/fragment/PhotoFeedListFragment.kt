package dev.test.photofeed_mvvm.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import dev.test.photofeed_mvvm.databinding.FragmentPhotoFeedListBinding
import dev.test.photofeed_mvvm.ui.home.adapter.PhotoAdapter
import dev.test.photofeed_mvvm.ui.home.viewmodel.PhotoFeedListViewModel
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.util.state.Status

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoFeedListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PhotoFeedListFragment : Fragment() {

    private lateinit var binding : FragmentPhotoFeedListBinding
    private val mViewModel by viewModels<PhotoFeedListViewModel>()

    private lateinit var mGridViewManager: GridLayoutManager

    lateinit var mPhotoAdapter: PhotoAdapter

    companion object {
        fun newInstance() = PhotoFeedListFragment()
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

        initGridRecyclerView()
        setObserver()
        mViewModel.fetchPhotoFeed()
    }

    /**
     * Init the Grid RV of [ArrayList]<[PhotoItem]>
     */
    private fun initGridRecyclerView(){
        mGridViewManager = GridLayoutManager(requireContext(), mViewModel.spanCount)
        if (!::mPhotoAdapter.isInitialized)
            mPhotoAdapter = PhotoAdapter()

        binding.rvPhoto.apply {
            layoutManager = mGridViewManager
            adapter = mPhotoAdapter
        }
    }

    private fun setObserver() {
        mViewModel.photoFeedList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.let { photoList ->
                        mPhotoAdapter.setPhotoList(photoList)
                    }
                }
                Status.ERROR -> {
                    resource.message?.let {
                        if (resource.data != null){
                            if (resource.data.isNotEmpty()) {
                                Toasty.error(
                                    requireContext(),
                                    "Network error, Retrieving local data",
                                    Toast.LENGTH_SHORT, true
                                ).show();

                                resource.data.let { photoList ->
                                    mPhotoAdapter.setPhotoList(photoList)
                                }
                            }else{
                                Toasty.error(
                                    requireContext(),
                                    "No local data, Please retry",
                                    Toast.LENGTH_SHORT, true
                                ).show();
                            }
                        }else{
                            Toasty.error(
                                requireContext(),
                                "Network error, Please retry",
                                Toast.LENGTH_SHORT, true
                            ).show();
                        }
                    }
                }
                Status.LOADING -> {
                }
                else -> {}
            }
        })
    }

}