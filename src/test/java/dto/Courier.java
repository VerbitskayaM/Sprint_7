package dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Courier {
    String login;
    String password;
    @Builder.Default
    String firstName = "Ann";
}
