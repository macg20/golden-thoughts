package pl.mgie.app.goldenthoughts.backend.core.gold.thought;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "GOLDEN_THOUGHTS_BUFFER")
public class GoldThoughtBufferEntity {

    @Id
    @GeneratedValue(generator = "gold_thought_buffer_generator")
    @GenericGenerator(
            name = "gold_thought_buffer_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "gold_thought_buff_id_seq"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private long id;
    private String description;
    private ZonedDateTime createdDateTime;
}
