import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFranchiseAllocationRules } from 'app/entities/franchise-allocation-rule/franchise-allocation-rule.reducer';
import { getEntities as getFieldAgentSkillRules } from 'app/entities/field-agent-skill-rule/field-agent-skill-rule.reducer';
import { createEntity, getEntity, updateEntity } from './pincode.reducer';

export const PincodeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const franchiseAllocationRules = useAppSelector(state => state.franchiseAllocationRule.entities);
  const fieldAgentSkillRules = useAppSelector(state => state.fieldAgentSkillRule.entities);
  const pincodeEntity = useAppSelector(state => state.pincode.entity);
  const loading = useAppSelector(state => state.pincode.loading);
  const updating = useAppSelector(state => state.pincode.updating);
  const updateSuccess = useAppSelector(state => state.pincode.updateSuccess);

  const handleClose = () => {
    navigate('/pincode');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFranchiseAllocationRules({}));
    dispatch(getFieldAgentSkillRules({}));
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
      ...pincodeEntity,
      ...values,
      franchiseRule: franchiseAllocationRules.find(it => it.id.toString() === values.franchiseRule?.toString()),
      fieldAgentSkillRule: fieldAgentSkillRules.find(it => it.id.toString() === values.fieldAgentSkillRule?.toString()),
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
          ...pincodeEntity,
          createdTime: convertDateTimeFromServer(pincodeEntity.createdTime),
          updatedTime: convertDateTimeFromServer(pincodeEntity.updatedTime),
          franchiseRule: pincodeEntity?.franchiseRule?.id,
          fieldAgentSkillRule: pincodeEntity?.fieldAgentSkillRule?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.pincode.home.createOrEditLabel" data-cy="PincodeCreateUpdateHeading">
            <Translate contentKey="framasaasApp.pincode.home.createOrEditLabel">Create or edit a Pincode</Translate>
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
                  id="pincode-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.pincode.pincode')}
                id="pincode-pincode"
                name="pincode"
                data-cy="pincode"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.pincode.createddBy')}
                id="pincode-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.pincode.createdTime')}
                id="pincode-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.pincode.updatedBy')}
                id="pincode-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.pincode.updatedTime')}
                id="pincode-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="pincode-franchiseRule"
                name="franchiseRule"
                data-cy="franchiseRule"
                label={translate('framasaasApp.pincode.franchiseRule')}
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
              <ValidatedField
                id="pincode-fieldAgentSkillRule"
                name="fieldAgentSkillRule"
                data-cy="fieldAgentSkillRule"
                label={translate('framasaasApp.pincode.fieldAgentSkillRule')}
                type="select"
              >
                <option value="" key="0" />
                {fieldAgentSkillRules
                  ? fieldAgentSkillRules.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pincode" replace color="info">
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

export default PincodeUpdate;
