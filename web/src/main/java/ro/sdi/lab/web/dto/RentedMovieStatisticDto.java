package ro.sdi.lab.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class RentedMovieStatisticDto
{
    String movieName;
    Long numberOfRentals;
}
