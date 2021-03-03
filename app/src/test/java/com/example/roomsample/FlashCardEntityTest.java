package com.example.roomsample;

import com.example.roomsample.Entities.FlashCardEntity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test classes run locally
 * and should test the functionality of one class
 * and validate that it does what it is suppose to.
 *
 * you dont have to use the Before and After stuff if you don't want to
 * */

public class FlashCardEntityTest {
    // set up object to test
    private FlashCardEntity flashcard;
    // parameters to test against
    private String front;
    private String back;
    private long id;

    @Before
    public void setUp(){
        // this runs before a test is executed
        flashcard = new FlashCardEntity();
        front = "TEST";
        back = "BACKTEST";
        id = 10245L;
    }

    @After
    public void finished(){
        // put in here what you wanna see when done
        System.out.println("Done testing");
    }

    @Test
    public void testSetFront() {
        flashcard.setFront("TEST");
        // message only displayed if and only if the two strings don't match
        assertEquals("Given input does not match expected",flashcard.getFront(),front);
    }

    @Test
    public void testSetBack() {
        flashcard.setBack("BACKTEST");
        assertEquals("Did not get correct stuff",flashcard.getBack(),back);
    }

    @Test
    public void testId() {
        flashcard.setId(10245L);
        assertEquals("IDs do not match", id, flashcard.getId());
    }
}
