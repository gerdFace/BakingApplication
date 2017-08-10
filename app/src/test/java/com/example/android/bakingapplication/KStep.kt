package com.example.android.bakingapplication

import android.os.Parcel
import android.os.Parcelable


data class KStep(val id: Int = 0,
                 val shortDescription: String = "",
                 val description: String = "0",
                 val videoURL: String = "",
                 val thumbnailURL: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(shortDescription)
        writeString(description)
        writeString(videoURL)
        writeString(thumbnailURL)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<KStep> = object : Parcelable.Creator<KStep> {
            override fun createFromParcel(source: Parcel): KStep = KStep(source)
            override fun newArray(size: Int): Array<KStep?> = arrayOfNulls(size)
        }
    }
}
