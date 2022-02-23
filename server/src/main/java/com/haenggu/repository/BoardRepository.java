package com.haenggu.repository;

import com.haenggu.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, UUID> {
}
