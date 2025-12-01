package org.example.bet.services;

import java.util.List;

import org.example.bet.dto.ShowDetailedEventInfoDto;
import org.example.bet.dto.ShowEventInfoDto;
import org.example.bet.dto.form.AddEventDto;

public interface EventService {
    void finishEvent(Long eventId, Long winningOptionId);
    void createEvent(AddEventDto form);
    List<ShowEventInfoDto> findAllEvents();
    ShowDetailedEventInfoDto findEventById(Long id);
    void deleteEvent(Long id);
}

/* 
public interface CompanyService {

    void addCompany(AddCompanyDto companyDTO);

    List<ShowCompanyInfoDto> allCompanies();

    Page<ShowCompanyInfoDto> allCompaniesPaginated(Pageable pageable);

    List<ShowCompanyInfoDto> searchCompanies(String searchTerm);

    List<ShowCompanyInfoDto> findByTown(String town);

    List<ShowCompanyInfoDto> findByBudgetGreaterThan(Double minBudget);

    ShowDetailedCompanyInfoDto companyDetails(String companyName);

    void removeCompany(String companyName);
}
*/