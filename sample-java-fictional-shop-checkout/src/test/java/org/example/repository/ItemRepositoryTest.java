package org.example.repository;

import org.junit.jupiter.api.Test;

class ItemRepositoryTest {

  @Test
  void getById() {
    var repository = new ItemRepository();

    repository.getById("001");
  }
}