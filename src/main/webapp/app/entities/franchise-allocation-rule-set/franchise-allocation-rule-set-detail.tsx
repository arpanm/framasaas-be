import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-allocation-rule-set.reducer';

export const FranchiseAllocationRuleSetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseAllocationRuleSetEntity = useAppSelector(state => state.franchiseAllocationRuleSet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseAllocationRuleSetDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.detail.title">FranchiseAllocationRuleSet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.name">Name</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.name}</dd>
          <dt>
            <span id="sortType">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.sortType">Sort Type</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.sortType}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.priority}</dd>
          <dt>
            <span id="isActive">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.isActive">Is Active</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseAllocationRuleSetEntity.createdTime ? (
              <TextFormat value={franchiseAllocationRuleSetEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseAllocationRuleSetEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseAllocationRuleSet.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseAllocationRuleSetEntity.updatedTime ? (
              <TextFormat value={franchiseAllocationRuleSetEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/franchise-allocation-rule-set" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-allocation-rule-set/${franchiseAllocationRuleSetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseAllocationRuleSetDetail;
