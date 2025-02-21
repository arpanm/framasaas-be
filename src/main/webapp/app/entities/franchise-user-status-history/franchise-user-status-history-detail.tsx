import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise-user-status-history.reducer';

export const FranchiseUserStatusHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseUserStatusHistoryEntity = useAppSelector(state => state.franchiseUserStatusHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseUserStatusHistoryDetailsHeading">
          <Translate contentKey="framasaasApp.franchiseUserStatusHistory.detail.title">FranchiseUserStatusHistory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseUserStatusHistoryEntity.id}</dd>
          <dt>
            <span id="userSatus">
              <Translate contentKey="framasaasApp.franchiseUserStatusHistory.userSatus">User Satus</Translate>
            </span>
          </dt>
          <dd>{franchiseUserStatusHistoryEntity.userSatus}</dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchiseUserStatusHistory.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseUserStatusHistoryEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchiseUserStatusHistory.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseUserStatusHistoryEntity.updatedTime ? (
              <TextFormat value={franchiseUserStatusHistoryEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchiseUserStatusHistory.franchiseUser">Franchise User</Translate>
          </dt>
          <dd>{franchiseUserStatusHistoryEntity.franchiseUser ? franchiseUserStatusHistoryEntity.franchiseUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise-user-status-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise-user-status-history/${franchiseUserStatusHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseUserStatusHistoryDetail;
