package com.example.technoboom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technoboom.databinding.RvRemoteItemBinding
import com.example.technoboom.models.files.Data
import com.example.technoboom.network.authService.ImageUtil
import com.example.technoboom.utils.gone
import com.example.technoboom.utils.visible
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ImageAdapter(var listData:ArrayList<Data>,var onItemClickListener: OnItemClickListener):RecyclerView.Adapter<ImageAdapter.Vh>(),CoroutineScope {
    inner class Vh(var rvRemoteItemBinding: RvRemoteItemBinding):RecyclerView.ViewHolder(rvRemoteItemBinding.root){
        fun onBind(data:Data,position: Int){


           launch(Dispatchers.IO + Job()) {
               async {
                   if (data.fileFormat.trim().lowercase() == "pdf".trim().lowercase()){
                       rvRemoteItemBinding.image.gone()
                       rvRemoteItemBinding.textPdf.visible()
                   }else{
                       ImageUtil.convert(data.file){image->
                           launch(Dispatchers.Main + Job()) {
                               rvRemoteItemBinding.image.setImageBitmap(image)
                           }
                       }
                   }
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