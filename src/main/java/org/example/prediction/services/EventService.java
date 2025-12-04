package org.example.prediction.services;

import java.util.List;

import org.example.prediction.dto.ShowDetailedEventInfoDto;
import org.example.prediction.dto.ShowEventInfoDto;
import org.example.prediction.dto.form.AddEventDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    
    void finishEvent(Long eventId, Long winningOptionId);
    void createEvent(AddEventDto form);
    List<ShowEventInfoDto> allEvents();
    ShowDetailedEventInfoDto findEventById(Long id);
    void deleteEvent(Long id);
    Page<ShowEventInfoDto> searchEvents(String query, Pageable pageable);
    org.example.prediction.models.entities.User getCurrentUserByUsername(String username);

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