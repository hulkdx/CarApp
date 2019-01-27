package hulkdx.com.carsearch.ui.main.builtdates

import android.view.View
import android.widget.TextView
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.CarBuiltDate
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.ui.main.BaseMainAdapter


/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
class CarBuiltDateAdapter: BaseMainAdapter<CarBuiltDateAdapter.ViewHolder, CarBuiltDate>(null)
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val model = mModels[position]
        holder.date.text = model.date
    }

    //---------------------------------------------------------------
    // override functions from BaseMainAdapter
    //---------------------------------------------------------------

    override fun recyclerLayoutId() = R.layout.recycler_built_dates

    override fun createInstanceViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : BaseMainAdapter.ViewHolder(itemView) {

        val date: TextView = itemView.findViewById(R.id.item_manufacture_date)
    }
}