package epf.com.office.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import epf.com.office.repository.EpfOfficeRepository

class BranchDetailViewModel(application: Application) : AndroidViewModel(application) {
    private var epfOfficeRepository: EpfOfficeRepository = EpfOfficeRepository(application)


    fun getOfficeDetail(id: Int) = epfOfficeRepository.getDataById(id)

}