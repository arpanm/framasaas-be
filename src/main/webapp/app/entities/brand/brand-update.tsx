import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchiseAllocationRules } from 'app/entities/franchise-allocation-rule/franchise-allocation-rule.reducer';
import { createEntity, getEntity, updateEntity } from './brand.reducer';

export const BrandUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchiseAllocationRules = useAppSelector(state => state.franchiseAllocationRule.entities);
  const brandEntity = useAppSelector(state => state.brand.entity);
  const loading = useAppSelector(state => state.brand.loading);
  const updating = useAppSelector(state => state.brand.updating);
  const updateSuccess = useAppSelector(state => state.brand.updateSuccess);

  const handleClose = () => {
    navigate('/brand');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchiseAllocationRules({}));
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
      ...brandEntity,
      ...values,
      franchiseRule: franchiseAllocationRules.find(it => it.id.toString() === values.franchiseRule?.toString()),
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
          ...brandEntity,
          createdTime: convertDateTimeFromServer(brandEntity.createdTime),
          updatedTime: convertDateTimeFromServer(brandEntity.updatedTime),
          franchiseRule: brandEntity?.franchiseRule?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.brand.home.createOrEditLabel" data-cy="BrandCreateUpdateHeading">
            <Translate contentKey="framasaasApp.brand.home.createOrEditLabel">Create or edit a Brand</Translate>
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
                  id="brand-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.brand.brandName')}
                id="brand-brandName"
                name="brandName"
                data-cy="brandName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.brand.logoPath')}
                id="brand-logoPath"
                name="logoPath"
                data-cy="logoPath"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.brand.vendorBrandId')}
                id="brand-vendorBrandId"
                name="vendorBrandId"
                data-cy="vendorBrandId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.brand.description')}
                id="brand-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.brand.createddBy')}
                id="brand-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.brand.createdTime')}
                id="brand-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.brand.updatedBy')}
                id="brand-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.brand.updatedTime')}
                id="brand-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="brand-franchiseRule"
                name="franchiseRule"
                data-cy="franchiseRule"
                label={translate('framasaasApp.brand.franchiseRule')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseAllocationRules
                  ? franchiseAllocationRules.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/brand" replace color="info">
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

export default BrandUpdate;
