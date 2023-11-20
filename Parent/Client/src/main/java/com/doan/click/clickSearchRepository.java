package com.doan.click;

import com.doan.mutual.entity.ClickSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clickSearchRepository extends JpaRepository<ClickSearch,Integer> {
}
