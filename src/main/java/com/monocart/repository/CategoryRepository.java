package com.monocart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocart.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
 
}
