import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { FieldAgentSkillRuleSetSortType } from 'app/shared/model/enumerations/field-agent-skill-rule-set-sort-type.model';
import { createEntity, getEntity, updateEntity } from './field-agent-skill-rule-set.reducer';

export const FieldAgentSkillRuleSetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldAgentSkillRuleSetEntity = useAppSelector(state => state.fieldAgentSkillRuleSet.entity);
  const loading = useAppSelector(state => state.fieldAgentSkillRuleSet.loading);
  const updating = useAppSelector(state => state.fieldAgentSkillRuleSet.updating);
  const updateSuccess = useAppSelector(state => state.fieldAgentSkillRuleSet.updateSuccess);
  const fieldAgentSkillRuleSetSortTypeValues = Object.keys(FieldAgentSkillRuleSetSortType);

  const handleClose = () => {
    navigate('/field-agent-skill-rule-set');
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
      ...fieldAgentSkillRuleSetEntity,
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
          ...fieldAgentSkillRuleSetEntity,
          createdTime: convertDateTimeFromServer(fieldAgentSkillRuleSetEntity.createdTime),
          updatedTime: convertDateTimeFromServer(fieldAgentSkillRuleSetEntity.updatedTime),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.fieldAgentSkillRuleSet.home.createOrEditLabel" data-cy="FieldAgentSkillRuleSetCreateUpdateHeading">
            <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.home.createOrEditLabel">
              Create or edit a FieldAgentSkillRuleSet
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
                  id="field-agent-skill-rule-set-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRuleSet.sortType')}
                id="field-agent-skill-rule-set-sortType"
                name="sortType"
                data-cy="sortType"
                type="select"
              >
                {fieldAgentSkillRuleSetSortTypeValues.map(fieldAgentSkillRuleSetSortType => (
                  <option value={fieldAgentSkillRuleSetSortType} key={fieldAgentSkillRuleSetSortType}>
                    {translate(`framasaasApp.FieldAgentSkillRuleSetSortType.${fieldAgentSkillRuleSetSortType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRuleSet.createddBy')}
                id="field-agent-skill-rule-set-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRuleSet.createdTime')}
                id="field-agent-skill-rule-set-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRuleSet.updatedBy')}
                id="field-agent-skill-rule-set-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRuleSet.updatedTime')}
                id="field-agent-skill-rule-set-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/field-agent-skill-rule-set" replace color="info">
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

export default FieldAgentSkillRuleSetUpdate;
