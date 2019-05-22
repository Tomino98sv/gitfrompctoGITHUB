package com.server.glbankServer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepository extends CrudRepository<loginclient, Integer> {
    public List<loginclient> findByLoginAndPassword(String name,String password);
}
