package org.example.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.config.ConfigReader;
import org.example.model.Employee;

public class UpdateItemHandler implements RequestHandler<Employee, String> {
    private static final String TABLE_NAME = ConfigReader.getProperty("dynamodb.table");

    @Override
    public String handleRequest(Employee employee, Context context) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable(TABLE_NAME);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("employeeId", employee.getEmployeeId())
                .withUpdateExpression("set firstName = :f, lastName = :l, age = :a")
                .withValueMap(new ValueMap()
                        .withString(":f", employee.getFirstName())
                        .withString(":l", employee.getLastName())
                        .withInt(":a", employee.getAge()))
                .withReturnValues("UPDATED_NEW");

        try {
            dynamoDB.getTable(TABLE_NAME).updateItem(updateItemSpec);
            return "Employee updated successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error updating employee: " + e.getMessage());
        }
    }
}
