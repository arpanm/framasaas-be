import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-status-history.reducer';

export const FranchiseStatusHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseStatusHistoryEntity = useAppSelector(state => state.franchiseStatusHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseStatusHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseStatusHistory.detail.title">FranchiseStatusHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseStatusHistoryEntity.id}</dd>
          <dt>
            <span id="franchiseSatus">
              <Translate contentKey="framasaasApp.franchiseStatusHistory.franchiseSatus">Franchise Satus</Translate>
            </span>
          </dt>
          <dd>{franchiseStatusHistoryEntity.franchiseSatus}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseStatusHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseStatusHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseStatusHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseStatusHistoryEntity.updatedTime ? (
              <TextFormat value={franchiseStatusHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseStatusHistory.franchise">Franchise</Translate>
          </dt>
          <dd>{franchiseStatusHistoryEntity.franchise ? franchiseStatusHistoryEntity.franchise.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-status-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-status-history/${franchiseStatusHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseStatusHistoryDetail;
