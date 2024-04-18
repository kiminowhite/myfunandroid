package com.knw.myfunandroid

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Dimension
import com.knw.myfunandroid.utils.SizeUtils

class MainPageBottomItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr)
{


    private var onItemClickListener : OnItemClickListener? = null
    private  var mItemTextName:String =""
    private var  mItemIconDefaultResId :Int =-1
    private var  mItemIconPressResId  :Int =-1
    private var   mTextColorDefalut  :Int =-1
    private var    mTextColorPress  :Int =-1
    private var   mTextSize :Float=5f

    private lateinit var mItemIcon:ImageView
    private lateinit var mItemName:TextView

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_main_page_bottom_item, this, true)
        initAttrs(context, attrs)
        initView(view)
        initData(view)


    }

    private fun initData(v:View) {
        mItemIcon.setImageResource(mItemIconDefaultResId)
        mItemName.setText(mItemTextName)
        mItemName.setTextColor(mTextColorDefalut)
        mItemName.setTextSize(mTextSize)

        //将颜色和资源带出去，让Main执行ui更新
        v.setOnClickListener({
           onItemClickListener?.onItemClick(v,mItemIconPressResId,mTextColorPress)
        })

    }

    private fun initView(view: View) {
        mItemIcon = view.findViewById(R.id.main_page_botton_item_icon)
        mItemName =view.findViewById(R.id.main_page_botton_item_name)
    }


    fun resetStyle() {
        // 重置样式
        mItemIcon.setImageResource(mItemIconDefaultResId)
        mItemName.setTextColor(mTextColorDefalut)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainPageBottomItemView)
        mItemTextName =
            typedArray.getString(R.styleable.MainPageBottomItemView_item_text_name) ?: ""
        mItemIconDefaultResId =
            typedArray.getResourceId(R.styleable.MainPageBottomItemView_item_icon_default, -1)
        mItemIconPressResId =
            typedArray.getResourceId(R.styleable.MainPageBottomItemView_item_icon_press, -1)
        mTextColorDefalut =
            typedArray.getColor(R.styleable.MainPageBottomItemView_text_color_defalut, -1)
        mTextColorPress =
            typedArray.getColor(R.styleable.MainPageBottomItemView_text_color_press, -1)
        mTextSize=typedArray.getDimension(R.styleable.MainPageBottomItemView_text_size,
            SizeUtils.dip2px(20f).toFloat())
        typedArray.recycle()
    }
     interface  OnItemClickListener{
      fun  onItemClick(v: View,mItemIconPressResId:Int,mTextColorPress:Int)

    }
   fun setOnItemClickListener(onItemClickListener:OnItemClickListener)
   {
       this.onItemClickListener = onItemClickListener
   }
}