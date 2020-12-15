package io.github.maciejlagowski.airfield.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String to;
    private String username;
    private String subject;
    private String text;
}
