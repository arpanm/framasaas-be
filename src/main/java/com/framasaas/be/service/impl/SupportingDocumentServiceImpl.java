package com.framasaas.be.service.impl;

import com.framasaas.be.domain.SupportingDocument;
import com.framasaas.be.repository.SupportingDocumentRepository;
import com.framasaas.be.service.SupportingDocumentService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.be.domain.SupportingDocument}.
 */
@Service
@Transactional
public class SupportingDocumentServiceImpl implements SupportingDocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocumentServiceImpl.class);

    private final SupportingDocumentRepository supportingDocumentRepository;

    public SupportingDocumentServiceImpl(SupportingDocumentRepository supportingDocumentRepository) {
        this.supportingDocumentRepository = supportingDocumentRepository;
    }

    @Override
    public SupportingDocument save(SupportingDocument supportingDocument) {
        LOG.debug("Request to save SupportingDocument : {}", supportingDocument);
        return supportingDocumentRepository.save(supportingDocument);
    }

    @Override
    public SupportingDocument update(SupportingDocument supportingDocument) {
        LOG.debug("Request to update SupportingDocument : {}", supportingDocument);
        return supportingDocumentRepository.save(supportingDocument);
    }

    @Override
    public Optional<SupportingDocument> partialUpdate(SupportingDocument supportingDocument) {
        LOG.debug("Request to partially update SupportingDocument : {}", supportingDocument);

        return supportingDocumentRepository
            .findById(supportingDocument.getId())
            .map(existingSupportingDocument -> {
                if (supportingDocument.getDocumentName() != null) {
                    existingSupportingDocument.setDocumentName(supportingDocument.getDocumentName());
                }
                if (supportingDocument.getDocumentType() != null) {
                    existingSupportingDocument.setDocumentType(supportingDocument.getDocumentType());
                }
                if (supportingDocument.getDocumentFormat() != null) {
                    existingSupportingDocument.setDocumentFormat(supportingDocument.getDocumentFormat());
                }
                if (supportingDocument.getDocumentSize() != null) {
                    existingSupportingDocument.setDocumentSize(supportingDocument.getDocumentSize());
                }
                if (supportingDocument.getDocumentPath() != null) {
                    existingSupportingDocument.setDocumentPath(supportingDocument.getDocumentPath());
                }
                if (supportingDocument.getIsValidated() != null) {
                    existingSupportingDocument.setIsValidated(supportingDocument.getIsValidated());
                }
                if (supportingDocument.getValidatedBy() != null) {
                    existingSupportingDocument.setValidatedBy(supportingDocument.getValidatedBy());
                }
                if (supportingDocument.getValidatedTime() != null) {
                    existingSupportingDocument.setValidatedTime(supportingDocument.getValidatedTime());
                }
                if (supportingDocument.getCreateddBy() != null) {
                    existingSupportingDocument.setCreateddBy(supportingDocument.getCreateddBy());
                }
                if (supportingDocument.getCreatedTime() != null) {
                    existingSupportingDocument.setCreatedTime(supportingDocument.getCreatedTime());
                }
                if (supportingDocument.getUpdatedBy() != null) {
                    existingSupportingDocument.setUpdatedBy(supportingDocument.getUpdatedBy());
                }
                if (supportingDocument.getUpdatedTime() != null) {
                    existingSupportingDocument.setUpdatedTime(supportingDocument.getUpdatedTime());
                }

                return existingSupportingDocument;
            })
            .map(supportingDocumentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupportingDocument> findAll(Pageable pageable) {
        LOG.debug("Request to get all SupportingDocuments");
        return supportingDocumentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportingDocument> findOne(Long id) {
        LOG.debug("Request to get SupportingDocument : {}", id);
        return supportingDocumentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SupportingDocument : {}", id);
        supportingDocumentRepository.deleteById(id);
    }
}
