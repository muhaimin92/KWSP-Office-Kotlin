package epf.com.office.view

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import epf.com.office.Constant.Companion.OFFICE_ID
import epf.com.office.Constant.Companion.SORT_BY_DIST
import epf.com.office.R
import epf.com.office.adapter.BranchAdapter
import epf.com.office.model.EpfOffice
import epf.com.office.service.DistanceCalculator
import epf.com.office.service.LocationService
import epf.com.office.viewmodel.BranchListViewModel
import kotlinx.android.synthetic.main.activity_branch_detail.*
import kotlinx.android.synthetic.main.branch_list_fragment.*

class BranchListFragment : Fragment() {

    companion object {
        fun newInstance() = BranchListFragment()
    }

    private lateinit var viewModel: BranchListViewModel
    private lateinit var branchAdapter: BranchAdapter
    private val REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.branch_list_fragment, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BranchListViewModel::class.java)
        viewModel.getBranchFromApi()
        viewModel.getBranch().observe(requireActivity(), Observer { renderBranch(it) })
        updateLocation()

        search_et.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                viewModel.queryName=s.toString()
                viewModel.updateData()
            }
        })


    }

    private fun renderBranch(epfOfficeList: List<EpfOffice>) {
        branchAdapter = BranchAdapter(epfOfficeList)
        branch_recyclerview.setHasFixedSize(true)
        branch_recyclerview.layoutManager = LinearLayoutManager(activity)
        branch_recyclerview.adapter = branchAdapter
        branchAdapter.setData(epfOfficeList)

        branchAdapter.onItemClick = {
            val intent = Intent(activity, BranchDetailActivity::class.java)
            intent.putExtra(OFFICE_ID, it.id)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                val intent = Intent(requireContext(), FilterActivity::class.java)
                startActivityForResult(intent,REQUEST_CODE)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            viewModel.updateData()
        }
    }


    fun updateLocation(){
        context?.let { viewModel.getCurrentLocation(it) }
    }



}
