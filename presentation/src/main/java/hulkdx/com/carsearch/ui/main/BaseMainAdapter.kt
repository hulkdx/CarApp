package hulkdx.com.carsearch.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import hulkdx.com.carsearch.R
import hulkdx.com.carsearch.utils.addRippleEffect

/**
 * Created by Mohammad Jafarzadeh Rezvan on 26/01/2019.
 */

abstract class BaseMainAdapter<ViewHolderType: BaseMainAdapter.ViewHolder, DataType>(
    private val mClickListeners: View.OnClickListener?
)
    : RecyclerView.Adapter<ViewHolderType>()
{

    internal var mModels: List<DataType> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderType {
        val itemView = LayoutInflater.from(parent.context).inflate(recyclerLayoutId(), parent, false)

        itemView.addRippleEffect()

        return createInstanceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderType, position: Int) {
        val isEven = position % 2 == 0
        holder.changeBackgroundColor(isEven)

        if (mClickListeners != null) holder.setOnClickListener(mClickListeners)
    }

    override fun onViewRecycled(holder: ViewHolderType) {
        holder.setOnClickListener(null)
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return mModels.size
    }

    //---------------------------------------------------------------
    // abstract functions
    //---------------------------------------------------------------

    fun setData(data: List<DataType>) {
        mModels = data
        notifyDataSetChanged()
    }

    fun getData(position: Int): DataType? {
        return mModels[position]
    }

    //---------------------------------------------------------------
    // abstract functions
    //---------------------------------------------------------------

    abstract fun recyclerLayoutId(): Int
    abstract fun createInstanceViewHolder(itemView: View): ViewHolderType

    //---------------------------------------------------------------
    // ViewHolder
    //---------------------------------------------------------------

    abstract class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val rootView = itemView

        fun changeBackgroundColor(isEven: Boolean) {
            rootView.background = ContextCompat.getDrawable(rootView.context,
                if (isEven) R.color.white else R.color.grey)
        }

        fun setOnClickListener(onClickListener: View.OnClickListener?) {
            rootView.setOnClickListener(onClickListener)
        }
    }
}