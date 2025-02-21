import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchises } from 'app/entities/franchise/franchise.reducer';
import { ServiceCategory } from 'app/shared/model/enumerations/service-category.model';
import { createEntity, getEntity, updateEntity } from './franchise-category-mapping.reducer';

export const FranchiseCategoryMappingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchises = useAppSelector(state => state.franchise.entities);
  const franchiseCategoryMappingEntity = useAppSelector(state => state.franchiseCategoryMapping.entity);
  const loading = useAppSelector(state => state.franchiseCategoryMapping.loading);
  const updating = useAppSelector(state => state.franchiseCategoryMapping.updating);
  const updateSuccess = useAppSelector(state => state.franchiseCategoryMapping.updateSuccess);
  const serviceCategoryValues = Object.keys(ServiceCategory);

  const handleClose = () => {
    navigate('/franchise-category-mapping');
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

    const entity = {
      ...franchiseCategoryMappingEntity,
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
      ? {}
      : {
          serviceCategory: 'AC',
          ...franchiseCategoryMappingEntity,
          franchise: franchiseCategoryMappingEntity?.franchise?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseCategoryMapping.home.createOrEditLabel" data-cy="FranchiseCategoryMappingCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseCategoryMapping.home.createOrEditLabel">
              Create or edit a FranchiseCategoryMapping
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
                  id="franchise-category-mapping-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseCategoryMapping.serviceCategory')}
                id="franchise-category-mapping-serviceCategory"
                name="serviceCategory"
                data-cy="serviceCategory"
                type="select"
              >
                {serviceCategoryValues.map(serviceCategory => (
                  <option value={serviceCategory} key={serviceCategory}>
                    {translate(`framasaasApp.ServiceCategory.${serviceCategory}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="franchise-category-mapping-franchise"
                name="franchise"
                data-cy="franchise"
                label={translate('framasaasApp.franchiseCategoryMapping.franchise')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise-category-mapping" replace color="info">
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

export default FranchiseCategoryMappingUpdate;
