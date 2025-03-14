import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './franchise.reducer';

export const FranchiseDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const franchiseEntity = useAppSelector(state => state.franchise.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="franchiseDetailsHeading">
          <Translate contentKey="framasaasApp.franchise.detail.title">Franchise</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.id}</dd>
          <dt>
            <span id="franchiseName">
              <Translate contentKey="framasaasApp.franchise.franchiseName">Franchise Name</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.franchiseName}</dd>
          <dt>
            <span id="owner">
              <Translate contentKey="framasaasApp.franchise.owner">Owner</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.owner}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="framasaasApp.franchise.email">Email</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.email}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="framasaasApp.franchise.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.contact}</dd>
          <dt>
            <span id="franchiseStatus">
              <Translate contentKey="framasaasApp.franchise.franchiseStatus">Franchise Status</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.franchiseStatus}</dd>
          <dt>
            <span id="gstNumber">
              <Translate contentKey="framasaasApp.franchise.gstNumber">Gst Number</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.gstNumber}</dd>
          <dt>
            <span id="registrationNumber">
              <Translate contentKey="framasaasApp.franchise.registrationNumber">Registration Number</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.registrationNumber}</dd>
          <dt>
            <span id="performanceScore">
              <Translate contentKey="framasaasApp.franchise.performanceScore">Performance Score</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.performanceScore}</dd>
          <dt>
            <span id="performanceTag">
              <Translate contentKey="framasaasApp.franchise.performanceTag">Performance Tag</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.performanceTag}</dd>
          <dt>
            <span id="dailyMaxServiceLimit">
              <Translate contentKey="framasaasApp.franchise.dailyMaxServiceLimit">Daily Max Service Limit</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.dailyMaxServiceLimit}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.franchise.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.franchise.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseEntity.createdTime ? <TextFormat value={franchiseEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.franchise.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{franchiseEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.franchise.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {franchiseEntity.updatedTime ? <TextFormat value={franchiseEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.franchise.address">Address</Translate>
          </dt>
          <dd>{franchiseEntity.address ? franchiseEntity.address.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.franchise.ruleset">Ruleset</Translate>
          </dt>
          <dd>{franchiseEntity.ruleset ? franchiseEntity.ruleset.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/franchise" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/franchise/${franchiseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FranchiseDetail;
