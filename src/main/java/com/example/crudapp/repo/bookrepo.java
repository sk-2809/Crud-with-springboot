package com.example.crudapp.repo;

import com.example.crudapp.model.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface bookrepo extends JpaRepository<book, Long>  {

    
} 
