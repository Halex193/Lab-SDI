package ro.sdi.lab.web.converter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Converter<Model, Dto>
{
    Model toModel(Dto dto);

    Dto toDto(Model model);

    default Set<Dto> toDtos(Iterable<Model> models)
    {
        return StreamSupport.stream(models.spliterator(), false)
                            .map(this::toDto)
                            .collect(Collectors.toSet());
    }
}
