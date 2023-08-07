package com.github.supercoding.service;

import com.github.supercoding.repository.AirlineTicket.AirlineTicket;
import com.github.supercoding.repository.AirlineTicket.AirlineTicketAndFlightInfo;
import com.github.supercoding.repository.AirlineTicket.AirlineTicketRepository;
import com.github.supercoding.repository.passenger.Passenger;
import com.github.supercoding.repository.passenger.PassengerRepository;
import com.github.supercoding.repository.reservations.Reservation;
import com.github.supercoding.repository.reservations.ReservationRepository;
import com.github.supercoding.repository.users.UserEntity;
import com.github.supercoding.repository.users.UserRepository;
import com.github.supercoding.service.exceptions.InvaliddValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirReservationService {

    private final UserRepository userRepository;
    private final AirlineTicketRepository airlineTicketRepository;

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도","왕복"));
        if(!ticketTypeSet.contains(ticketType))
            throw new InvaliddValueException("해당 티켓타입"+ticketType+"은 지원하지 않습니다.");


        UserEntity userEntity = userRepository.findUserById(userId).orElseThrow(() -> new NotFoundException("해당 ID: " + userId +" 유저를 찾을 수 없습니다."));
        String likePlace = userEntity.getLikeTravelPlace();

        List<AirlineTicket> airlineTickets
                = airlineTicketRepository.findAllAirLineTicketsWithPlaceAntTicketType(likePlace,ticketType);

        if(airlineTickets.isEmpty()) throw new NotFoundException("해당"+likePlace+"와 티켓"+ticketType+"에 해당하는 항공편이 없습니다.");

        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());

        return tickets;
    }

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        Passenger passenger = passengerRepository.findPassengerByUserid(userId).orElseThrow(()-> new NotFoundException("요청하신 유저 아이디"+userId+"찾을 수 없습니다."));
        Integer passengerId = passenger.getUserId();

        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfo
                = airlineTicketRepository.findAllAirLineTicketAndFlightInfo(airlineTicketId);


        if(airlineTicketAndFlightInfo.isEmpty()) throw new NotFoundException("아이디"+airlineTicketId+"에 해당하는 항공편과 항공권을 찾을 수 없습니다.");

        Reservation reservation = new Reservation(passengerId,airlineTicketId);
        Boolean isSuccess = false;
        try {
            isSuccess = reservationRepository.saveReservation(reservation);
        }catch (RuntimeException e){
            throw new NotAcceptException("reservation이 등록되는 과정이 거부되엇습니다.");
        }

        List<Integer> prices = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getPrice).collect(Collectors.toList());
        List<Integer> charges = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getCharge).collect(Collectors.toList());
        Integer tax = airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Integer totalPrice =airlineTicketAndFlightInfo.stream().map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return new ReservationResult(prices,charges,tax,totalPrice,isSuccess);
    }
}
