package com.example.sneakersapp.features.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.R
import com.example.sneakersapp.core.constant.AppConstant
import com.example.sneakersapp.databinding.FragmentSneakerDetailsBinding
import com.example.sneakersapp.features.model.Sneaker
import com.example.sneakersapp.features.view.adapter.ViewPagerAdapter
import com.example.sneakersapp.features.viewmodel.HomeViewModel
import com.example.sneakersapp.features.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SneakerDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SneakerDetailsFragment : Fragment() {

    private var sneaker: Sneaker? = null
    private lateinit var binding: FragmentSneakerDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sneaker = it.getParcelable(AppConstant.SNEAKER_DETAIL_KEY_ACTION)
        }
        (activity?.application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sneaker_details, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sneaker_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), sneaker!!.media)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "    "
                1 -> tab.text = "    "
                2 -> tab.text = "    "
            }
        }.attach()
        startObservingLiveData()
    }

    override fun onStart() {
        super.onStart()
        updateUi()
    }

    override fun onResume() {
        super.onResume()
        binding.btnSeven.setOnClickListener {
            btnSevenSelected()
            sneaker?.size = 7
        }
        binding.btnEight.setOnClickListener {
            btnEightSelected()
            sneaker?.size = 8
        }
        binding.btnNine.setOnClickListener {
            btnNineSelected()
            sneaker?.size = 9
        }
        binding.btnPink.setOnClickListener {
            btnPinkSelected()
        }
        binding.btnDarkBlue.setOnClickListener {
            btnDarkBlueSelected()
        }
        binding.btnLightBlue.setOnClickListener {
            btnLightBlueSelected()
        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addSneakersDetailsToCart(sneaker!!)
        }
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun startObservingLiveData() {
        viewModel.insertSuccessOrFailLiveData?.observe(viewLifecycleOwner, Observer {
            if (it == AppConstant.INSERT_ITEM_SUCCESS) {
                Toast.makeText(requireContext(), "Item Added Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Item not Added", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateUi() {
        sneaker?.let {
            binding.tvSneakerTittle.text = it.shoe
            binding.tvSneakerPriceValue.text = "$" + " " + it.retailPrice.toString()

            when(it.size) {
                7 -> btnSevenSelected()
                8 -> btnEightSelected()
                9 -> btnNineSelected()
                else -> {
                    //do nothing
                }
            }

            if (!it.color.isNullOrEmpty()) {
                when(it.color) {
                    AppConstant.PINK -> btnPinkSelected()
                    AppConstant.DARK_BLUE -> btnDarkBlueSelected()
                    AppConstant.LIGHT_BLUE -> btnLightBlueSelected()
                    else -> {
                        //do nothing
                    }
                }
            }
        }
    }

    private fun btnSevenSelected() {
        binding.btnSeven.isSelected = true
        binding.btnSeven.setTextColor(resources.getColor(R.color.white))
        binding.btnEight.isSelected = false
        binding.btnEight.setTextColor(resources.getColor(R.color.orange))
        binding.btnNine.isSelected = false
        binding.btnNine.setTextColor(resources.getColor(R.color.orange))
    }

    private fun btnEightSelected() {
        binding.btnSeven.isSelected = false
        binding.btnSeven.setTextColor(resources.getColor(R.color.orange))
        binding.btnEight.isSelected = true
        binding.btnEight.setTextColor(resources.getColor(R.color.white))
        binding.btnNine.isSelected = false
        binding.btnNine.setTextColor(resources.getColor(R.color.orange))
    }

    private fun btnNineSelected() {
        binding.btnSeven.isSelected = false
        binding.btnSeven.setTextColor(resources.getColor(R.color.orange))
        binding.btnEight.isSelected = false
        binding.btnEight.setTextColor(resources.getColor(R.color.orange))
        binding.btnNine.isSelected = true
        binding.btnNine.setTextColor(resources.getColor(R.color.white))
    }

    private fun btnPinkSelected() {
        binding.btnPink.isSelected = true
        binding.btnDarkBlue.isSelected = false
        binding.btnLightBlue.isSelected = false
        sneaker?.color = AppConstant.PINK
    }

    private fun btnDarkBlueSelected() {
        binding.btnPink.isSelected = false
        binding.btnDarkBlue.isSelected = true
        binding.btnLightBlue.isSelected = false
        sneaker?.color = AppConstant.DARK_BLUE
    }

    private fun btnLightBlueSelected() {
        binding.btnPink.isSelected = false
        binding.btnDarkBlue.isSelected = false
        binding.btnLightBlue.isSelected = true
        sneaker?.color = AppConstant.LIGHT_BLUE
    }

    companion object {
        @JvmStatic
        fun newInstance(sneaker: Sneaker) =
            SneakerDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AppConstant.SNEAKER_DETAIL_KEY_ACTION, sneaker)
                }
            }
    }
}