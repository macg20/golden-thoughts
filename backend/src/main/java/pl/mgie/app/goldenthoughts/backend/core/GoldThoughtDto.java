package pl.mgie.app.goldenthoughts.backend.core;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldThoughtDto {

    @NotNull
    private String description;
}
