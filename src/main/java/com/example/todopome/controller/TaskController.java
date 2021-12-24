package com.example.todopome.controller;

import com.example.todopome.entity.Task;
import com.example.todopome.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public Task save(@RequestBody Task task){
        return taskRepository.save(task);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable(value = "id") String id){
        return taskRepository.findById(id);
    }

    @GetMapping
    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable(value = "id") String id,
                         @RequestBody Task task){
        return taskRepository.update(id, task);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") String id){
        return taskRepository.delete(id);
    }
}

