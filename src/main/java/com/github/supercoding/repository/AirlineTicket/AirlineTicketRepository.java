package com.github.supercoding.repository.AirlineTicket;

import java.util.List;

public interface AirlineTicketRepository {
    List<AirlineTicket> findAllAirLineTicketsWithPlaceAntTicketType(String likePlace, String ticketType);

    List<AirlineTicketAndFlightInfo> findAllAirLineTicketAndFlightInfo(Integer airlineTicketId);
}
