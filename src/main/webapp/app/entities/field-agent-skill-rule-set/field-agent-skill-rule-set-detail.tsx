import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-agent-skill-rule-set.reducer';

export const FieldAgentSkillRuleSetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldAgentSkillRuleSetEntity = useAppSelector(state => state.fieldAgentSkillRuleSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldAgentSkillRuleSetDetailsHeading">
          <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.detail.title">FieldAgentSkillRuleSet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleSetEntity.id}</dd>
          <dt>
            <span id="sortType">
              <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.sortType">Sort Type</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleSetEntity.sortType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleSetEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldAgentSkillRuleSetEntity.createdTime ? (
              <TextFormat value={fieldAgentSkillRuleSetEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{fieldAgentSkillRuleSetEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.fieldAgentSkillRuleSet.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {fieldAgentSkillRuleSetEntity.updatedTime ? (
              <TextFormat value={fieldAgentSkillRuleSetEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/field-agent-skill-rule-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-agent-skill-rule-set/${fieldAgentSkillRuleSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldAgentSkillRuleSetDetail;
