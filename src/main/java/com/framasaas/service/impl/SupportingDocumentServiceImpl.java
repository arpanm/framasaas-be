package com.framasaas.service.impl;

import com.framasaas.domain.SupportingDocument;
import com.framasaas.repository.SupportingDocumentRepository;
import com.framasaas.service.SupportingDocumentService;
import com.framasaas.service.dto.SupportingDocumentDTO;
import com.framasaas.service.mapper.SupportingDocumentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.framasaas.domain.SupportingDocument}.
 */
@Service
@Transactional
public class SupportingDocumentServiceImpl implements SupportingDocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocumentServiceImpl.class);

    private final SupportingDocumentRepository supportingDocumentRepository;

    private final SupportingDocumentMapper supportingDocumentMapper;

    public SupportingDocumentServiceImpl(
        SupportingDocumentRepository supportingDocumentRepository,
        SupportingDocumentMapper supportingDocumentMapper
    ) {
        this.supportingDocumentRepository = supportingDocumentRepository;
        this.supportingDocumentMapper = supportingDocumentMapper;
    }

    @Override
    public SupportingDocumentDTO save(SupportingDocumentDTO supportingDocumentDTO) {
        LOG.debug("Request to save SupportingDocument : {}", supportingDocumentDTO);
        SupportingDocument supportingDocument = supportingDocumentMapper.toEntity(supportingDocumentDTO);
        supportingDocument = supportingDocumentRepository.save(supportingDocument);
        return supportingDocumentMapper.toDto(supportingDocument);
    }

    @Override
    public SupportingDocumentDTO update(SupportingDocumentDTO supportingDocumentDTO) {
        LOG.debug("Request to update SupportingDocument : {}", supportingDocumentDTO);
        SupportingDocument supportingDocument = supportingDocumentMapper.toEntity(supportingDocumentDTO);
        supportingDocument = supportingDocumentRepository.save(supportingDocument);
        return supportingDocumentMapper.toDto(supportingDocument);
    }

    @Override
    public Optional<SupportingDocumentDTO> partialUpdate(SupportingDocumentDTO supportingDocumentDTO) {
        LOG.debug("Request to partially update SupportingDocument : {}", supportingDocumentDTO);

        return supportingDocumentRepository
            .findById(supportingDocumentDTO.getId())
            .map(existingSupportingDocument -> {
                supportingDocumentMapper.partialUpdate(existingSupportingDocument, supportingDocumentDTO);

                return existingSupportingDocument;
            })
            .map(supportingDocumentRepository::save)
            .map(supportingDocumentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SupportingDocumentDTO> findOne(Long id) {
        LOG.debug("Request to get SupportingDocument : {}", id);
        return supportingDocumentRepository.findById(id).map(supportingDocumentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SupportingDocument : {}", id);
        supportingDocumentRepository.deleteById(id);
    }
}
