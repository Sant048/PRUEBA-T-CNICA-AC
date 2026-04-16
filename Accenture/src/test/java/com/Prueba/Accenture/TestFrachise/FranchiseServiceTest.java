package com.Prueba.Accenture.TestFrachise;

import com.Prueba.Accenture.franchise.application.FranchiseService;
import com.Prueba.Accenture.franchise.domain.Franchise;
import com.Prueba.Accenture.franchise.domain.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FranchiseServiceTest {

    @Mock
    FranchiseRepository repository;

    @InjectMocks
    FranchiseService service;

    @Test
    void shouldCreateFranchise() {
        Franchise saved = new Franchise(1L, "Nike");

        when(repository.save(any())).thenReturn(Mono.just(saved));

        Franchise result = service.create("Nike").block();

        assertNotNull(result);
        assertEquals("Nike", result.getName());

        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldFindById() {
        Franchise franchise = new Franchise(1L, "Adidas");

        when(repository.findById(1L)).thenReturn(Mono.just(franchise));

        Franchise result = service.findById(1L).block();

        assertEquals("Adidas", result.getName());
    }

    @Test
    void shouldReturnAllFranchises() {
        when(repository.findAll()).thenReturn(
                Flux.just(new Franchise(1L, "A"), new Franchise(2L, "B"))
        );

        assertEquals(2, service.findAll().collectList().block().size());
    }

    @Test
    void shouldUpdateName() {
        Franchise existing = new Franchise(1L, "Old");

        when(repository.findById(1L)).thenReturn(Mono.just(existing));
        when(repository.save(any())).thenReturn(Mono.just(existing));

        Franchise result = service.updateName(1L, "New").block();

        assertEquals("New", result.getName());
    }

    @Test
    void shouldFailUpdateWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Mono.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateName(1L, "New").block());
    }

    @Test
    void shouldDeleteFranchise() {

        Franchise franchise = new Franchise(1L, "Nike");

        when(repository.findById(1L))
                .thenReturn(Mono.just(franchise));

        when(repository.deleteById(1L))
                .thenReturn(Mono.empty());

        service.delete(1L).block();

        verify(repository, times(1)).deleteById(1L);
    }
}