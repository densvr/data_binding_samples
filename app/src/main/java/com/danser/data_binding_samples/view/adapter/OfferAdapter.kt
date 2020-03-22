package com.danser.data_binding_samples.view.adapter


import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danser.data_binding_samples.databinding.ItemOfferBinding
import com.danser.data_binding_samples.domain.FeedItem
import com.example.delegateadapter.delegate.diff.IComparableItem

class OfferAdapter(
    private val context: Context,
    val onClick: (payload: FeedItem.Offer) -> Unit
) : BindingDelegateAdapter<OfferViewModel>() {

    private lateinit var binding: ItemOfferBinding

    override fun createView(parent: ViewGroup): View {
        binding = ItemOfferBinding.inflate(
            context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
            parent,
            false
        )
        return binding.vRoot
    }

    override fun onBind(item: OfferViewModel, viewHolder: BindingViewHolder) {
        binding.offer = item
        binding.adapter = this
    }

    override fun isForViewType(items: MutableList<*>, position: Int): Boolean =
        items[position] is OfferViewModel
}

data class OfferViewModel(
    val title: String,
    val price: String,
    val text: String,
    val payload: FeedItem.Offer
) : IComparableItem {

    override fun id(): Any = title

    override fun content(): Any = this
}
