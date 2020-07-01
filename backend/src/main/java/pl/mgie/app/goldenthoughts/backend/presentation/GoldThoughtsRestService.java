package pl.mgie.app.goldenthoughts.backend.presentation;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mgie.app.goldenthoughts.backend.core.AcceptRequestDto;
import pl.mgie.app.goldenthoughts.backend.core.GoldThoughtBufferDto;
import pl.mgie.app.goldenthoughts.backend.core.GoldThoughtDto;
import pl.mgie.app.goldenthoughts.backend.core.gold.thought.GoldThoughtBufferEntity;
import pl.mgie.app.goldenthoughts.backend.core.gold.thought.GoldThoughtBufferRepository;
import pl.mgie.app.goldenthoughts.backend.core.gold.thought.GoldThoughtEntity;
import pl.mgie.app.goldenthoughts.backend.core.gold.thought.GoldThoughtRepository;


import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value = "/goldenthoughts")
class GoldThoughtsRestService {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private GoldThoughtRepository goldThoughtRepository;
    private GoldThoughtBufferRepository goldThoughtBufferRepository;

    public GoldThoughtsRestService(GoldThoughtRepository goldThoughtRepository, GoldThoughtBufferRepository goldThoughtBufferRepository) {
        this.goldThoughtRepository = goldThoughtRepository;
        this.goldThoughtBufferRepository = goldThoughtBufferRepository;
    }

    @GetMapping(params = {"page", "size"})
    ResponseEntity<Page<GoldThoughtDto>> findAllPagination(@RequestParam(name = "page", required = false) int page) {
        PageRequest.of(page, DEFAULT_PAGE_SIZE);

        Page<GoldThoughtDto> goldenThoughtsPage = goldThoughtRepository
                .findAll(PageRequest.of(page, DEFAULT_PAGE_SIZE)).map(e -> {
                    GoldThoughtDto dto = new GoldThoughtDto();
                    dto.setDescription(e.getDescription());
                    return dto;
                });

        return ResponseEntity.ok(goldenThoughtsPage);
    }

    @GetMapping(value = "/admin/buffer", params = {"page", "size"})
    @ApiOperation(value ="Endpoint pokazuje dane buforowe", hidden = true)
    ResponseEntity<Page<GoldThoughtBufferDto>> findAllBuffersPagination(@RequestParam(name = "page", required = false) int page) {
        PageRequest.of(page, DEFAULT_PAGE_SIZE);

        Page<GoldThoughtBufferDto> goldenThoughtsPage = goldThoughtBufferRepository
                .findAll(PageRequest.of(page, DEFAULT_PAGE_SIZE)).map(e -> {
                    GoldThoughtBufferDto dto = new GoldThoughtBufferDto();
                    dto.setDescription(e.getDescription());
                    dto.setId(e.getId());
                    return dto;
                });

        return ResponseEntity.ok(goldenThoughtsPage);
    }

    @PostMapping
    ResponseEntity save(@RequestBody @Valid GoldThoughtDto dto) {

        GoldThoughtBufferEntity entity = new GoldThoughtBufferEntity();
        entity.setCreatedDateTime(ZonedDateTime.now());
        entity.setDescription(dto.getDescription());

        goldThoughtBufferRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/admin/buffer")
    @ApiOperation(value ="Endpoint akceptuje dane buforowe", hidden = true)
    ResponseEntity accept(AcceptRequestDto acceptRequestDto) throws NotFoundException {

        Optional<GoldThoughtBufferEntity> buffer = goldThoughtBufferRepository.findById(acceptRequestDto.getId());

        GoldThoughtEntity entity = buffer.map(e -> {
            GoldThoughtEntity temp = new GoldThoughtEntity();
            temp.setDescription(e.getDescription());
            temp.setCreatedDateTime(e.getCreatedDateTime());
            temp.setAcceptedDateTime(ZonedDateTime.now());
            return temp;
        }).orElseThrow(() -> new NotFoundException("Not fonud buffers"));

        goldThoughtRepository.save(entity);

        return ResponseEntity.accepted().build();
    }
}
