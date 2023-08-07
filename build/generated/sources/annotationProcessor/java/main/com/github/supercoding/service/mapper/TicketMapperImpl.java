package com.github.supercoding.service.mapper;

import com.github.supercoding.repository.AirlineTicket.AirlineTicket;
import com.github.supercoding.web.dto.airline.Ticket;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-06T21:53:25+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.11 (AdoptOpenJDK)"
)
public class TicketMapperImpl implements TicketMapper {

    @Override
    public Ticket airlineTicketToTicket(AirlineTicket airelineTicket) {
        if ( airelineTicket == null ) {
            return null;
        }

        Ticket ticket = new Ticket();

        ticket.setDepart( airelineTicket.getDepartureLocation() );
        ticket.setArrival( airelineTicket.getArrivalLocation() );
        ticket.setDepartureTime( TicketMapper.localDateTimeToString( airelineTicket.getDepartureAt() ) );
        ticket.setReturnTime( TicketMapper.localDateTimeToString( airelineTicket.getReturnAt() ) );
        ticket.setTicketId( airelineTicket.getTicketId() );

        return ticket;
    }
}
