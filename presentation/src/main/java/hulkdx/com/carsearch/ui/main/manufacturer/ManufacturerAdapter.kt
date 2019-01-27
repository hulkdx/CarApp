package hulkdx.com.carsearch.ui.main.manufacturer

import android.view.View
import android.widget.TextView
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.data.models.Manufacturer
import hulkdx.com.carsearch.ui.main.BaseMainAdapter


/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */
class ManufacturerAdapter(mClickListeners: View.OnClickListener)
    : BaseMainAdapter<ManufacturerAdapter.ViewHolder, Manufacturer>(mClickListeners)
{

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val model = mModels[position]
        holder.name.text = model.name
        holder.id.text   = model.id
    }

    //---------------------------------------------------------------
    // override functions from BaseMainAdapter
    //---------------------------------------------------------------

    override fun recyclerLayoutId() = R.layout.recycler_manufacture

    override fun createInstanceViewHolder(itemView: View): ViewHolder {
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : BaseMainAdapter.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.item_manufacture_name)
        val id: TextView = itemView.findViewById(R.id.item_manufacture_id)
    }
}