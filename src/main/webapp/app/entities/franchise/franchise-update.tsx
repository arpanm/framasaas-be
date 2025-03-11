import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { getEntities as getFranchiseAllocationRuleSets } from 'app/entities/franchise-allocation-rule-set/franchise-allocation-rule-set.reducer';
import { FranchiseStatus } from 'app/shared/model/enumerations/franchise-status.model';
import { PerformanceTag } from 'app/shared/model/enumerations/performance-tag.model';
import { createEntity, getEntity, updateEntity } from './franchise.reducer';

export const FranchiseUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const addresses = useAppSelector(state => state.address.entities);
  const franchiseAllocationRuleSets = useAppSelector(state => state.franchiseAllocationRuleSet.entities);
  const franchiseEntity = useAppSelector(state => state.franchise.entity);
  const loading = useAppSelector(state => state.franchise.loading);
  const updating = useAppSelector(state => state.franchise.updating);
  const updateSuccess = useAppSelector(state => state.franchise.updateSuccess);
  const franchiseStatusValues = Object.keys(FranchiseStatus);
  const performanceTagValues = Object.keys(PerformanceTag);

  const handleClose = () => {
    navigate('/franchise');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getAddresses({}));
    dispatch(getFranchiseAllocationRuleSets({}));
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
    if (values.contact !== undefined && typeof values.contact !== 'number') {
      values.contact = Number(values.contact);
    }
    if (values.performanceScore !== undefined && typeof values.performanceScore !== 'number') {
      values.performanceScore = Number(values.performanceScore);
    }
    if (values.dailyMaxServiceLimit !== undefined && typeof values.dailyMaxServiceLimit !== 'number') {
      values.dailyMaxServiceLimit = Number(values.dailyMaxServiceLimit);
    }
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseEntity,
      ...values,
      address: addresses.find(it => it.id.toString() === values.address?.toString()),
      ruleset: franchiseAllocationRuleSets.find(it => it.id.toString() === values.ruleset?.toString()),
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
          franchiseStatus: 'PendingApproval',
          performanceTag: 'High',
          ...franchiseEntity,
          createdTime: convertDateTimeFromServer(franchiseEntity.createdTime),
          updatedTime: convertDateTimeFromServer(franchiseEntity.updatedTime),
          address: franchiseEntity?.address?.id,
          ruleset: franchiseEntity?.ruleset?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchise.home.createOrEditLabel" data-cy="FranchiseCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchise.home.createOrEditLabel">Create or edit a Franchise</Translate>
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
                  id="franchise-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchise.franchiseName')}
                id="franchise-franchiseName"
                name="franchiseName"
                data-cy="franchiseName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.owner')}
                id="franchise-owner"
                name="owner"
                data-cy="owner"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.email')}
                id="franchise-email"
                name="email"
                data-cy="email"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  pattern: {
                    value: /^[^@\s]+@[^@\s]+\.[^@\s]+$/,
                    message: translate('entity.validation.pattern', { pattern: '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$' }),
                  },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.contact')}
                id="franchise-contact"
                name="contact"
                data-cy="contact"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 1000000000, message: translate('entity.validation.min', { min: 1000000000 }) },
                  max: { value: 9999999999, message: translate('entity.validation.max', { max: 9999999999 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.franchiseStatus')}
                id="franchise-franchiseStatus"
                name="franchiseStatus"
                data-cy="franchiseStatus"
                type="select"
              >
                {franchiseStatusValues.map(franchiseStatus => (
                  <option value={franchiseStatus} key={franchiseStatus}>
                    {translate(`framasaasApp.FranchiseStatus.${franchiseStatus}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchise.gstNumber')}
                id="franchise-gstNumber"
                name="gstNumber"
                data-cy="gstNumber"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.registrationNumber')}
                id="franchise-registrationNumber"
                name="registrationNumber"
                data-cy="registrationNumber"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.performanceScore')}
                id="franchise-performanceScore"
                name="performanceScore"
                data-cy="performanceScore"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.performanceTag')}
                id="franchise-performanceTag"
                name="performanceTag"
                data-cy="performanceTag"
                type="select"
              >
                {performanceTagValues.map(performanceTag => (
                  <option value={performanceTag} key={performanceTag}>
                    {translate(`framasaasApp.PerformanceTag.${performanceTag}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchise.dailyMaxServiceLimit')}
                id="franchise-dailyMaxServiceLimit"
                name="dailyMaxServiceLimit"
                data-cy="dailyMaxServiceLimit"
                type="text"
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.createddBy')}
                id="franchise-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.createdTime')}
                id="franchise-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.updatedBy')}
                id="franchise-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchise.updatedTime')}
                id="franchise-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="franchise-address"
                name="address"
                data-cy="address"
                label={translate('framasaasApp.franchise.address')}
                type="select"
              >
                <option value="" key="0" />
                {addresses
                  ? addresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="franchise-ruleset"
                name="ruleset"
                data-cy="ruleset"
                label={translate('framasaasApp.franchise.ruleset')}
                type="select"
              >
                <option value="" key="0" />
                {franchiseAllocationRuleSets
                  ? franchiseAllocationRuleSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise" replace color="info">
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

export default FranchiseUpdate;
