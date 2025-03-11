import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-order-spare.reducer';

export const ServiceOrderSpareDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceOrderSpareEntity = useAppSelector(state => state.serviceOrderSpare.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceOrderSpareDetailsHeading">
          <Translate contentKey="framasaasApp.serviceOrderSpare.detail.title">ServiceOrderSpare</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.id}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="framasaasApp.serviceOrderSpare.price">Price</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.price}</dd>
          <dt>
            <span id="tax">
              <Translate contentKey="framasaasApp.serviceOrderSpare.tax">Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.tax}</dd>
          <dt>
            <span id="totalCharge">
              <Translate contentKey="framasaasApp.serviceOrderSpare.totalCharge">Total Charge</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.totalCharge}</dd>
          <dt>
            <span id="franchiseCommision">
              <Translate contentKey="framasaasApp.serviceOrderSpare.franchiseCommision">Franchise Commision</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.franchiseCommision}</dd>
          <dt>
            <span id="franchiseCommisionTax">
              <Translate contentKey="framasaasApp.serviceOrderSpare.franchiseCommisionTax">Franchise Commision Tax</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.franchiseCommisionTax}</dd>
          <dt>
            <span id="orderedFrom">
              <Translate contentKey="framasaasApp.serviceOrderSpare.orderedFrom">Ordered From</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.orderedFrom}</dd>
          <dt>
            <span id="spareStatus">
              <Translate contentKey="framasaasApp.serviceOrderSpare.spareStatus">Spare Status</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.spareStatus}</dd>
          <dt>
            <span id="createddBy">
              <Translate contentKey="framasaasApp.serviceOrderSpare.createddBy">Createdd By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.createddBy}</dd>
          <dt>
            <span id="createdTime">
              <Translate contentKey="framasaasApp.serviceOrderSpare.createdTime">Created Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderSpareEntity.createdTime ? (
              <TextFormat value={serviceOrderSpareEntity.createdTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedBy">
              <Translate contentKey="framasaasApp.serviceOrderSpare.updatedBy">Updated By</Translate>
            </span>
          </dt>
          <dd>{serviceOrderSpareEntity.updatedBy}</dd>
          <dt>
            <span id="updatedTime">
              <Translate contentKey="framasaasApp.serviceOrderSpare.updatedTime">Updated Time</Translate>
            </span>
          </dt>
          <dd>
            {serviceOrderSpareEntity.updatedTime ? (
              <TextFormat value={serviceOrderSpareEntity.updatedTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderSpare.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{serviceOrderSpareEntity.serviceOrder ? serviceOrderSpareEntity.serviceOrder.id : ''}</dd>
          <dt>
            <Translate contentKey="framasaasApp.serviceOrderSpare.product">Product</Translate>
          </dt>
          <dd>{serviceOrderSpareEntity.product ? serviceOrderSpareEntity.product.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-order-spare" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-order-spare/${serviceOrderSpareEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceOrderSpareDetail;
