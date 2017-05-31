package com.example.android.bakingapplication

import android.os.Parcel
import android.os.Parcelable

data class KRecipe(val dessertName: String = "",
                   val numberOfSteps: String = "0",
                   val numberOfIngredients: String = "0",
                   val numberOfServings: String = "",
                   val thumbnail: Int = 0) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<KRecipe> = object : Parcelable.Creator<KRecipe> {
            override fun createFromParcel(source: Parcel): KRecipe = KRecipe(source)
            override fun newArray(size: Int): Array<KRecipe?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readString(),
    source.readString(),
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(dessertName)
        dest.writeString(numberOfSteps)
        dest.writeString(numberOfIngredients)
        dest.writeString(numberOfServings)
        dest.writeInt(thumbnail)
    }
}


