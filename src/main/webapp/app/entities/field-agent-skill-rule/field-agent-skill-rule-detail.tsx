import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-agent-skill-rule.reducer';

export const FieldAgentSkillRuleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldAgentSkillRuleEntity = useAppSelector(state => state.fieldAgentSkillRule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldAgentSkillRuleDetailsHeading">
          <Translate contentKey="framasaasApp.fieldAgentSkillRule.detail.title">FieldAgentSkillRule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.id}</dd>
          <dt>
            <span id="skillType">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.skillType">Skill Type</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.skillType}</dd>
          <dt>
            <span id="joinType">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.joinType">Join Type</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.joinType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldAgentSkillRuleEntity.createdTime ? (
              <TextFormat value={fieldAgentSkillRuleEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.fieldAgentSkillRule.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldAgentSkillRuleEntity.updatedTime ? (
              <TextFormat value={fieldAgentSkillRuleEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.fieldAgentSkillRule.fieldAgentSkillRuleSet">Field Agent Skill Rule Set</Translate>
          </dt>
          <dd>{fieldAgentSkillRuleEntity.fieldAgentSkillRuleSet ? fieldAgentSkillRuleEntity.fieldAgentSkillRuleSet.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/field-agent-skill-rule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-agent-skill-rule/${fieldAgentSkillRuleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldAgentSkillRuleDetail;
