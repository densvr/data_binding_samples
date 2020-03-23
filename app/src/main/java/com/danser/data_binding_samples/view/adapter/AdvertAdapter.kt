package com.danser.data_binding_samples.view.adapter

import android.view.View
import android.view.ViewGroup
import com.danser.data_binding_samples.databinding.ItemAdvertBinding
import com.danser.data_binding_samples.domain.FeedItem
import com.danser.data_binding_samples.utils.layoutInflater
import com.example.delegateadapter.delegate.diff.IComparableItem

class AdvertAdapter(
    val onHideClick: (FeedItem.Advert) -> Unit
) : BindingDelegateAdapter<AdvertViewModel>() {

    private lateinit var binding: ItemAdvertBinding

    override fun createView(parent: ViewGroup): View {
        binding = ItemAdvertBinding.inflate(parent.layoutInflater, parent, false)
        binding.adapter = this
        return binding.vRoot
    }

    override fun onBind(item: AdvertViewModel, viewHolder: BindingViewHolder) {
        binding.advert = item
        binding.executePendingBindings()
    }

    override fun isForViewType(items: MutableList<*>, position: Int): Boolean =
        items[position] is AdvertViewModel
}

data class AdvertViewModel(
    val payload: FeedItem.Advert
) : IComparableItem {

    override fun id(): Any = AdvertViewModel::javaClass

    override fun content(): Any = this
}
