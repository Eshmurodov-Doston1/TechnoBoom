package com.example.technoboom.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.technoboom.databinding.RvRemoteItemBinding
import com.example.technoboom.models.files.Data
import com.example.technoboom.utils.visible
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ImageAdapter(var listData:ArrayList<Data>,var onItemClickListener: OnItemClickListener):RecyclerView.Adapter<ImageAdapter.Vh>(),CoroutineScope {
    inner class Vh(var rvRemoteItemBinding: RvRemoteItemBinding):RecyclerView.ViewHolder(rvRemoteItemBinding.root){
        fun onBind(data:Data,position: Int){
            launch(Dispatchers.IO) {
                    val decodedString: ByteArray = Base64.decode(data.file, Base64.DEFAULT)
                    val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                    launch(Dispatchers.Main) {
                        rvRemoteItemBinding.image.setImageBitmap(decodedByte)
                    }
            }
            if (data.isSend!=null && data.isSend==false){
                rvRemoteItemBinding.noSend.visible()
            }
            rvRemoteItemBinding.cardImage.setOnClickListener {
                onItemClickListener.onItemClick(data,position)
            }
        }
    }

    class MyDiffUtil :DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.equals(newItem)
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.equals(newItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvRemoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listData[position],position)
    }
    interface OnItemClickListener{
        fun onItemClick(uri: Data,position: Int)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + Job()
}