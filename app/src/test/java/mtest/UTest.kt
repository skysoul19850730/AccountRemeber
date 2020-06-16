package mtest

import org.junit.Test

class UTest {

    data class User(var name:String="qc",var age:Int =1){
        var sex = 1
    }

    @Test
    fun testUser(){
        println(User("sqc",34))
        println(User("cc"))
        var user = User("tf",35)
        user.sex = 0
        user.name="tffff"
        println(user)
        user = User()
        println(user)
    }
}