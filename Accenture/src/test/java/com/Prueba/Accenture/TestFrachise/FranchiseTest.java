package com.Prueba.Accenture.TestFrachise;

import com.Prueba.Accenture.franchise.domain.Franchise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FranchiseTest {

    @Test
    void shouldRenameSuccessfully() {
        Franchise franchise = new Franchise(1L, "Old Name");
        franchise.rename("New Name");
        assertEquals("New Name", franchise.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Franchise franchise = new Franchise();

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> franchise.rename(""));

        assertEquals("Franchise name cannot be empty", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Franchise franchise = new Franchise();

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> franchise.rename(null));

        assertEquals("Franchise name cannot be empty", ex.getMessage());
    }
}