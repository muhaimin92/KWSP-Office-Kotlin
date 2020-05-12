package epf.com.office.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import epf.com.office.Constant
import epf.com.office.R
import epf.com.office.adapter.StateAdapter
import epf.com.office.viewmodel.FilterViewModel
import kotlinx.android.synthetic.main.activity_filter.*


class FilterActivity : AppCompatActivity() {

    private lateinit var viewModel: FilterViewModel
    private lateinit var stateAdapter: StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="Filter"

        stateAdapter = StateAdapter()
        state_recyclerview.setHasFixedSize(true)
        state_recyclerview.layoutManager = LinearLayoutManager(this)
        state_recyclerview.adapter = stateAdapter

        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)
        viewModel.getState()
        viewModel.stateCode.observe(this,
            Observer {
                stateAdapter.setData(it)
                stateAdapter.onItemClick =
                    { stateCode -> viewModel.updateSelectedState(stateCode.key) }
            })

        if (viewModel.getCurrentSort().equals(Constant.SORT_BY_NAME)) {
            name_iv.visibility = View.VISIBLE
            distance_iv.visibility = View.GONE
        } else {
            name_iv.visibility = View.GONE
            distance_iv.visibility = View.VISIBLE
        }

        name_layout.setOnClickListener {
            name_iv.visibility = View.VISIBLE
            distance_iv.visibility = View.GONE
            viewModel.setSort(Constant.SORT_BY_NAME)
        }

        distance_layout.setOnClickListener {
            name_iv.visibility = View.GONE
            distance_iv.visibility = View.VISIBLE
            viewModel.setSort(Constant.SORT_BY_DIST)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }
}
