package capstone.toursantara.app.model

import android.os.Parcel
import android.os.Parcelable

data class Place(
    var category: String? = null,
    var city: String? = null,
    var description: String? = null,
    var place_id: String? = null,
    var place_name: String? = null,
    var price: String? = null,
    var rating: String? = null,
    var imageUrl: String? = null,
    var lat: String? = null,
    var long: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(category)
        parcel.writeString(city)
        parcel.writeString(description)
        parcel.writeString(place_id)
        parcel.writeString(place_name)
        parcel.writeString(price)
        parcel.writeString(rating)
        parcel.writeString(imageUrl)
        parcel.writeString(lat)
        parcel.writeString(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel): Place {
            return Place(parcel)
        }

        override fun newArray(size: Int): Array<Place?> {
            return arrayOfNulls(size)
        }
    }
}