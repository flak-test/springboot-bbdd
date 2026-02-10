package com.project.code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
// JpaRepository es una interface que provides basic CRUD operations out of the box
}