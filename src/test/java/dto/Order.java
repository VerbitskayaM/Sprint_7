package dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Order {
    String firstName;
    String lastName;
    String address;
    int metroStation;
    String phone;
    int rentTime;
    String deliveryDate;
    String comment;
    List<String> color;
}
