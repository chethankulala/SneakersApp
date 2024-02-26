package com.example.sneakersapp.features.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.sneakersapp.R
import com.example.sneakersapp.features.model.Sneaker

class HomeAdapter(private var list: List<Sneaker>): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private var onItemClickListener: ((Sneaker) -> Unit)? = null

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivSneakerImage: ImageView = itemView.findViewById(R.id.iv_sneaker_image)
        val tvSneakerName: TextView = itemView.findViewById(R.id.tv_sneaker_name)
        val tvSneakerPrice: TextView = itemView.findViewById(R.id.tv_sneaker_price)
        fun bind(sneaker: Sneaker) {
            Glide.with(itemView.context).load(sneaker.media.imageUrl).apply(RequestOptions.fitCenterTransform())
                .transition(DrawableTransitionOptions.withCrossFade()).into(ivSneakerImage)
            tvSneakerName.text = sneaker.shoe
            tvSneakerPrice.text = "$" + " " + sneaker.retailPrice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sneakers_home, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val sneaker = list.get(position)
        holder.bind(sneaker)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(sneaker) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<Sneaker>?) {
        list?.let {
            this.list = java.util.ArrayList(it)
            notifyDataSetChanged()
        }
    }

    fun setOnItemClickListener(listener: (Sneaker) -> Unit) {
        onItemClickListener = listener
    }
}