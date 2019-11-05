package com.server.running.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.running.group.dto.Group;
import com.sun.xml.bind.v2.model.core.ID;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
