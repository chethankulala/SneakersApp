package com.example.sneakersapp.features.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sneakersapp.MyApplication
import com.example.sneakersapp.R
import com.example.sneakersapp.core.di.AppComponent
import com.example.sneakersapp.databinding.FragmentHomeBinding
import com.example.sneakersapp.features.view.adapter.HomeAdapter
import com.example.sneakersapp.features.viewmodel.HomeViewModel
import com.example.sneakersapp.features.viewmodel.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var adapter: HomeAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingLiveData()
        setUpViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSneakersDetails()
    }

    private fun startObservingLiveData() {
        viewModel.sneaker.observe(viewLifecycleOwner, Observer {
            adapter?.updateList(it)
        })
    }

    private fun setUpViewModel() {
        binding.rvSneakers.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = HomeAdapter(ArrayList())
        binding.rvSneakers.adapter = adapter
        adapter?.setOnItemClickListener {

            addReplaceFragment(SneakerDetailsFragment.newInstance(it), false, false)
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
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}