package com.example.sneakersapp.features.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.sneakersapp.R
import com.example.sneakersapp.features.model.Sneaker

class CartAdapter(private var list: List<Sneaker>): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var onItemClickListener: ((View, Sneaker) -> Unit)? = null

    inner class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivCartSneakerImage: ImageView = itemView.findViewById(R.id.iv_cart_sneaker_image)
        val tvCartSneakerName: TextView = itemView.findViewById(R.id.tv_cart_sneaker_name)
        val tvCartSneakerPrice: TextView = itemView.findViewById(R.id.tv_cart_sneaker_price)
        val llCartLayout: ConstraintLayout = itemView.findViewById(R.id.ll_cart_layout)
        val ivRemoveCart: ImageView = itemView.findViewById(R.id.iv_remove_cart)
        fun bind(sneaker: Sneaker) {
            Glide.with(itemView.context).load(sneaker.media.imageUrl).apply(RequestOptions.fitCenterTransform())
                .transition(DrawableTransitionOptions.withCrossFade()).into(ivCartSneakerImage)
            tvCartSneakerName.text = sneaker.shoe
            tvCartSneakerPrice.text = "$" + " " + sneaker.retailPrice.toString()
            llCartLayout.setOnClickListener { view ->
                onItemClickListener?.let { it(view, sneaker) }
            }
            ivRemoveCart.setOnClickListener { view ->
                onItemClickListener?.let { it(view, sneaker) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sneakers_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val sneaker = list.get(position)
        holder.bind(sneaker)
        /*holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(sneaker) }
            }
        }*/
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

    fun setOnItemClickListener(listener: (View, Sneaker) -> Unit) {
        onItemClickListener = listener
    }
}