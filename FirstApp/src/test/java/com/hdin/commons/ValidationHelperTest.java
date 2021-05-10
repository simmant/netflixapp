package com.hdin.commons;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
public class ValidationHelperTest {

    @Test
    public void testIsAuthPresent(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Auth-Token")).thenReturn("test");
        Assertions.assertTrue(ValidationHelper.isAuthPresent(request));
    }

    @Test
    public void testIsAuthPresent_NoAuth(){
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Auth-Token")).thenReturn(null);
        Assertions.assertFalse(ValidationHelper.isAuthPresent(request));
    }
}
