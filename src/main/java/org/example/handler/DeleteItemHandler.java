package org.example.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.config.ConfigReader;

import java.util.Map;

public class DeleteItemHandler implements RequestHandler<Map<String, Object>, String> {
    private static final String TABLE_NAME = ConfigReader.getProperty("dynamodb.table");

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        String employeeId = input.get("employeeId").toString();

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("employeeId", employeeId);

        try {
            dynamoDB.getTable(TABLE_NAME).deleteItem(deleteItemSpec);
            return "Employee deleted successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error deleting employee: " + e.getMessage());
        }
    }
}
