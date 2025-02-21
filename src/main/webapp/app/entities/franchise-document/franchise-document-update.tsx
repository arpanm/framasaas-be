import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';
import { DocumentFormat } from 'app/shared/model/enumerations/document-format.model';
import { createEntity, getEntity, updateEntity } from './franchise-document.reducer';

export const FranchiseDocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchiseDocumentEntity = useAppSelector(state => state.franchiseDocument.entity);
  const loading = useAppSelector(state => state.franchiseDocument.loading);
  const updating = useAppSelector(state => state.franchiseDocument.updating);
  const updateSuccess = useAppSelector(state => state.franchiseDocument.updateSuccess);
  const documentTypeValues = Object.keys(DocumentType);
  const documentFormatValues = Object.keys(DocumentFormat);

  const handleClose = () => {
    navigate('/franchise-document');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchises({}));
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseDocumentEntity,
      ...values,
      franchise: franchises.find(it => it.id.toString() === values.franchise?.toString()),
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
          createdTime: displayDefaultDateTime(),
          updatedTime: displayDefaultDateTime(),
        }
      : {
          documentType: 'AddressProof',
          documentFormat: 'PdfFormat',
          ...franchiseDocumentEntity,
          createdTime: convertDateTimeFromServer(franchiseDocumentEntity.createdTime),
          updatedTime: convertDateTimeFromServer(franchiseDocumentEntity.updatedTime),
          franchise: franchiseDocumentEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseDocument.home.createOrEditLabel" data-cy="FranchiseDocumentCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseDocument.home.createOrEditLabel">Create or edit a FranchiseDocument</Translate>
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
                  id="franchise-document-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.documentName')}
                id="franchise-document-documentName"
                name="documentName"
                data-cy="documentName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.documentType')}
                id="franchise-document-documentType"
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
                label={translate('framasaasApp.franchiseDocument.documentFormat')}
                id="franchise-document-documentFormat"
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
                label={translate('framasaasApp.franchiseDocument.documentSize')}
                id="franchise-document-documentSize"
                name="documentSize"
                data-cy="documentSize"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.documentPath')}
                id="franchise-document-documentPath"
                name="documentPath"
                data-cy="documentPath"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.createddBy')}
                id="franchise-document-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.createdTime')}
                id="franchise-document-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.updatedBy')}
                id="franchise-document-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseDocument.updatedTime')}
                id="franchise-document-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-document-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.franchiseDocument.franchise')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise-document" replace color="info">
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

export default FranchiseDocumentUpdate;
