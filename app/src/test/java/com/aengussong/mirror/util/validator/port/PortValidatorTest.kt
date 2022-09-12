package com.aengussong.mirror.util.validator.port

import com.aengussong.mirror.util.Validator
import org.junit.Assert
import org.junit.Test

class PortValidatorTest {

    @Test
    fun `letters in port - should fail`() {
        val invalidPort = "tttt"
        val isValid = Validator.validatePort(invalidPort)
        Assert.assertEquals(false, isValid)
    }

    @Test
    fun `negative integer in port - should fail`() {
        val invalidPort = "-355"
        val isValid = Validator.validatePort(invalidPort)
        Assert.assertEquals(false, isValid)
    }

    @Test
    fun `max border overflow in port - should fail`() {
        val invalidPort = "65536"
        val isValid = Validator.validatePort(invalidPort)
        Assert.assertEquals(false, isValid)
    }

    @Test
    fun `non letter characters in port - should fail`() {
        val invalidPort = "56@45"
        val isValid = Validator.validatePort(invalidPort)
        Assert.assertEquals(false, isValid)
    }
    
    @Test
    fun `valid port - should succeed`(){
        val validPort = "2234"
        val isValid = Validator.validatePort(validPort)
        Assert.assertEquals(true, isValid)
    }

    @Test
    fun `leading zeroes - should succeed`() {
        val validPort = "0024"
        val isValid = Validator.validatePort(validPort)
        Assert.assertEquals(true, isValid)
    }
}