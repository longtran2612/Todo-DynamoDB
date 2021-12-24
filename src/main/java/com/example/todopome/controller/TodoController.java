package com.example.todopome.controller;


import com.example.todopome.entity.Todo;
import com.example.todopome.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
        @Autowired
        private TodoRepository todoRepository;

        @PostMapping
        public Todo save(@RequestBody Todo todo){
            return todoRepository.save(todo);
        }

        @GetMapping("/{id}")
        public Todo findById(@PathVariable(value = "id") String id){
            return todoRepository.findById(id);
        }

        @GetMapping
        public List<Todo> findAll(){
            return todoRepository.findAll();
        }

        @PutMapping("/{id}")
        public Todo update(@PathVariable(value = "id") String id,
                           @RequestBody Todo todo){
            return todoRepository.update(id, todo);
        }


        @DeleteMapping("/{id}")
        public String delete(@PathVariable(value = "id") String id){
            return todoRepository.delete(id);
        }
}

