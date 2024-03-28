package com.project.mockito;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MyServiceTest {

    @Test
    public void testAdd() {
        // Create a mock of the dependency
        MyService dependencyMock = mock(MyService.class);
        
        // Stubbing behavior for the mock
        when(dependencyMock.add(2, 3)).thenReturn(5);
        
        // Create an instance of the class under test
        MyClassUnderTest classUnderTest = new MyClassUnderTest(dependencyMock);
        
        // Invoke the method under test
        int result = classUnderTest.performAddition(2, 3);
        
        // Verify the result
        assertEquals(5, result);
        
        // Verify interactions with the mock
        verify(dependencyMock).add(2, 3);
    }
}
