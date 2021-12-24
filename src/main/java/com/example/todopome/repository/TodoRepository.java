package com.example.todopome.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import com.example.todopome.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TodoRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public Todo save(Todo todo) {
        dynamoDBMapper.save(todo);
        return todo;
    }
    public List<Todo> findAll(){
        return dynamoDBMapper.scan(Todo.class, new DynamoDBScanExpression());
    }

    public Todo findById(String id) {
        return dynamoDBMapper.load(Todo.class, id);
    }

    public String delete(String id) {
        Todo todo = dynamoDBMapper.load(Todo.class, id);
        dynamoDBMapper.delete(todo);
        return "todo Deleted!";
    }

    public Todo update(String id, Todo todo) {
        dynamoDBMapper.save(todo,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(id)
                                )));
        return todo;
    }
}
