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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.R
import com.example.sneakersapp.core.constant.AppConstant
import com.example.sneakersapp.databinding.FragmentCartBinding
import com.example.sneakersapp.databinding.FragmentHomeBinding
import com.example.sneakersapp.features.view.adapter.CartAdapter
import com.example.sneakersapp.features.view.adapter.HomeAdapter
import com.example.sneakersapp.features.viewmodel.HomeViewModel
import com.example.sneakersapp.features.viewmodel.ViewModelFactory
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCartBinding
    private var adapter: CartAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        (activity?.application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_cart, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingLiveData()
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSneakersAddedToCart()

        binding.ivCartBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun startObservingLiveData() {
        viewModel.sneakersFromCart.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                showEmptyLayout()
            } else {
                binding.linearLayout.visibility = View.VISIBLE
                binding.llCartRecyclerview.visibility = View.VISIBLE
                binding.llNoList.visibility = View.GONE
                adapter?.updateList(it)
            }
        })
        viewModel.deleteSuccessOrFailLiveData?.observe(viewLifecycleOwner, Observer {
            if (it == AppConstant.DELETE_ITEM_SUCCESS) {
                Toast.makeText(requireContext(), "Item Deleted Successfully", Toast.LENGTH_LONG).show()
                viewModel.getSneakersAddedToCart()
            } else {
                Toast.makeText(requireContext(), "Item not Deleted", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.subTotalLiveData?.observe(viewLifecycleOwner, Observer {
            binding.tvSubtotalValue.text = "$" + " " + it.toString()
            val total = it + 40
            binding.tvTotalValue.text = "$" + " " + total.toString()
        })
    }

    private fun showEmptyLayout() {
        binding.linearLayout.visibility = View.GONE
        binding.llCartRecyclerview.visibility = View.GONE
        binding.llNoList.visibility = View.VISIBLE
    }

    private fun setUpViewModel() {
        binding.rvCartSneakers.layoutManager = LinearLayoutManager(requireContext())
        adapter = CartAdapter(ArrayList())
        binding.rvCartSneakers.adapter = adapter
        adapter?.setOnItemClickListener { view, sneaker ->
            when(view.id) {
                R.id.iv_remove_cart -> {
                    viewModel.deleteSneakerItem(sneaker.id)
                }
                else -> {
                    addReplaceFragment(SneakerDetailsFragment.newInstance(sneaker), false, false)
                }
            }
        }
    }

    private fun addReplaceFragment(
        fragment: Fragment,
        addFragment: Boolean,
        addToBackStack: Boolean
    ) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        if (addFragment) {
            transaction.add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        } else {
            transaction.replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}