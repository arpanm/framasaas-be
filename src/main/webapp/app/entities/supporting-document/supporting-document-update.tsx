import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { getEntities as getArticleWarrantyDetails } from 'app/entities/article-warranty-details/article-warranty-details.reducer';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';
import { createEntity, getEntity, updateEntity } from './supporting-document.reducer';

export const SupportingDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const articleWarrantyDetails = useAppSelector(state => state.articleWarrantyDetails.entities);
  const supportingDocumentEntity = useAppSelector(state => state.supportingDocument.entity);
  const loading = useAppSelector(state => state.supportingDocument.loading);
  const updating = useAppSelector(state => state.supportingDocument.updating);
  const updateSuccess = useAppSelector(state => state.supportingDocument.updateSuccess);
  const documentTypeValues = Object.keys(DocumentType);
  const documentFormatValues = Object.keys(DocumentFormat);

  const handleClose = () => {
    navigate('/supporting-document');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchises({}));
    dispatch(getArticleWarrantyDetails({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.documentSize !== undefined && typeof values.documentSize !== 'number') {
      values.documentSize = Number(values.documentSize);
    }
    values.validatedTime = convertDateTimeToServer(values.validatedTime);
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...supportingDocumentEntity,
      ...values,
      franchise: franchises.find(it => it.id.toString() === values.franchise?.toString()),
      articleWarranty: articleWarrantyDetails.find(it => it.id.toString() === values.articleWarranty?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          validatedTime: displayDefaultDateTime(),
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          documentType: 'AddressProof',
          documentFormat: 'PdfFormat',
          ...supportingDocumentEntity,
          validatedTime: convertDateTimeFromServer(supportingDocumentEntity.validatedTime),
          createdTime: convertDateTimeFromServer(supportingDocumentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(supportingDocumentEntity.updatedTime),
          franchise: supportingDocumentEntity?.franchise?.id,
          articleWarranty: supportingDocumentEntity?.articleWarranty?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.supportingDocument.home.createOrEditLabel" data-cy="SupportingDocumentCreateUpdateHeading">
            <Translate contentKey="framasaasApp.supportingDocument.home.createOrEditLabel">Create or edit a SupportingDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="supporting-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.documentName')}
                id="supporting-document-documentName"
                name="documentName"
                data-cy="documentName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.documentType')}
                id="supporting-document-documentType"
                name="documentType"
                data-cy="documentType"
                type="select"
              >
                {documentTypeValues.map(documentType => (
                  <option value={documentType} key={documentType}>
                    {translate(`framasaasApp.DocumentType.${documentType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.documentFormat')}
                id="supporting-document-documentFormat"
                name="documentFormat"
                data-cy="documentFormat"
                type="select"
              >
                {documentFormatValues.map(documentFormat => (
                  <option value={documentFormat} key={documentFormat}>
                    {translate(`framasaasApp.DocumentFormat.${documentFormat}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.documentSize')}
                id="supporting-document-documentSize"
                name="documentSize"
                data-cy="documentSize"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.documentPath')}
                id="supporting-document-documentPath"
                name="documentPath"
                data-cy="documentPath"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.isValidated')}
                id="supporting-document-isValidated"
                name="isValidated"
                data-cy="isValidated"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.validatedBy')}
                id="supporting-document-validatedBy"
                name="validatedBy"
                data-cy="validatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.validatedTime')}
                id="supporting-document-validatedTime"
                name="validatedTime"
                data-cy="validatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.createddBy')}
                id="supporting-document-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.createdTime')}
                id="supporting-document-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.updatedBy')}
                id="supporting-document-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.supportingDocument.updatedTime')}
                id="supporting-document-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="supporting-document-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.supportingDocument.franchise')}
                type="select"
              >
                <option value="" key="0" />
                {franchises
                  ? franchises.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="supporting-document-articleWarranty"
                name="articleWarranty"
                data-cy="articleWarranty"
                label={translate('framasaasApp.supportingDocument.articleWarranty')}
                type="select"
              >
                <option value="" key="0" />
                {articleWarrantyDetails
                  ? articleWarrantyDetails.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/supporting-document" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SupportingDocumentUpdate;
