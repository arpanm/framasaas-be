import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './customer.reducer';

export const CustomerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const customerEntity = useAppSelector(state => state.customer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="customerDetailsHeading">
          <Translate contentKey="framasaasApp.customer.detail.title">Customer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{customerEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="framasaasApp.customer.email">Email</Translate>
            </span>
          </dt>
          <dd>{customerEntity.email}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="framasaasApp.customer.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{customerEntity.contact}</dd>
          <dt>
            <span id="alternameContact">
              <Translate contentKey="framasaasApp.customer.alternameContact">Altername Contact</Translate>
            </span>
          </dt>
          <dd>{customerEntity.alternameContact}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="framasaasApp.customer.language">Language</Translate>
            </span>
          </dt>
          <dd>{customerEntity.language}</dd>
          <dt>
            <span id="userStatus">
              <Translate contentKey="framasaasApp.customer.userStatus">User Status</Translate>
            </span>
          </dt>
          <dd>{customerEntity.userStatus}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.customer.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{customerEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.customer.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {customerEntity.createdTime ? <TextFormat value={customerEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.customer.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{customerEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.customer.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {customerEntity.updatedTime ? <TextFormat value={customerEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/customer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/customer/${customerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CustomerDetail;
