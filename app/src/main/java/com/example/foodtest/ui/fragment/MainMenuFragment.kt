package com.example.foodtest.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.foodtest.R
import com.example.foodtest.databinding.FragmentMainMenuBinding
import com.example.foodtest.ui.adapter.*
import com.example.foodtest.ui.adapter.listeners.BannerListListener
import com.example.foodtest.ui.adapter.listeners.CategoryBtnListListener
import com.example.foodtest.ui.adapter.listeners.RecyclerProductItemListener
import com.example.foodtest.ui.model.BannerPlug
import com.example.foodtest.ui.model.ListViewState
import com.example.foodtest.ui.model.ProductItem
import com.example.foodtest.ui.viewmodel.MainMenuViewModel
import com.example.foodtest.utils.network.OnlineLiveData
import com.example.foodtest.utils.ui.Category
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : Fragment() {
    private val viewModel by viewModel<MainMenuViewModel>()
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private var isNetworkAvailable: Boolean = true


    private val categoryBtnListener = object : CategoryBtnListListener {
        override fun onClick(category: Category) {
            viewModel.getDataByCategory(category, isNetworkAvailable)
        }
    }
    private val categoryBtnAdapter: CategoryButtonListAdapter =
        CategoryButtonListAdapter(categoryBtnListener)

    private val bannerListListener = object : BannerListListener {
        override fun onItemClick(item: BannerPlug) {
            showToastMessage("Banner ${++item.id} clicked")
        }
    }
    private val bannerAdapter: BannerListAdapter = BannerListAdapter(listener = bannerListListener)

    private val recyclerProductItemListener = object : RecyclerProductItemListener {
        override fun onItemClick(item: ProductItem) {
            showToastMessage("${item.title} clicked")
        }

        override fun onOrderBtnClick(item: ProductItem) {
            showToastMessage("${item.title} ORDER button clicked")
        }
    }
    private val productAdapter: ProductItemListAdapter =
        ProductItemListAdapter(listener = recyclerProductItemListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToNetworkChange()
    }

    private fun subscribeToNetworkChange() {
        isNetworkAvailable =
            (requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true
        if (!isNetworkAvailable) showToastMessage(getString(R.string.network_connecion_not_available))

        OnlineLiveData(requireContext()).observe(this) { isAvailable ->
            isNetworkAvailable = isAvailable
            if (!isNetworkAvailable) showToastMessage(getString(R.string.network_connecion_not_available))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initViewModel() {
        viewModel.viewState.onEach { renderState(it) }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initData() {
        viewModel.getData(Category.BURGERS, isNetworkAvailable)
    }

    private fun initView() = with(binding) {
        initAdapters()
        initClickListeners()
    }

    private fun initAdapters() = with(binding) {
        bannersLayout.bannersRecycler.adapter = bannerAdapter
        bannerAdapter.submitList(getBannerPlugsList())
        chipsGroup.categoryBtnRecycler.adapter = categoryBtnAdapter
        categoryBtnAdapter.setCategoryBtnTitleList(getCategoryBtnTitlesList())

        mainMenuRecycler.adapter = productAdapter
    }

    private fun getCategoryBtnTitlesList(): List<String> = Category.values().map {
        getString(it.nameResId)
    }.toList()

    private fun initClickListeners() = with(binding) {
        mainMenuSelectCity.setOnClickListener { showToastMessage(NOT_REALISED_MESSAGE) }
        mainMenuScanQrBtn.setOnClickListener { showToastMessage(NOT_REALISED_MESSAGE) }
    }

    private fun renderState(state: ListViewState) {
        when (state) {
            is ListViewState.Empty -> {
                enableEmptyState(state = true)
                enableProgress(state = false)
                enableError(state = false)
                enableContent(state = false)
            }
            is ListViewState.Loading -> {
                enableProgress(state = true)
                enableEmptyState(state = false)
                enableError(state = false)
                enableContent(state = false)
            }
            is ListViewState.Error -> {
                enableError(state = true)
                enableEmptyState(state = false)
                enableProgress(state = false)
                enableContent(state = false)
                showToastMessage(message = state.message ?: getString(R.string.unknown_error))
            }
            is ListViewState.Data -> {
                enableContent(state = true)
                enableEmptyState(state = false)
                enableProgress(state = false)
                enableError(state = false)
                productAdapter.submitList(list = state.data)
            }
        }
    }

    private fun enableEmptyState(state: Boolean) {
        binding.emptyProductListImage.isVisible = state
    }

    private fun enableProgress(state: Boolean) {
        binding.loader.isVisible = state
    }

    private fun enableContent(state: Boolean) {
        binding.mainMenuRecycler.isVisible = state
    }

    private fun enableError(state: Boolean) {
        binding.error.isVisible = state
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun getBannerPlugsList(): List<BannerPlug> =
        (0..BANNER_PLUGS_AMOUNT).map { BannerPlug(id = it) }.toList()

    companion object {
        const val NOT_REALISED_MESSAGE = "Not realised yet!"
        const val BANNER_PLUGS_AMOUNT = 10
    }
}