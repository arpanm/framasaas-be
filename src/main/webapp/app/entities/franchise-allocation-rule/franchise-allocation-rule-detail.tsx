import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-allocation-rule.reducer';

export const FranchiseAllocationRuleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseAllocationRuleEntity = useAppSelector(state => state.franchiseAllocationRule.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseAllocationRuleDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseAllocationRule.detail.title">FranchiseAllocationRule</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleEntity.id}</dd>
          <dt>
            <span id="ruleType">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.ruleType">Rule Type</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleEntity.ruleType}</dd>
          <dt>
            <span id="joinType">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.joinType">Join Type</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleEntity.joinType}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseAllocationRuleEntity.createdTime ? (
              <TextFormat value={franchiseAllocationRuleEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseAllocationRule.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseAllocationRuleEntity.updatedTime ? (
              <TextFormat value={franchiseAllocationRuleEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/franchise-allocation-rule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-allocation-rule/${franchiseAllocationRuleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseAllocationRuleDetail;
