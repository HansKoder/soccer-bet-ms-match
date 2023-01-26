package com.hans.soccer.bet.msmatch.mapper;

import com.hans.soccer.bet.msmatch.documents.Tournament;
import com.hans.soccer.bet.msmatch.dtos.TournamentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    TournamentDTO tournamentToTournamentDto (Tournament tournament);

    Tournament tournamentDtoToTournament (TournamentDTO tournamentDTO);

}
