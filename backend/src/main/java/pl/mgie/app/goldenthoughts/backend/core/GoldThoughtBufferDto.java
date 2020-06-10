package pl.mgie.app.goldenthoughts.backend.core;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class GoldThoughtBufferDto {

    private long id;
    private String description;
    private ZonedDateTime createdDate;
}
