
package io.github.maciejlagowski.airfield.model.service.weather;

import lombok.Data;

@Data
class Temp {

        private Double day;
        private Double min;
        private Double max;
        private Double night;
        private Double eve;
        private Double morn;
}
