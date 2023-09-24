package org.example.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.config.ConfigReader;
import org.example.model.Employee;

import java.util.Map;

public class ReadItemHandler implements RequestHandler<Map<String, Object>, Employee> {
    private static final String TABLE_NAME = ConfigReader.getProperty("dynamodb.table");

    @Override
    public Employee handleRequest(Map<String, Object> input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        String employeeId = input.get("employeeId").toString();

        Item item = table.getItem("employeeId", employeeId);

        if (item != null) {
            return new Employee(
                    item.getString("employeeId"),
                    item.getString("firstName"),
                    item.getString("lastName"),
                    item.getInt("age")
            );
        } else {
            throw new RuntimeException("Employee not found");
        }
    }
}
