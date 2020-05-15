package ro.sdi.lab.core.model.copyadapters;


import org.springframework.stereotype.Component;

import ro.sdi.lab.core.model.Rental;

@Component
public class RentalCopyAdapter implements CopyAdapter<Rental>
{
    @Override
    public void copy(Rental source, Rental destination)
    {
        destination.setId(source.getId());
        destination.setTime(source.getTime());
    }
}
