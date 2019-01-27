package hulkdx.com.carsearch.data.models.remoteresponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
data class ApiResponse(

    @SerializedName("page")
    @Expose
    val page: Int,

    @SerializedName("pageSize")
    @Expose
    val pageSize: Int,

    @SerializedName("totalPageCount")
    @Expose
    val totalPageCount: Int,

    @SerializedName("wkda")
    @Expose
    val map: Map<String, String>
)