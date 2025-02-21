import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { Brand } from 'app/shared/model/enumerations/brand.model';
import { createEntity, getEntity, updateEntity } from './franchise-brand-mapping.reducer';

export const FranchiseBrandMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchiseBrandMappingEntity = useAppSelector(state => state.franchiseBrandMapping.entity);
  const loading = useAppSelector(state => state.franchiseBrandMapping.loading);
  const updating = useAppSelector(state => state.franchiseBrandMapping.updating);
  const updateSuccess = useAppSelector(state => state.franchiseBrandMapping.updateSuccess);
  const brandValues = Object.keys(Brand);

  const handleClose = () => {
    navigate('/franchise-brand-mapping');
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseBrandMappingEntity,
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
          brand: 'Samsung',
          ...franchiseBrandMappingEntity,
          createdTime: convertDateTimeFromServer(franchiseBrandMappingEntity.createdTime),
          updatedTime: convertDateTimeFromServer(franchiseBrandMappingEntity.updatedTime),
          franchise: franchiseBrandMappingEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseBrandMapping.home.createOrEditLabel" data-cy="FranchiseBrandMappingCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseBrandMapping.home.createOrEditLabel">
              Create or edit a FranchiseBrandMapping
            </Translate>
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
                  id="franchise-brand-mapping-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseBrandMapping.brand')}
                id="franchise-brand-mapping-brand"
                name="brand"
                data-cy="brand"
                type="select"
              >
                {brandValues.map(brand => (
                  <option value={brand} key={brand}>
                    {translate(`framasaasApp.Brand.${brand}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseBrandMapping.createddBy')}
                id="franchise-brand-mapping-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseBrandMapping.createdTime')}
                id="franchise-brand-mapping-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseBrandMapping.updatedBy')}
                id="franchise-brand-mapping-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseBrandMapping.updatedTime')}
                id="franchise-brand-mapping-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-brand-mapping-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.franchiseBrandMapping.franchise')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise-brand-mapping" replace color="info">
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

export default FranchiseBrandMappingUpdate;
