package com.example.contactlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class ContactAdapter(private val originalData: ArrayList<contactListData>, private val itemClickListener: ItemClickListener): RecyclerView.Adapter<ContactAdapter.ViewHolder>(),
    Filterable {

    private var filteredData: ArrayList<contactListData> = originalData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.contact_list_row, parent, false)
        )
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredData[position]
        // Bind item data to the views in the ViewHolder
        holder.bind(item)


        val isSelected = item.isSelected // Set the background color of the row based on the selection state
        val backgroundColor = if (isSelected) {
            ContextCompat.getColor(holder.itemView.context, R.color.selectedColor)

        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.white)

        }
        holder.itemView.setBackgroundColor(backgroundColor)
//        else {
//            holder.itemView.setBackgroundColor(Color.WHITE)
//        }
        holder.itemView.setOnClickListener {
            itemClickListener.onContactSelected(item)
            item.isSelected = !isSelected
            notifyItemChanged(position)
        }
    }

    interface ItemClickListener {
        fun onContactSelected(contact: contactListData)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: contactListData) {
            itemView.findViewById<ImageView>(R.id.smileyView).setImageResource(item.img)
            itemView.findViewById<TextView>(R.id.contactName).text = item.name
            itemView.findViewById<TextView>(R.id.subtitle).text = item.phoneType
            itemView.findViewById<TextView>(R.id.mobNo).text = item.phoneNumber
        }
    }



    override fun getItemCount(): Int {
        return filteredData.size
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filteredList = mutableListOf<contactListData>()



                query?.let { searchText ->
                    if (searchText.isNotBlank()) {
                        for (item in originalData) {
                            if (item.name.contains(
                                    searchText,
                                    ignoreCase = true
                                ) || item.phoneNumber.contains(searchText, ignoreCase = true)
                            ) {
                                filteredList.add(item)
                            }
                        }
                    } else {
                        filteredList.addAll(originalData)
                    }
                }


                // Apply selection filtering to the filtered list
                val filteredSelectedList = filteredList.filter { originalData.contains(it) }


                val results = FilterResults()
                results.values = filteredSelectedList
                results.count = filteredSelectedList.size
                return results
            }


            @Suppress("UNCHECKED_CAST")
            override fun publishResults(query: CharSequence?, results: FilterResults?) {
                filteredData = (results?.values as? List<contactListData>
                    ?: emptyList()) as ArrayList<contactListData>
                notifyDataSetChanged()
            }


            fun setData(newData: ArrayList<contactListData>) {
                originalData.clear()
                originalData.addAll(newData)
                filteredData = originalData
                notifyDataSetChanged()
            }



            }
        }
        }


