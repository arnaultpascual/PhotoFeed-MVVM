package dev.test.photofeed_mvvm.ui.home.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import dev.test.photofeed_mvvm.R
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
class PhotoFeedListFragment : Fragment(), PhotoAdapter.PhotoAdapterListener {

    private lateinit var binding : FragmentPhotoFeedListBinding
    private val mViewModel by viewModels<PhotoFeedListViewModel>()

    private lateinit var mGridViewManager: GridLayoutManager
    private lateinit var mLinearViewManager: LinearLayoutManager

    lateinit var mPhotoAdapter: PhotoAdapter

    lateinit var skeletonRecyclerView: Skeleton

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

        setOnClickListeners()
        setRefreshLayoutListener()
        initRecyclerView()
        setObserver()
        mViewModel.fetchPhotoFeed()
    }

    private fun initRecyclerView(){
        setRecyclerViewStyle()
    }

    private fun setRecyclerViewStyle(){
        if (mViewModel.displayInGridStyle)
            initGridRecyclerView()
        else
            initListRecyclerView()
    }

    private fun setOnClickListeners(){
        binding.fab.setOnClickListener {
            mViewModel.displayInGridStyle = !mViewModel.displayInGridStyle
            setRecyclerViewStyle()
        }
    }

    /**
     * Init the Grid RV of [ArrayList]<[PhotoItem]>
     */
    private fun initGridRecyclerView(){
        mGridViewManager = GridLayoutManager(requireContext(), mViewModel.spanCount)
        if (!::mPhotoAdapter.isInitialized)
            mPhotoAdapter = PhotoAdapter(this)

        mViewModel.displayInGridStyle = true
        mPhotoAdapter.displayInGridStyle = true
        binding.rvPhoto.apply {
            layoutManager = mGridViewManager
            adapter = mPhotoAdapter
        }

        binding.fab.setImageResource(R.drawable.ic_list)
    }

    /**
     * Init the Grid RV of [ArrayList]<[PhotoItem]>
     */
    private fun initListRecyclerView(){
        mLinearViewManager = LinearLayoutManager(requireContext())
        if (!::mPhotoAdapter.isInitialized)
            mPhotoAdapter = PhotoAdapter(this)

        mViewModel.displayInGridStyle = false
        mPhotoAdapter.displayInGridStyle = false
        binding.rvPhoto.apply {
            layoutManager = mLinearViewManager
            adapter = mPhotoAdapter
        }

        binding.fab.setImageResource(R.drawable.ic_grid)
    }

    private fun setObserver() {
        mViewModel.photoFeedList.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    resource.data?.let { photoList ->
                        bindUiWithSuccessfullyRemoteReturn(photoList)
                    }
                }
                Status.ERROR -> {

                    mViewModel.isRefreshing = false
                    binding.refreshLayout.isRefreshing = mViewModel.isRefreshing

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
                                    skeletonRecyclerView.showOriginal()
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

                    if (mViewModel.firstLaunch || mViewModel.isRefreshing){
                        setupSkeletonForRecyclerView()
                    }
                }
                else -> {}
            }
        })
    }

    /**
     * Init the RV of [Skeleton] to match the [ArrayList]<[PhotoItem]> RV
     */
    private fun setupSkeletonForRecyclerView(){
        skeletonRecyclerView = binding.rvPhoto.applySkeleton(
            R.layout.photo_grid_item_shimmer,
            25,
            SkeletonConfig.default(requireContext())
        )

        skeletonRecyclerView.shimmerAngle = 45
        skeletonRecyclerView.maskCornerRadius = 28.toFloat()
        skeletonRecyclerView.showSkeleton()
    }

    private fun bindUiWithSuccessfullyRemoteReturn(photoList : ArrayList<PhotoItem>){
        if (mViewModel.firstLaunch || mViewModel.isRefreshing){
            Handler(Looper.getMainLooper()).postDelayed({
                mPhotoAdapter.setPhotoList(photoList)
                skeletonRecyclerView.showOriginal()
                mViewModel.firstLaunch = false
                mViewModel.isRefreshing = false
                binding.refreshLayout.isRefreshing = mViewModel.isRefreshing
            }, 1000)
        }
    }

    /**
     * get updated data with remote call on swipe
     */
    private fun setRefreshLayoutListener(){
        binding.refreshLayout.setOnRefreshListener {
            mViewModel.isRefreshing = true
            initGridRecyclerView()
            mViewModel.fetchPhotoFeed()
        }
    }

    override fun onPhotoClicked(imageView: View, photo: PhotoItem, position: Int) {

        val args = Bundle()
        args.putParcelable("clickedPhoto", photo)

        TODO("Not yet implemented")
    }
}