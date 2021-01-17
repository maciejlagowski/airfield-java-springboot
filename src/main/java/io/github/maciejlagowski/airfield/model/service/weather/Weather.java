
package io.github.maciejlagowski.airfield.model.service.weather;

import lombok.Data;

@Data
class Weather {

    private Long id;
    private String main;
    private String description;
    private String icon; // TODO hourly with icon
}
