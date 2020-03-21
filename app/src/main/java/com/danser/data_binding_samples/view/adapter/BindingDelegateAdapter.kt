package com.danser.data_binding_samples.view.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.delegateadapter.delegate.IDelegateAdapter
import kotlinx.android.extensions.LayoutContainer

abstract class BindingDelegateAdapter<T> : IDelegateAdapter<BindingViewHolder, T> {

    protected abstract fun createView(parent: ViewGroup): View

    protected open fun onCreated(view: View) = Unit

    protected abstract fun onBind(item: T, viewHolder: BindingViewHolder)

    override fun onRecycled(holder: BindingViewHolder) = Unit

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = createView(parent)
        val viewHolder = BindingViewHolder(view) { onCreated(it) }
        viewHolder.setListener(object : BindingViewHolder.ItemInflateListener {
            override fun inflated(viewType: Any, view: View) {
                onBind(viewType as T, viewHolder)
            }
        })
        return viewHolder
    }

    final override fun onBindViewHolder(
        holder: BindingViewHolder,
        items: MutableList<T>,
        position: Int
    ) {
        holder.bind(items[position] as Any)
    }
}

class BindingViewHolder(
    override val containerView: View,
    onCreated: (View) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        onCreated(containerView)
    }

    private var listener: ItemInflateListener? = null

    fun setListener(listener: ItemInflateListener) {
        this.listener = listener
    }

    fun bind(item: Any) {
        listener?.inflated(item, itemView)
    }

    interface ItemInflateListener {
        fun inflated(viewType: Any, view: View)
    }
}


