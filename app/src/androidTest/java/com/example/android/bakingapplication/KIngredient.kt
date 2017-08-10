package com.example.android.bakingapplication

import android.os.Parcel
import android.os.Parcelable

data class KIngredient(val quantity: Int = 0,
                       val measure: String = "",
                       val ingredient: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(quantity)
        writeString(measure)
        writeString(ingredient)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<KIngredient> = object : Parcelable.Creator<KIngredient> {
            override fun createFromParcel(source: Parcel): KIngredient = KIngredient(source)
            override fun newArray(size: Int): Array<KIngredient?> = arrayOfNulls(size)
        }
    }
}
