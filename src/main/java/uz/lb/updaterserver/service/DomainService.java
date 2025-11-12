package uz.lb.updaterserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.DomainDTO;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.Domain;
import uz.lb.updaterserver.enums.GeneralStatus;
import uz.lb.updaterserver.enums.RoleEnum;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.payload.DomainPayload;
import uz.lb.updaterserver.repository.DomainRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainService {

    private final DomainRepository domainRepository;

    public ResponseEntity<ResultDTO> saveDomain(CustomUserDetails currentUser, DomainPayload domainPayload) {

        if (!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN)) {
            throw new AppForbiddenException("Permission is not available for this Role. role = " + currentUser.getRole());
        }

        Domain domain = domainRepository.save(Domain.builder()
                .domain(domainPayload.getDomain())
                .visible(domainPayload.getVisible() == null ? Boolean.TRUE : domainPayload.getVisible())
                .status(domainPayload.getStatus() == null ? GeneralStatus.ACTIVE : domainPayload.getStatus())
                .createdByUserId(currentUser.getId())
                .build());

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.DomainToDomainDTO(domain)));
    }

    public ResponseEntity<ResultDTO> getAllDomain() {

        List<Domain> domains = domainRepository.findAll();

        if (domains.isEmpty()) {
            throw new AppItemNotFoundException("domain does not exist");
        }

        List<Domain> domainsResult = domains.stream()
                .filter(domain -> Boolean.TRUE.equals(domain.getVisible()) && domain.getStatus() == GeneralStatus.ACTIVE)
                .toList();

        if (domainsResult.isEmpty()) {
            throw new AppItemNotFoundException("no active visible domains found");
        }

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.DomainListToDomainDTOList(domains)));
    }

    public ResponseEntity<ResultDTO> getDomainById(String id) {
        Domain domain = findDomainById(id);
        if (!domain.getStatus().equals(GeneralStatus.ACTIVE) || !domain.getVisible().equals(Boolean.TRUE)) {
            throw new AppItemNotFoundException("no active visible domains found");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.DomainToDomainDTO(domain)));
    }

    public ResponseEntity<ResultDTO> getDomainsByDomain(DomainPayload domainPayload) {

        List<Domain> domains = domainRepository.findDomainsByDomain(domainPayload.getDomain());

        if (domains == null || domains.isEmpty()) {
            throw new AppItemNotFoundException("domain not found with this domain = " + domainPayload.getDomain());
        }

        List<Domain> domainList = domains.stream()
                .filter(domain -> Boolean.TRUE.equals(domain.getVisible()) && domain.getStatus() == GeneralStatus.ACTIVE)
                .toList();

        if (domainList == null || domainList.isEmpty()) {
            throw new AppItemNotFoundException("domain not found with this domain = " + domainPayload.getDomain());
        }

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.DomainListToDomainDTOList(domainList)));
    }

    public ResponseEntity<ResultDTO> getDomainForApplications() {
        Domain domain = domainRepository.findTopByStatusAndVisibleTrueOrderByCreatedAtDesc(GeneralStatus.ACTIVE);
        if (domain == null) {
            throw new AppItemNotFoundException("domain not found");
        }

        return ResponseEntity.ok(
                new ResultDTO().success(
                        new DomainDTO(
                                domain.getId(),
                                domain.getDomain(),
                                domain.getCreatedByUserId() != null ? domain.getCreatedByUserId() : null,
                                domain.getUpdatedByUserId() != null ? domain.getUpdatedByUserId() : null)));
    }

    @Transactional
    public ResponseEntity<ResultDTO> updateDomainById(CustomUserDetails currentUser, String id, DomainPayload domainPayload) {

        if (!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() != id) {
            throw new AppForbiddenException("Permission is not available for this Role. role = " + currentUser.getRole());
        }
        Domain domain = domainRepository.findDomainByDomain(domainPayload.getDomain());
        if (domain != null && domain.getId() != id) {
            throw new AppBadRequestException("Domain with this domain = " + domainPayload.getDomain() + " exists");
        }
        domain = findDomainById(id);
        domain.setDomain(domainPayload.getDomain());
        domain.setVisible(domainPayload.getVisible() == null ? Boolean.TRUE : domainPayload.getVisible());
        domain.setStatus(domainPayload.getStatus() == null ? GeneralStatus.ACTIVE : domainPayload.getStatus());
        domain.setUpdatedByUserId(currentUser.getId());
        domain = domainRepository.save(domain);
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.DomainToDomainDTO(domain)));
    }

    @Transactional
    public ResponseEntity<ResultDTO> deleteDomainById(CustomUserDetails currentUser, String id) {

        if (!currentUser.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() != id) {
            throw new AppForbiddenException("Permission is not available for this Role. role = " + currentUser.getRole());
        }

        Domain domain = findDomainById(id);
        domain.setStatus(GeneralStatus.DELETED);
        domain.setUpdatedByUserId(currentUser.getId());
        domainRepository.save(domain);

        return ResponseEntity.ok(new ResultDTO().success("Domain deleted. domainId = " + id));

    }

    /*********************************************************************************************************************/

    private Domain findDomainById(String domainId) {
        return domainRepository.findById(domainId).orElseThrow(() -> {
            throw new AppItemNotFoundException("domain not found with this id = " + domainId);
        });
    }
}