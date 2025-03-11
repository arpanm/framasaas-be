import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FranchiseAllocationRuleType } from 'app/shared/model/enumerations/franchise-allocation-rule-type.model';
import { FranchiseAllocationRuleJoinType } from 'app/shared/model/enumerations/franchise-allocation-rule-join-type.model';
import { createEntity, getEntity, updateEntity } from './franchise-allocation-rule.reducer';

export const FranchiseAllocationRuleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchiseAllocationRuleEntity = useAppSelector(state => state.franchiseAllocationRule.entity);
  const loading = useAppSelector(state => state.franchiseAllocationRule.loading);
  const updating = useAppSelector(state => state.franchiseAllocationRule.updating);
  const updateSuccess = useAppSelector(state => state.franchiseAllocationRule.updateSuccess);
  const franchiseAllocationRuleTypeValues = Object.keys(FranchiseAllocationRuleType);
  const franchiseAllocationRuleJoinTypeValues = Object.keys(FranchiseAllocationRuleJoinType);

  const handleClose = () => {
    navigate('/franchise-allocation-rule');
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
    values.createdTime = convertDateTimeToServer(values.createdTime);
    values.updatedTime = convertDateTimeToServer(values.updatedTime);

    const entity = {
      ...franchiseAllocationRuleEntity,
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
          ruleType: 'BRANDRULE',
          joinType: 'ANDJOIN',
          ...franchiseAllocationRuleEntity,
          createdTime: convertDateTimeFromServer(franchiseAllocationRuleEntity.createdTime),
          updatedTime: convertDateTimeFromServer(franchiseAllocationRuleEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.franchiseAllocationRule.home.createOrEditLabel" data-cy="FranchiseAllocationRuleCreateUpdateHeading">
            <Translate contentKey="framasaasApp.franchiseAllocationRule.home.createOrEditLabel">
              Create or edit a FranchiseAllocationRule
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
                  id="franchise-allocation-rule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.ruleType')}
                id="franchise-allocation-rule-ruleType"
                name="ruleType"
                data-cy="ruleType"
                type="select"
              >
                {franchiseAllocationRuleTypeValues.map(franchiseAllocationRuleType => (
                  <option value={franchiseAllocationRuleType} key={franchiseAllocationRuleType}>
                    {translate(`framasaasApp.FranchiseAllocationRuleType.${franchiseAllocationRuleType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.joinType')}
                id="franchise-allocation-rule-joinType"
                name="joinType"
                data-cy="joinType"
                type="select"
              >
                {franchiseAllocationRuleJoinTypeValues.map(franchiseAllocationRuleJoinType => (
                  <option value={franchiseAllocationRuleJoinType} key={franchiseAllocationRuleJoinType}>
                    {translate(`framasaasApp.FranchiseAllocationRuleJoinType.${franchiseAllocationRuleJoinType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.createddBy')}
                id="franchise-allocation-rule-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.createdTime')}
                id="franchise-allocation-rule-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.updatedBy')}
                id="franchise-allocation-rule-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.franchiseAllocationRule.updatedTime')}
                id="franchise-allocation-rule-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/franchise-allocation-rule" replace color="info">
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

export default FranchiseAllocationRuleUpdate;
