package com.example.foodtest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foodtest.databinding.FragmentMainMenuBinding
import com.example.foodtest.ui.adapter.BannerListAdapter
import com.example.foodtest.ui.adapter.ProductItemListAdapter
import com.example.foodtest.ui.model.BannerPlug
import com.example.foodtest.ui.model.ProductItemPlug
import com.example.foodtest.ui.viewmodel.MainMenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainMenuFragment : Fragment() {
    private val viewModel by viewModel<MainMenuViewModel>()
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!
    private val bannerAdapter: BannerListAdapter = BannerListAdapter()
    private val productAdapter: ProductItemListAdapter = ProductItemListAdapter()

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
    }

    private fun initView() = with(binding) {
        val bannerPlugList = getGeneratedBannersPlugs(6)
        bannersLayout.bannersRecycler.adapter = bannerAdapter
        bannerAdapter.submitList(bannerPlugList)

        val productPlugList = getGeneratedProductPlugs(10)
        mainMenuRecycler.adapter = productAdapter
        productAdapter.submitList(productPlugList)

        mainMenuSelectCity.setOnClickListener { showToastMessage(NOT_REALISED_MESSAGE) }
        mainMenuScanQrBtn.setOnClickListener { showToastMessage(NOT_REALISED_MESSAGE) }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun getGeneratedProductPlugs(amount: Int): List<ProductItemPlug> =
        (0..amount).map { ProductItemPlug(id = it) }.toList()

    private fun getGeneratedBannersPlugs(amount: Int): List<BannerPlug> =
        (0..amount).map { BannerPlug(id = it) }.toList()

    companion object {
        const val NOT_REALISED_MESSAGE = "Not realised yet!"
    }
}