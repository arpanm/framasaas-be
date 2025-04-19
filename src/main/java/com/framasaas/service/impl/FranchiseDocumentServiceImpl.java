package com.framasaas.service.impl;

import com.framasaas.domain.FranchiseDocument;
import com.framasaas.repository.FranchiseDocumentRepository;
import com.framasaas.service.FranchiseDocumentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.FranchiseDocument}.
 */
@Service
@Transactional
public class FranchiseDocumentServiceImpl implements FranchiseDocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(FranchiseDocumentServiceImpl.class);

    private final FranchiseDocumentRepository franchiseDocumentRepository;

    public FranchiseDocumentServiceImpl(FranchiseDocumentRepository franchiseDocumentRepository) {
        this.franchiseDocumentRepository = franchiseDocumentRepository;
    }

    @Override
    public FranchiseDocument save(FranchiseDocument franchiseDocument) {
        LOG.debug("Request to save FranchiseDocument : {}", franchiseDocument);
        return franchiseDocumentRepository.save(franchiseDocument);
    }

    @Override
    public FranchiseDocument update(FranchiseDocument franchiseDocument) {
        LOG.debug("Request to update FranchiseDocument : {}", franchiseDocument);
        return franchiseDocumentRepository.save(franchiseDocument);
    }

    @Override
    public Optional<FranchiseDocument> partialUpdate(FranchiseDocument franchiseDocument) {
        LOG.debug("Request to partially update FranchiseDocument : {}", franchiseDocument);

        return franchiseDocumentRepository
            .findById(franchiseDocument.getId())
            .map(existingFranchiseDocument -> {
                if (franchiseDocument.getDocumentName() != null) {
                    existingFranchiseDocument.setDocumentName(franchiseDocument.getDocumentName());
                }
                if (franchiseDocument.getDocumentType() != null) {
                    existingFranchiseDocument.setDocumentType(franchiseDocument.getDocumentType());
                }
                if (franchiseDocument.getDocumentFormat() != null) {
                    existingFranchiseDocument.setDocumentFormat(franchiseDocument.getDocumentFormat());
                }
                if (franchiseDocument.getDocumentSize() != null) {
                    existingFranchiseDocument.setDocumentSize(franchiseDocument.getDocumentSize());
                }
                if (franchiseDocument.getDocumentPath() != null) {
                    existingFranchiseDocument.setDocumentPath(franchiseDocument.getDocumentPath());
                }
                if (franchiseDocument.getIsValidated() != null) {
                    existingFranchiseDocument.setIsValidated(franchiseDocument.getIsValidated());
                }
                if (franchiseDocument.getValidatedBy() != null) {
                    existingFranchiseDocument.setValidatedBy(franchiseDocument.getValidatedBy());
                }
                if (franchiseDocument.getValidatedTime() != null) {
                    existingFranchiseDocument.setValidatedTime(franchiseDocument.getValidatedTime());
                }
                if (franchiseDocument.getCreateddBy() != null) {
                    existingFranchiseDocument.setCreateddBy(franchiseDocument.getCreateddBy());
                }
                if (franchiseDocument.getCreatedTime() != null) {
                    existingFranchiseDocument.setCreatedTime(franchiseDocument.getCreatedTime());
                }
                if (franchiseDocument.getUpdatedBy() != null) {
                    existingFranchiseDocument.setUpdatedBy(franchiseDocument.getUpdatedBy());
                }
                if (franchiseDocument.getUpdatedTime() != null) {
                    existingFranchiseDocument.setUpdatedTime(franchiseDocument.getUpdatedTime());
                }

                return existingFranchiseDocument;
            })
            .map(franchiseDocumentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FranchiseDocument> findAll(Pageable pageable) {
        LOG.debug("Request to get all FranchiseDocuments");
        return franchiseDocumentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FranchiseDocument> findOne(Long id) {
        LOG.debug("Request to get FranchiseDocument : {}", id);
        return franchiseDocumentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FranchiseDocument : {}", id);
        franchiseDocumentRepository.deleteById(id);
    }
}
