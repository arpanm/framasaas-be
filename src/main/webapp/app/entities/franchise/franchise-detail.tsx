import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

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
            <Translate contentKey="framasaasApp.franchise.address">Address</Translate>
          </dt>
          <dd>{franchiseEntity.address ? franchiseEntity.address.id : ''}</dd>
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
