package com.samiun.mycricket

import com.samiun.mycricket.model.fixturewithrun.Run
import com.samiun.mycricket.utils.Constants
import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun age_isCorrect() {
        val age= Constants.calculateAge("1987-03-24")
        assertEquals("35 Years", age)
    }

    @Test
    fun fixture_isCorrect() {
        val time = Constants.getTime(0)
        assertEquals("2023-02-26T00:00:00.000000Z", time)
    }

    @Test
    fun date_isCorrect() {
        val date = Constants.dateFormat("2021-01-07T10:20:00.000000Z")
        assertNotEquals("Thu, Jan 7", date)
    }




    @Test
    fun url_isCorrect() {
        val time = Constants.BASE_URL
        assertEquals("2:50 PM", time)
    }



    @Test
    fun winningpercentage() {

        val List = mutableListOf<Run>()
        val run1 = Run(2239,121,1,20.0,"44/2",null,null,null,200,10,null,5)
        val run2 = Run(2239,122,2,4.0,"40/2",null,null,null,45,37,null,1)
        List.add(run1)
        List.add(run2)
        val percent1 = Constants.WinningPercentage(List)

        assertEquals("50.0", "$percent1")
    }
/*

    @Test
    fun date_isCorrect() {
        val date = Constants.dateFormat("2021-01-07T10:20:00.000000Z")
        assertNotEquals("Thu, Jan 7", date)
    }
*/




    @Test
    fun time_isCorrect() {
        val time = Constants.timeFormat("2021-01-07T10:20:00.000000Z")
        assertEquals("2:50 PM", time)
    }


}