package com.anushka.retrofitdemo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var text: TextView
    private lateinit var retService: DwellService

    lateinit var sessionList : MutableList<SessionDataItem>
    lateinit var venueList : MutableList<VenuesDataItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.textView)
        retService = RetrofitInstance.getRetrofitInstance().create(DwellService::class.java)
        getSessionData()
        getVenueData()

    }

    // get session data
    private fun getSessionData() {
        val sessionLiveData: LiveData<Response<SessionData>> = liveData {
            val response: Response<SessionData> = retService.getSession()
            emit(response)
        }
        sessionLiveData.observe(this, Observer {
            val sessionList = it.body()
            Log.e("get session size", sessionList!!.get(0).sessionId)

        })
    }

    // get venue data
    private fun getVenueData() {
        val venueLiveData: LiveData<Response<VenuesData>> = liveData {
            val response: Response<VenuesData> = retService.getVenue()
            emit(response)
        }
        venueLiveData.observe(this, Observer {
            venueList = it.body()!!

            computeDwellTimes(sessionList,venueList,5.0)

        })
    }

    fun computeDwellTimes(sessions: List<SessionDataItem>, venues: List<VenuesDataItem>,
                          distanceThreshold: Double): Map<String, Double> {
        // Create a map to store the dwell time for each venue
        val dwellTimes = mutableMapOf<String, Double>()

        // Loop over each venue
        for (venue in venues) {
            // Loop over each session
            for (session in sessions) {
                // Check if the session is within the distance threshold of the venue
                val distance = calculateDistance(session.path.last().position, venue.position)
                if (distance <= distanceThreshold) {
                    // Calculate the dwell time for the session
                    val dwellTime = session.endTimeUtc.toInstant().toEpochMilli() -
                            session.startTimeLocal.toInstant().toEpochMilli()

                    // Add the dwell time to the total for the venue
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        dwellTimes[venue.id] = dwellTimes.getOrDefault(venue.id, 0.0) + dwellTime
                    }
                }
            }
        }

        // Convert the dwell times from milliseconds to seconds and return the result
        return dwellTimes.mapValues { it.value / 1000 }
    }

    fun calculateDistance(position1: Position, position2: VenuePosition): Double {
        val dx = position1.x - position2.x
        val dy = position1.y - position2.y
        return Math.sqrt(dx*dx + dy*dy)
    }
}