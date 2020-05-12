package epf.com.office.model

data class EpfOfficeResponse (
    var sta: String,
    var ver: String,
    var lis: List<EpfOffice>
)