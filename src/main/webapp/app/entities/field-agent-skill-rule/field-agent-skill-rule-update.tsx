import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getFieldAgentSkillRuleSets } from 'app/entities/field-agent-skill-rule-set/field-agent-skill-rule-set.reducer';
import { FieldAgentSkillRuleType } from 'app/shared/model/enumerations/field-agent-skill-rule-type.model';
import { FieldAgentSkillRuleJoinType } from 'app/shared/model/enumerations/field-agent-skill-rule-join-type.model';
import { createEntity, getEntity, updateEntity } from './field-agent-skill-rule.reducer';

export const FieldAgentSkillRuleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldAgentSkillRuleSets = useAppSelector(state => state.fieldAgentSkillRuleSet.entities);
  const fieldAgentSkillRuleEntity = useAppSelector(state => state.fieldAgentSkillRule.entity);
  const loading = useAppSelector(state => state.fieldAgentSkillRule.loading);
  const updating = useAppSelector(state => state.fieldAgentSkillRule.updating);
  const updateSuccess = useAppSelector(state => state.fieldAgentSkillRule.updateSuccess);
  const fieldAgentSkillRuleTypeValues = Object.keys(FieldAgentSkillRuleType);
  const fieldAgentSkillRuleJoinTypeValues = Object.keys(FieldAgentSkillRuleJoinType);

  const handleClose = () => {
    navigate('/field-agent-skill-rule');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFieldAgentSkillRuleSets({}));
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
      ...fieldAgentSkillRuleEntity,
      ...values,
      fieldAgentSkillRuleSet: fieldAgentSkillRuleSets.find(it => it.id.toString() === values.fieldAgentSkillRuleSet?.toString()),
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
          skillType: 'BRANDSKILL',
          joinType: 'ANDJOIN',
          ...fieldAgentSkillRuleEntity,
          createdTime: convertDateTimeFromServer(fieldAgentSkillRuleEntity.createdTime),
          updatedTime: convertDateTimeFromServer(fieldAgentSkillRuleEntity.updatedTime),
          fieldAgentSkillRuleSet: fieldAgentSkillRuleEntity?.fieldAgentSkillRuleSet?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="framasaasApp.fieldAgentSkillRule.home.createOrEditLabel" data-cy="FieldAgentSkillRuleCreateUpdateHeading">
            <Translate contentKey="framasaasApp.fieldAgentSkillRule.home.createOrEditLabel">Create or edit a FieldAgentSkillRule</Translate>
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
                  id="field-agent-skill-rule-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.skillType')}
                id="field-agent-skill-rule-skillType"
                name="skillType"
                data-cy="skillType"
                type="select"
              >
                {fieldAgentSkillRuleTypeValues.map(fieldAgentSkillRuleType => (
                  <option value={fieldAgentSkillRuleType} key={fieldAgentSkillRuleType}>
                    {translate(`framasaasApp.FieldAgentSkillRuleType.${fieldAgentSkillRuleType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.joinType')}
                id="field-agent-skill-rule-joinType"
                name="joinType"
                data-cy="joinType"
                type="select"
              >
                {fieldAgentSkillRuleJoinTypeValues.map(fieldAgentSkillRuleJoinType => (
                  <option value={fieldAgentSkillRuleJoinType} key={fieldAgentSkillRuleJoinType}>
                    {translate(`framasaasApp.FieldAgentSkillRuleJoinType.${fieldAgentSkillRuleJoinType}`)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.createddBy')}
                id="field-agent-skill-rule-createddBy"
                name="createddBy"
                data-cy="createddBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.createdTime')}
                id="field-agent-skill-rule-createdTime"
                name="createdTime"
                data-cy="createdTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.updatedBy')}
                id="field-agent-skill-rule-updatedBy"
                name="updatedBy"
                data-cy="updatedBy"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('framasaasApp.fieldAgentSkillRule.updatedTime')}
                id="field-agent-skill-rule-updatedTime"
                name="updatedTime"
                data-cy="updatedTime"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="field-agent-skill-rule-fieldAgentSkillRuleSet"
                name="fieldAgentSkillRuleSet"
                data-cy="fieldAgentSkillRuleSet"
                label={translate('framasaasApp.fieldAgentSkillRule.fieldAgentSkillRuleSet')}
                type="select"
              >
                <option value="" key="0" />
                {fieldAgentSkillRuleSets
                  ? fieldAgentSkillRuleSets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/field-agent-skill-rule" replace color="info">
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

export default FieldAgentSkillRuleUpdate;
