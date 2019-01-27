package hulkdx.com.carsearch.ui.main.cartype

import android.view.View
import android.widget.TextView
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.CarType
import hulkdx.com.carsearch.ui.main.BaseMainAdapter


/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */

class CarTypeAdapter(mClickListeners: View.OnClickListener)
    : BaseMainAdapter<CarTypeAdapter.ViewHolder, CarType>(mClickListeners)
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        
        val model = mModels[position]

        holder.name.text = model.name
    }

    //---------------------------------------------------------------
    // override functions from BaseMainAdapter
    //---------------------------------------------------------------

    override fun recyclerLayoutId() = R.layout.recycler_car_type
    
    override fun createInstanceViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : BaseMainAdapter.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.item_manufacture_name)

    }
}