package ru.practicum.service.compilation;

import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.dto.category.NewCompilationDto;
import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;
import ru.practicum.entity.Compilation;
import ru.practicum.entity.Event;
import ru.practicum.exception.CompilationException;
import ru.practicum.exception.CompilationNotFound;
import ru.practicum.exception.NotFoundEventException;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.qobjects.QCompilation;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        if (Objects.isNull(newCompilationDto.getTitle())) {
            throw new CompilationException("Name is not can null");
        }
        validation(newCompilationDto);
        Compilation compilationToSave = CompilationMapper.toCompilation(newCompilationDto);
        Compilation compilation = compilationRepository.save(compilationToSave);
        log.info(compilation.toString());
        return CompilationMapper.toCompilationDto(compilation);
    }

    private void validation(NewCompilationDto compilation) {
        if (Objects.nonNull(compilation.getTitle())) {
            if (compilation.getTitle().isBlank() || compilation.getTitle().isEmpty()) {
                throw new CompilationException("Name is empty or blank");
            }
            if (compilation.getTitle().length() > 50) {
                throw new CompilationException("Name length not might bigger than 50");
            }
        }
        if (Objects.isNull(compilation.getEvents())) {
            compilation.setEvents(new Integer[0]);
        }
    }

    @Override
    public List<CompilationDto> getAllCompilation(String pinned, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size, Sort.Direction.ASC, "id");
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (Objects.nonNull(pinned)) {
            booleanBuilder.and(QCompilation.compilation.pinned.eq(Boolean.valueOf(pinned)));
        }
        return compilationRepository.findAll(booleanBuilder, pageRequest).get().map(CompilationMapper::toCompilationDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCompilationByID(int id) {
        compilationRepository.deleteById(id);
    }

    @Override
    public CompilationDto getCompilationById(int id) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(id).orElseThrow(() -> new CompilationNotFound("Compilation not found exception")));
    }

    @Override
    public CompilationDto putCompilation(int id, UpdateCompilationRequest updComp) {
        validation(NewCompilationDto.builder().title(updComp.getTitle()).events(updComp.getEvents()).build());
        Compilation compilation = compilationRepository.findById(id).orElseThrow(() -> new CompilationNotFound("Compilation not found"));
        if (Objects.nonNull(updComp.getEvents())) {
            List<Event> list = Arrays.stream(updComp.getEvents()).map(e -> eventRepository.findById(e).orElseThrow(() -> new NotFoundEventException("Event not found"))).collect(Collectors.toList());
            compilation.setEvent(list);
        }

        if (Objects.nonNull(updComp.getPinned())) {
            compilation.setPinned(updComp.getPinned());
        }

        if (Objects.nonNull(updComp.getTitle())) {
            compilation.setTitle(updComp.getTitle());
        }
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }


}
