package com.example.android.bakingapplication

import android.os.Parcel
import android.os.Parcelable

data class KRecipe(val id: Int = 0,
                   val dessertName: String = "",
                   val numberOfSteps: String = "0",
                   val ingredients: ArrayList<KIngredient>,
                   val steps: ArrayList<KStep>,
                   val servings: Int = 0,
                   val image: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.createTypedArrayList(KIngredient.CREATOR),
            source.createTypedArrayList(KStep.CREATOR),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(dessertName)
        writeString(numberOfSteps)
        writeTypedList(ingredients)
        writeTypedList(steps)
        writeInt(servings)
        writeString(image)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<KRecipe> = object : Parcelable.Creator<KRecipe> {
            override fun createFromParcel(source: Parcel): KRecipe = KRecipe(source)
            override fun newArray(size: Int): Array<KRecipe?> = arrayOfNulls(size)
        }
    }
}



