package com.example.todopome.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import com.example.todopome.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public Task save(Task task) {
        dynamoDBMapper.save(task);
        return task;
    }
    public List<Task> findAll(){
        return dynamoDBMapper.scan(Task.class, new DynamoDBScanExpression());
    }

    public Task findById(String task) {
        return dynamoDBMapper.load(Task.class, task);
    }



    public String delete(String id) {
        Task task = dynamoDBMapper.load(Task.class, id);
        dynamoDBMapper.delete(task);
        return "todo Deleted!";
    }

    public Task update(String id, Task task) {
        dynamoDBMapper.save(task,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return task;
    }
}
