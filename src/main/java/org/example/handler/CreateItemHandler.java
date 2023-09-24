package org.example.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.config.ConfigReader;
import org.example.model.Employee;

public class CreateItemHandler implements RequestHandler<Employee, String> {
    private static final String TABLE_NAME = ConfigReader.getProperty("dynamodb.table");

    @Override
    public String handleRequest(Employee employee, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        Item item = new Item()
                .withPrimaryKey("employeeId", employee.getEmployeeId())
                .withString("firstName", employee.getFirstName())
                .withString("lastName", employee.getLastName())
                .withInt("age", employee.getAge());

        PutItemOutcome putItemOutcome = table.putItem(item);

        return "Item created successfully";
    }
}
