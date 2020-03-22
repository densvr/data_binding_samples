package com.danser.data_binding_samples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.danser.data_binding_samples.databinding.ActivityFeedBinding
import com.danser.data_binding_samples.presentation.FeedPresentationModel
import com.danser.data_binding_samples.presentation.FeedViewModel
import com.danser.data_binding_samples.view.adapter.AdvertAdapter
import com.danser.data_binding_samples.view.adapter.DividerAdapter
import com.danser.data_binding_samples.view.adapter.OfferAdapter
import com.example.delegateadapter.delegate.diff.DiffUtilCompositeAdapter

class FeedActivity : AppCompatActivity() {

    private lateinit var presentation: FeedPresentationModel

    private val adapter: DiffUtilCompositeAdapter by lazy { getDiffAdapter() }

    private lateinit var binding: ActivityFeedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed)

        setSupportActionBar(binding.vToolbar)

        initPresentationModel()
        bindRecycler()
    }

    private fun initPresentationModel() {
        presentation = ViewModelProviders.of(this)[FeedPresentationModel::class.java]

        presentation.modelLiveData.observe(this, Observer { model: FeedViewModel ->
            update(model)
        })

        binding.shownOffersCount = presentation.shownOfferCount

    }

    private fun bindRecycler() {
        binding.rvList.adapter = adapter
        binding.rvList.layoutManager = LinearLayoutManager(this)
    }

    private fun update(model: FeedViewModel) {
        adapter.swapData(model.items)
    }

    private fun getDiffAdapter(): DiffUtilCompositeAdapter = DiffUtilCompositeAdapter.Builder()
        .add(OfferAdapter(this, presentation::onOfferClicked))
        .add(AdvertAdapter(presentation::onHideAdvertClicked))
        .add(DividerAdapter)
        .build()
}
