import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-performance-history.reducer';

export const FranchisePerformanceHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchisePerformanceHistoryEntity = useAppSelector(state => state.franchisePerformanceHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchisePerformanceHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.franchisePerformanceHistory.detail.title">FranchisePerformanceHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.id}</dd>
          <dt>
            <span id="performanceScore">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.performanceScore">Performance Score</Translate>
            </span>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.performanceScore}</dd>
          <dt>
            <span id="performanceTag">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.performanceTag">Performance Tag</Translate>
            </span>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.performanceTag}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchisePerformanceHistoryEntity.updatedTime ? (
              <TextFormat value={franchisePerformanceHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchisePerformanceHistory.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchisePerformanceHistoryEntity.createdTime ? (
              <TextFormat value={franchisePerformanceHistoryEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchisePerformanceHistory.franchise">Franchise</Translate>
          </dt>
          <dd>{franchisePerformanceHistoryEntity.franchise ? franchisePerformanceHistoryEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-performance-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-performance-history/${franchisePerformanceHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchisePerformanceHistoryDetail;
