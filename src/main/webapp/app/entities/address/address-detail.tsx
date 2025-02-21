import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './address.reducer';

export const AddressDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="framasaasApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="address1">
              <Translate contentKey="framasaasApp.address.address1">Address 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.address1}</dd>
          <dt>
            <span id="address2">
              <Translate contentKey="framasaasApp.address.address2">Address 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.address2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="framasaasApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="framasaasApp.address.area">Area</Translate>
            </span>
          </dt>
          <dd>{addressEntity.area}</dd>
          <dt>
            <span id="district">
              <Translate contentKey="framasaasApp.address.district">District</Translate>
            </span>
          </dt>
          <dd>{addressEntity.district}</dd>
          <dt>
            <span id="pincode">
              <Translate contentKey="framasaasApp.address.pincode">Pincode</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pincode}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="framasaasApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="framasaasApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.address.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.address.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.createdTime ? <TextFormat value={addressEntity.createdTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.address.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{addressEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.address.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {addressEntity.updatedTime ? <TextFormat value={addressEntity.updatedTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.address.location">Location</Translate>
          </dt>
          <dd>{addressEntity.location ? addressEntity.location.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;
