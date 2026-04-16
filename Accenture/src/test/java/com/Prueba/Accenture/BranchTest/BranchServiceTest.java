package com.Prueba.Accenture.BranchTest;

import com.Prueba.Accenture.branch.application.BranchService;
import com.Prueba.Accenture.branch.domain.Branch;
import com.Prueba.Accenture.branch.domain.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class BranchServiceTest {

    private BranchRepository repository;
    private BranchService service;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(BranchRepository.class);
        service = new BranchService(repository);
    }

    @Test
    void shouldCreateBranch() {

        Branch saved = new Branch(1L, "Branch A", 10L);

        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(Mono.just(saved));

        Mono<Branch> result = service.create("Branch A", 10L);

        StepVerifier.create(result)
                .expectNextMatches(branch ->
                        branch.getName().equals("Branch A") &&
                                branch.getFranchiseId().equals(10L)
                )
                .verifyComplete();
    }

    @Test
    void shouldFindById() {

        Mockito.when(repository.findById(1L))
                .thenReturn(Mono.just(new Branch(1L, "A", 1L)));

        StepVerifier.create(service.findById(1L))
                .expectNextMatches(b -> b.getName().equals("A"))
                .verifyComplete();
    }

    @Test
    void shouldFindAll() {

        Mockito.when(repository.findAll())
                .thenReturn(Flux.just(
                        new Branch(1L, "A", 1L),
                        new Branch(2L, "B", 1L)
                ));

        StepVerifier.create(service.findAll())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldFindByFranchise() {

        Mockito.when(repository.findByFranchiseId(1L))
                .thenReturn(Flux.just(
                        new Branch(1L, "A", 1L)
                ));

        StepVerifier.create(service.findByFranchise(1L))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldUpdateName() {

        Branch branch = new Branch(1L, "Old", 1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Mono.just(branch));

        Mockito.when(repository.save(Mockito.any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(service.updateName(1L, "New"))
                .expectNextMatches(b -> b.getName().equals("New"))
                .verifyComplete();
    }

    @Test
    void shouldDelete() {

        Branch branch = new Branch(1L, "Test", 1L);

        Mockito.when(repository.findById(1L))
                .thenReturn(Mono.just(branch));

        Mockito.when(repository.deleteById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1L))
                .verifyComplete();
    }
}