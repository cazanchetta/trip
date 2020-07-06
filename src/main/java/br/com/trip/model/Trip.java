package br.com.trip.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "trip")
public class Trip {
    @DynamoDBHashKey(attributeName = "country")
    private String country;

    @DynamoDBIndexRangeKey(attributeName = "city", localSecondaryIndexName = "cityIndex")
    private String city;

    @DynamoDBRangeKey(attributeName = "dateTrip")
    private String dateTrip;

    public Trip(String country, String city, String date, String reason) {
        super();
        this.country = country;
        this.city = city;
        this.dateTrip = date;
    }

    public Trip() {
        super();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(String dateTrip) {
        this.dateTrip = dateTrip;
    }
}
