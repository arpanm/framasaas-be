import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FranchiseAllocationRuleSetSortType } from 'app/shared/model/enumerations/franchise-allocation-rule-set-sort-type.model';
import { createEntity, getEntity, updateEntity } from './franchise-allocation-rule-set.reducer';

export const FranchiseAllocationRuleSetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchiseAllocationRuleSetEntity = useAppSelector(state => state.franchiseAllocationRuleSet.entity);
  const loading = useAppSelector(state => state.franchiseAllocationRuleSet.loading);
  const updating = useAppSelector(state => state.franchiseAllocationRuleSet.updating);
  const updateSuccess = useAppSelector(state => state.franchiseAllocationRuleSet.updateSuccess);
  const franchiseAllocationRuleSetSortTypeValues = Object.keys(FranchiseAllocationRuleSetSortType);

  const handleClose = () => {
    navigate('/franchise-allocation-rule-set');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    if (values.priority !== undefined && typeof values.priority !== 'number') {
      values.priority = Number(values.priority);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseAllocationRuleSetEntity,
      ...values,
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
          sortType: 'NPS',
          ...franchiseAllocationRuleSetEntity,
          createdTime: convertDateTimeFromServer(franchiseAllocationRuleSetEntity.createdTime),
          updatedTime: convertDateTimeFromServer(franchiseAllocationRuleSetEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseAllocationRuleSet.home.createOrEditLabel" data-cy="FranchiseAllocationRuleSetCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.home.createOrEditLabel">
              Create or edit a FranchiseAllocationRuleSet
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
                  id="franchise-allocation-rule-set-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.name')}
                id="franchise-allocation-rule-set-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.sortType')}
                id="franchise-allocation-rule-set-sortType"
                name="sortType"
                data-cy="sortType"
                type="select"
              >
                {franchiseAllocationRuleSetSortTypeValues.map(franchiseAllocationRuleSetSortType => (
                  <option value={franchiseAllocationRuleSetSortType} key={franchiseAllocationRuleSetSortType}>
                    {translate(`framasaasApp.FranchiseAllocationRuleSetSortType.${franchiseAllocationRuleSetSortType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.priority')}
                id="franchise-allocation-rule-set-priority"
                name="priority"
                data-cy="priority"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.isActive')}
                id="franchise-allocation-rule-set-isActive"
                name="isActive"
                data-cy="isActive"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.createddBy')}
                id="franchise-allocation-rule-set-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.createdTime')}
                id="franchise-allocation-rule-set-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.updatedBy')}
                id="franchise-allocation-rule-set-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRuleSet.updatedTime')}
                id="franchise-allocation-rule-set-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/franchise-allocation-rule-set"
                replace
                color="info"
              >
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

export default FranchiseAllocationRuleSetUpdate;
