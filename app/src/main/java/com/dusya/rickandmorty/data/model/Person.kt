package com.dusya.rickandmorty.data.model

import android.os.Parcel
import android.os.Parcelable
import com.dusya.rickandmorty.data.model.location.Location

/**
 * Person description model
 *
 * @author Dusya Bagdasaryan on 2023.01.23
 */
data class Person(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String?>,
    val url: String,
    val created: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readParcelable(Location::class.java.classLoader) ?: Location("", ""),
        parcel.readParcelable(Location::class.java.classLoader) ?: Location("", ""),
        parcel.readString().orEmpty(),
        parcel.createStringArrayList().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(species)
        parcel.writeString(type)
        parcel.writeString(gender)
        parcel.writeParcelable(origin, flags)
        parcel.writeParcelable(location, flags)
        parcel.writeString(image)
        parcel.writeStringList(episode)
        parcel.writeString(url)
        parcel.writeString(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}