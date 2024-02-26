package com.example.sneakersapp.features.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.sneakersapp.R
import com.example.sneakersapp.core.constant.AppConstant
import com.example.sneakersapp.databinding.FragmentSneakerImageBinding
import com.example.sneakersapp.features.model.Media

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SneakerImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SneakerImageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var media: Media? = null
    private var pos: Int = 0
    private lateinit var binding: FragmentSneakerImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            media = it.getParcelable(AppConstant.SNEAKER_IMAGE_KEY_ACTION)
            pos = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sneaker_image, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sneaker_image, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(pos) {
            0 -> {
                Glide.with(view.context).load(media?.imageUrl).into(binding.ivSneakerImage)
            }
            1 -> {
                Glide.with(view.context).load(media?.smallImageUrl).into(binding.ivSneakerImage)
            }
            2 -> {
                Glide.with(view.context).load(media?.thumbUrl).into(binding.ivSneakerImage)
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(media: Media, pos: Int) =
            SneakerImageFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AppConstant.SNEAKER_IMAGE_KEY_ACTION, media)
                    putInt("position", pos)
                }
            }
    }
}